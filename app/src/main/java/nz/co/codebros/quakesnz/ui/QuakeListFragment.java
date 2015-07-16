package nz.co.codebros.quakesnz.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import javax.inject.Inject;
import javax.inject.Named;

import nz.co.codebros.quakesnz.QuakesNZApplication;
import nz.co.codebros.quakesnz.R;
import nz.co.codebros.quakesnz.component.DaggerQuakeListComponent;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.module.QuakeListModule;
import nz.co.codebros.quakesnz.presenter.QuakeListPresenter;

public class QuakeListFragment extends Fragment implements QuakeListView,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = QuakeListFragment.class.getSimpleName();

    @Inject
    QuakeListPresenter mPresenter;

    @Inject
    @Named("app")
    Tracker mTracker;

    private FeatureAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    public static QuakeListFragment newInstance() {
        return new QuakeListFragment();
    }

    @Override
    public void hideProgress() {
        Log.d(TAG, "Hide progress.");
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void listQuakes(Feature[] features) {
        Log.d(TAG, "List quakes.");
        mAdapter = new FeatureAdapter(features, mPresenter);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            mPresenter.onRefresh();
        } else mPresenter.onLoadQuakes();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerQuakeListComponent.builder()
                .applicationComponent(((QuakesNZApplication) getActivity().getApplication())
                        .getApplicationComponent())
                .quakeListModule(new QuakeListModule())
                .build()
                .inject(this);

        mPresenter.bindView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quakes, container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unbindView();
    }

    @Override
    public void onRefresh() {

        // Build and send an Event.
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Interactions")
                .setAction("Refresh")
                .setLabel("Refresh")
                .build());

        mPresenter.onRefresh();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeRefreshLayout = ((SwipeRefreshLayout) view);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void showDownloadFailedMessage() {
        Log.d(TAG, "Show download failed message.");
        Toast.makeText(getActivity(), R.string.failed_to_update, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadFailedMessage() {
        Log.d(TAG, "Show load failed message.");
        Toast.makeText(getActivity(), R.string.failed_to_load, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showQuakeDetail(View view, Feature feature) {
        Log.d(TAG, "Feature selected.");
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_FEATURE, feature);

        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(getActivity(), view,
                        getString(R.string.transition_name));

        ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
    }

    @Override
    public void showProgress() {
        Log.d(TAG, "Show progress.");
        mSwipeRefreshLayout.setRefreshing(true);
    }
}
