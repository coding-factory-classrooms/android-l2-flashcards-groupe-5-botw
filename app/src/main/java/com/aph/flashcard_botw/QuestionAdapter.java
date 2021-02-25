package com.aph.flashcard_botw;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> implements View.OnClickListener {

    private List<Questions> questions;

    public QuestionAdapter(List<Questions> questions) {
        this.questions = questions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_questions, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Questions question = this.questions.get(position);
        holder.image.setImageResource(question.getimageId());
        holder.question.setText(question.getQuestion());
        holder.difficulty.setText(question.getDifficulty());
        holder.itemView.setTag(question);
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    @Override
    public void onClick(View v) {
        Log.d("QuestionListClick", "Click");
        switch (v.getId()){
            case R.id.rootQuestionItem:
                //Start a flashCardActivity with the extra needed
                Questions question = (Questions) v.getTag();
                Context context = v.getContext();
                Intent intent = new Intent(context, FlashCardActivity.class);
                intent.putExtra("question", question);
                intent.putExtra("isOnlyOneQuestion", true);
                context.startActivity(intent);
                break;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView image;
        final TextView difficulty;
        final TextView question;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.questionImageView);
            difficulty = itemView.findViewById(R.id.difficultyTextView);
            question = itemView.findViewById(R.id.previewQuestionTextView);
        }
    }

}
