package nz.co.codebros.quakesnz.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import nz.co.codebros.quakesnz.QuakesNZApplication;
import nz.co.codebros.quakesnz.R;
import nz.co.codebros.quakesnz.component.DaggerQuakeDetailComponent;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.model.Properties;
import nz.co.codebros.quakesnz.module.QuakeDetailModule;
import nz.co.codebros.quakesnz.presenter.QuakeDetailPresenter;
import nz.co.codebros.quakesnz.utils.QuakesUtils;
import nz.co.codebros.quakesnz.view.QuakeDetailView;

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

    public static Fragment newInstance(Feature feature) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_FEATURE, feature);

        QuakeDetailFragment fragment = new QuakeDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static Fragment newInstance(String publicID) {
        Bundle args = new Bundle();
        args.putString(ARG_PUBLIC_ID, publicID);

        QuakeDetailFragment fragment = new QuakeDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState == null) {
            if (getArguments().containsKey(ARG_FEATURE)) {
                final Feature feature = getArguments().getParcelable(ARG_FEATURE);
                presenter.onInit(feature);
            } else if (getArguments().containsKey(ARG_PUBLIC_ID)) {
                final String publicID = getArguments().getString(ARG_PUBLIC_ID);
                presenter.onInit(publicID);
            }
        } else if (savedInstanceState.containsKey(ARG_FEATURE)) {
            final Feature feature = savedInstanceState.getParcelable(ARG_FEATURE);
            presenter.onInit(feature);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        DaggerQuakeDetailComponent.builder()
                .applicationComponent(QuakesNZApplication.get(context).getComponent())
                .quakeDetailModule(new QuakeDetailModule(this))
                .build().inject(this);
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
    public void onStop() {
        super.onStop();
        presenter.onStop();
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

        view.findViewById(R.id.share_button).setOnClickListener(this);
    }

    @Override
    public void share(Feature feature) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.default_share_content,
                QuakesUtils.getIntensity(getContext(), feature.getProperties().getMmi()).toLowerCase(),
                feature.getProperties().getMagnitude(),
                feature.getProperties().getLocality(),
                feature.getProperties().getPublicId()
        ));
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    @Override
    public void showDetails(Feature feature) {
        this.feature = feature;

        if (getChildFragmentManager().findFragmentById(R.id.map) == null) {
            getChildFragmentManager().beginTransaction()
                    .add(R.id.map, MyMapFragment.newInstance(feature.getGeometry()))
                    .commit();
        }

        Properties properties = feature.getProperties();
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
    public void showLoadingError() {
        Toast.makeText(getContext(), R.string.error_loading_feature, Toast.LENGTH_SHORT).show();
    }
}
