package nz.co.codebros.quakesnz.ui;

import android.app.Activity;
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
        SwipeRefreshLayout.OnRefreshListener, FeatureAdapter.Listener {

    private static final String TAG = QuakeListFragment.class.getSimpleName();

    private FeatureAdapter mAdapter;

    @Inject
    QuakeListPresenter mPresenter;

    @Inject
    @Named("app")
    Tracker mTracker;

    private Listener mListener;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static QuakeListFragment newInstance() {
        return new QuakeListFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (Listener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement QuakeListFragment.Listener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.onLoadQuakes();
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
        mAdapter = new FeatureAdapter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quakes, container, false);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unbindView();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeRefreshLayout = ((SwipeRefreshLayout) view);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
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
    public void hideProgress() {
        Log.d(TAG, "Hide progress.");
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void listQuakes(Feature[] features) {
        Log.d(TAG, "List quakes.");
        if (features != null) {
            mAdapter.clear();
            mAdapter.addAll(features);
        } else Toast.makeText(getActivity(), R.string.no_data_available, Toast.LENGTH_SHORT);
    }

    @Override
    public void showQuakeDetail(View view, Feature feature) {
        mListener.onFeatureSelected(feature, view);
    }

    @Override
    public void showProgress() {
        Log.d(TAG, "Show progress.");
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onFeatureClicked(View view, Feature feature) {
        Log.d(TAG, "Feature selected.");
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_FEATURE, feature);

        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(getActivity(), view,
                        getString(R.string.transition_name));

        ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
    }

    public interface Listener {
        void onFeatureSelected(Feature feature, View view);
    }
}
