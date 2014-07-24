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

        LatLng coordinates = geometry.getCoordinates();

        Bundle args = new Bundle();
        args.putDouble(ARG_LATITUDE, coordinates.latitude);
        args.putDouble(ARG_LONGITUDE, coordinates.longitude);

        MyMapFragment f = new MyMapFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        GoogleMap map = getMap();

        LatLng location = new LatLng(getArguments().getDouble(ARG_LATITUDE),
                getArguments().getDouble(ARG_LONGITUDE));

        map.addMarker(new MarkerOptions().position(location));

        if (savedInstanceState == null) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 6));
        }
    }
}
