package nz.co.codebros.quakesnz.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.util.Linkify;
import android.widget.TextView;

import com.google.android.gms.analytics.GoogleAnalytics;

import nz.co.codebros.quakesnz.QuakesNZApplication;
import nz.co.codebros.quakesnz.R;

public class AboutActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((QuakesNZApplication) getApplication())
                .getTracker(QuakesNZApplication.TrackerName.APP_TRACKER);

        setContentView(R.layout.activity_about);
        Linkify.addLinks((TextView) findViewById(R.id.about_body), Linkify.ALL);
        Linkify.addLinks((TextView) findViewById(R.id.acknowledgements_body), Linkify.ALL);
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