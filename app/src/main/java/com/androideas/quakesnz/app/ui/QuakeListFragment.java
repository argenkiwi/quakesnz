package com.androideas.quakesnz.app.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.androideas.quakesnz.app.R;
import com.androideas.quakesnz.app.loader.QuakesLoader;
import com.androideas.quakesnz.app.model.Feature;
import com.androideas.quakesnz.app.utils.LatLngUtils;

import java.util.Locale;

public class QuakeListFragment extends ListFragment
        implements LoaderManager.LoaderCallbacks<Feature[]> {

    private static final String TAG = QuakeListFragment.class.getSimpleName();

    public static final String ARG_SCOPE = "arg_scope";

    private Listener mListener;

    public static QuakeListFragment newInstance(int scope) {

        Log.d(TAG, "Create new instance.");

        QuakeListFragment f = new QuakeListFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_SCOPE, scope);
        f.setArguments(args);

        return f;
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
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        Log.d(TAG, "Creating quake list fragment.");
        if (savedInstanceState == null){
            Log.d(TAG, "Loading quake data.");
            getLoaderManager().initLoader(0, getArguments(), this).forceLoad();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        int padding = (int) getResources().getDimension(R.dimen.padding_medium);

        ListView listView = getListView();
        listView.setBackgroundResource(R.drawable.background_repeat);
        listView.setPadding(padding, padding, padding, padding);
        listView.setClipToPadding(false);
        listView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        listView.setDivider(getResources().getDrawable(android.R.color.transparent));
        listView.setDividerHeight(padding);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        mListener.onFeatureSelected((Feature) l.getItemAtPosition(position));
    }

    @Override
    public Loader<Feature[]> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "Creating loader.");
        return new QuakesLoader(getActivity(), args.getInt(ARG_SCOPE));
    }

    @Override
    public void onLoadFinished(Loader<Feature[]> loader, Feature[] features) {
        Log.d(TAG, "Load finished.");
        setListAdapter(new FeatureAdapter(getActivity(), features));
    }

    @Override
    public void onLoaderReset(Loader<Feature[]> loader) {
    }

    public interface Listener {
        void onFeatureSelected(Feature feature);
    }

    private static class ViewHolder {
        TextView txtMagnitudeBig;
        TextView txtMagnitudeSmall;
        TextView txtIntensity;
        TextView txtLocation;
        TextView txtTime;
        View vTab;
    }

    private class FeatureAdapter extends ArrayAdapter<Feature> {

        public FeatureAdapter(Context context, Feature[] objects) {
            super(context, 0, objects);
        }

        @Override
        public Feature getItem(int position) {
            return super.getItem(super.getCount() - position - 1);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.item_summary,
                        null);

                viewHolder = new ViewHolder();

                viewHolder.txtMagnitudeBig = (TextView) convertView
                        .findViewById(R.id.magnitude_big);
                viewHolder.txtMagnitudeSmall = (TextView) convertView
                        .findViewById(R.id.magnitude_small);
                viewHolder.txtIntensity = (TextView) convertView.findViewById(R.id.intensity);
                viewHolder.txtLocation = (TextView) convertView.findViewById(R.id.location);
                viewHolder.txtTime = (TextView) convertView.findViewById(R.id.time);
                viewHolder.vTab = convertView.findViewById(R.id.colorTab);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            Feature item = getItem(position);

            String[] magnitude = String.format(Locale.ENGLISH, "%1$.1f", item.getProperties().getMagnitude()).split("\\.");

            viewHolder.txtMagnitudeBig.setText(magnitude[0]);
            String intensity = item.getProperties().getIntensity();
            final int colorForIntensity = getColorForIntensity(intensity);
            viewHolder.txtMagnitudeBig.setTextColor(colorForIntensity);
            viewHolder.txtMagnitudeSmall.setText("." + magnitude[1]);
            viewHolder.txtMagnitudeSmall.setTextColor(colorForIntensity);
            viewHolder.txtIntensity.setText(intensity);
            viewHolder.txtLocation.setText(getString(R.string.location, Math.round(LatLngUtils.findDistance(item.getGeometry().getCoordinates(), item.getClosestCity().getCoordinates()) / 1000), item.getClosestCity().getName()));
            viewHolder.txtTime.setText(DateUtils.getRelativeTimeSpanString(item.getProperties()
                    .getOriginTime().getTime()));
            viewHolder.vTab.setBackgroundColor(colorForIntensity);
            return convertView;
        }

    }
}
