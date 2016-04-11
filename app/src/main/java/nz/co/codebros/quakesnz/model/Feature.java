package nz.co.codebros.quakesnz.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class Feature implements Parcelable {

    public static final Creator<Feature> CREATOR = new Creator<Feature>() {
        @Override
        public Feature createFromParcel(Parcel in) {
            return new Feature(in);
        }

        @Override
        public Feature[] newArray(int size) {
            return new Feature[size];
        }
    };

    @SerializedName("geometry")
    private Geometry geometry;

    @SerializedName("properties")
    private Properties properties;

    protected Feature(Parcel in) {
        geometry = in.readParcelable(Geometry.class.getClassLoader());
        properties = in.readParcelable(Properties.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public Properties getProperties() {
        return properties;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(geometry, flags);
        dest.writeParcelable(properties, flags);
    }
}
