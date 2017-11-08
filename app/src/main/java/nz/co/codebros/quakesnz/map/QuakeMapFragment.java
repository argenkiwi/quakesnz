package nz.co.codebros.quakesnz.map;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import nz.co.codebros.quakesnz.core.model.Coordinates;
import nz.co.codebros.quakesnz.core.model.Feature;
import nz.co.codebros.quakesnz.repository.FeatureRepository;

/**
 * Created by Leandro on 9/11/2017.
 */

public class QuakeMapFragment extends SupportMapFragment {

    @Inject
    FeatureRepository repository;

    private final CompositeDisposable disposables = new CompositeDisposable();
    private Marker marker;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        disposables.add(repository.subscribe(new Consumer<Feature>() {
            @Override
            public void accept(final Feature feature) throws Exception {
                getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        Coordinates coordinates = feature.getGeometry().getCoordinates();
                        LatLng latLng = new LatLng(
                                coordinates.getLatitude(),
                                coordinates.getLongitude()
                        );
                        if (marker == null) {
                            marker = googleMap.addMarker(new MarkerOptions().position(latLng));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 6f));
                        } else {
                            marker.setPosition(latLng);
                            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                        }
                    }
                });
            }
        }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposables.dispose();
    }
}
