package nz.co.codebros.quakesnz.list;

import android.content.Context;
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
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.ui.DetailActivity;
import nz.co.codebros.quakesnz.ui.FeatureAdapter;

public class QuakeListFragment extends Fragment implements QuakeListView,
        SwipeRefreshLayout.OnRefreshListener, FeatureAdapter.Listener {

    private static final String TAG = QuakeListFragment.class.getSimpleName();

    @Inject
    QuakeListPresenter presenter;

    @Inject
    @Named("app")
    Tracker tracker;

    private FeatureAdapter featureAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static QuakeListFragment newInstance() {
        return new QuakeListFragment();
    }

    @Override
    public void hideProgress() {
        Log.d(TAG, "Hide progress.");
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void listQuakes(Feature[] features) {
        Log.d(TAG, "List quakes.");
        featureAdapter.setFeatures(features);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) presenter.onRefresh();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        QuakesNZApplication.get(context).getComponent()
                .plus(new QuakeListModule(this))
                .inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        featureAdapter = new FeatureAdapter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        presenter.onCreateView();
        return inflater.inflate(R.layout.fragment_quakes, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroyView();
    }

    @Override
    public void onFeatureClicked(View view, Feature feature) {
        Log.d(TAG, "Feature selected.");
        Intent intent = DetailActivity.newIntent(getContext(), feature);
        ActivityCompat.startActivity(getContext(), intent, ActivityOptionsCompat
                .makeSceneTransitionAnimation(getActivity(), view,
                        getString(R.string.transition_name)).toBundle());
    }

    @Override
    public void onRefresh() {
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("Interactions")
                .setAction("Refresh")
                .setLabel("Refresh")
                .build());

        presenter.onRefresh();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout = ((SwipeRefreshLayout) view);
        swipeRefreshLayout.setOnRefreshListener(this);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(featureAdapter);
    }

    @Override
    public void showError() {
        Log.d(TAG, "Show download failed message.");
        Toast.makeText(getContext(), R.string.failed_to_download, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        Log.d(TAG, "Show progress.");
        swipeRefreshLayout.setRefreshing(true);
    }
}
