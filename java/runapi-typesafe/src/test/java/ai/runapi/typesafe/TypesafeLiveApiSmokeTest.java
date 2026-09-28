    package ai.runapi.typesafe;

    import static org.junit.jupiter.api.Assertions.assertEquals;
    import static org.junit.jupiter.api.Assertions.assertNotNull;
    import static org.junit.jupiter.api.Assumptions.assumeTrue;

        import ai.runapi.core.errors.TaskFailedException;
    import ai.runapi.core.RequestOptions;
    import ai.runapi.core.json.Json;
    import ai.runapi.typesafe.types.SystemOneResponse;
    import ai.runapi.typesafe.types.SystemOneModel;
import ai.runapi.typesafe.types.SystemOneParams;
import ai.runapi.typesafe.types.SystemOneResponse;
    import com.fasterxml.jackson.databind.node.ObjectNode;
    import java.nio.charset.StandardCharsets;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.time.Duration;
    import org.junit.jupiter.api.Test;

    class TypesafeLiveApiSmokeTest {
      @Test
      void primaryResourceRunAgainstLiveRunApi() throws Exception {
        assumeTrue("true".equals(System.getenv("RUNAPI_JAVA_LIVE_TYPESAFE_SMOKE")));

        String baseUrl = requireEnv("RUNAPI_BASE_URL");
        String apiKey = requireEnv("RUNAPI_API_KEY");

        Path outputPath = Paths.get(System.getenv().getOrDefault("RUNAPI_JAVA_LIVE_TYPESAFE_OUTPUT", "build/live-typesafe-smoke-result.json"));
        Files.createDirectories(outputPath.getParent());
        try (TypesafeClient client = TypesafeClient.builder().apiKey(apiKey).baseUrl(baseUrl).build()) {
          ObjectNode result = Json.mapper().createObjectNode();
          result.put("action", "typesafe/system-one");
          result.put("result_field", "answers");

          try {
      SystemOneResponse response = client.systemOne().run(
              SystemOneParams.builder()
                  .state(java.util.Collections.singletonMap("candidate", "Option A"))
                  .model("jev-latest")
                  .questions(TypesafeClientTest.questions())
                  .build()
      );


            com.fasterxml.jackson.databind.JsonNode answer = Json.mapper().valueToTree(response.getAnswers().get("recommendation"));
            assertEquals("choice", answer.path("type").asText());
            assertEquals("Option A", answer.path("choice").asText());
            assertNotNull(answer.get("probabilities"));
            assertNotNull(answer.get("confidence"));
            result.set("answers", Json.mapper().valueToTree(response.getAnswers()));
            result.set("model", response.extraFields().get("model"));
            result.set("usage", response.extraFields().get("usage"));
            result.put("status", "sync");
            Files.write(outputPath, Json.mapper().writerWithDefaultPrettyPrinter().writeValueAsString(result).getBytes(StandardCharsets.UTF_8));
          } catch (TaskFailedException failure) {
            result.put("status", "failed");
            result.put("exception", failure.getClass().getSimpleName());
            result.put("message", failure.getMessage());

            Files.write(outputPath, Json.mapper().writerWithDefaultPrettyPrinter().writeValueAsString(result).getBytes(StandardCharsets.UTF_8));
            throw failure;
          } catch (RuntimeException failure) {
            result.put("status", "error");
            result.put("exception", failure.getClass().getSimpleName());
            result.put("message", failure.getMessage());
            Files.write(outputPath, Json.mapper().writerWithDefaultPrettyPrinter().writeValueAsString(result).getBytes(StandardCharsets.UTF_8));
            throw failure;
          }
        }
      }

      private static String callbackUrl(String modelSlug) {
        String base = requireEnv("RUNAPI_CALLBACK_URL");
        String normalized = base.endsWith("/") ? base.substring(0, base.length() - 1) : base;
        return normalized + "/java-live-smoke/" + modelSlug + "/" + System.currentTimeMillis();
      }

      private static String requireEnv(String name) {
        String value = System.getenv(name);
        if (value == null || value.trim().isEmpty()) {
          throw new IllegalStateException(name + " is required");
        }
        return value;
      }
    }
