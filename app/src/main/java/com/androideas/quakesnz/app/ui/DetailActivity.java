package com.androideas.quakesnz.app.ui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.androideas.quakesnz.app.QuakesNZApplication;
import com.androideas.quakesnz.app.R;
import com.androideas.quakesnz.app.model.Feature;
import com.google.android.gms.analytics.GoogleAnalytics;

public class DetailActivity extends ActionBarActivity {

    public static final String EXTRA_FEATURE = "extra_feature";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((QuakesNZApplication) getApplication())
                .getTracker(QuakesNZApplication.TrackerName.APP_TRACKER);

        setContentView(R.layout.activity_detail);

        if(savedInstanceState == null){
            Feature feature = getIntent().getParcelableExtra(EXTRA_FEATURE);
            getSupportFragmentManager().beginTransaction().add(R.id.content, QuakeDetailFragment.newInstance(feature)).commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
    }
}
