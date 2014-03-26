package com.androideas.quakesnz.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Properties implements Parcelable {

    public static final Parcelable.Creator<Properties> CREATOR = new Creator<Properties>() {

        @Override
        public Properties createFromParcel(Parcel source) {
            return new Properties(source);
        }

        @Override
        public Properties[] newArray(int size) {
            return new Properties[size];
        }
    };

    private String agency;
    private float depth;
    private String intensity;
    private float magnitude;

    @SerializedName("origintime")
    private String originTime;

    @SerializedName("publicid")
    private String publicId;

    private String status;

    @SerializedName("updatetime")
    private String updateTime;

    public Properties(Parcel source) {
        agency = source.readString();
        depth = source.readFloat();
        intensity = source.readString();
        magnitude = source.readFloat();
        originTime = source.readString();
        publicId = source.readString();
        status = source.readString();
        updateTime = source.readString();
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    public String getAgency() {
        return agency;
    }

    public float getDepth() {
        return depth;
    }

    public String getIntensity() {
        return intensity;
    }

    public float getMagnitude() {
        return magnitude;
    }

    public String getOriginTime() {
        return originTime;
    }

    public String getPublicId() {
        return publicId;
    }

    public String getStatus() {
        return status;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public void setDepth(float depth) {
        this.depth = depth;
    }

    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }

    public void setMagnitude(float magnitude) {
        this.magnitude = magnitude;
    }

    public void setOriginTime(String originTime) {
        this.originTime = originTime;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
