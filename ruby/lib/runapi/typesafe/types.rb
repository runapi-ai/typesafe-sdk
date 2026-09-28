# frozen_string_literal: true

module RunApi
  module Typesafe
    module Types
      # Result of a synchronous system-one call.
      class SystemOneResponse < RunApi::Core::TaskResponse
        optional :model, String
        optional :answers, Hash
        optional :usage, Hash
      end
    end
  end
end
