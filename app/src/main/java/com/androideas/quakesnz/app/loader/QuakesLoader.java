package com.androideas.quakesnz.app.loader;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.androideas.quakesnz.app.R;
import com.androideas.quakesnz.app.model.City;
import com.androideas.quakesnz.app.model.Feature;
import com.androideas.quakesnz.app.model.FeatureCollection;
import com.androideas.quakesnz.app.service.GeonetService;
import com.androideas.quakesnz.app.utils.DateDeserializer;
import com.androideas.quakesnz.app.utils.LatLngAdapter;
import com.androideas.quakesnz.app.utils.LatLngUtils;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

/**
 * Created by leandro on 24/05/14.
 */
public class QuakesLoader extends AsyncTaskLoader<Feature[]> {

    private static final String TAG = QuakesLoader.class.getSimpleName();
    private final int mScope;

    public QuakesLoader(Context context, int scope) {
        super(context);
        mScope = scope;
    }

    @Override
    public Feature[] loadInBackground() {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LatLng.class, new LatLngAdapter())
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .create();

        try {
            // Load quakes.
            InputStream input = getContext().openFileInput(GeonetService.getFileNameForScope(mScope));
            InputStreamReader reader = new InputStreamReader(input);

            FeatureCollection featureCollection = gson.fromJson(reader,
                    FeatureCollection.class);

            reader.close();
            input.close();

            // Load cities.
            input = getContext().getResources().openRawResource(R.raw.cities);
            reader = new InputStreamReader(input);

            City[] cities = gson.fromJson(reader, City[].class);

            reader.close();
            input.close();

            Feature[] features = featureCollection.getFeatures();

            Feature aux;
            final int count = features.length;
            for (int i = 0; i < count / 2; i++) {
                aux = features[i];
                features[i] = features[count - 1 - i];
                features[count - 1 - i] = aux;

                features[i].setClosestCity(findClosest(features[i].getGeometry().getCoordinates(), cities));
                aux.setClosestCity(findClosest(aux.getGeometry().getCoordinates(), cities));
            }

            return features;

        } catch (IOException e) {
            Log.e(TAG, "I/O Exception.", e);
        }

        return new Feature[0];
    }

    private City findClosest(LatLng coordinates, City[] cities) {

        City result = null;

        double minDistance = Double.MAX_VALUE;
        for (City city : cities) {
            final double distance = LatLngUtils.findDistance(coordinates, city.getCoordinates());
            if (minDistance > distance) {
                minDistance = distance;
                result = city;
            }
        }

        return result;
    }

}
