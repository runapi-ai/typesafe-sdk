package typesafe

// SystemOneParams configures a TypeSafe Jev structured-decision request.
// Model is sent on the wire. This is a synchronous operation (no Create/Get polling, use Run directly).
type SystemOneParams struct {
	State     any            `json:"state" help:"required; text or structured state to evaluate"`
	Model     string         `json:"model" help:"required; TypeSafe model identifier, jev-latest"`
	Questions map[string]any `json:"questions" help:"required; named Choice, Score, or Noul questions"`
}

// SystemOneResponse is the result of a synchronous system-one call.
type SystemOneResponse struct {
	Model   string         `json:"model,omitempty"`
	Answers map[string]any `json:"answers,omitempty"`
	Usage   map[string]any `json:"usage,omitempty"`
}
