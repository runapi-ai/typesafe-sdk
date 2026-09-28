# TypeSafe Go SDK for RunAPI

The TypeSafe Go SDK is the language-specific package for TypeSafe Jev on RunAPI. Use this package when your application needs typed request builders, calibrated decision answers, and consistent RunAPI errors in Go.

This README is the Go package guide inside the public `typesafe-sdk` repository. For the repository overview, start at `../README.md`; for model details, use https://runapi.ai/models/jev; for API reference, use https://runapi.ai/docs/api/typesafe/system-one; for SDK docs, use https://runapi.ai/docs/resources/sdks.

## Install

```bash
go get github.com/runapi-ai/typesafe-sdk/go@latest
```

## Quick start

```go
import (
  "context"

  "github.com/runapi-ai/typesafe-sdk/go/typesafe"
)

client, err := typesafe.NewClient()
result, err := client.SystemOne.Run(context.Background(), typesafe.SystemOneParams{
  Model: "jev-latest",
  State: map[string]any{"candidate": "Option A", "context": "Choose the best candidate."},
  Questions: map[string]any{
    "recommendation": map[string]any{
      "type":         "choice",
      "instructions": "Which candidate should be selected?",
      "criteria": map[string]any{"Option A": "The candidate is Option A.", "Option B": "The candidate is Option B."},
    },
  },
})
```

`system-one` is synchronous. Call `Run` and read `Answers` from the response. Keep `RUNAPI_API_KEY` in the environment or your secret manager; never commit API keys.

## Language notes

Use the public Go module with `github.com/runapi-ai/core-sdk/go` options when building classify, route, score, or branch services. `State` may be text or a structured object. `Questions` is a map of named Choice, Score, or Noul questions.

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
