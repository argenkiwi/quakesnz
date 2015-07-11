package nz.co.codebros.quakesnz.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import nz.co.codebros.quakesnz.QuakesNZApplication;
import nz.co.codebros.quakesnz.R;
import nz.co.codebros.quakesnz.component.DaggerQuakeListComponent;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.module.QuakeListModule;
import nz.co.codebros.quakesnz.presenter.QuakeListPresenter;
import nz.co.codebros.quakesnz.utils.LatLngUtils;

public class QuakeListFragment extends SwipeRefreshListFragment implements QuakeListView,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = QuakeListFragment.class.getSimpleName();

    @Inject
    QuakeListPresenter mPresenter;

    @Inject
    @Named("app")
    Tracker mTracker;

    private Listener mListener;
    private FeatureAdapter mAdapter;

    public static QuakeListFragment newInstance() {
        return new QuakeListFragment();
    }

    public int getColorForIntensity(String intensity) {
        int color;
        if (intensity.equals("unnoticeable")) {
            color = getResources().getColor(R.color.unnoticeable);
        } else if (intensity.equals("weak")) {
            color = getResources().getColor(R.color.weak);
        } else if (intensity.equals("light")) {
            color = getResources().getColor(R.color.light);
        } else if (intensity.equals("moderate")) {
            color = getResources().getColor(R.color.moderate);
        } else if (intensity.equals("strong")) {
            color = getResources().getColor(R.color.strong);
        } else if (intensity.equals("severe")) {
            color = getResources().getColor(R.color.severe);
        } else
            color = Color.LTGRAY;

        return color;
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

        mAdapter = new FeatureAdapter(getActivity());

        setListAdapter(mAdapter);

        int padding = (int) getResources().getDimension(R.dimen.padding_small);

        ListView listView = getListView();
        listView.setPadding(padding, padding, padding, padding);
        listView.setClipToPadding(false);
        listView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        listView.setDivider(null);

        setEmptyText(getString(R.string.no_data_available));
        setOnRefreshListener(this);

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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unbindView();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        mListener.onFeatureSelected((Feature) l.getItemAtPosition(position), v);
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
        mPresenter.onLoadQuakes();
    }

    @Override
    public void showProgress() {
        Log.d(TAG, "Show progress.");
        setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        Log.d(TAG, "Hide progress.");
        setRefreshing(false);
    }

    @Override
    public void listQuakes(Feature[] features) {
        Log.d(TAG, "List quakes.");
        if (features != null) {
            mAdapter.clear();
            mAdapter.addAll(features);
        } else Toast.makeText(getActivity(), R.string.no_data_available, Toast.LENGTH_SHORT);
    }

    public interface Listener {
        void onFeatureSelected(Feature feature, View view);
    }

    private static class ViewHolder {
        TextView txtMagnitudeBig;
        TextView txtMagnitudeSmall;
        TextView txtIntensity;
        TextView txtLocation;
        TextView txtDepth;
        TextView txtTime;
        View vTab;
    }

    private class FeatureAdapter extends ArrayAdapter<Feature> {

        public FeatureAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public Feature getItem(int position) {
            return super.getItem(super.getCount() - position - 1);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.item_summary, null);

                viewHolder = new ViewHolder();
                viewHolder.txtMagnitudeBig = (TextView) convertView.findViewById(R.id.magnitude_big);
                viewHolder.txtMagnitudeSmall = (TextView) convertView.findViewById(R.id.magnitude_small);
                viewHolder.txtIntensity = (TextView) convertView.findViewById(R.id.intensity);
                viewHolder.txtLocation = (TextView) convertView.findViewById(R.id.location);
                viewHolder.txtDepth = (TextView) convertView.findViewById(R.id.depth);
                viewHolder.txtTime = (TextView) convertView.findViewById(R.id.time);
                viewHolder.vTab = convertView.findViewById(R.id.colorTab);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            Feature item = getItem(position);

            String[] magnitude = String.format(Locale.ENGLISH, "%1$.1f", item.getProperties()
                    .getMagnitude()).split("\\.");

            viewHolder.txtMagnitudeBig.setText(magnitude[0]);
            String intensity = item.getProperties().getIntensity();
            final int colorForIntensity = getColorForIntensity(intensity);
            viewHolder.txtMagnitudeBig.setTextColor(colorForIntensity);
            viewHolder.txtMagnitudeSmall.setText("." + magnitude[1]);
            viewHolder.txtMagnitudeSmall.setTextColor(colorForIntensity);
            viewHolder.txtIntensity.setText(intensity);
            viewHolder.txtLocation.setText(getString(R.string.location, Math
                    .round(LatLngUtils.findDistance(item.getGeometry().getCoordinates(),
                            item.getClosestCity().getCoordinates()) / 1000), item.getClosestCity()
                    .getName()));
            viewHolder.txtDepth.setText(getString(R.string.depth, item.getProperties().getDepth()));
            viewHolder.txtTime.setText(DateUtils.getRelativeTimeSpanString(item.getProperties()
                    .getOriginTime().getTime()));
            viewHolder.vTab.setBackgroundColor(colorForIntensity);
            return convertView;
        }
    }

}
