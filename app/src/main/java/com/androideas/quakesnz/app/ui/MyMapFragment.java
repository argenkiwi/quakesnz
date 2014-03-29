package com.androideas.quakesnz.app.ui;

import android.os.Bundle;

import com.androideas.quakesnz.app.model.Geometry;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MyMapFragment extends SupportMapFragment {

    public static final String ARG_LATITUDE = "arg_latitude";
    public static final String ARG_LONGITUDE = "arg_longitude";

    public static MyMapFragment newInstance(Geometry geometry) {

        float[] coordinates = geometry.getCoordinates();

        Bundle args = new Bundle();
        args.putFloat(ARG_LATITUDE, coordinates[0]);
        args.putFloat(ARG_LONGITUDE, coordinates[1]);

        MyMapFragment f = new MyMapFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        GoogleMap map = getMap();

        LatLng location = new LatLng(getArguments().getFloat(ARG_LONGITUDE), getArguments().getFloat(ARG_LATITUDE));

        map.addMarker(new MarkerOptions().position(location));

        if (savedInstanceState == null) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 6));
        }
    }
}
