package ai.runapi.typesafe;

import ai.runapi.core.BaseClient;
import ai.runapi.core.ClientOptions;
import ai.runapi.core.http.HttpTransport;
import java.net.URI;
import ai.runapi.typesafe.resources.SystemOneResource;

/** Typesafe model-family Java SDK client. */
public final class TypesafeClient extends BaseClient {
  private final SystemOneResource systemOne;

  private TypesafeClient(ClientOptions options) {
    super(options);
    this.systemOne = new SystemOneResource(transport(), options());
  }

  /** Creates a new TypesafeClient builder. */
  public static Builder builder() {
    return new Builder();
  }

  /** System One operations. */
  public SystemOneResource systemOne() {
    return systemOne;
  }

  /** Builder for {@link TypesafeClient}. */
  public static final class Builder extends BaseClient.Builder<Builder> {
    private Builder() {}

    /** Sets the API key. If omitted, the SDK reads {@code RUNAPI_API_KEY}. */
    @Override
    public Builder apiKey(String value) {
      return super.apiKey(value);
    }

    /** Sets the RunAPI base URL. If omitted, the SDK reads {@code RUNAPI_BASE_URL}. */
    @Override
    public Builder baseUrl(String value) {
      return super.baseUrl(value);
    }

    /** Sets the RunAPI base URL from a URI. */
    @Override
    public Builder baseUrl(URI value) {
      return super.baseUrl(value);
    }

    /** Sets a custom HTTP transport. User-provided transports are not closed by SDK clients. */
    @Override
    public Builder transport(HttpTransport value) {
      return super.transport(value);
    }

    /** Builds an immutable TypesafeClient. */
    @Override
    public TypesafeClient build() {
      return new TypesafeClient(options.build());
    }
  }
}
