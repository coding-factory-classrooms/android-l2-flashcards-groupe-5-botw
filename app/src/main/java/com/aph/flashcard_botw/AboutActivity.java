package com.aph.flashcard_botw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Intent srcIntent = getIntent();
        User u = srcIntent.getParcelableExtra("author");
        String versionName = srcIntent.getStringExtra("versionName");

        TextView nameTextView = findViewById(R.id.nameTextView);
        nameTextView.setText(u.name);
        TextView versionTextView = findViewById(R.id.versionTextView);
        versionTextView.setText(versionName);
    }
}