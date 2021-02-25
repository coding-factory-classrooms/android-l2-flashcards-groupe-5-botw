package com.aph.flashcard_botw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent srcIntent = getIntent();
        int totalQuestion = srcIntent.getIntExtra("totalQuestion", 0);
        int goodAnswers = srcIntent.getIntExtra("goodAnswers", 0);
        String difficulty = srcIntent.getStringExtra("difficulty");

        ImageView resultImageView = findViewById(R.id.resultImageView);
        TextView difficultyTextView = findViewById(R.id.difficultyTextView);
        TextView totalTextView = findViewById(R.id.totalTextView);
        TextView percentTextView = findViewById(R.id.percentTextView);

        int percent = goodAnswers / totalQuestion * 100;

        if(percent > 50) {
            resultImageView.setImageResource(R.drawable.happy_zelda);
        }
        else{
            resultImageView.setImageResource(R.drawable.zelda_cry);
        }

        difficultyTextView.setText("Difficulté : " + difficulty);
        totalTextView.setText(goodAnswers + "/" + totalQuestion);
        percentTextView.setText(percent+ "% de bonne réponses");

    }
}