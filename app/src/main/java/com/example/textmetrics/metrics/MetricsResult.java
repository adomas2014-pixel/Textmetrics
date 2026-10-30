package com.example.textmetrics.metrics;

/**
 * Immutable result of a text analysis.
 * Pure Java - no Android dependencies, so it can be reused in any project.
 */
public final class MetricsResult {

    private final int sentences;
    private final int words;
    private final int characters;
    private final int numbers;

    public MetricsResult(int sentences, int words, int characters, int numbers) {
        this.sentences = sentences;
        this.words = words;
        this.characters = characters;
        this.numbers = numbers;
    }

    public int getSentences() {
        return sentences;
    }

    public int getWords() {
        return words;
    }

    /** All characters of the text: letters, digits, spaces and punctuation. */
    public int getCharacters() {
        return characters;
    }

    /** Count of numbers (e.g. "1", "2026", "21,5" each count as one number). */
    public int getNumbers() {
        return numbers;
    }
}
