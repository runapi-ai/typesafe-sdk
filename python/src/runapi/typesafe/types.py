"""TypeSafe enums and response models."""

from __future__ import annotations

from runapi.core import TaskResponse, optional


class SystemOneResponse(TaskResponse):
    """Result of a TypeSafe Jev structured-decision call."""

    model = optional(str)
    answers = optional(dict)
    usage = optional(dict)
