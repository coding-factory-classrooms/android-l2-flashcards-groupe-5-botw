package com.aph.flashcard_botw;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import javax.security.auth.login.LoginException;

public class CustomDialog extends Dialog {


    public void difficultyChoose(String difficulty){
        Log.i("dialog", difficulty);
        Intent intent = new Intent(this.context, FlashCardActivity.class);
        intent.putExtra("difficulty", difficulty);
        this.context.startActivity(intent);
    };

    public Context context;
    private Button submitDifficultyButton;
    private Button cancelButton;
    private RadioGroup difficultyRatiopGroup;

    public CustomDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_difficulty_dialo);

        this.submitDifficultyButton = (Button) findViewById(R.id.submitDifficultyButton);
        this.cancelButton = (Button) findViewById(R.id.cancelButton);
        this.difficultyRatiopGroup = (RadioGroup) findViewById(R.id.difficultyRadioGroup) ;

        this.submitDifficultyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonOKClick();
            }
        });
        this.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonCancelClick();
            }
        });
    }

    // User click "OK" button.
    private void buttonOKClick() {
        int radioButtonID = difficultyRatiopGroup.getCheckedRadioButtonId();

        Log.i("DialogDifficulty", "RatioId: " + radioButtonID);

        if (radioButtonID == -1) {
            Log.i("DialogDifficulty", "Enter if");
            Toast.makeText(this.context, "Veuillez choisir une réponse", Toast.LENGTH_SHORT).show();
            Log.i("DialogDifficulty", "After toast");
            return;
        }
        RadioButton radioButton = difficultyRatiopGroup.findViewById(radioButtonID);
        String selectedAnswer = (String) radioButton.getText();
        Log.i("DialogDifficulty", "Difficulty: " + selectedAnswer);

        this.dismiss(); // Close Dialog
        this.difficultyChoose(selectedAnswer);
    }

    // User click "Cancel" button.
    private void buttonCancelClick() {
        this.dismiss();
    }
}