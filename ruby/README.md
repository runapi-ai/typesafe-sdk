# TypeSafe Ruby SDK for RunAPI

The TypeSafe Ruby SDK is the language-specific package for TypeSafe Jev on RunAPI. Use this package when your application needs typed request builders, calibrated decision answers, and consistent RunAPI errors in Ruby.

This README is the Ruby package guide inside the public `typesafe-sdk` repository. For the repository overview, start at `../README.md`; for model details, use https://runapi.ai/models/jev; for API reference, use https://runapi.ai/docs/api/typesafe/system-one; for SDK docs, use https://runapi.ai/docs/resources/sdks.

## Install

```bash
gem install runapi-typesafe
```

## Quick start

```ruby
require "runapi/typesafe"

client = RunApi::Typesafe::Client.new
result = client.system_one.run(
  model: "jev-latest",
  state: {candidate: "Option A", context: "Choose the best candidate."},
  questions: {
    recommendation: {
      type: "choice",
      instructions: "Which candidate should be selected?",
      criteria: {"Option A" => "The candidate is Option A.", "Option B" => "The candidate is Option B."}
    }
  }
)
puts result.answers
```

`system-one` is synchronous. Call `run` and read `answers` from the response. Keep `RUNAPI_API_KEY` in the environment or your secret manager; never commit API keys.

## Language notes

Use Ruby keyword arguments and the `RunApi::Typesafe` error classes when building classify, route, score, or branch jobs. `state` may be text or a structured object. `questions` is a map of named Choice, Score, or Noul questions.

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
