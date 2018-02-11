package com.tada.shyam.movies_p2;

import android.os.Parcel;
import android.os.Parcelable;



public class Trailer_data implements Parcelable {

    private final String videoUrl;

    public Trailer_data() {videoUrl = "https://www.youtube.com/watch?v=";
    }

    public String getVideoUrl() {
        return videoUrl+getKey();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String key;
    private String name;
    protected Trailer_data(Parcel in) {
        videoUrl=in.readString();
        key=in.readString();
        name=in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(videoUrl);
        dest.writeString(key);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Trailer_data> CREATOR = new Creator<Trailer_data>() {
        @Override
        public Trailer_data createFromParcel(Parcel in) {
            return new Trailer_data(in);
        }

        @Override
        public Trailer_data[] newArray(int size) {
            return new Trailer_data[size];
        }
    };
}
