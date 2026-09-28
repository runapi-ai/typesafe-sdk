# frozen_string_literal: true

require "runapi/core"
require_relative "typesafe/types"
require_relative "typesafe/contract_gen"
require_relative "typesafe/resources/system_one"
require_relative "typesafe/client"

module RunApi
  module Typesafe
    AuthenticationError = RunApi::Core::AuthenticationError
    RateLimitError = RunApi::Core::RateLimitError
    InsufficientCreditsError = RunApi::Core::InsufficientCreditsError
    NotFoundError = RunApi::Core::NotFoundError
    ValidationError = RunApi::Core::ValidationError
  end
end
