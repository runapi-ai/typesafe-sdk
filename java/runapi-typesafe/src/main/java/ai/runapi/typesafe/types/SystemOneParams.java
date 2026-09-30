package ai.runapi.typesafe.types;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Parameters for system one operations. */
public final class SystemOneParams {
  private final Object state;
  private final String model;
  private final Map<String, Object> questions;

  private SystemOneParams(Builder builder) {
    this.state = builder.state;
    this.model = builder.model;
    this.questions = builder.questions;
  }

  /** Creates a new SystemOneParams builder. */
  public static Builder builder() {
    return new Builder();
  }

  /** Returns the RunAPI action key for this request. */
  public String action() {
    return "typesafe/system-one";
  }

  /** Converts these parameters to the JSON request body shape. */
  public Map<String, Object> toMap() {
    Map<String, Object> raw = new LinkedHashMap<String, Object>();
    raw.put("state", TypesafeParamUtils.wireValue(state));
    raw.put("model", TypesafeParamUtils.wireValue(model));
    raw.put("questions", TypesafeParamUtils.wireValue(questions));
    return TypesafeParamUtils.compact(raw);
  }



  /** Builder for {@link SystemOneParams}. */
  public static final class Builder {
    private Object state;
    private String model;
    private Map<String, Object> questions;

    private Builder() {}

    /** Sets the state. */
    public Builder state(Object value) {
      this.state = value;
      return this;
    }

    /** Sets the model slug using a typed model value. */
    public Builder model(SystemOneModel value) {
      this.model = java.util.Objects.requireNonNull(value, "model").value();
      return this;
    }

    /** Sets the model slug using a string value. */
    public Builder model(String value) {
      this.model = value;
      return this;
    }


    /** Sets the questions. */
    public Builder questions(Map<String, Object> value) {
      this.questions = value;
      return this;
    }

    /** Builds immutable system one parameters. */
    public SystemOneParams build() {
      return new SystemOneParams(this);
    }
  }
}
