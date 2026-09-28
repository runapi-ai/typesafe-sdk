<p align="center">
  <a href="https://github.com/runapi-ai/typesafe">
    <h3 align="center">TypeSafe API Skill for RunAPI</h3>
  </a>
</p>

<p align="center">
  Install this agent skill, inspect TypeSafe Jev fields, then submit structured-decision requests through the RunAPI CLI.
</p>

<p align="center">
  <a href="https://runapi.ai/models/jev"><strong>Model Reference</strong></a> · <a href="https://github.com/runapi-ai/cli"><strong>CLI</strong></a> · <a href="https://github.com/runapi-ai/typesafe-sdk"><strong>SDK</strong></a>
</p>

<div align="center">

[![skills.sh](https://www.skills.sh/b/runapi-ai/typesafe)](https://www.skills.sh/runapi-ai/typesafe/typesafe)
[![ClawHub](https://img.shields.io/badge/ClawHub-runapi--typesafe-111827)](https://clawhub.ai/runapi-ai/runapi-typesafe)
[![License](https://img.shields.io/github/license/runapi-ai/typesafe)](https://github.com/runapi-ai/typesafe/blob/main/LICENSE)

</div>
<br/>

Submit TypeSafe Jev structured-decision requests through RunAPI. This skill helps Claude Code, Codex, Gemini CLI, Cursor, and 50+ agents use TypeSafe Jev through RunAPI.

The canonical agent file is `skills/typesafe/SKILL.md`.


## Install

```bash
npx skills add runapi-ai/typesafe -g
```

Or paste this prompt to your AI agent:

```text
Install the typesafe skill for me:

1. Clone https://github.com/runapi-ai/typesafe
2. Copy the skills/typesafe/ directory into your
   user-level skills directory.
3. Verify that SKILL.md is present.
4. Confirm the install path when done.
```

## Quick example

```shell
runapi typesafe system-one --input-file request.json
```

## Routing

- Model page: https://runapi.ai/models/jev
- Product docs: https://runapi.ai/docs/api/typesafe/system-one
- SDK docs: https://runapi.ai/docs/resources/sdks
- SDK repository: https://github.com/runapi-ai/typesafe-sdk
- Jev latest pricing and rate limits: https://runapi.ai/models/jev/jev-latest
- Browse all RunAPI models and skills: https://runapi.ai/models

## Agent rules

- Integration work uses the target language SDK; one-off generation, manual smoke tests, debugging, or user-requested CLI runs use the RunAPI CLI skill: https://github.com/runapi-ai/cli-skill
- RunAPI-generated file URLs are temporary. Download and store generated images, videos, audio, or other files in your own durable storage within 7 days; do not treat returned URLs as long-term assets.

## License

Licensed under the Apache License, Version 2.0.
