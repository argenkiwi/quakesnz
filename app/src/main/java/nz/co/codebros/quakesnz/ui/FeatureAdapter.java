package nz.co.codebros.quakesnz.ui;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import nz.co.codebros.quakesnz.R;
import nz.co.codebros.quakesnz.model.Feature;
import nz.co.codebros.quakesnz.presenter.QuakeListPresenter;
import nz.co.codebros.quakesnz.utils.LatLngUtils;

/**
 * Created by leandro on 12/07/15.
 */
public class FeatureAdapter extends RecyclerView.Adapter<FeatureAdapter.ViewHolder> {

    private final QuakeListPresenter mPresenter;
    private ArrayList<Feature> mFeatures = new ArrayList<>();

    public FeatureAdapter(Feature[] features, QuakeListPresenter presenter) {
        mFeatures.addAll(Arrays.asList(features));
        mPresenter = presenter;
    }

    private static int getColorForIntensity(Resources resources, String intensity) {
        int color;
        if (intensity.equals("unnoticeable")) {
            color = resources.getColor(R.color.unnoticeable);
        } else if (intensity.equals("weak")) {
            color = resources.getColor(R.color.weak);
        } else if (intensity.equals("light")) {
            color = resources.getColor(R.color.light);
        } else if (intensity.equals("moderate")) {
            color = resources.getColor(R.color.moderate);
        } else if (intensity.equals("strong")) {
            color = resources.getColor(R.color.strong);
        } else if (intensity.equals("severe")) {
            color = resources.getColor(R.color.severe);
        } else
            color = Color.LTGRAY;

        return color;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_summary, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {

        Resources resources = viewHolder.itemView.getResources();

        Feature item = mFeatures.get(i);

        String[] magnitude = String.format(Locale.ENGLISH, "%1$.1f", item.getProperties()
                .getMagnitude()).split("\\.");
        viewHolder.txtMagnitudeBig.setText(magnitude[0]);

        String intensity = item.getProperties().getIntensity();
        final int colorForIntensity = getColorForIntensity(resources, intensity);
        viewHolder.txtMagnitudeBig.setTextColor(colorForIntensity);
        viewHolder.txtMagnitudeSmall.setText("." + magnitude[1]);
        viewHolder.txtMagnitudeSmall.setTextColor(colorForIntensity);
        viewHolder.txtIntensity.setText(intensity);

        final long distance = Math.round(LatLngUtils.findDistance(item.getGeometry()
                        .getCoordinates(), item.getClosestCity().getCoordinates()) / 1000);
        viewHolder.txtLocation.setText(resources.getString(R.string.location, distance,
                item.getClosestCity().getName()));
        viewHolder.txtDepth.setText(resources.getString(R.string.depth, item.getProperties()
                .getDepth()));
        viewHolder.txtTime.setText(DateUtils.getRelativeTimeSpanString(item.getProperties()
                .getOriginTime().getTime()));
        viewHolder.vTab.setBackgroundColor(colorForIntensity);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onFeatureClicked(view, mFeatures.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFeatures.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView txtMagnitudeBig;
        private final TextView txtMagnitudeSmall;
        private final TextView txtIntensity;
        private final TextView txtLocation;
        private final TextView txtDepth;
        private final TextView txtTime;
        private final View vTab;

        public ViewHolder(View itemView) {
            super(itemView);
            this.txtMagnitudeBig = (TextView) itemView.findViewById(R.id.magnitude_big);
            this.txtMagnitudeSmall = (TextView) itemView.findViewById(R.id.magnitude_small);
            this.txtIntensity = (TextView) itemView.findViewById(R.id.intensity);
            this.txtLocation = (TextView) itemView.findViewById(R.id.location);
            this.txtDepth = (TextView) itemView.findViewById(R.id.depth);
            this.txtTime = (TextView) itemView.findViewById(R.id.time);
            this.vTab = itemView.findViewById(R.id.colorTab);
        }
    }
}
