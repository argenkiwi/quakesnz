package nz.co.codebros.quakesnz.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import nz.co.codebros.quakesnz.detail.QuakeDetailFragment;

public class DetailActivity extends AppCompatActivity {

    private static final String EXTRA_PUBLIC_ID = "extra_public_id";

    public static Intent newIntent(Context context, String publicId) {
        return new Intent(context, DetailActivity.class).putExtra(EXTRA_PUBLIC_ID, publicId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) getSupportActionBar().setHomeButtonEnabled(true);
        if (savedInstanceState == null) {
            Fragment fragment = getIntent().getData() != null
                    ? QuakeDetailFragment.newInstance(getIntent().getData().getLastPathSegment())
                    : QuakeDetailFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent) || isTaskRoot()) {
                    TaskStackBuilder.create(this)
                            .addNextIntentWithParentStack(upIntent)
                            .startActivities();
                    finish();
                } else NavUtils.navigateUpTo(this, upIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
