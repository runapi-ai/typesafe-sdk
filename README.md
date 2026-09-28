<p align="center">
  <a href="https://runapi.ai"><img src="https://runapi.ai/icon.svg" height="56" alt="RunAPI"></a>
</p>

<h3 align="center">
  <a href="https://github.com/runapi-ai/typesafe-sdk">TypeSafe API SDK for RunAPI</a>
</h3>

<p align="center">
  TypeSafe Jev API SDKs for JavaScript, Python, Ruby, Go, Java, and PHP on RunAPI.
</p>

<div align="center">

[![npm](https://img.shields.io/npm/v/@runapi.ai/typesafe)](https://www.npmjs.com/package/@runapi.ai/typesafe)
[![PyPI](https://img.shields.io/pypi/v/runapi-typesafe)](https://pypi.org/project/runapi-typesafe/)
[![RubyGems](https://img.shields.io/gem/v/runapi-typesafe)](https://rubygems.org/gems/runapi-typesafe)
[![Go Reference](https://pkg.go.dev/badge/github.com/runapi-ai/typesafe-sdk/go.svg)](https://pkg.go.dev/github.com/runapi-ai/typesafe-sdk/go)
[![Maven Central](https://img.shields.io/maven-central/v/ai.runapi/runapi-typesafe)](https://central.sonatype.com/artifact/ai.runapi/runapi-typesafe)
[![License](https://img.shields.io/github/license/runapi-ai/typesafe-sdk)](https://github.com/runapi-ai/typesafe-sdk/blob/main/LICENSE)

</div>
<br/>

The TypeSafe API SDK packages JavaScript, Python, Ruby, Go, Java, and PHP clients for TypeSafe Jev on RunAPI. Use it for typed decisions in software — classify, route, score, and branch with calibrated confidence — when your app needs typed request builders, account helpers, and consistent RunAPI errors.

TypeSafe Jev is listed in the RunAPI model catalog at https://runapi.ai/models/jev. Variant pages below carry pricing, rate-limit, and commercial-usage details. The public `typesafe-sdk` repository groups the non-PHP language packages, examples, CI, and release tags for this model. The PHP package is released from a split Composer repository.

## Install

```bash
npm install @runapi.ai/typesafe
pip install runapi-typesafe
gem install runapi-typesafe
go get github.com/runapi-ai/typesafe-sdk/go@latest
```

Gradle:

```kotlin
dependencies {
  implementation("ai.runapi:runapi-typesafe:0.2.0")
}
```

Maven:

```xml
<dependency>
  <groupId>ai.runapi</groupId>
  <artifactId>runapi-typesafe</artifactId>
  <version>0.2.0</version>
</dependency>
```

Use the Java BOM when installing multiple RunAPI Java modules:

```kotlin
dependencies {
  implementation(platform("ai.runapi:runapi-bom:0.7.0"))
  implementation("ai.runapi:runapi-typesafe")
}
```

The PHP package is published from the split Composer repository as `runapi-ai/typesafe`; see https://github.com/runapi-ai/typesafe-php for PHP install and examples.

## What you can build

- Build apps, agent workflows, batch jobs, and production services around TypeSafe Jev requests.
- Install only the language package your app needs while keeping one model-specific repository for docs and releases.
- Use `run` for the synchronous system-one endpoint.
- Handle validation, authentication, rate limits, and insufficient credits through RunAPI SDK errors.

## Java quick start

```java
import ai.runapi.typesafe.TypesafeClient;
import ai.runapi.typesafe.types.SystemOneParams;
import java.util.Map;

TypesafeClient client = TypesafeClient.builder()
    .apiKey(System.getenv("RUNAPI_API_KEY"))
    .build();

var result = client.systemOne().run(
    SystemOneParams.builder()
        .model("jev-latest")
        .state(Map.of("candidate", "Option A"))
        .questions(Map.of("recommendation", Map.of(
            "type", "choice",
            "instructions", "Which candidate should be selected?",
            "criteria", Map.of("Option A", "The candidate is Option A.", "Option B", "The candidate is Option B.")
        )))
        .build()
);
```

Java packages target Java 8 bytecode and are tested on Java 8, 11, 17, and 21. Each model artifact depends on `ai.runapi:runapi-core`, so application code normally installs only `ai.runapi:runapi-typesafe`.

## Task lifecycle

`system-one` is synchronous. `run(params)` submits the request and returns typed answers in the same call. Do not use create/get polling for this endpoint.

## Repository layout

- `js/` publishes `@runapi.ai/typesafe`.
- `python/` publishes `runapi-typesafe`.
- `ruby/` publishes `runapi-typesafe`.
- `go/` publishes `github.com/runapi-ai/typesafe-sdk/go` and depends on `github.com/runapi-ai/core-sdk/go`.
- `java/` publishes `ai.runapi:runapi-typesafe` and depends on `ai.runapi:runapi-core`.

## Public links

- Model page: https://runapi.ai/models/jev
- SDK docs: https://runapi.ai/docs/resources/sdks
- Product docs: https://runapi.ai/docs/api/typesafe/system-one
- SDK repository: https://github.com/runapi-ai/typesafe-sdk
- PHP package repository: https://github.com/runapi-ai/typesafe-php
- Skill repository: https://github.com/runapi-ai/typesafe
- Provider comparison: https://runapi.ai/providers/typesafe
- Full catalog: https://runapi.ai/models

## Pricing and variants

Use the most specific TypeSafe Jev variant page for pricing, rate limits, and commercial usage:
- [Jev latest](https://runapi.ai/models/jev/jev-latest)

Default pricing link for the TypeSafe SDK: https://runapi.ai/models/jev/jev-latest

## File storage

RunAPI-generated file URLs are temporary. Download and store generated images, videos, audio, or other files in your own durable storage within 7 days; do not treat returned URLs as long-term assets.

## FAQ

### Which package should I install for TypeSafe work?

Install the model package for your language: `@runapi.ai/typesafe` on npm, `runapi-typesafe` on PyPI, `runapi-typesafe` on RubyGems, `github.com/runapi-ai/typesafe-sdk/go`, `ai.runapi:runapi-typesafe` on Maven Central, or `runapi-ai/typesafe` on Packagist. Install core SDK packages only when you are building shared SDK infrastructure.

### Where should public links point?

Primary TypeSafe links point to https://runapi.ai/models/jev. Pricing and usage-policy links point to variant pages such as https://runapi.ai/models/jev/jev-latest. Provider comparisons point to https://runapi.ai/providers/typesafe, and broad browsing points to https://runapi.ai/models.

## License

Licensed under the Apache License, Version 2.0.
