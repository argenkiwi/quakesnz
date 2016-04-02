package nz.co.codebros.quakesnz.ui;

import android.os.Bundle;

import nz.co.codebros.quakesnz.model.Geometry;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MyMapFragment extends SupportMapFragment implements OnMapReadyCallback {

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
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng location = new LatLng(getArguments().getDouble(ARG_LATITUDE),
                getArguments().getDouble(ARG_LONGITUDE));

        googleMap.addMarker(new MarkerOptions().position(location));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 6));
    }
}
