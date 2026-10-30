package com.example.textmetrics;

import androidx.lifecycle.ViewModel;

import com.example.textmetrics.metrics.MetricsResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Holds the runtime history of calculations. Survives screen rotation,
 * but is cleared when the app process ends (history is runtime-only).
 */
public class MetricsViewModel extends ViewModel {

    public static final class Entry {
        private final String text;
        private final MetricsResult result;

        Entry(String text, MetricsResult result) {
            this.text = text;
            this.result = result;
        }

        public String getText() {
            return text;
        }

        public MetricsResult getResult() {
            return result;
        }
    }

    private final List<Entry> history = new ArrayList<>();

    /** Adds a new entry; the newest entry is always first. */
    public void add(String text, MetricsResult result) {
        history.add(0, new Entry(text, result));
    }

    public List<Entry> getHistory() {
        return Collections.unmodifiableList(history);
    }
}
