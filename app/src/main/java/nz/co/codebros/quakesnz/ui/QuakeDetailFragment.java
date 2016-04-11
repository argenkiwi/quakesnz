package nz.co.codebros.quakesnz.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;

import nz.co.codebros.quakesnz.R;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.model.Properties;
import nz.co.codebros.quakesnz.utils.LatLngUtils;
import nz.co.codebros.quakesnz.utils.QuakesUtils;

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


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Properties properties = mFeature.getProperties();
        final int colorForIntensity = QuakesUtils.getColor(getContext(), properties.getMmi());
        String[] magnitude = String.format(Locale.ENGLISH, "%1$.1f", properties.getMagnitude())
                .split("\\.");

        mMagnitudeBigView.setText(magnitude[0]);
        mMagnitudeBigView.setTextColor(colorForIntensity);
        mMagnitudeSmallView.setText("." + magnitude[1]);
        mMagnitudeSmallView.setTextColor(colorForIntensity);
        mIntensityView.setText(QuakesUtils.getIntensity(getContext(), properties.getMmi()));
        mLocationView.setText(properties.getLocality());
        mDepthView.setText(getString(R.string.depth, properties.getDepth()));
        mTimeView.setText(DateUtils.getRelativeTimeSpanString(properties.getTime().getTime()));
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
