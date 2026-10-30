package com.example.textmetrics.metrics;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Calculates text metrics: sentences, words, characters and numbers.
 * Pure Java - no Android dependencies, so the class can be reused elsewhere.
 *
 * Definitions:
 * - sentence:   a run of text ended by . ! ? or an ellipsis followed by whitespace
 *               (or by the end of the text); the last sentence needs no terminator
 * - word:       a sequence of letters, optionally joined by a hyphen or apostrophe;
 *               numbers are NOT words
 * - character:  every character of the text, including spaces and punctuation
 * - number:     a sequence of digits, optionally with decimal/thousand separators
 *               (e.g. 1, 2026, 21,5, 3.14)
 */
public final class TextMetrics {

    private static final String LETTER = "[\\p{L}\\p{M}]";

    private static final Pattern WORD =
            Pattern.compile(LETTER + "+(?:['\u2019\\-]" + LETTER + "+)*");

    private static final Pattern NUMBER =
            Pattern.compile("\\d+(?:[.,]\\d+)*");

    private static final Pattern SENTENCE_BREAK =
            Pattern.compile("(?<=[.!?\u2026])\\s+");

    private static final Pattern LETTER_OR_DIGIT =
            Pattern.compile("[\\p{L}\\p{M}\\d]");

    private TextMetrics() {
        // utility class
    }

    public static MetricsResult analyze(String text) {
        if (text == null) {
            return new MetricsResult(0, 0, 0, 0);
        }
        return new MetricsResult(
                countSentences(text),
                countMatches(WORD, text),
                text.codePointCount(0, text.length()),
                countMatches(NUMBER, text));
    }

    private static int countSentences(String text) {
        int count = 0;
        for (String part : SENTENCE_BREAK.split(text)) {
            if (LETTER_OR_DIGIT.matcher(part).find()) {
                count++;
            }
        }
        return count;
    }

    private static int countMatches(Pattern pattern, String text) {
        Matcher matcher = pattern.matcher(text);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }
}
