package nz.co.codebros.quakesnz.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

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

    private LatLng coordinates;

    public Geometry(Parcel source) {
        coordinates = source.readParcelable(LatLng.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(LatLng coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(coordinates, flags);
    }

}
