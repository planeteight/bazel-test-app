package com.planeteight.pulseboard;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String SCORE_SERVICE = "cpp-pulseboard";
    private static final String INSIGHTS_SERVICE = "java-pulseboard";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView scoreValue = findViewById(R.id.scoreValue);
        ProgressBar scoreProgress = findViewById(R.id.scoreProgress);
        TextView habitsView = findViewById(R.id.habitsValue);
        TextView statusView = findViewById(R.id.statusValue);
        TextView servicesView = findViewById(R.id.servicesValue);
        TextView insightView = findViewById(R.id.insightValue);

        int[][] habits = {
                {6, 8},  // Hydration
                {3, 4},  // Focus Blocks
                {1, 1},  // Walk
                {20, 30} // Reading
        };

        String[] names = {"Hydration", "Focus Blocks", "Walk", "Reading"};

        StringBuilder habitsText = new StringBuilder();

        for (int i = 0; i < habits.length; i++) {
            int completed = habits[i][0];
            int target = habits[i][1];
            int percent = target == 0 ? 0 : (completed * 100) / target;
            habitsText.append(names[i])
                    .append(": ")
                    .append(completed)
                    .append("/")
                    .append(target)
                    .append(" (")
                    .append(percent)
                    .append("%)\n");
        }

        int dailyScore = 69;
        String scoreStatus = "Good start, keep the streak alive.";
        String recommendation = "Add one focused block for Reading to close the largest gap.";

        scoreValue.setText(dailyScore + "%");
        scoreProgress.setProgress(dailyScore);
        habitsView.setText(habitsText.toString().trim());
        servicesView.setText("Score: " + SCORE_SERVICE + "\nInsights: " + INSIGHTS_SERVICE);
        insightView.setText(recommendation);
        statusView.setText(scoreStatus);
    }
}
