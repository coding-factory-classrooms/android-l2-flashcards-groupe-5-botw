package com.aph.flashcard_botw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
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

    private String difficulty;
    private SoundPool sounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent srcIntent = getIntent();
        int totalQuestion = srcIntent.getIntExtra("totalQuestion", 0);
        int goodAnswers = srcIntent.getIntExtra("goodAnswers", 0);
        String difficulty = srcIntent.getStringExtra("difficulty");
        int percent = goodAnswers * 100 / totalQuestion ;

        ImageView resultImageView = findViewById(R.id.resultImageView);
        TextView difficultyTextView = findViewById(R.id.difficultyTextView);
        TextView totalTextView = findViewById(R.id.totalTextView);
        TextView percentTextView = findViewById(R.id.percentTextView);
        Context context = resultImageView.getContext();

        //Change the image depending of the percent of success
        if(percent == 100){
            resultImageView.setImageResource(R.drawable.champions);
            sounds = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
            MediaPlayer song = MediaPlayer.create(context, R.raw.result_4);
            song.start();
        }
        else if(percent > 50) {
            resultImageView.setImageResource(R.drawable.happy_zelda);
            sounds = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
            MediaPlayer song = MediaPlayer.create(context, R.raw.result_3);
            song.start();
        }
        else if (percent > 0) {
            resultImageView.setImageResource(R.drawable.sad_zelda);
            sounds = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
            MediaPlayer song = MediaPlayer.create(context, R.raw.result_2);
            song.start();
        }
        else {
            resultImageView.setImageResource(R.drawable.zelda_cry);
            sounds = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
            MediaPlayer song = MediaPlayer.create(context, R.raw.result_1);
            song.start();
        }

        //Display the statistics
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