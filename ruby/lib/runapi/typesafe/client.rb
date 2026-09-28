# frozen_string_literal: true

module RunApi
  module Typesafe
    # TypeSafe Jev client for typed decisions in software.
    #
    # @example
    #   client = RunApi::Typesafe::Client.new(api_key: "sk-...")
    #   result = client.system_one.run(
    #     model: "jev-latest",
    #     state: {candidate: "Option A"},
    #     questions: {recommendation: {
    #       type: "choice", instructions: "Choose the matching candidate.",
    #       criteria: {"Option A" => "The candidate is Option A.", "Option B" => "The candidate is Option B."}
    #     }}
    #   )
    #   puts result.answers
    class Client < RunApi::Core::Client
      # @return [Resources::SystemOne] Evaluates application state (synchronous).
      attr_reader :system_one

      def initialize(api_key: nil, **options)
        super
        @system_one = Resources::SystemOne.new(http)
      end
    end
  end
end
