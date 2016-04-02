package nz.co.codebros.quakesnz.utils;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import nz.co.codebros.quakesnz.R;
import nz.co.codebros.quakesnz.model.City;
import nz.co.codebros.quakesnz.model.Feature;

/**
 * Created by leandro on 2/04/16.
 */
public class LoadCitiesHelper {

    private final Context context;
    private final Gson gson;

    public LoadCitiesHelper(Context context, Gson gson) {
        this.context = context;
        this.gson = gson;
    }

    private static City findClosest(LatLng coordinates, City[] cities) {
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

    public Feature[] execute(Feature[] features) throws IOException {
        InputStream input = context.getResources().openRawResource(R.raw.cities);
        InputStreamReader reader = new InputStreamReader(input);
        City[] cities = gson.fromJson(reader, City[].class);
        reader.close();
        input.close();

        for (Feature feature : features) {
            feature.setClosestCity(findClosest(feature.getGeometry().getCoordinates(),
                    cities));
        }

        return features;
    }
}
