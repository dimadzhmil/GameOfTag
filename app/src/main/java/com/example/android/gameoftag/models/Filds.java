package com.example.android.gameoftag.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by User on 26.03.2016.
 */
public class Filds implements Parcelable {

    public Filds(){

    }

    protected Filds(Parcel in) {
    }

    public static final Creator<Filds> CREATOR = new Creator<Filds>() {
        @Override
        public Filds createFromParcel(Parcel in) {
            return new Filds(in);
        }

        @Override
        public Filds[] newArray(int size) {
            return new Filds[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
