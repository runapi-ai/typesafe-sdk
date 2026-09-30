"""TypeSafe system-one resource (synchronous)."""

from __future__ import annotations

from typing import Any, Optional

from runapi.core import Resource, RequestOptions

from ..types import SystemOneResponse


class SystemOne(Resource):
    """Evaluate application state against Choice, Score, and Noul questions.

    Synchronous: ``run()`` returns the result directly.
    """

    ENDPOINT = "/api/v1/typesafe/system_one"

    RESPONSE_CLASS = SystemOneResponse

    def run(self, options: Optional[RequestOptions] = None, **params: Any) -> Any:
        """Submit a TypeSafe Jev structured-decision request (synchronous).

        Args:
            **params: Request parameters (state, model, questions).

        Returns:
            The typed answers, usage, and billing facts.
        """
        compacted = self._compact_params(params)
        return self._request("post", self.ENDPOINT, body=compacted, options=options)
