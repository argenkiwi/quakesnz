package nz.co.codebros.quakesnz.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

public class Geometry implements Parcelable {

    public static final Creator<Geometry> CREATOR = new Creator<Geometry>() {
        @Override
        public Geometry createFromParcel(Parcel in) {
            return new Geometry(in);
        }

        @Override
        public Geometry[] newArray(int size) {
            return new Geometry[size];
        }
    };

    @SerializedName("coordinates")
    private LatLng coordinates;

    protected Geometry(Parcel in) {
        coordinates = in.readParcelable(LatLng.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(coordinates, flags);
    }
}
