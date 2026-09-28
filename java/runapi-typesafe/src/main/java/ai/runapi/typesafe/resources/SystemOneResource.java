package ai.runapi.typesafe.resources;

import ai.runapi.core.ClientOptions;
import ai.runapi.core.RequestOptions;
import ai.runapi.core.http.HttpTransport;
import ai.runapi.typesafe.types.SystemOneParams;
import ai.runapi.typesafe.types.SystemOneResponse;

/** System One operations. */
public final class SystemOneResource extends TypesafeResource {
  /** API endpoint path for system one operations. */
  public static final String ENDPOINT = "/api/v1/typesafe/system_one";

  /** Creates a resource bound to the supplied transport and client options. */
  public SystemOneResource(HttpTransport transport, ClientOptions options) {
    super(transport, options, ENDPOINT);
  }

  /** Runs system one and returns the response. */
  public SystemOneResponse run(SystemOneParams params) {
    return run(params, RequestOptions.none());
  }

  /** Runs system one with per-request options and returns the response. */
  public SystemOneResponse run(SystemOneParams params, RequestOptions options) {
    return runSync(params.action(), params.toMap(), options, SystemOneResponse.class);
  }
}
