package com.aph.flashcard_botw;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

public class FlashCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_card);

        JSONParser jsonP = new JSONParser();

        String string = "";
        try {
            InputStream inputStream = getAssets().open("questions.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            string = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JSONObject jsonObject = new JSONObject(string);
            JSONObject bokoblin = jsonObject.getJSONObject("Bokoblin");
            Log.i("jsonobject", bokoblin + "");

            JSONObject questionInfo = bokoblin.getJSONObject("0");

            String question = questionInfo.getString("question");

            TextView questionText = findViewById(R.id.questionTextView);
            questionText.setText(question);

            String goodAnswer = questionInfo.getString("goodAnswer");
            JSONArray badAnswers = questionInfo.getJSONArray("badAnswers");
            ArrayList <String> answers = new ArrayList<String>();
            answers.add(goodAnswer);
            for (int i = 0; i < badAnswers.length(); i++) {
                answers.add(badAnswers.getString(i));
            }
            Collections.shuffle(answers);

            RadioGroup answerRadioGroup = findViewById(R.id.answerRadioGroup);
            for (int j = 0; j < answers.size(); j++) {
                RadioButton answerButton = new RadioButton(this);
                answerButton.setText(answers.get(j));
                answerRadioGroup.addView(answerButton);
            }

            String image = questionInfo.getString("imageName");
            ImageView questionImage = findViewById(R.id.imageView);
            Context context = questionImage.getContext();
            int id = context.getResources().getIdentifier(image, "drawable", context.getPackageName());
            questionImage.setImageResource(id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}