package nz.co.codebros.quakesnz.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Feature implements Parcelable {

    public static final Parcelable.Creator<Feature> CREATOR = new Creator<Feature>() {

        @Override
        public Feature createFromParcel(Parcel source) {
            return new Feature(source);
        }

        @Override
        public Feature[] newArray(int size) {
            return new Feature[size];
        }
    };

    private Geometry geometry;

    @SerializedName("geometry_name")
    private String geometryName;
    private String id;
    private Properties properties;
    private City closestCity;

    public Feature(Parcel source) {
        geometry = source.readParcelable(Geometry.class.getClassLoader());
        geometryName = source.readString();
        id = source.readString();
        properties = source.readParcelable(Properties.class.getClassLoader());
        closestCity = source.readParcelable(City.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public String getGeometryName() {
        return geometryName;
    }

    public String getId() {
        return id;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public void setGeometryName(String geometryName) {
        this.geometryName = geometryName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(geometry, flags);
        dest.writeString(geometryName);
        dest.writeString(id);
        dest.writeParcelable(properties, flags);
        dest.writeParcelable(closestCity, flags);
    }


    public City getClosestCity() {
        return closestCity;
    }

    public void setClosestCity(City closestCity) {
        this.closestCity = closestCity;
    }
}
