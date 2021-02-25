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
    private ArrayList<JSONObject> questionList;
    private int questionIndex = 0;
    private int goodAnswersTotal = 0;
    private String difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_card);

        Intent srcIntent = getIntent();
        difficulty = srcIntent.getStringExtra("difficulty"); //get difficulty
        Boolean isOnlyOneQuestion = srcIntent.getBooleanExtra("isOnlyOneQuestion", true); //get the boolean saying if it's a normam quizz or only one question

        //If it's a normal quiz
        if(isOnlyOneQuestion == false) {
            String string = "";
            try {
                //get questions from Json
                InputStream inputStream = getAssets().open("questions.json");
                int size = inputStream.available();
                byte[] buffer = new byte[size];
                inputStream.read(buffer);
                string = new String(buffer);
                JSONObject jsonObject = new JSONObject(string);
                //Get questions from the right difficulty
                JSONObject questionListTemporary = jsonObject.getJSONObject(difficulty);
                questionList= new ArrayList<JSONObject>();
                for (int i = 0; i < questionListTemporary.length(); i++) { //Add each questions in ArrayList
                    questionList.add(questionListTemporary.getJSONObject(String.valueOf(i)));
                }
                Collections.shuffle(questionList); //Randomyze the order of questions
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            displayQuestion();
            addListenerOnButton();
        }

        //If it's only one question (from ListQuestionActivity)
        else{
            //Het the questions from extra
            Questions q = srcIntent.getParcelableExtra("question");
            try {
                String string = "";
                //get the questions list from json (for get threst of the infos of the question)
                InputStream inputStream = getAssets().open("questions.json");
                int size = inputStream.available();
                byte[] buffer = new byte[size];
                inputStream.read(buffer);
                string = new String(buffer);
                JSONObject jsonObject = new JSONObject(string);

                //Search the right questions infos in the right difficulty
                difficulty = q.getDifficulty();
                JSONObject questionListTemporary = jsonObject.getJSONObject(difficulty);
                questionList= new ArrayList<JSONObject>();
                for (int i = 0; i < questionListTemporary.length(); i++) {
                    //For if question question check if it's the right one by comparing the question title
                    JSONObject currentQuestion  =  questionListTemporary.getJSONObject(String.valueOf(i)) ;
                    String questionTitle = currentQuestion.getString("question");
                    Log.d("FlashCardFromList", questionTitle + "/" + q.getQuestion() );
                    if(questionTitle.equals(q.getQuestion())){
                        //If it's the right one add it to questionList and break
                        questionList.add(questionListTemporary.getJSONObject(String.valueOf(i)));
                        break;
                    }
                }
                displayQuestion();
                addListenerOnButton();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //Display the infos of a question
    public void displayQuestion() {
        try {
            JSONObject questionInfo = questionList.get(questionIndex);
            String question = questionInfo.getString("question");

            TextView questionText = findViewById(R.id.questionTextView);
            questionText.setText(question);

            goodAnswer = questionInfo.getString("goodAnswer"); //Save the right answer
            JSONArray badAnswers = questionInfo.getJSONArray("badAnswers");
            ArrayList <String> answers = new ArrayList<String>();
            answers.add(goodAnswer);
            for (int i = 0; i < badAnswers.length(); i++) {
                answers.add(badAnswers.getString(i));
            }
            //Randomize the order of the answers
            Collections.shuffle(answers);

            RadioGroup answerRadioGroup = findViewById(R.id.answerRadioGroup);
            answerRadioGroup.removeAllViews(); //Clear the radioGroup from the previous question
            TextView responseTextView = findViewById(R.id.responseTextView);
            responseTextView.setText("");
            //Add radio buttons for each answer
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

            //Display the index of the question
            TextView indexTextView = findViewById(R.id.indexTextView);
            indexTextView.setText((questionIndex+1) + "/" + questionList.size());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addListenerOnButton() {
        radioGroup = findViewById(R.id.answerRadioGroup);
        validateQuestionButton = findViewById(R.id.validateQuestionButton);
        nextQuestionButton = findViewById(R.id.nextQuestionButton);
        resultQuestionButton = findViewById(R.id.resultQuestionButton);

        validateQuestionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int radioButtonID = radioGroup.getCheckedRadioButtonId();

                //if no answer selected display a toast
                if (radioButtonID == -1) {
                    Toast.makeText(FlashCardActivity.this, "Veuillez choisir une réponse", Toast.LENGTH_SHORT).show();
                    return;
                }
                RadioButton radioButton = radioGroup.findViewById(radioButtonID);
                String selectedAnswer = (String) radioButton.getText();

                TextView responseTextView = findViewById(R.id.responseTextView);

                //Check if the user answer is correct
                if (selectedAnswer == goodAnswer) {
                    responseTextView.setText("Tintintintin");
                    goodAnswersTotal++; //Increment the total of good answers
                }
                else {
                    responseTextView.setText("T'es trop faible, va faire des sanctuaires et reviens après ! \n C'était : "+ goodAnswer);
                }

                questionIndex ++; //Increment the cirrent question index
                //Hide the current button and display the rerquired one
                validateQuestionButton.setVisibility(View.GONE);
                if (questionIndex < questionList.size()) {
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
                //Hide the current button and display the rerquired one
                nextQuestionButton.setVisibility(View.GONE);
                validateQuestionButton.setVisibility(View.VISIBLE);
                displayQuestion(); //Display the new question
            }
        });

        resultQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Display the result activity with the extra needed
                Intent intent = new Intent(FlashCardActivity.this, ResultActivity.class);
                //intent.putExtra("author", user);
                intent.putExtra("totalQuestion", questionList.size());
                intent.putExtra("goodAnswers", goodAnswersTotal);
                intent.putExtra("difficulty", difficulty);
                startActivity(intent);
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