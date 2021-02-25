package com.aph.flashcard_botw;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    private List<Questions> questions;

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
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}
