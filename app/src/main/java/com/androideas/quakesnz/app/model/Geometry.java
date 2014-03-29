package com.androideas.quakesnz.app.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Geometry implements Parcelable {

    public static final Parcelable.Creator<Geometry> CREATOR = new Creator<Geometry>() {

        @Override
        public Geometry[] newArray(int size) {
            return new Geometry[size];
        }

        @Override
        public Geometry createFromParcel(Parcel source) {
            return new Geometry(source);
        }
    };

    private float[] coordinates;

    public Geometry(Parcel source) {
        coordinates = new float[source.readInt()];
        source.readFloatArray(coordinates);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public float[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(float[] coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(coordinates.length);
        dest.writeFloatArray(coordinates);
    }

}
