package nz.co.codebros.quakesnz.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;

import java.util.Locale;

import nz.co.codebros.quakesnz.R;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.model.Properties;
import nz.co.codebros.quakesnz.utils.LatLngUtils;

public class QuakeDetailFragment extends Fragment {

    private static final String ARG_FEATURE = "arg_feature";

    private Feature mFeature;
    private TextView mMagnitudeBigView;
    private View mTabView;
    private TextView mMagnitudeSmallView;
    private TextView mIntensityView;
    private TextView mTimeView;
    private TextView mLocationView;
    private TextView mDepthView;

    public static Fragment newInstance(Feature feature) {
        QuakeDetailFragment f = new QuakeDetailFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARG_FEATURE, feature);
        f.setArguments(args);

        return f;
    }

    public int getColorForIntensity(String intensity) {
        switch (intensity) {
            case "unnoticeable":
                return getResources().getColor(R.color.unnoticeable);
            case "weak":
                return getResources().getColor(R.color.weak);
            case "light":
                return getResources().getColor(R.color.light);
            case "moderate":
                return getResources().getColor(R.color.moderate);
            case "strong":
                return getResources().getColor(R.color.strong);
            case "severe":
                return getResources().getColor(R.color.severe);
            default:
                return Color.LTGRAY;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Properties properties = mFeature.getProperties();
        final String intensity = properties.getIntensity();
        final int colorForIntensity = getColorForIntensity(intensity);
        String[] magnitude = String.format(Locale.ENGLISH, "%1$.1f", properties.getMagnitude())
                .split("\\.");

        mMagnitudeBigView.setText(magnitude[0]);
        mMagnitudeBigView.setTextColor(colorForIntensity);
        mMagnitudeSmallView.setText("." + magnitude[1]);
        mMagnitudeSmallView.setTextColor(colorForIntensity);
        mIntensityView.setText(intensity);
        mLocationView.setText(getString(R.string.location, Math.round(LatLngUtils
                .findDistance(mFeature.getGeometry().getCoordinates(), mFeature.getClosestCity()
                        .getCoordinates()) / 1000), mFeature.getClosestCity().getName()));
        mDepthView.setText(getString(R.string.depth, properties.getDepth()));
        mTimeView.setText(DateUtils.getRelativeTimeSpanString(properties.getOriginTime().getTime()));
        mTabView.setBackgroundColor(colorForIntensity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFeature = getArguments().getParcelable(ARG_FEATURE);
        if (savedInstanceState == null) {
            getChildFragmentManager().beginTransaction()
                    .add(R.id.map, MyMapFragment.newInstance(mFeature.getGeometry()))
                    .commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quake_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMagnitudeBigView = (TextView) view.findViewById(R.id.magnitude_big);
        mMagnitudeSmallView = (TextView) view.findViewById(R.id.magnitude_small);
        mIntensityView = (TextView) view.findViewById(R.id.intensity);
        mLocationView = (TextView) view.findViewById(R.id.location);
        mDepthView = (TextView) view.findViewById(R.id.depth);
        mTimeView = (TextView) view.findViewById(R.id.time);
        mTabView = view.findViewById(R.id.colorTab);
    }
}
