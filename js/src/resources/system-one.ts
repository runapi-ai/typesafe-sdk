import type { HttpClient, RequestOptions } from '@runapi.ai/core';
import { compactParams } from '@runapi.ai/core';
import type { SystemOneParams, SystemOneResponse } from '../types';

const ENDPOINT = '/api/v1/typesafe/system_one';

/**
 * Evaluates application state against named Choice, Score, and Noul questions.
 * This is a synchronous operation -- only `run()` is available (no create/get polling).
 */
export class SystemOne {
  constructor(private readonly http: HttpClient) {}

  /**
   * Submit a TypeSafe Jev structured-decision request (synchronous).
   * @param params State, model, and questions.
   * @param options Per-request overrides.
   * @returns Typed answers with probabilities, confidence, and usage.
   */
  async run(params: SystemOneParams, options?: RequestOptions): Promise<SystemOneResponse> {
    const body = compactParams(params);
    return this.http.request<SystemOneResponse>('POST', ENDPOINT, {
      body,
      ...options,
    });
  }
}
