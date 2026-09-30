# frozen_string_literal: true

module RunApi
  module Typesafe
    module Resources
      # Evaluates application state against named Choice, Score, and Noul questions.
      # Synchronous -- only +run+ is available (no create/get polling).
      class SystemOne
        include RunApi::Core::ResourceHelpers

        ENDPOINT = "/api/v1/typesafe/system_one"
        RESPONSE_CLASS = Types::SystemOneResponse

        def initialize(http)
          @http = http
        end

        def run(options: nil, **params)
          params = compact_params(params)
          request(:post, ENDPOINT, body: params, options: options)
        end
      end
    end
  end
end
