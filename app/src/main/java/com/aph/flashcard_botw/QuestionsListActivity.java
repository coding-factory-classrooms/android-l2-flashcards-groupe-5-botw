package com.aph.flashcard_botw;

import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionsListActivity extends AppCompatActivity {

    private List<Questions> questions;
    private QuestionAdapter adapter;
    private String difficulty;
    private ArrayList questionList;
    //private ArrayList questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_list);

        questions = new ArrayList<>();

        String string = "";
        try {
            InputStream inputStream = getAssets().open("questions.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            string = new String(buffer);
            JSONObject jsonObject = new JSONObject(string);
            JSONObject bokoblinQuestion = jsonObject.getJSONObject("Bokoblin");
            JSONObject lezalfosQuestion = jsonObject.getJSONObject("Lezalfos");
            JSONObject lynelQuestion = jsonObject.getJSONObject("Lynel");

            //Add all the questions in an ArrayList
            questionList= new ArrayList<JSONObject>();
            for (int i = 0; i < bokoblinQuestion.length(); i++) {
                JSONObject jsonQuestion = bokoblinQuestion.getJSONObject(String.valueOf(i));
                String imageName =jsonQuestion.getString("imageName");
                int id = this.getResources().getIdentifier(imageName, "drawable", this.getPackageName());
                questions.add(new Questions(id, jsonQuestion.getString("question"), "Bokoblin"));
            }
            for (int i = 0; i < lezalfosQuestion.length(); i++) {
                JSONObject jsonQuestion = lezalfosQuestion.getJSONObject(String.valueOf(i));
                String imageName =jsonQuestion.getString("imageName");
                int id = this.getResources().getIdentifier(imageName, "drawable", this.getPackageName());
                questions.add(new Questions(id, jsonQuestion.getString("question"), "Lezalfos"));
            }
            for (int i = 0; i < lynelQuestion.length(); i++) {
                JSONObject jsonQuestion = lynelQuestion.getJSONObject(String.valueOf(i));
                String imageName =jsonQuestion.getString("imageName");
                int id = this.getResources().getIdentifier(imageName, "drawable", this.getPackageName());
                questions.add(new Questions(id, jsonQuestion.getString("question"), "Lynel"));
            }

            Log.i("questionList", questionList + "");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        adapter = new QuestionAdapter(questions);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}