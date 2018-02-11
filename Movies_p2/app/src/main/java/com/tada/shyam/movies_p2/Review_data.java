package com.tada.shyam.movies_p2;

import android.os.Parcel;
import android.os.Parcelable;



public class Review_data implements Parcelable {

    public Review_data() {
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String authorName;
    private String content;
    protected Review_data(Parcel in) {

        authorName=in.readString();
        content=in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(authorName);
        dest.writeString(content);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Review_data> CREATOR = new Creator<Review_data>() {
        @Override
        public Review_data createFromParcel(Parcel in) {
            return new Review_data(in);
        }

        @Override
        public Review_data[] newArray(int size) {
            return new Review_data[size];
        }
    };
}
