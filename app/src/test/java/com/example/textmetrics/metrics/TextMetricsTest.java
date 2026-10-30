package com.example.textmetrics.metrics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TextMetricsTest {

    private static void assertMetrics(String text, int sentences, int words, int chars, int numbers) {
        MetricsResult r = TextMetrics.analyze(text);
        assertEquals("sentences", sentences, r.getSentences());
        assertEquals("words", words, r.getWords());
        assertEquals("characters", chars, r.getCharacters());
        assertEquals("numbers", numbers, r.getNumbers());
    }

    @Test
    public void assignmentExample1() {
        assertMetrics("Labas rytas", 1, 2, 11, 0);
    }

    @Test
    public void assignmentExample2() {
        assertMetrics("Rugsėjo 1 prasidėjo karas. Liūdna", 2, 4, 33, 1);
    }

    @Test
    public void emptyAndNullText() {
        assertMetrics("", 0, 0, 0, 0);
        assertMetrics(null, 0, 0, 0, 0);
    }

    @Test
    public void decimalNumberIsOneNumberAndDoesNotSplitSentence() {
        assertMetrics("Temperatūra yra 21,5 laipsnio.", 1, 3, 30, 1);
    }

    @Test
    public void multipleTerminators() {
        assertMetrics("Valio! Ar tikrai?! Taip.", 3, 4, 24, 0);
    }
}
