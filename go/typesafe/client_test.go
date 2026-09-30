package typesafe

import (
	"context"
	"encoding/json"
	"reflect"
	"testing"

	"github.com/runapi-ai/core-sdk/go/core"
)

type stubHTTPClient struct {
	method string
	path   string
	body   any
}

func (s *stubHTTPClient) Request(_ context.Context, method, path string, opts *core.HTTPRequestOptions) (json.RawMessage, error) {
	s.method = method
	s.path = path
	if opts != nil {
		s.body = opts.Body
	}
	return json.RawMessage(`{"model":"jev-1.13.0","answers":{"recommendation":{"type":"choice","choice":"Option A","probabilities":{"Option A":0.88,"Option B":0.12},"confidence":0.81}},"usage":{"input_tokens":318,"output_tokens":34},"billing":{"reservation":null,"settlement":{"charged_amount_cents":0,"amount_micro_cents":0},"refund":null}}`), nil
}

func TestSystemOneRunSendsCorrectRequest(t *testing.T) {
	stub := &stubHTTPClient{}
	client := NewClientWithHTTP(stub)
	resp, err := client.SystemOne.Run(context.Background(), SystemOneParams{
		Model: "jev-latest",
		State: map[string]any{"candidate": "Option A"},
		Questions: map[string]any{
			"recommendation": map[string]any{"type": "choice", "instructions": "Choose the matching candidate.", "criteria": map[string]any{"Option A": "The candidate is Option A.", "Option B": "The candidate is Option B."}},
		},
	})
	if err != nil {
		t.Fatal(err)
	}
	if stub.method != "POST" || stub.path != "/api/v1/typesafe/system_one" {
		t.Fatalf("unexpected request: %s %s", stub.method, stub.path)
	}
	body, ok := stub.body.(map[string]any)
	if !ok {
		t.Fatalf("expected flat body map, got %T", stub.body)
	}
	if body["model"] != "jev-latest" {
		t.Fatalf("expected model on the wire, got %#v", body)
	}
	if !reflect.DeepEqual(body["state"], map[string]any{"candidate": "Option A"}) {
		t.Fatalf("unexpected state on the wire: %#v", body)
	}
	expectedQuestions := map[string]any{"recommendation": map[string]any{
		"type": "choice", "instructions": "Choose the matching candidate.",
		"criteria": map[string]any{"Option A": "The candidate is Option A.", "Option B": "The candidate is Option B."},
	}}
	if !reflect.DeepEqual(body["questions"], expectedQuestions) {
		t.Fatalf("unexpected questions on the wire: %#v", body)
	}
	expectedAnswers := map[string]any{"recommendation": map[string]any{
		"type": "choice", "choice": "Option A",
		"probabilities": map[string]any{"Option A": 0.88, "Option B": 0.12}, "confidence": 0.81,
	}}
	if !reflect.DeepEqual(resp.Answers, expectedAnswers) {
		t.Fatalf("unexpected answers: %#v", resp.Answers)
	}
	if resp.Model != "jev-1.13.0" || !reflect.DeepEqual(resp.Usage, map[string]any{"input_tokens": float64(318), "output_tokens": float64(34)}) {
		t.Fatalf("unexpected response metadata: %#v", resp)
	}
}
