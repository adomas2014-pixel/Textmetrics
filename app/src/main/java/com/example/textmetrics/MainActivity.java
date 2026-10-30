package com.example.textmetrics;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.textmetrics.metrics.MetricsResult;
import com.example.textmetrics.metrics.SampleTextGenerator;
import com.example.textmetrics.metrics.TextMetrics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_GENERATED_SENTENCES = 3;

    private final Random random = new Random();

    private EditText inputText;
    private TextView resultText;
    private ArrayAdapter<String> historyAdapter;
    private MetricsViewModel viewModel;
    private SampleTextGenerator generator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(
                    WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.ime());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        viewModel = new ViewModelProvider(this).get(MetricsViewModel.class);

        inputText = findViewById(R.id.inputText);
        resultText = findViewById(R.id.resultText);

        ListView historyList = findViewById(R.id.historyList);
        historyList.setEmptyView(findViewById(R.id.historyEmpty));
        historyAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, new ArrayList<>());
        historyList.setAdapter(historyAdapter);

        generator = new SampleTextGenerator(
                getResources().getStringArray(R.array.sample_sentences), random);

        findViewById(R.id.calculateButton).setOnClickListener(v -> onCalculate());
        findViewById(R.id.generateButton).setOnClickListener(v -> onGenerate());

        refreshHistory();
    }

    private void onCalculate() {
        String text = inputText.getText().toString();

        if (text.trim().isEmpty()) {
            resultText.setText("");
            inputText.requestFocus();
            inputText.setError(getString(R.string.error_empty_input));
            return;
        }

        inputText.setError(null);
        MetricsResult result = TextMetrics.analyze(text);
        resultText.setText(getString(R.string.result_format,
                result.getSentences(),
                result.getWords(),
                result.getCharacters(),
                result.getNumbers()));

        viewModel.add(text, result);
        refreshHistory();
    }

    private void onGenerate() {
        int count = 1 + random.nextInt(MAX_GENERATED_SENTENCES);
        inputText.setError(null);
        inputText.setText(generator.generate(count));
        inputText.setSelection(inputText.length());
    }

    private void refreshHistory() {
        List<String> lines = new ArrayList<>();
        for (MetricsViewModel.Entry entry : viewModel.getHistory()) {
            MetricsResult r = entry.getResult();
            lines.add(getString(R.string.history_entry,
                    entry.getText(),
                    r.getSentences(),
                    r.getWords(),
                    r.getCharacters(),
                    r.getNumbers()));
        }
        historyAdapter.clear();
        historyAdapter.addAll(lines);
    }
}
