package nz.co.codebros.quakesnz.interactor;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import nz.co.codebros.quakesnz.GeonetService;
import nz.co.codebros.quakesnz.R;
import nz.co.codebros.quakesnz.model.City;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.model.FeatureCollection;
import nz.co.codebros.quakesnz.utils.DateDeserializer;
import nz.co.codebros.quakesnz.utils.LatLngAdapter;
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

    public LoadQuakesInteractorImpl(Context context, GeonetService service) {
        mContext = context;
        mService = service;
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
    public void downloadQuakes(final Listener listener) {

        mService.listAllQuakes(getFilterName(SCOPE_FELT), new Callback<FeatureCollection>() {

            @Override
            public void success(FeatureCollection featureCollection, Response response) {
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
                    listener.onQuakesDownloaded();
                } catch (IOException e) {
                    Log.d(TAG, "I/O Exception.", e);
                    listener.onQuakesDownloaded();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "Retrofit Error.", error);
                listener.onQuakesDownloaded();
            }
        });
    }

    @Override
    public void loadQuakes(final Listener listener) {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LatLng.class, new LatLngAdapter())
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .create();

        InputStream input;
        InputStreamReader reader;
        try {
            // Load quakes.
            input = mContext.openFileInput(String.format(QUAKES_FILE, getFilterName(SCOPE_FELT)));
            reader = new InputStreamReader(input);
            Feature[] features = gson.fromJson(reader, FeatureCollection.class).getFeatures();
            reader.close();
            input.close();

            // Load cities.
            input = mContext.getResources().openRawResource(R.raw.cities);
            reader = new InputStreamReader(input);
            City[] cities = gson.fromJson(reader, City[].class);
            reader.close();
            input.close();

            Feature aux;
            final int count = features.length;
            for (int i = 0; i < count / 2; i++) {
                aux = features[i];
                features[i] = features[count - 1 - i];
                features[count - 1 - i] = aux;

                features[i].setClosestCity(findClosest(features[i].getGeometry().getCoordinates(), cities));
                aux.setClosestCity(findClosest(aux.getGeometry().getCoordinates(), cities));
            }

            listener.onQuakesLoaded(features);
        } catch (FileNotFoundException e) {
            Log.e(TAG, "I/O Exception.", e);
            listener.onQuakesLoadFailed();
        } catch (IOException e) {
            Log.e(TAG, "I/O Exception.", e);
            listener.onQuakesLoadFailed();
        }
    }
}
