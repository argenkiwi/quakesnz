package com.androideas.quakesnz.app.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.androideas.quakesnz.app.R;
import com.androideas.quakesnz.app.model.Feature;
import com.androideas.quakesnz.app.service.GeonetService;

public class MainActivity extends ActionBarActivity implements ActionBar.OnNavigationListener,
        QuakeListFragment.Listener {

    public static final String STATE_SCOPE = "state_scope";
    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mReceiver;
    private int mCurrentScope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getSupportActionBar()
                        .getThemedContext(), R.array.filters,
                android.R.layout.simple_spinner_dropdown_item
        );

        getSupportActionBar().setListNavigationCallbacks(adapter, this);

        setContentView(R.layout.activity_main);

        mReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                if (GeonetService.ACTION_DOWNLOAD_FAILURE.equals(intent.getAction())) {
                    Toast.makeText(MainActivity.this, "Could not update data.",
                            Toast.LENGTH_SHORT).show();
                }
                showQuakeList();
            }
        };

        if (savedInstanceState == null) {
            Log.d(TAG, "Restore scope from preferences.");
            final int scope = PreferenceManager.getDefaultSharedPreferences(this)
                    .getInt(STATE_SCOPE, GeonetService.SCOPE_ALL);

            // Set invalid scope value to force load.
            mCurrentScope = -1;

            getSupportActionBar().setSelectedNavigationItem(scope);
        } else {
            Log.d(TAG, "Restore scope from state.");
            mCurrentScope = savedInstanceState.getInt(STATE_SCOPE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(GeonetService.ACTION_DOWNLOAD_SUCCESS);
        filter.addAction(GeonetService.ACTION_DOWNLOAD_FAILURE);
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
    }

    private void showQuakeList() {
        Log.d(TAG, "Show quake list.");
        QuakeListFragment f = QuakeListFragment.newInstance(mCurrentScope);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_layer, f).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            Intent intent = new Intent(this, GeonetService.class);
            intent.putExtra(GeonetService.EXTRA_SCOPE, mCurrentScope);
            startService(intent);
        } else if (id == R.id.action_info) {
            startActivity(new Intent(this, InfoActivity.class));
        } else if (id == R.id.action_about) {
            startActivity(new Intent(this, AboutActivity.class));
        } else
            return super.onOptionsItemSelected(item);

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(int position, long l) {

        Log.d(TAG, "Navigation item selected.");

        if (mCurrentScope != position) {
            mCurrentScope = position;

            Intent intent = new Intent(this, GeonetService.class);
            intent.putExtra(GeonetService.EXTRA_SCOPE, position);
            startService(intent);
        }

        return true;
    }

    @Override
    public void onFeatureSelected(Feature feature) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_FEATURE, feature);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SCOPE, mCurrentScope);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).edit()
                .putInt(STATE_SCOPE, mCurrentScope).commit();
    }
}
