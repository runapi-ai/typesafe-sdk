# TypeSafe Java SDK for RunAPI

[![Maven Central](https://img.shields.io/maven-central/v/ai.runapi/runapi-typesafe)](https://central.sonatype.com/artifact/ai.runapi/runapi-typesafe)

The TypeSafe Java SDK is the language-specific package for TypeSafe Jev on RunAPI. Use it when your Java application needs typed builders, server-side request validation, and consistent RunAPI errors for structured decisions.

This README is the Java package guide inside the public `typesafe-sdk` repository. For the repository overview, start at `../README.md`; for model details, use https://runapi.ai/models/jev; for API reference, use https://runapi.ai/docs/api/typesafe/system-one; for SDK docs, use https://runapi.ai/docs/resources/sdks.

## Requirements

The Java SDK targets Java 8 bytecode and is tested on Java 8, 11, 17, and 21.

## Install

Gradle:

```kotlin
dependencies {
  implementation("ai.runapi:runapi-typesafe:0.3.0")
}
```

Maven:

```xml
<dependency>
  <groupId>ai.runapi</groupId>
  <artifactId>runapi-typesafe</artifactId>
  <version>0.3.0</version>
</dependency>
```

Use the BOM when multiple RunAPI Java modules are installed:

```kotlin
dependencies {
  implementation(platform("ai.runapi:runapi-bom:0.9.0"))
  implementation("ai.runapi:runapi-typesafe")
}
```

## Quick Start

```java
import ai.runapi.typesafe.TypesafeClient;
import ai.runapi.typesafe.types.SystemOneParams;
import ai.runapi.typesafe.types.SystemOneResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

TypesafeClient client = TypesafeClient.builder()
    .apiKey(System.getenv("RUNAPI_API_KEY"))
    .build();

Map<String, Object> state = new HashMap<String, Object>();
state.put("candidate", "Option A");
state.put("context", "Choose the best candidate.");

Map<String, Object> question = new HashMap<String, Object>();
question.put("type", "choice");
question.put("instructions", "Which candidate should be selected?");
Map<String, Object> criteria = new HashMap<String, Object>();
criteria.put("Option A", "The candidate is Option A.");
criteria.put("Option B", "The candidate is Option B.");
question.put("criteria", criteria);

SystemOneResponse result = client.systemOne().run(
    SystemOneParams.builder()
        .model("jev-latest")
        .state(state)
        .questions(Collections.<String, Object>singletonMap("recommendation", question))
        .build()
);
```

`system-one` is synchronous. Call `run` and read `getAnswers()` from the response. The client builder reads `RUNAPI_API_KEY` when `.apiKey(...)` is omitted.

## Error Handling

All SDK errors extend `RunApiException`.

```java
import ai.runapi.core.errors.RateLimitException;
import ai.runapi.core.errors.RunApiException;

try {
  client.systemOne().run(params);
} catch (RateLimitException error) {
  System.err.println(error.getRetryAfter());
} catch (RunApiException error) {
  System.err.println(error.getCode() + " " + error.getStatusCode());
}
```

## Links

- Model page: https://runapi.ai/models/jev
- SDK docs: https://runapi.ai/docs/resources/sdks
- Product docs: https://runapi.ai/docs/api/typesafe/system-one
- Pricing and rate limits: https://runapi.ai/models/jev/jev-latest
- Provider comparison: https://runapi.ai/providers/typesafe
- Full catalog: https://runapi.ai/models
- Repository: https://github.com/runapi-ai/typesafe-sdk

## License

Licensed under the Apache License, Version 2.0.
