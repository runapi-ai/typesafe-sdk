# frozen_string_literal: true

Dir.chdir(__dir__) do

  Gem::Specification.new do |spec|
    spec.name = "runapi-typesafe"
    spec.version = "0.3.0"
    spec.metadata["runapi_slug"] = "typesafe"
    spec.authors = ["RunAPI"]
    spec.email = ["contact@runapi.ai"]

    spec.summary = "TypeSafe Ruby SDK for RunAPI"
    spec.description = "The TypeSafe Ruby SDK is the language-specific package for TypeSafe Jev on RunAPI. Use this package when your application needs typed request builders, calibrated decision answers, and consistent RunAPI errors in Ruby."
    spec.homepage = "https://runapi.ai/models/jev"
    spec.license = "Apache-2.0"
    spec.required_ruby_version = ">= 3.1.0"
    spec.metadata["homepage_uri"] = "https://runapi.ai/models/jev"
    spec.metadata["documentation_uri"] = "https://github.com/runapi-ai/typesafe-sdk/blob/main/ruby/README.md"
    spec.metadata["source_code_uri"] = "https://github.com/runapi-ai/typesafe-sdk"
    spec.metadata["bug_tracker_uri"] = "https://github.com/runapi-ai/typesafe-sdk/issues"
    spec.metadata["changelog_uri"] = "https://github.com/runapi-ai/typesafe-sdk/blob/main/CHANGELOG.md"


    spec.files = Dir.glob("lib/**/*") + %w[LICENSE README.md]
    spec.extra_rdoc_files = ["README.md"]
        spec.require_paths = ["lib"]

    spec.add_dependency "runapi-core", "~> 0.6.0"
  end
end
