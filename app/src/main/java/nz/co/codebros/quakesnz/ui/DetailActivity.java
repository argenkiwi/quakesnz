package nz.co.codebros.quakesnz.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import nz.co.codebros.quakesnz.model.Feature;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_FEATURE = "extra_feature";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Feature feature = getIntent().getParcelableExtra(EXTRA_FEATURE);
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, QuakeDetailFragment.newInstance(feature))
                    .commit();
        }
    }
}
