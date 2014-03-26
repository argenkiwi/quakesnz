package com.androideas.quakesnz.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Gallery;

import com.androideas.quakesnz.app.model.Feature;


public class QuakeGraphFragment extends Fragment implements
        OnItemSelectedListener {

    private class FeatureAdapter extends ArrayAdapter<Feature> {

        public FeatureAdapter(Context context, Feature[] objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(getActivity(),
                        R.layout.gallery_item_feature, null);

                viewHolder = new ViewHolder();
                viewHolder.magnitude = (BarView) convertView
                        .findViewById(R.id.bar_magnitude);
                viewHolder.depth = (BarView) convertView
                        .findViewById(R.id.bar_depth);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            Feature item = getItem(position);

            viewHolder.magnitude
                    .setValue(item.getProperties().getMagnitude() / 10);
            viewHolder.magnitude.setColor(getColorForIntensity(item
                    .getProperties().getIntensity()));
            viewHolder.depth.setValue(1 - item.getProperties().getDepth()
                    / mMaxDepth);

            return convertView;
        }
    }

    private static class ViewHolder {
        BarView depth;
        BarView magnitude;
    }

    private static final String ARG_FEATURES = "arg_features";

    public static QuakeGraphFragment newInstance(Feature[] features) {
        QuakeGraphFragment f = new QuakeGraphFragment();

        Bundle args = new Bundle();
        args.putParcelableArray(ARG_FEATURES, features);
        f.setArguments(args);

        return f;
    }

    private FeatureAdapter mAdapter;

    private Gallery mGallery;

    private float mMaxDepth;
    private BroadcastReceiver mReceiver;

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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mGallery.setAdapter(mAdapter);
        mGallery.setSelection(mAdapter.getCount() - 1);
        mGallery.setOnItemSelectedListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Feature[] features = (Feature[]) getArguments().getParcelableArray(
                ARG_FEATURES);

        mMaxDepth = 0;

        for (Feature feature : features) {
            mMaxDepth = Math.max(mMaxDepth, feature.getProperties().getDepth());
        }

        mAdapter = new FeatureAdapter(getActivity(), features);
        mReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                if (MainActivity.ACTION_QUAKE_SELECTED.equals(intent
                        .getAction())) {
                    int position = intent.getIntExtra(
                            MainActivity.EXTRA_QUAKE_POSITION, 0);
                    mGallery.setSelection(position, true);
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_quake_graph, container,
                false);

        mGallery = (Gallery) v.findViewById(R.id.graph);

        return v;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        Intent intent = new Intent(MainActivity.ACTION_QUAKE_SELECTED);
        intent.putExtra(MainActivity.EXTRA_QUAKE_POSITION, position);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(mReceiver,
                        new IntentFilter(MainActivity.ACTION_QUAKE_SELECTED));
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(
                mReceiver);
    }

}
