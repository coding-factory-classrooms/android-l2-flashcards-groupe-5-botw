package com.aph.flashcard_botw;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class EnlargedImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enlarged_image);

        ImageView imageView = findViewById(R.id.enlargedImageView);


        Intent srcIntent = getIntent();
        String image = srcIntent.getStringExtra("imageName");
        Log.i("steuplé", image);

        int id = getResources().getIdentifier(image, "drawable",getPackageName());
        imageView.setImageResource(id);

//        String string = "";
//        try {
//            JSONObject jsonObject = new JSONObject(string);
//            JSONObject bokoblin = jsonObject.getJSONObject("Bokoblin");
//            Log.i("jsonobject", bokoblin + "");
//
//            JSONObject questionInfo = bokoblin.getJSONObject("0");
//
//            String image = questionInfo.getString("imageName");
//            ImageView questionImage = findViewById(R.id.enlargedImageView);
//            Context context = questionImage.getContext();
//            int id = context.getResources().getIdentifier(image, "drawable", context.getPackageName());
//            questionImage.setImageResource(id);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }
}