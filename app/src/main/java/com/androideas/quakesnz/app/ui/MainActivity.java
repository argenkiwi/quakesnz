package com.androideas.quakesnz.app.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.androideas.quakesnz.app.service.GeonetService;
import com.androideas.quakesnz.app.R;
import com.androideas.quakesnz.app.model.Feature;
import com.androideas.quakesnz.app.model.FeatureCollection;
import com.androideas.quakesnz.app.utils.DateDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

public class MainActivity extends FragmentActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private BroadcastReceiver mReceiver;

    protected void loadData() {

        try {
            FileInputStream input = openFileInput(GeonetService.QUAKES_FILE);
            InputStreamReader reader = new InputStreamReader(input);

            Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();

            FeatureCollection featureCollection = gson.fromJson(reader,
                    FeatureCollection.class);

            reader.close();
            input.close();

            Feature[] features = featureCollection.getFeatures();

            Feature aux;
            final int count = features.length;
            for (int i = 0; i < count / 2; i++) {
                aux = features[i];
                features[i] = features[count - 1 - i];
                features[count - 1 - i] = aux;
            }

            showQuakeList(features);

        } catch (FileNotFoundException e) {
            Log.e(TAG, "File not found.", e);
        } catch (IOException e) {
            Log.e(TAG, "Input/Output error.", e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {

                if (GeonetService.ACTION_DOWNLOAD_SUCCESS.equals(intent
                        .getAction())) {
                    loadData();
                } else if (GeonetService.ACTION_DOWNLOAD_FAILURE.equals(intent
                        .getAction())) {
                    if (getFileStreamPath(GeonetService.QUAKES_FILE).exists()) {
                        Toast.makeText(MainActivity.this,
                                "Could not update data.", Toast.LENGTH_SHORT)
                                .show();
                        loadData();
                    } else {
                        Toast.makeText(MainActivity.this, "No data available.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };

        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            Intent intent = new Intent(this, GeonetService.class);
            startService(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(GeonetService.ACTION_DOWNLOAD_SUCCESS);
        filter.addAction(GeonetService.ACTION_DOWNLOAD_FAILURE);
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver,
                filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
    }

    private void showQuakeList(Feature[] features) {
        QuakeListFragment f = QuakeListFragment.newInstance(features);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_layer, f).commit();
    }

    public void showQuakeDetail(Feature feature) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_FEATURE, feature);
        startActivity(intent);
        overridePendingTransition(0,0);
    }
}
