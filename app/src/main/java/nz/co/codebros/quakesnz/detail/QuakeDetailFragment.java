package nz.co.codebros.quakesnz.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.jetbrains.annotations.Nullable;

import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import nz.co.codebros.quakesnz.R;
import nz.co.codebros.quakesnz.core.model.Feature;
import nz.co.codebros.quakesnz.core.model.Properties;
import nz.co.codebros.quakesnz.core.BasePresenter;
import nz.co.codebros.quakesnz.core.BaseFragment;
import nz.co.codebros.quakesnz.QuakesUtils;

public class QuakeDetailFragment extends BaseFragment<QuakeDetailProps> implements QuakeDetailView,
        View.OnClickListener {

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

    @NonNull
    @Override
    protected BasePresenter<?, QuakeDetailProps> getPresenter() {
        return presenter;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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

        view.findViewById(R.id.share_button).setOnClickListener(this);
    }

    @Nullable
    @Override
    protected QuakeDetailProps fromArguments(@NonNull Bundle bundle) {
        String publicId = bundle.getString(ARG_PUBLIC_ID);
        return publicId != null ? new QuakeDetailProps(publicId) : null;
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
