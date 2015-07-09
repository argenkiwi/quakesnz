package nz.co.codebros.quakesnz.ui;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import nz.co.codebros.quakesnz.R;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.service.GeonetIntentService;

public class MainActivity extends AppCompatActivity implements ActionBar.OnNavigationListener,
        QuakeListFragment.Listener {

    public static final String STATE_SCOPE = "state_scope";
    private static final String TAG = MainActivity.class.getSimpleName();

    private int mCurrentScope = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getSupportActionBar().getThemedContext(), R.array.filters,
                R.layout.support_simple_spinner_dropdown_item);

        getSupportActionBar().setListNavigationCallbacks(adapter, this);

        if (savedInstanceState == null) {
            Log.d(TAG, "Restore scope from preferences.");
            final int scope = PreferenceManager.getDefaultSharedPreferences(this)
                    .getInt(STATE_SCOPE, GeonetIntentService.SCOPE_ALL);
            getSupportActionBar().setSelectedNavigationItem(scope);
        } else {
            Log.d(TAG, "Restore scope from state.");
            mCurrentScope = savedInstanceState.getInt(STATE_SCOPE);
            getSupportActionBar().setSelectedNavigationItem(mCurrentScope);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).edit()
                .putInt(STATE_SCOPE, mCurrentScope).commit();
    }

    @Override
    public void onFeatureSelected(Feature feature, View view) {

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_FEATURE, feature);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                view, getString(R.string.transition_name));

        ActivityCompat.startActivity(this, intent, options.toBundle());
    }

    @Override
    public boolean onNavigationItemSelected(int position, long l) {

        Log.d(TAG, "Navigation item selected.");

        if (mCurrentScope != position) {
            mCurrentScope = position;
            showQuakeList();
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                startActivity(new Intent(this, InfoActivity.class));
                return true;
            case R.id.action_about:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SCOPE, mCurrentScope);
    }

    private void showQuakeList() {
        Log.d(TAG, "Show quake list.");
        QuakeListFragment f = QuakeListFragment.newInstance(mCurrentScope);
        getSupportFragmentManager().beginTransaction().replace(R.id.content, f).commit();
    }
}
