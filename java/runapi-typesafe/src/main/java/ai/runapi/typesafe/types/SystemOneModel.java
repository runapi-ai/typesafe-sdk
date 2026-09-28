package ai.runapi.typesafe.types;

import com.fasterxml.jackson.annotation.JsonCreator;

/** Model slug for system one operations. */
public final class SystemOneModel extends TypesafeValue {
  /** jev-latest model slug. */
  public static final SystemOneModel JEV_LATEST = new SystemOneModel("jev-latest");

  /** Creates a model value from a literal model slug. */
  @JsonCreator
  public SystemOneModel(String value) {
    super(value);
  }
}
