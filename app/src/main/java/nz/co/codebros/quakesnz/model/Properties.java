package nz.co.codebros.quakesnz.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Properties implements Parcelable {

    public static final Creator<Properties> CREATOR = new Creator<Properties>() {
        @Override
        public Properties createFromParcel(Parcel in) {
            return new Properties(in);
        }

        @Override
        public Properties[] newArray(int size) {
            return new Properties[size];
        }
    };

    @SerializedName("publicID")
    private String publicId;

    @SerializedName("time")
    private Date time;

    @SerializedName("depth")
    private double depth;

    @SerializedName("magnitude")
    private double magnitude;

    @SerializedName("locality")
    private String locality;

    @SerializedName("mmi")
    private int mmi;

    @SerializedName("quality")
    private String quality;

    protected Properties(Parcel in) {
        publicId = in.readString();
        time = (Date) in.readSerializable();
        depth = in.readDouble();
        magnitude = in.readDouble();
        locality = in.readString();
        mmi = in.readInt();
        quality = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public double getDepth() {
        return depth;
    }

    public String getLocality() {
        return locality;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public int getMmi() {
        return mmi;
    }

    public String getPublicId() {
        return publicId;
    }

    public String getQuality() {
        return quality;
    }

    public Date getTime() {
        return time;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(publicId);
        dest.writeSerializable(time);
        dest.writeDouble(depth);
        dest.writeDouble(magnitude);
        dest.writeString(locality);
        dest.writeInt(mmi);
        dest.writeString(quality);
    }
}
