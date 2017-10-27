package nz.co.codebros.quakesnz.list;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import kotlin.Unit;
import nz.co.codebros.quakesnz.R;
import nz.co.codebros.quakesnz.core.BaseFragment;
import nz.co.codebros.quakesnz.core.BasePresenter;
import nz.co.codebros.quakesnz.core.model.Feature;
import nz.co.codebros.quakesnz.ui.FeatureAdapter;

public class QuakeListFragment extends BaseFragment<Unit> implements QuakeListView,
        SwipeRefreshLayout.OnRefreshListener, FeatureAdapter.Listener {

    private static final String TAG = QuakeListFragment.class.getSimpleName();

    @Inject
    QuakeListPresenter presenter;

    @Inject
    QuakeListViewModel viewModel;

    @Inject
    FeatureAdapter featureAdapter;

    @Inject
    OnFeatureClickedListener listener;

    @Inject
    @Named("app")
    Tracker tracker;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void hideProgress() {
        Log.d(TAG, "Hide progress.");
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void listQuakes(@NonNull List<Feature> features) {
        Log.d(TAG, "List quakes.");
        featureAdapter.setFeatures(features);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.getFeatures().observe(this, new Observer<List<Feature>>() {
            @Override
            public void onChanged(@Nullable List<Feature> features) {
                listQuakes(features);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_quakes, container, false);
    }

    @Override
    public void onFeatureClicked(@NonNull View view, @NonNull Feature feature) {
        Log.d(TAG, "Feature selected.");
        presenter.onFeatureSelected(feature);
        listener.onFeatureClicked(view);
    }

    @Override
    public void onRefresh() {
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("Interactions")
                .setAction("Refresh")
                .setLabel("Refresh")
                .build());

        presenter.onInit(null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout = ((SwipeRefreshLayout) view);
        swipeRefreshLayout.setOnRefreshListener(this);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(featureAdapter);
    }

    @NonNull
    @Override
    protected BasePresenter<?, Unit> getPresenter() {
        return presenter;
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

    @Override
    public void selectFeature(@NonNull Feature feature) {
        featureAdapter.setSelectedFeature(feature);
    }

    public interface OnFeatureClickedListener {
        void onFeatureClicked(View view);
    }
}
