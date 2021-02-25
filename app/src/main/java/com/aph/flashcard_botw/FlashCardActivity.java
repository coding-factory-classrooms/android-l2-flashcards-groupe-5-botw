package com.aph.flashcard_botw;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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

    private RadioGroup radioGroup;
    private Button nextQuestionButton;
    private Button validateQuestionButton;
    private Button resultQuestionButton;
    private String goodAnswer;
    private String image;
    private JSONObject questionList;
    private int questionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_card);

        Intent srcIntent = getIntent();
        String difficulty = srcIntent.getStringExtra("difficulty");

        String string = "";
        try {
            InputStream inputStream = getAssets().open("questions.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            string = new String(buffer);
            JSONObject jsonObject = new JSONObject(string);
            questionList = jsonObject.getJSONObject(difficulty);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        displayQuestion();
        addListenerOnButton(goodAnswer, image);
    }

    public void displayQuestion() {
        try {
            JSONObject questionInfo = questionList.getJSONObject(String.valueOf(questionIndex));

            String question = questionInfo.getString("question");

            TextView questionText = findViewById(R.id.questionTextView);
            questionText.setText(question);

            goodAnswer = questionInfo.getString("goodAnswer");
            JSONArray badAnswers = questionInfo.getJSONArray("badAnswers");
            ArrayList <String> answers = new ArrayList<String>();
            answers.add(goodAnswer);
            for (int i = 0; i < badAnswers.length(); i++) {
                answers.add(badAnswers.getString(i));
            }
            Collections.shuffle(answers);

            RadioGroup answerRadioGroup = findViewById(R.id.answerRadioGroup);
            answerRadioGroup.removeAllViews();
            TextView responseTextView = findViewById(R.id.responseTextView);
            responseTextView.setText("");
            for (int j = 0; j < answers.size(); j++) {
                RadioButton answerButton = new RadioButton(this);
                answerButton.setText(answers.get(j));
                answerRadioGroup.addView(answerButton);
            }

            image = questionInfo.getString("imageName");
            ImageView questionImage = findViewById(R.id.imageButton);
            Context context = questionImage.getContext();
            int id = context.getResources().getIdentifier(image, "drawable", context.getPackageName());
            questionImage.setImageResource(id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addListenerOnButton(String goodAnswer, String image) {
        radioGroup = (RadioGroup) findViewById(R.id.answerRadioGroup);
        validateQuestionButton = (Button) findViewById(R.id.validateQuestionButton);
        nextQuestionButton = (Button) findViewById(R.id.nextQuestionButton);
        resultQuestionButton = (Button) findViewById(R.id.resultQuestionButton);

        validateQuestionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int radioButtonID = radioGroup.getCheckedRadioButtonId();

                Log.i("radioButton", radioButtonID + "");
                if (radioButtonID == -1) {
                    Toast.makeText(FlashCardActivity.this, "Veuillez choisir une réponse", Toast.LENGTH_SHORT).show();
                    return;
                }
                RadioButton radioButton = radioGroup.findViewById(radioButtonID);
                String selectedAnswer = (String) radioButton.getText();

                TextView responseTextView = findViewById(R.id.responseTextView);

                if (selectedAnswer == goodAnswer) {
                    responseTextView.setText("Tintintintin");
                }
                else {
                    responseTextView.setText("T'es pourri mec");
                }

                questionIndex ++;
                validateQuestionButton.setVisibility(View.GONE);
                if (questionIndex <= questionList.length()) {
                    nextQuestionButton.setVisibility(View.VISIBLE);
                }
                else {
                    resultQuestionButton.setVisibility(View.VISIBLE);
                }
            }
        });

        nextQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestionButton.setVisibility(View.GONE);
                validateQuestionButton.setVisibility(View.VISIBLE);
                displayQuestion();
            }
        });

        ImageButton enlargeImage = findViewById(R.id.imageButton);
        enlargeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentImage = new Intent(FlashCardActivity.this, EnlargedImageActivity.class);
                intentImage.putExtra("imageName", image);
                startActivity(intentImage);
            }
        });
    }
}