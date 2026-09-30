import { beforeEach, describe, expect, it, vi } from 'vitest';
import type { HttpClient } from '@runapi.ai/core';
import { SystemOne } from '../../src/resources/system-one';

describe('TypeSafe resources', () => {
  const mockHttp: HttpClient = {
    request: vi.fn(),
  };

  beforeEach(() => {
    vi.clearAllMocks();
  });

  it('posts system-one with model on the wire', async () => {
    vi.mocked(mockHttp.request).mockResolvedValueOnce({
      model: 'jev-1.13.0',
      answers: { recommendation: { type: 'choice', choice: 'Option A', probabilities: { 'Option A': 0.88, 'Option B': 0.12 }, confidence: 0.81 } },
      usage: { input_tokens: 318, output_tokens: 34 },
    });
    const systemOne = new SystemOne(mockHttp);
    const params = {
      model: 'jev-latest',
      state: { candidate: 'Option A' },
      questions: { recommendation: { type: 'choice', instructions: 'Choose the matching candidate.', criteria: { 'Option A': 'The candidate is Option A.', 'Option B': 'The candidate is Option B.' } } },
    };

    const result = await systemOne.run(params);

    expect(mockHttp.request).toHaveBeenCalledWith('POST', '/api/v1/typesafe/system_one', {
      body: params,
    });
    expect(result.answers).toEqual({ recommendation: { type: 'choice', choice: 'Option A', probabilities: { 'Option A': 0.88, 'Option B': 0.12 }, confidence: 0.81 } });
    expect(result.model).toBe('jev-1.13.0');
    expect(result.usage).toEqual({ input_tokens: 318, output_tokens: 34 });
  });
});
