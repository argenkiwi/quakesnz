package com.androideas.quakesnz.app.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.androideas.quakesnz.app.R;
import com.androideas.quakesnz.app.model.Feature;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class QuakeListFragment extends ListFragment {

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
            viewHolder.txtTime.setText(SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT).format(item.getProperties().getOriginTime()));
            viewHolder.vTab.setBackgroundColor(colorForIntensity);
            return convertView;
        }

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

    private static final String ARG_FEATURES = "arg_features";

    public static QuakeListFragment newInstance(Feature[] features) {
        QuakeListFragment f = new QuakeListFragment();

        Bundle args = new Bundle();
        args.putParcelableArray(ARG_FEATURES, features);
        f.setArguments(args);

        return f;
    }

    private FeatureAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Feature[] features = (Feature[]) getArguments().getParcelableArray(
                ARG_FEATURES);
        mAdapter = new FeatureAdapter(getActivity(), features);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setListAdapter(mAdapter);

        int padding = (int) getResources().getDimension(R.dimen.padding_medium);

        ListView listView = getListView();
        listView.setBackgroundColor(Color.parseColor("#CCCCCC"));
        listView.setPadding(padding, padding, padding, padding);
        listView.setClipToPadding(false);
        listView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        listView.setDivider(getResources().getDrawable(android.R.color.transparent));
        listView.setDividerHeight(padding);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        super.onListItemClick(l, v, position, id);

        Feature item = (Feature) l.getItemAtPosition(position);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            runExitAnimation(v, item);
//        } else {
            ((MainActivity) getActivity()).showQuakeDetail(item);
//        }

    }

    private void runReturnAnimation() {
        int count = getListView().getChildCount();
        for (int i = 0; i < count; i++) {
            getListView().getChildAt(i).animate().setDuration(250).translationY(0);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void runExitAnimation(View v, final Feature item) {

        int[] location = new int[2];

        v.getLocationOnScreen(location);

        int bottomY = getListView().getHeight() - getListView().getPaddingBottom() - getListView().getDividerHeight() - location[1];

        v.animate().setDuration(250).translationYBy(bottomY).withEndAction(new Runnable() {
            @Override
            public void run() {
                ((MainActivity) getActivity()).showQuakeDetail(item);
            }
        });

        int topY = location[1] - v.getHeight();

        int count = getListView().getChildCount();
        for (int i = 0; i < count; i++) {
            View sibling = getListView().getChildAt(i);
            if (!v.equals(sibling) && sibling != null) {
                if (sibling.getY() > v.getY()) {
                    sibling.animate().setDuration(250).translationYBy(bottomY);
                } else {
                    sibling.animate().setDuration(250).translationYBy(-topY);
                }
            }
        }
    }
}
