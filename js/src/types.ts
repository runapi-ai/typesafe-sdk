/** TypeSafe model identifier accepted by system-one. */
export type TypesafeModel = 'jev-latest';

/**
 * Parameters for a TypeSafe Jev structured-decision request.
 * `model` is sent on the wire.
 */
export interface SystemOneParams {
  /** Text or structured state to evaluate. */
  state: unknown;
  /** TypeSafe model identifier. */
  model: TypesafeModel | string;
  /** Named Choice, Score, or Noul questions. */
  questions: Record<string, unknown>;
}

/** Result of a synchronous system-one call. */
export interface SystemOneResponse {
  model?: string;
  answers?: Record<string, unknown>;
  usage?: Record<string, unknown>;
  [key: string]: unknown;
}
