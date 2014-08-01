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
import java.net.URL;

public class GeonetService extends IntentService {

    public static final String EXTRA_SCOPE = "extra_scope";
    public static final int SCOPE_ALL = 0;
    public static final int SCOPE_FELT = 1;

    public static final String ACTION_DOWNLOAD_FAILURE = "action_download_failure";
    public static final String ACTION_DOWNLOAD_SUCCESS = "action_download_success";
    public static final String QUAKES_FILE = "%s.json";

    private static final String TAG = GeonetService.class.getSimpleName();
    private static final String GEONET_URL = "http://www.geonet.org.nz/quakes/services/%s.json";

    public GeonetService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        final int scope = intent.getIntExtra(EXTRA_SCOPE, SCOPE_ALL);

        try {

            URL url = new URL(getURLForScope(scope));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.d(TAG, "HTTP response code: " + conn.getResponseCode());
                LocalBroadcastManager.getInstance(this).sendBroadcast(
                        new Intent(ACTION_DOWNLOAD_FAILURE));
                return;
            }

            BufferedInputStream input = new BufferedInputStream(
                    conn.getInputStream());

            FileOutputStream output = openFileOutput(getFileNameForScope(scope), MODE_PRIVATE);

            byte[] data = new byte[1024];
            int count;
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

    private static String getURLForScope(int scope) {
        return String.format(GEONET_URL, getPrefixForScope(scope));
    }

    public static String getFileNameForScope(int scope) {
        return String.format(QUAKES_FILE, getPrefixForScope(scope));
    }

    private static String getPrefixForScope(int scope) {
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
