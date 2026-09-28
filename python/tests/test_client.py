import pytest

from runapi.core import config
from runapi.core.errors import AuthenticationError, ValidationError
from runapi.typesafe import TypesafeClient
from runapi.typesafe.resources.system_one import SystemOne
from runapi.typesafe.types import SystemOneResponse


class FakeHttp:
    def __init__(self, *responses):
        self._responses = list(responses)
        self.calls = []

    def request(self, method, path, body=None, options=None):
        self.calls.append((method, path, body))
        if self._responses:
            return self._responses.pop(0)
        raise AssertionError("Unexpected HTTP request")


@pytest.fixture(autouse=True)
def reset_config(monkeypatch):
    monkeypatch.delenv("RUNAPI_API_KEY", raising=False)
    monkeypatch.setattr(config, "api_key", None)
    yield


def test_accepts_api_key_parameter():
    assert isinstance(TypesafeClient(api_key="k", http_client=FakeHttp()), TypesafeClient)


def test_falls_back_to_global(monkeypatch):
    monkeypatch.setattr(config, "api_key", "global-key")
    assert isinstance(TypesafeClient(http_client=FakeHttp()), TypesafeClient)


def test_falls_back_to_env(monkeypatch):
    monkeypatch.setenv("RUNAPI_API_KEY", "env-key")
    assert isinstance(TypesafeClient(http_client=FakeHttp()), TypesafeClient)


def test_raises_without_api_key():
    with pytest.raises(AuthenticationError, match="API key is required"):
        TypesafeClient()


def test_uses_injected_http_client_and_accessors():
    fake = FakeHttp()
    client = TypesafeClient(api_key="k", http_client=fake)
    assert isinstance(client.system_one, SystemOne)
    assert client.system_one._http is fake


def test_system_one_posts_once_and_returns_typed():
    fake = FakeHttp({
        "model": "jev-1.13.0",
        "answers": {"recommendation": {"type": "choice", "choice": "Option A", "probabilities": {"Option A": 0.88, "Option B": 0.12}, "confidence": 0.81}},
        "usage": {"input_tokens": 318, "output_tokens": 34},
    })
    client = TypesafeClient(api_key="k", http_client=fake)
    params = {
        "model": "jev-latest",
        "state": {"candidate": "Option A"},
        "questions": {"recommendation": {"type": "choice", "instructions": "Choose the matching candidate.", "criteria": {"Option A": "The candidate is Option A.", "Option B": "The candidate is Option B."}}},
    }
    result = client.system_one.run(**params)
    assert fake.calls == [("post", "/api/v1/typesafe/system_one", params)]
    assert isinstance(result, SystemOneResponse)
    assert result.answers == {"recommendation": {"type": "choice", "choice": "Option A", "probabilities": {"Option A": 0.88, "Option B": 0.12}, "confidence": 0.81}}
    assert result.model == "jev-1.13.0"
    assert result.usage == {"input_tokens": 318, "output_tokens": 34}


def test_system_one_requires_model():
    client = TypesafeClient(api_key="k", http_client=FakeHttp())
    with pytest.raises(ValidationError, match="model"):
        client.system_one.run(
            state={"candidate": "Option A"},
            questions={"recommendation": {"type": "choice", "instructions": "Choose the matching candidate.", "criteria": {"Option A": "The candidate is Option A.", "Option B": "The candidate is Option B."}}},
        )
