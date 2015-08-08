package nz.co.codebros.quakesnz.interactor;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import nz.co.codebros.quakesnz.GeonetService;
import nz.co.codebros.quakesnz.R;
import nz.co.codebros.quakesnz.model.City;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.model.FeatureCollection;
import nz.co.codebros.quakesnz.utils.LatLngUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by leandro on 9/07/15.
 */
public class LoadQuakesInteractorImpl implements LoadQuakesInteractor {

    public static final int SCOPE_ALL = 0;
    public static final int SCOPE_FELT = 1;
    public static final String QUAKES_FILE = "%s.json";

    private static final String TAG = LoadQuakesInteractorImpl.class.getSimpleName();

    private final Context mContext;
    private final GeonetService mService;
    private final Gson mGson;

    public LoadQuakesInteractorImpl(Context context, GeonetService service, Gson gson) {
        mContext = context;
        mService = service;
        mGson = gson;
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

    private static String getFilterName(int scope) {
        String prefix;
        switch (scope) {
            case SCOPE_FELT:
                prefix = "felt";
                break;
            case SCOPE_ALL:
            default:
                prefix = "all";
        }
        return prefix;
    }

    @Override
    public void loadQuakes(OnQuakesLoadedListener listener) {
        InputStream input;
        InputStreamReader reader;
        try {
            // Load quakes.
            input = mContext.openFileInput(String.format(QUAKES_FILE, getFilterName(SCOPE_FELT)));
            reader = new InputStreamReader(input);
            Feature[] features = mGson.fromJson(reader, FeatureCollection.class).getFeatures();
            reader.close();
            input.close();

            // Load cities.
            input = mContext.getResources().openRawResource(R.raw.cities);
            reader = new InputStreamReader(input);
            City[] cities = mGson.fromJson(reader, City[].class);
            reader.close();
            input.close();

            final int count = features.length;
            for (int i = 0; i < count; i++) {
                features[i].setClosestCity(findClosest(features[i].getGeometry().getCoordinates(),
                        cities));
            }

            listener.onLoadQuakesSuccess(features);
        } catch (FileNotFoundException e) {
            Log.e(TAG, "I/O Exception.", e);
            listener.onLoadQuakesFailure();
        } catch (IOException e) {
            Log.e(TAG, "I/O Exception.", e);
            listener.onLoadQuakesFailure();
        }
    }

    @Override
    public void saveQuakes(Response response, OnQuakesSavedListener listener) {
        try {
            BufferedInputStream input = new BufferedInputStream(response.getBody().in());
            FileOutputStream output = mContext.openFileOutput(String.format(QUAKES_FILE,
                    getFilterName(SCOPE_FELT)), Context.MODE_PRIVATE);
            byte[] data = new byte[1024];
            int count;
            while ((count = input.read(data)) != -1) {
                output.write(data, 0, count);
            }
            output.close();
            input.close();
            listener.onSaveQuakesSuccess();
        } catch (IOException e) {
            Log.d(TAG, "I/O Exception.", e);
            listener.onSaveQuakesFailure();
        }
    }
}
