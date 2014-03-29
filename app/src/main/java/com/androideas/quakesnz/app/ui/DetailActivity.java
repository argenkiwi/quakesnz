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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
