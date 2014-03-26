package com.androideas.quakesnz.app;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GeonetService extends IntentService {

    public static final String ACTION_DOWNLOAD_FAILURE = "action_download_failure";
    public static final String ACTION_DOWNLOAD_SUCCESS = "action_download_success";
    public static final String QUAKES_FILE = "quakes.json";

    private static final String TAG = GeonetService.class.getSimpleName();
    private static final String GEONET_URL = "http://www.geonet.org.nz/quakes/services/all.json";

    public GeonetService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        try {
            URL url = new URL(GEONET_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.d(TAG, "HTTP response code: " + conn.getResponseCode());
                LocalBroadcastManager.getInstance(this).sendBroadcast(
                        new Intent(ACTION_DOWNLOAD_FAILURE));
                return;
            }

            BufferedInputStream input = new BufferedInputStream(
                    conn.getInputStream());

            FileOutputStream output = openFileOutput(QUAKES_FILE, MODE_PRIVATE);

            byte[] data = new byte[1024];
            int count = 0;
            while ((count = input.read(data)) != -1) {
                output.write(data, 0, count);
            }

            output.close();
            input.close();

            LocalBroadcastManager.getInstance(this).sendBroadcast(
                    new Intent(ACTION_DOWNLOAD_SUCCESS));
        } catch (MalformedURLException e) {
            Log.e(TAG, "Malformend URL.", e);
            LocalBroadcastManager.getInstance(this).sendBroadcast(
                    new Intent(ACTION_DOWNLOAD_FAILURE));
        } catch (IOException e) {
            Log.e(TAG, "Input/Output exception.", e);
            LocalBroadcastManager.getInstance(this).sendBroadcast(
                    new Intent(ACTION_DOWNLOAD_FAILURE));
        }

    }
}
