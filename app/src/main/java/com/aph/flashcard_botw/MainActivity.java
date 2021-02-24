package com.aph.flashcard_botw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CustomDialog dialog = new CustomDialog(MainActivity.this);
                dialog.show();

            }
        });

        Button aboutButton = findViewById(R.id.aboutButton);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User("Marion, Alyssia et Juan");

                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                intent.putExtra("author", user);
                intent.putExtra("versionName", "v1.2.3");
                startActivity(intent);
            }
        });
    }
}