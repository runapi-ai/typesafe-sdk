    package ai.runapi.typesafe;

    import static org.junit.jupiter.api.Assertions.assertEquals;
    import static org.junit.jupiter.api.Assertions.assertNotNull;

    import ai.runapi.core.Constants;
    import ai.runapi.core.RequestOptions;
    import ai.runapi.core.json.Json;
    import ai.runapi.typesafe.types.SystemOneResponse;
    import ai.runapi.typesafe.types.SystemOneModel;
import ai.runapi.typesafe.types.SystemOneParams;
import ai.runapi.typesafe.types.SystemOneResponse;
    import com.fasterxml.jackson.databind.JsonNode;
    import com.sun.net.httpserver.HttpExchange;
    import com.sun.net.httpserver.HttpServer;
    import java.io.ByteArrayOutputStream;
    import java.io.IOException;
    import java.io.OutputStream;
    import java.net.InetSocketAddress;
    import java.nio.charset.StandardCharsets;
    import java.time.Duration;
    import java.util.ArrayList;
    import java.util.List;
    import org.junit.jupiter.api.AfterEach;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;

    class TypesafeLocalApiSmokeTest {
      private HttpServer server;
      private final List<CapturedRequest> requests = new ArrayList<CapturedRequest>();

      @BeforeEach
      void startServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/api/v1/typesafe/system_one", this::handleRequest);
        server.start();
      }

      @AfterEach
      void stopServer() {
        server.stop(0);
      }

      @Test
      void runUsesApacheTransportAgainstLocalApi() throws Exception {
        try (TypesafeClient client =
            TypesafeClient.builder()
                .apiKey("sk-test")
                .baseUrl("http://127.0.0.1:" + server.getAddress().getPort())
                .build()) {
      SystemOneResponse response = client.systemOne().run(
              SystemOneParams.builder()
                  .state(java.util.Collections.singletonMap("candidate", "Option A"))
                  .model("jev-latest")
                  .questions(TypesafeClientTest.questions())
                  .build()
      );

          JsonNode answer = Json.mapper().valueToTree(response.getAnswers().get("recommendation"));
          assertEquals("choice", answer.path("type").asText());
          assertEquals("Option A", answer.path("choice").asText());
          assertEquals(0.88, answer.path("probabilities").path("Option A").asDouble());
          assertEquals(0.12, answer.path("probabilities").path("Option B").asDouble());
          assertEquals(0.81, answer.path("confidence").asDouble());
          assertEquals("jev-1.13.0", response.extraFields().get("model").asText());
          assertEquals(318, response.extraFields().get("usage").path("input_tokens").asInt());
          assertEquals(34, response.extraFields().get("usage").path("output_tokens").asInt());
          assertEquals("kept", response.extraFields().get("custom").asText());
        }

        assertEquals(1, requests.size());
        CapturedRequest create = requests.get(0);
        assertEquals("POST", create.method);
        assertEquals("/api/v1/typesafe/system_one", create.path);
        assertEquals("Bearer sk-test", create.header("Authorization"));
        assertEquals(Constants.SDK_USER_AGENT, create.header("User-Agent"));
        JsonNode body = Json.mapper().readTree(create.body);
        assertEquals("jev-latest", body.path("model").asText());
        assertEquals("Option A", body.path("state").path("candidate").asText());
        assertEquals(Json.mapper().valueToTree(TypesafeClientTest.questions()), body.path("questions"));
      }

      private void handleRequest(HttpExchange exchange) throws IOException {
        CapturedRequest captured = CapturedRequest.from(exchange);
        requests.add(captured);

        assertEquals("POST", captured.method);
        assertEquals("/api/v1/typesafe/system_one", captured.path);
        write(exchange, 200, "{\"model\":\"jev-1.13.0\",\"answers\":{\"recommendation\":{\"type\":\"choice\",\"choice\":\"Option A\",\"probabilities\":{\"Option A\":0.88,\"Option B\":0.12},\"confidence\":0.81}},\"usage\":{\"input_tokens\":318,\"output_tokens\":34},\"custom\":\"kept\"}");
      }

      private static void write(HttpExchange exchange, int status, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream out = exchange.getResponseBody()) {
          out.write(bytes);
        }
      }

      private static final class CapturedRequest {
        private final String method;
        private final String path;
        private final com.sun.net.httpserver.Headers headers;
        private final String body;

        private CapturedRequest(String method, String path, com.sun.net.httpserver.Headers headers, String body) {
          this.method = method;
          this.path = path;
          this.headers = headers;
          this.body = body;
        }

        private static CapturedRequest from(HttpExchange exchange) throws IOException {
          return new CapturedRequest(
              exchange.getRequestMethod(),
              exchange.getRequestURI().getPath(),
              exchange.getRequestHeaders(),
              new String(readAll(exchange), StandardCharsets.UTF_8));
        }

        private String header(String name) {
          List<String> values = headers.get(name);
          if (values == null || values.isEmpty()) {
            return null;
          }
          return values.get(0);
        }

        private static byte[] readAll(HttpExchange exchange) throws IOException {
          ByteArrayOutputStream out = new ByteArrayOutputStream();
          byte[] buffer = new byte[1024];
          int read;
          while ((read = exchange.getRequestBody().read(buffer)) != -1) {
            out.write(buffer, 0, read);
          }
          return out.toByteArray();
        }
      }
    }
