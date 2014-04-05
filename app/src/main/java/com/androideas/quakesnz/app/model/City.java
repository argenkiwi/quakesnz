package com.androideas.quakesnz.app.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Leandro on 02/04/2014.
 */
public class City {

    private String name;
    private LatLng coordinates;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(LatLng coordinates) {
        this.coordinates = coordinates;
    }
}
