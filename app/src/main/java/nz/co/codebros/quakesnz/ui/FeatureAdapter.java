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

    private final Listener listener;
    private ArrayList<Feature> mFeatures = new ArrayList<>();

    public FeatureAdapter(Listener listener) {
        this.listener = listener;
    }

    private static int getColorForIntensity(Resources resources, String intensity) {
        switch (intensity) {
            case "unnoticeable":
                return resources.getColor(R.color.unnoticeable);
            case "weak":
                return resources.getColor(R.color.weak);
            case "light":
                return resources.getColor(R.color.light);
            case "moderate":
                return resources.getColor(R.color.moderate);
            case "strong":
                return resources.getColor(R.color.strong);
            case "severe":
                return resources.getColor(R.color.severe);
            default:
                return Color.LTGRAY;
        }
    }

    @Override
    public int getItemCount() {
        return mFeatures.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {

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
                listener.onFeatureClicked(view, mFeatures.get(viewHolder.getAdapterPosition()));
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_summary, viewGroup, false);
        return new ViewHolder(view);
    }

    public void setFeatures(Feature[] features) {
        mFeatures.clear();
        mFeatures.addAll(Arrays.asList(features));
        notifyDataSetChanged();
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

    public interface Listener {
        void onFeatureClicked(View view, Feature feature);
    }
}
