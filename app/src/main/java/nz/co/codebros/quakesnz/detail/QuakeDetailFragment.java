package nz.co.codebros.quakesnz.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.support.AndroidSupportInjection;
import nz.co.codebros.quakesnz.R;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.model.Properties;
import nz.co.codebros.quakesnz.ui.MyMapFragment;
import nz.co.codebros.quakesnz.utils.QuakesUtils;

public class QuakeDetailFragment extends Fragment implements QuakeDetailView, View.OnClickListener {

    private static final String ARG_FEATURE = "arg_feature";
    private static final String ARG_PUBLIC_ID = "arg_public_id";

    @Inject
    QuakeDetailPresenter presenter;

    @Inject
    @Named("app")
    Tracker tracker;

    private TextView mMagnitudeBigView;
    private View mTabView;
    private TextView mMagnitudeSmallView;
    private TextView mIntensityView;
    private TextView mTimeView;
    private TextView mLocationView;
    private TextView mDepthView;
    private Feature feature;

    @NonNull
    public static QuakeDetailFragment newInstance() {
        return new QuakeDetailFragment();
    }

    @NonNull
    public static Fragment newInstance(String publicID) {
        Bundle args = new Bundle();
        args.putString(ARG_PUBLIC_ID, publicID);

        QuakeDetailFragment fragment = newInstance();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null && getArguments() != null) {
            presenter.onRefresh(getArguments().getString(ARG_PUBLIC_ID));
        }
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.share_button:
                tracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Interactions")
                        .setAction("Share")
                        .setLabel("Share")
                        .build());

                presenter.onShare(feature);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quake_detail, container, false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (feature != null) outState.putParcelable(ARG_FEATURE, feature);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.onViewCreated();

        mMagnitudeBigView = (TextView) view.findViewById(R.id.magnitude_big);
        mMagnitudeSmallView = (TextView) view.findViewById(R.id.magnitude_small);
        mIntensityView = (TextView) view.findViewById(R.id.intensity);
        mLocationView = (TextView) view.findViewById(R.id.location);
        mDepthView = (TextView) view.findViewById(R.id.depth);
        mTimeView = (TextView) view.findViewById(R.id.time);
        mTabView = view.findViewById(R.id.colorTab);

        view.findViewById(R.id.share_button).setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroyView();
    }

    @Override
    public void share(@NonNull Feature feature) {
        Properties properties = feature.getProperties();
        startActivity(new Intent()
                .setAction(Intent.ACTION_SEND)
                .putExtra(Intent.EXTRA_TEXT, getString(R.string.default_share_content,
                        QuakesUtils.INSTANCE.getIntensity(getContext(), properties.getMmi())
                                .toLowerCase(),
                        properties.getMagnitude(),
                        properties.getLocality(),
                        properties.getPublicId()
                ))
                .setType("text/plain"));
    }

    @Override
    public void showDetails(@NonNull Feature feature) {
        this.feature = feature;

        if (getChildFragmentManager().findFragmentById(R.id.map) == null) {
            getChildFragmentManager().beginTransaction()
                    .add(R.id.map, MyMapFragment.Companion.newInstance(feature.getGeometry()))
                    .commit();
        }

        Properties properties = feature.getProperties();
        final int colorForIntensity = QuakesUtils.INSTANCE.getColor(getContext(), properties.getMmi());
        String[] magnitude = String.format(Locale.ENGLISH, "%1$.1f", properties.getMagnitude())
                .split("\\.");

        mMagnitudeBigView.setText(magnitude[0]);
        mMagnitudeBigView.setTextColor(colorForIntensity);
        mMagnitudeSmallView.setText("." + magnitude[1]);
        mMagnitudeSmallView.setTextColor(colorForIntensity);
        mIntensityView.setText(QuakesUtils.INSTANCE.getIntensity(getContext(), properties.getMmi()));
        mLocationView.setText(properties.getLocality());
        mDepthView.setText(getString(R.string.depth, properties.getDepth()));
        mTimeView.setText(DateUtils.getRelativeTimeSpanString(properties.getTime().getTime()));
        mTabView.setBackgroundColor(colorForIntensity);
    }

    @Override
    public void showLoadingError() {
        Toast.makeText(getContext(), R.string.error_loading_feature, Toast.LENGTH_SHORT).show();
    }
}
