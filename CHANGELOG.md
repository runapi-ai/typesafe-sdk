# Changelog

## [js/v0.3.0](https://github.com/runapi-ai/typesafe-sdk/releases/tag/js%2Fv0.3.0), [ruby/v0.3.0](https://github.com/runapi-ai/typesafe-sdk/releases/tag/ruby%2Fv0.3.0), [go/v0.3.0](https://github.com/runapi-ai/typesafe-sdk/releases/tag/go%2Fv0.3.0), [python/v0.3.0](https://github.com/runapi-ai/typesafe-sdk/releases/tag/python%2Fv0.3.0), [java/v0.3.0](https://github.com/runapi-ai/typesafe-sdk/releases/tag/java%2Fv0.3.0) - 2026-09-30

### Changed
- Send request parameters to the service without local validation. Model ids and parameter values the service supports work without an SDK upgrade; static types and enum constants remain for completion.
  Migration: Invalid parameters now fail with the validation error built from the service's 400 response, including its status and message, instead of a validation error raised locally before the request. The error type is unchanged: `ValidationError` in JavaScript, Python, and Ruby, `ValidationException` in Java and PHP, and `ErrValidation` in Go.


## [js/v0.2.0](https://github.com/runapi-ai/typesafe-sdk/releases/tag/js%2Fv0.2.0), [ruby/v0.2.0](https://github.com/runapi-ai/typesafe-sdk/releases/tag/ruby%2Fv0.2.0), [go/v0.2.0](https://github.com/runapi-ai/typesafe-sdk/releases/tag/go%2Fv0.2.0), [python/v0.2.0](https://github.com/runapi-ai/typesafe-sdk/releases/tag/python%2Fv0.2.0), [java/v0.2.0](https://github.com/runapi-ai/typesafe-sdk/releases/tag/java%2Fv0.2.0) - 2026-09-28

### Added
- Add TypeSafe Jev system-one as a synchronous structured-decision client across JavaScript, Python, Ruby, Go, and Java.
