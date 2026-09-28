import { BaseClient, type ClientOptions } from '@runapi.ai/core';
import { SystemOne } from './resources/system-one';

/**
 * TypeSafe Jev client for typed decisions in software.
 *
 * @example
 * ```typescript
 * import { TypesafeClient } from '@runapi.ai/typesafe';
 * const client = new TypesafeClient({ apiKey: 'sk-...' });
 * const result = await client.systemOne.run({
 *   model: 'jev-latest',
 *   state: { candidate: 'Option A', context: 'Choose the best candidate.' },
 *   questions: {
 *     recommendation: {
 *       type: 'choice',
 *       instructions: 'Which candidate should be selected?',
 *       criteria: { 'Option A': 'The candidate is Option A.', 'Option B': 'The candidate is Option B.' },
 *     },
 *   },
 * });
 * console.log(result.answers);
 * ```
 */
export class TypesafeClient extends BaseClient {
  /** Evaluates application state against Choice, Score, and Noul questions (synchronous). */
  public readonly systemOne: SystemOne;

  constructor(options: ClientOptions = {}) {
    super(options);
    this.systemOne = new SystemOne(this.http);
  }
}
