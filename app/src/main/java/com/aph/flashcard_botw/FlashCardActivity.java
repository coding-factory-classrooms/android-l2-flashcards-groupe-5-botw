package com.aph.flashcard_botw;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("string", string);

       /* try {
            InputStreamReader jsonFile = new InputStreamReader(getAssets().open("questions.json"));
            BufferedReader bReader = new BufferedReader(jsonFile);
            String line = bReader.readLine();
            StringBuffer buff = new StringBuffer();
            while (line != null) {
                buff.append(line);
                line = bReader.readLine();
            }
            Log.i("buffToString", buff.toString());
            Object obj = jsonP.parse(buff.toString());
            JSONArray questionsList = (JSONArray) obj;
            Log.i("jsonReader", String.valueOf(questionsList));

        } catch (IOException | ParseException e) {
            Log.e("ReadJsonFailed", String.valueOf(e));
        }*/
    }
}