"""TypeSafe client."""

from __future__ import annotations

from typing import Any, Optional

from runapi.core import ProviderClient

from .resources.system_one import SystemOne


class TypesafeClient(ProviderClient):
    """TypeSafe Jev client for typed decisions in software.

    Example::

        client = TypesafeClient(api_key="sk-...")
        result = client.system_one.run(
            model="jev-latest",
            state={"candidate": "Option A"},
            questions={"recommendation": {
                "type": "choice",
                "instructions": "Choose the matching candidate.",
                "criteria": {"Option A": "The candidate is Option A.", "Option B": "The candidate is Option B."},
            }},
        )
    """

    def __init__(self, api_key: Optional[str] = None, **options: Any) -> None:
        super().__init__(api_key, **options)
        http = self._http
        self.system_one = SystemOne(http)
