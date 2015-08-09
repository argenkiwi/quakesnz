package nz.co.codebros.quakesnz.interactor;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import nz.co.codebros.quakesnz.R;
import nz.co.codebros.quakesnz.model.City;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.model.FeatureCollection;
import nz.co.codebros.quakesnz.utils.AsyncTaskResult;
import nz.co.codebros.quakesnz.utils.LatLngUtils;
import retrofit.client.Response;

/**
 * Created by leandro on 9/07/15.
 */
public class LoadQuakesInteractor {

    public static final String QUAKES_FILE = "quakes.json";

    private static final String TAG = LoadQuakesInteractor.class.getSimpleName();

    private final Context mContext;
    private final Gson mGson;

    public LoadQuakesInteractor(Context context, Gson gson) {
        mContext = context;
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

    public void loadQuakes(final OnQuakesLoadedListener listener) {

        new AsyncTask<Void, Void, AsyncTaskResult<Feature[]>>() {

            @Override
            protected AsyncTaskResult<Feature[]> doInBackground(Void... voids) {
                try {
                    InputStream input = mContext.openFileInput(QUAKES_FILE);
                    InputStreamReader reader = new InputStreamReader(input);
                    Feature[] features = mGson.fromJson(reader, FeatureCollection.class)
                            .getFeatures();
                    reader.close();
                    input.close();
                    return new AsyncTaskResult<>(features);
                } catch (IOException e) {
                    return new AsyncTaskResult<>(e);
                }
            }

            @Override
            protected void onPostExecute(AsyncTaskResult<Feature[]> asyncTaskResult) {
                if (asyncTaskResult.isError()) {
                    Log.e(TAG, "I/O Exception.", asyncTaskResult.getError());
                    listener.onLoadQuakesFailure();
                } else loadQuakes(asyncTaskResult.getResult(), listener);
            }
        }.execute();
    }

    public void loadQuakes(Feature[] features, final OnQuakesLoadedListener listener) {

        new AsyncTask<Feature, Void, AsyncTaskResult<Feature[]>>(){

            @Override
            protected AsyncTaskResult<Feature[]> doInBackground(Feature... features) {
                try {
                    InputStream input = mContext.getResources().openRawResource(R.raw.cities);
                    InputStreamReader reader = new InputStreamReader(input);// Load cities.
                    City[] cities = mGson.fromJson(reader, City[].class);
                    reader.close();
                    input.close();

                    final int count = features.length;
                    for (int i = 0; i < count; i++) {
                        features[i].setClosestCity(findClosest(features[i].getGeometry()
                                        .getCoordinates(),                                cities));
                    }

                    return new AsyncTaskResult<>(features);
                } catch (IOException e) {
                    return new AsyncTaskResult<>(e);
                }
            }

            @Override
            protected void onPostExecute(AsyncTaskResult<Feature[]> result) {
                if(result.isError()){
                    Log.e(TAG, "I/O Exception.", result.getError());
                    listener.onLoadQuakesFailure();
                } else listener.onLoadQuakesSuccess(result.getResult());
            }
        }.execute(features);
    }

    public void saveQuakes(Response response, final OnQuakesSavedListener listener) {

        new AsyncTask<Response, Void, AsyncTaskResult>() {

            @Override
            protected AsyncTaskResult doInBackground(Response... responses) {
                try {
                    BufferedInputStream input = new BufferedInputStream(responses[0].getBody().in());
                    FileOutputStream output = mContext.openFileOutput(QUAKES_FILE, Context.MODE_PRIVATE);
                    byte[] data = new byte[1024];
                    int count;
                    while ((count = input.read(data)) != -1) {
                        output.write(data, 0, count);
                    }
                    output.close();
                    input.close();
                    return new AsyncTaskResult(null);
                } catch (IOException e) {
                    Log.d(TAG, "I/O Exception.", e);
                    return new AsyncTaskResult<>(e);
                }
            }

            @Override
            protected void onPostExecute(AsyncTaskResult result) {
                if (result.isError()) {
                    Log.e(TAG, "I/O Exception.", result.getError());
                    listener.onSaveQuakesFailure();
                } else listener.onSaveQuakesSuccess();
            }
        }.execute(response);

    }

    public interface OnQuakesLoadedListener {
        void onLoadQuakesFailure();

        void onLoadQuakesSuccess(Feature[] features);
    }

    public interface OnQuakesSavedListener {
        void onSaveQuakesFailure();

        void onSaveQuakesSuccess();
    }
}
