package ai.runapi.typesafe.types;

import ai.runapi.core.types.RunApiValue;

abstract class TypesafeValue extends RunApiValue {
  TypesafeValue(String value) {
    super(value);
  }
}
