package com.example.textmetrics.metrics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Builds test text by picking random sentences from a pool.
 * The pool is passed in (e.g. from a localized string-array resource),
 * so this class has no Android dependencies.
 */
public final class SampleTextGenerator {

    private final List<String> pool;
    private final Random random;

    public SampleTextGenerator(String[] sentencePool, Random random) {
        if (sentencePool == null || sentencePool.length == 0) {
            throw new IllegalArgumentException("Sentence pool must not be empty");
        }
        this.pool = new ArrayList<>();
        Collections.addAll(this.pool, sentencePool);
        this.random = random;
    }

    /** Returns up to {@code sentenceCount} distinct random sentences joined by spaces. */
    public String generate(int sentenceCount) {
        List<String> shuffled = new ArrayList<>(pool);
        Collections.shuffle(shuffled, random);
        int n = Math.max(1, Math.min(sentenceCount, shuffled.size()));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (i > 0) {
                sb.append(' ');
            }
            sb.append(shuffled.get(i));
        }
        return sb.toString();
    }
}
