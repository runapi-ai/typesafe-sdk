package ai.runapi.typesafe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import ai.runapi.core.http.HttpRequest;
import ai.runapi.core.http.HttpResponse;
import ai.runapi.core.http.HttpTransport;
import ai.runapi.core.http.JsonRequestBody;
import ai.runapi.core.json.Json;
import ai.runapi.typesafe.types.SystemOneModel;
import ai.runapi.typesafe.types.SystemOneParams;
import ai.runapi.typesafe.types.SystemOneResponse;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class TypesafeClientTest {
  private static final String RESPONSE = "{\"model\":\"jev-1.13.0\",\"answers\":{\"recommendation\":{\"type\":\"choice\",\"choice\":\"Option A\",\"probabilities\":{\"Option A\":0.88,\"Option B\":0.12},\"confidence\":0.81}},\"usage\":{\"input_tokens\":318,\"output_tokens\":34},\"custom\":\"kept\"}";

  static Map<String, Object> questions() {
    Map<String, Object> criteria = new LinkedHashMap<String, Object>();
    criteria.put("Option A", "The candidate is Option A.");
    criteria.put("Option B", "The candidate is Option B.");
    Map<String, Object> question = new LinkedHashMap<String, Object>();
    question.put("type", "choice");
    question.put("instructions", "Choose the matching candidate.");
    question.put("criteria", criteria);
    return Collections.<String, Object>singletonMap("recommendation", question);
  }

  @Test
  void builderCreatesClientAndUniversalResources() {
    TypesafeClient client = TypesafeClient.builder().apiKey("sk-test").build();

    assertNotNull(client.systemOne());
    assertNotNull(client.files());
    assertNotNull(client.account());
    assertNotNull(client.pricing());
  }

  @Test
  void openValueClassesSerializeAsScalarStrings() throws Exception {
    String json = Json.mapper().writeValueAsString(new SystemOneModel("jev-latest"));

    assertEquals("\"jev-latest\"", json);
    assertEquals(new SystemOneModel("jev-latest"), Json.mapper().readValue(json, SystemOneModel.class));
  }

  @Test
  void runSendsExpectedRequestShape() throws Exception {
    CapturingTransport transport = new CapturingTransport(RESPONSE);
    TypesafeClient client = TypesafeClient.builder().apiKey("sk-test").transport(transport).build();

    client.systemOne().run(
        SystemOneParams.builder()
            .state(java.util.Collections.singletonMap("candidate", "Option A"))
            .model("jev-latest")
            .questions(questions())
            .build()
    );

    assertEquals("POST", transport.request.getMethod().name());
    assertEquals("/api/v1/typesafe/system_one", transport.request.getPath());
    JsonNode body = bodyJson(transport.request);
    assertEquals("jev-latest", body.path("model").asText());
    assertEquals("Option A", body.path("state").path("candidate").asText());
    assertEquals(Json.mapper().valueToTree(questions()), body.path("questions"));
  }

  @Test
  void runDecodesResponseAndExtraFields() {
    CapturingTransport transport = new CapturingTransport(RESPONSE);
    TypesafeClient client = TypesafeClient.builder().apiKey("sk-test").transport(transport).build();

    SystemOneResponse response = client.systemOne().run(
        SystemOneParams.builder()
            .state(java.util.Collections.singletonMap("candidate", "Option A"))
            .model("jev-latest")
            .questions(questions())
            .build()
    );

    assertEquals("POST", transport.request.getMethod().name());
    assertEquals("/api/v1/typesafe/system_one", transport.request.getPath());
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


  private static JsonNode bodyJson(HttpRequest request) throws Exception {
    JsonRequestBody body = (JsonRequestBody) request.getBody();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    body.writeTo(out);
    return Json.mapper().readTree(out.toByteArray());
  }

  private static final class CapturingTransport implements HttpTransport {
    private final String body;
    private HttpRequest request;

    private CapturingTransport(String body) {
      this.body = body;
    }

    public HttpResponse send(HttpRequest request) {
      this.request = request;
      return new HttpResponse(200, body, Collections.<String, java.util.List<String>>emptyMap());
    }

    public void close() {}
  }

}
