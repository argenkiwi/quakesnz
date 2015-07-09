package nz.co.codebros.quakesnz.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import javax.inject.Inject;

import nz.co.codebros.quakesnz.GeonetService;
import nz.co.codebros.quakesnz.QuakesNZApplication;
import retrofit.client.Response;

public class GeonetIntentService extends IntentService {

    public static final String EXTRA_SCOPE = "extra_scope";
    public static final int SCOPE_ALL = 0;
    public static final int SCOPE_FELT = 1;

    public static final String ACTION_DOWNLOAD_FAILURE = "action_download_failure";
    public static final String ACTION_DOWNLOAD_SUCCESS = "action_download_success";
    public static final String QUAKES_FILE = "%s.json";

    private static final String TAG = GeonetIntentService.class.getSimpleName();

    @Inject
    GeonetService mGeonetService;

    public GeonetIntentService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ((QuakesNZApplication) getApplication()).getApplicationComponent().inject(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        final int scope = intent.getIntExtra(EXTRA_SCOPE, SCOPE_ALL);

        Response response = mGeonetService.listAllQuakes(getFilterName(scope));

        if (response.getStatus() != HttpURLConnection.HTTP_OK) {
            Log.d(TAG, "HTTP response code: " + response.getStatus());
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ACTION_DOWNLOAD_FAILURE));
            return;
        }

        try {

            BufferedInputStream input = new BufferedInputStream(response.getBody().in());
            FileOutputStream output = openFileOutput(getFileNameForScope(scope), MODE_PRIVATE);

            byte[] data = new byte[1024];
            int count;
            while ((count = input.read(data)) != -1) {
                output.write(data, 0, count);
            }

            output.close();
            input.close();

            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ACTION_DOWNLOAD_SUCCESS));
        } catch (MalformedURLException e) {
            Log.e(TAG, "Malformend URL.", e);
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ACTION_DOWNLOAD_FAILURE));
        } catch (IOException e) {
            Log.e(TAG, "Input/Output exception.", e);
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ACTION_DOWNLOAD_FAILURE));
        }
    }

    public static String getFileNameForScope(int scope) {
        return String.format(QUAKES_FILE, getFilterName(scope));
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
}
