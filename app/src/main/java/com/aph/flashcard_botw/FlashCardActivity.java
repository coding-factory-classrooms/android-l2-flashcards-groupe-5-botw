package com.aph.flashcard_botw;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

public class FlashCardActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton answerButton;
    private Button nextQuestionButton;

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

            addListenerOnButton(goodAnswer);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addListenerOnButton(String goodAnswer) {
        radioGroup = (RadioGroup) findViewById(R.id.answerRadioGroup);
        nextQuestionButton = (Button) findViewById(R.id.nextQuestionButton);

        nextQuestionButton.setOnClickListener(new View.OnClickListener() {

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

            }
        });
    }
}