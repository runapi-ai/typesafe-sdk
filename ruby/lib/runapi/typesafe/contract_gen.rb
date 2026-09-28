# frozen_string_literal: true

module RunApi
  module Typesafe
    CONTRACT = {
      "system-one" => {
        "models" => ["jev-latest"],
        "fields_by_model" => {
          "jev-latest" => {
            "model" => {
              "enum" => ["jev-latest"],
              "required" => true
            },
            "questions" => {
              "required" => true
            },
            "state" => {
              "required" => true
            }
          }
        }
      }
    }.freeze
  end
end
