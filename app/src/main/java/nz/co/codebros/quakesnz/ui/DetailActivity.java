package nz.co.codebros.quakesnz.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import nz.co.codebros.quakesnz.model.Feature;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_FEATURE = "extra_feature";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            if (getIntent().hasExtra(EXTRA_FEATURE)) {
                Feature feature = getIntent().getParcelableExtra(EXTRA_FEATURE);
                getSupportFragmentManager().beginTransaction()
                        .add(android.R.id.content, QuakeDetailFragment.newInstance(feature))
                        .commit();
            } else {
                Uri data = getIntent().getData();
                getSupportFragmentManager().beginTransaction()
                        .add(android.R.id.content,
                                QuakeDetailFragment.newInstance(data.getLastPathSegment()))
                        .commit();
            }
        }
    }
}
