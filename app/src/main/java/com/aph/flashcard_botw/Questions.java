package com.aph.flashcard_botw;

import android.os.Parcel;
import android.os.Parcelable;

public class Questions implements Parcelable {
    private int imageId;
    private String question;
    private String difficulty;

    protected Questions(Parcel in) {
        imageId = in.readInt();
        question = in.readString();
        difficulty = in.readString();
    }

    public Questions(Object imageName, Object question, String bokoblin) {
        this.imageId = imageId;
        this.question = (String) question;
        this.difficulty = difficulty;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imageId);
        dest.writeString(question);
        dest.writeString(difficulty);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Questions> CREATOR = new Creator<Questions>() {
        @Override
        public Questions createFromParcel(Parcel in) {
            return new Questions(in);
        }

        @Override
        public Questions[] newArray(int size) {
            return new Questions[size];
        }
    };

    public int getimageId() {
        return imageId;
    }

    public String getQuestion() {
        return question;
    }

    public String getDifficulty() {
        return difficulty;
    }
}
