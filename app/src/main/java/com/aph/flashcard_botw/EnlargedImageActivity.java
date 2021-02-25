package com.aph.flashcard_botw;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class EnlargedImageActivity extends AppCompatActivity {

    @Override
    //Create a view with only an image
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enlarged_image);

        ImageView imageView = findViewById(R.id.enlargedImageView);

        Intent srcIntent = getIntent();
        String image = srcIntent.getStringExtra("imageName");

        int id = getResources().getIdentifier(image, "drawable", getPackageName());
        imageView.setImageResource(id);
    }
}
