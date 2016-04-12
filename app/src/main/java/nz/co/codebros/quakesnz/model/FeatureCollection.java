package nz.co.codebros.quakesnz.model;

import com.google.gson.annotations.SerializedName;

public class FeatureCollection {

    @SerializedName("features")
    private Feature[] features;

    public Feature[] getFeatures() {
        return features;
    }
}
