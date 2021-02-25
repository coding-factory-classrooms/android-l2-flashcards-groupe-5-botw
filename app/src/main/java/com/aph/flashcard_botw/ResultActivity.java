package com.aph.flashcard_botw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

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

        int percent = goodAnswers * 100 / totalQuestion ;

        if(percent == 100){
            resultImageView.setImageResource(R.drawable.champions);
        }
        else if(percent > 50) {
            resultImageView.setImageResource(R.drawable.happy_zelda);
        }
        else if (percent > 0) {
            resultImageView.setImageResource(R.drawable.sad_zelda);
        }
        else {
            resultImageView.setImageResource(R.drawable.zelda_cry);
        }

        difficultyTextView.setText("Difficulté : " + difficulty);
        totalTextView.setText(goodAnswers + "/" + totalQuestion);
        percentTextView.setText(percent + "% de bonne réponses");

        Button backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intentImage = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(intentImage);
            }
        });
    }


}