// Package typesafe provides the TypeSafe Jev API client for structured decisions in software.
//
//	client, err := typesafe.NewClient(option.WithAPIKey("sk-your-api-key"))
//	result, err := client.SystemOne.Run(ctx, typesafe.SystemOneParams{
//	    Model: "jev-latest",
//	    State: map[string]any{"candidate": "Option A"},
//	    Questions: map[string]any{"recommendation": map[string]any{
//	        "type": "choice", "instructions": "Choose the matching candidate.",
//	        "criteria": map[string]any{"Option A": "The candidate is Option A.", "Option B": "The candidate is Option B."},
//	    }},
//	})
package typesafe

import (
	"context"

	"github.com/runapi-ai/core-sdk/go/base"
	"github.com/runapi-ai/core-sdk/go/core"
	"github.com/runapi-ai/core-sdk/go/option"
)

const systemOnePath = "/api/v1/typesafe/system_one"

// Client provides TypeSafe Jev structured decisions: classify, route, score, and branch with calibrated confidence.
type Client struct {
	base.Base
	SystemOne *SystemOne
}

// NewClient creates a TypeSafe client with the given options.
func NewClient(opts ...option.ClientOption) (*Client, error) {
	resolved, err := option.ResolveClientOptions(opts...)
	if err != nil {
		return nil, err
	}
	httpClient, err := core.NewHTTPClient(resolved)
	if err != nil {
		return nil, err
	}
	return NewClientWithHTTP(httpClient), nil
}

// NewClientWithHTTP creates a TypeSafe client with a pre-configured HTTP transport.
func NewClientWithHTTP(httpClient core.HTTPClient) *Client {
	return &Client{
		Base:      base.New(httpClient),
		SystemOne: &SystemOne{http: httpClient},
	}
}

// SystemOne evaluates application state against named Choice, Score, and Noul questions.
// This is synchronous -- only Run is available (no Create/Get polling).
type SystemOne struct{ http core.HTTPClient }

// Run submits a TypeSafe Jev structured-decision request and returns the result.
func (r *SystemOne) Run(ctx context.Context, params SystemOneParams, opts ...option.RequestOption) (*SystemOneResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	body := core.CompactParams(params)
	if err := core.ValidateParams(contractSchema["system-one"], body); err != nil {
		return nil, err
	}
	return core.PostJSON[SystemOneResponse](ctx, r.http, systemOnePath, body, requestOptions)
}
