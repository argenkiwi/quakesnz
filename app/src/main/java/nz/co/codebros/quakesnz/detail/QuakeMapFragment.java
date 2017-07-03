package nz.co.codebros.quakesnz.detail;

import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import nz.co.codebros.quakesnz.core.model.Coordinates;
import nz.co.codebros.quakesnz.core.model.Feature;
import nz.co.codebros.quakesnz.core.model.Geometry;
import nz.co.codebros.quakesnz.repository.FeatureRepository;

/**
 * Created by leandro on 29/06/17.
 */

public class QuakeMapFragment extends SupportMapFragment {

    @Inject
    FeatureRepository repository;

    private Disposable disposable;
    private Marker marker;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.moveCamera(CameraUpdateFactory
                        .newLatLngZoom(new LatLng(-41.3090732, 175.1858282), 4.5f));
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        disposable = repository.subscribe(new Consumer<Feature>() {
            @Override
            public void accept(@NonNull final Feature feature) throws Exception {
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
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if (disposable != null) disposable.dispose();
    }
}
