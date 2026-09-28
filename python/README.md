# TypeSafe Python SDK for RunAPI

The TypeSafe Python SDK is the language-specific package for TypeSafe Jev on RunAPI. Use this package when your application needs typed request builders, calibrated decision answers, and consistent RunAPI errors in Python.

This README is the Python package guide inside the public `typesafe-sdk` repository. For the repository overview, start at `../README.md`; for model details, use https://runapi.ai/models/jev; for API reference, use https://runapi.ai/docs/api/typesafe/system-one; for SDK docs, use https://runapi.ai/docs/resources/sdks.

## Install

```bash
pip install runapi-typesafe
```

## Quick start

```python
from runapi.typesafe import TypesafeClient

client = TypesafeClient()  # reads RUNAPI_API_KEY, or pass api_key="sk-..."
result = client.system_one.run(
    model="jev-latest",
    state={"candidate": "Option A", "context": "Choose the best candidate."},
    questions={
        "recommendation": {
            "type": "choice",
            "instructions": "Which candidate should be selected?",
            "criteria": {"Option A": "The candidate is Option A.", "Option B": "The candidate is Option B."},
        }
    },
)
print(result.answers)
```

`system-one` is synchronous. Call `run` and read `answers` from the response. Keep `RUNAPI_API_KEY` in the environment or your secret manager; never commit API keys.

## Language notes

Use keyword arguments on `client.system_one.run`. `state` may be text or a structured object. `questions` is a map of named Choice, Score, or Noul questions.

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
