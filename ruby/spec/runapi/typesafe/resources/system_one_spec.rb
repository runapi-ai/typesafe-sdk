# frozen_string_literal: true

require "spec_helper"

RSpec.describe RunApi::Typesafe::Resources::SystemOne do
  let(:http) { instance_double(RunApi::Core::HttpClient) }
  let(:resource) { described_class.new(http) }
  let(:endpoint) { "/api/v1/typesafe/system_one" }

  it "POSTs to the correct endpoint with model on the wire" do
    params = {
      model: "jev-latest",
      state: {"candidate" => "Option A"},
      questions: {"recommendation" => {"type" => "choice", "instructions" => "Choose the matching candidate.", "criteria" => {"Option A" => "The candidate is Option A.", "Option B" => "The candidate is Option B."}}}
    }
    expect(http).to receive(:request).with(:post, endpoint, body: params)
      .and_return(
        "model" => "jev-1.13.0",
        "answers" => {"recommendation" => {"type" => "choice", "choice" => "Option A", "probabilities" => {"Option A" => 0.88, "Option B" => 0.12}, "confidence" => 0.81}},
        "usage" => {"input_tokens" => 318, "output_tokens" => 34}
      )

    result = resource.run(**params)

    expect(result.answers).to eq("recommendation" => {"type" => "choice", "choice" => "Option A", "probabilities" => {"Option A" => 0.88, "Option B" => 0.12}, "confidence" => 0.81})
    expect(result.model).to eq("jev-1.13.0")
    expect(result.usage).to eq("input_tokens" => 318, "output_tokens" => 34)
  end
end
