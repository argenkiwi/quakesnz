package com.androideas.quakesnz.app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androideas.quakesnz.app.model.Feature;
import com.androideas.quakesnz.app.model.Properties;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class QuakeDetailFragment extends Fragment {

    private static final String ARG_FEATURE = "arg_feature";
    private static final SimpleDateFormat sDateFormat;
    private static final String TAG = QuakeDetailFragment.class.getSimpleName();

    static {
        sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S",
                Locale.ENGLISH);
        sDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    private Feature mFeature;
    private TextView mMagnitudeBigView;
    private View mTabView;
    private TextView mMagnitudeSmallView;
    private TextView mIntensityView;

    public static Fragment newInstance(Feature feature) {

        QuakeDetailFragment f = new QuakeDetailFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARG_FEATURE, feature);
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container,
                false);

        mMagnitudeBigView = (TextView) v
                .findViewById(R.id.magnitude_big);
        mMagnitudeSmallView = (TextView) v
                .findViewById(R.id.magnitude_small);
        mIntensityView = (TextView) v.findViewById(R.id.intensity);
        mTabView = v.findViewById(R.id.colorTab);
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFeature = getArguments().getParcelable(ARG_FEATURE);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Properties properties = mFeature.getProperties();

        String[] magnitude = String.format(Locale.ENGLISH, "%1$.1f", properties.getMagnitude()).split("\\.");

        mMagnitudeBigView.setText(magnitude[0]);
        String intensity = properties.getIntensity();
        final int colorForIntensity = getColorForIntensity(intensity);
        mMagnitudeBigView.setTextColor(colorForIntensity);
        mMagnitudeSmallView.setText("" + magnitude[1]);
        mMagnitudeSmallView.setTextColor(colorForIntensity);
        mIntensityView.setText(intensity);
        mTabView.setBackgroundColor(colorForIntensity);

//		try {
//			Log.d(TAG, properties.getOriginTime());
//			Date date = sDateFormat.parse(properties.getOriginTime().substring(
//					0, properties.getOriginTime().length() - 3));
//			mDateView.setText(DateUtils.getRelativeTimeSpanString(
//					date.getTime(), Calendar.getInstance().getTimeInMillis(),
//					DateUtils.MINUTE_IN_MILLIS));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

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
}
