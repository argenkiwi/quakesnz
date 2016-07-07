package nz.co.codebros.quakesnz.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import nz.co.codebros.quakesnz.model.Feature;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_FEATURE = "extra_feature";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().setHomeButtonEnabled(true);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    TaskStackBuilder.create(this)
                            .addNextIntentWithParentStack(upIntent)
                            .startActivities();
                } else NavUtils.navigateUpTo(this, upIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
