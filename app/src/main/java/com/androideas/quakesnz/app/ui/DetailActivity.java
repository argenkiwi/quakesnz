package com.androideas.quakesnz.app.ui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.androideas.quakesnz.app.R;
import com.androideas.quakesnz.app.model.Feature;

public class DetailActivity extends ActionBarActivity {

    public static final String EXTRA_FEATURE = "extra_feature";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(savedInstanceState == null){
            Feature feature = getIntent().getParcelableExtra(EXTRA_FEATURE);
            getSupportFragmentManager().beginTransaction().add(R.id.content, QuakeDetailFragment.newInstance(feature)).commit();
        }
    }

}
