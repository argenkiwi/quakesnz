package nz.co.codebros.quakesnz.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
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
import nz.co.codebros.quakesnz.utils.LatLngUtils;

/**
 * Created by leandro on 12/07/15.
 */
public class FeatureAdapter extends RecyclerView.Adapter<FeatureAdapter.ViewHolder> {

    private final Listener listener;
    private ArrayList<Feature> features = new ArrayList<>();

    public FeatureAdapter(Listener listener) {
        this.listener = listener;
    }

    private static int getColorForIntensity(Context context, String intensity) {
        switch (intensity) {
            case "unnoticeable":
                return ContextCompat.getColor(context, R.color.unnoticeable);
            case "weak":
                return ContextCompat.getColor(context, R.color.weak);
            case "light":
                return ContextCompat.getColor(context, R.color.light);
            case "moderate":
                return ContextCompat.getColor(context, R.color.moderate);
            case "strong":
                return ContextCompat.getColor(context, R.color.strong);
            case "severe":
                return ContextCompat.getColor(context, R.color.severe);
            default:
                return Color.LTGRAY;
        }
    }

    @Override
    public int getItemCount() {
        return features.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        Feature item = features.get(i);

        String[] magnitude = String.format(Locale.ENGLISH, "%1$.1f", item.getProperties()
                .getMagnitude()).split("\\.");
        viewHolder.txtMagnitudeBig.setText(magnitude[0]);

        String intensity = item.getProperties().getIntensity();
        final Context context = viewHolder.itemView.getContext();
        final int colorForIntensity = getColorForIntensity(context, intensity);
        viewHolder.txtMagnitudeBig.setTextColor(colorForIntensity);
        viewHolder.txtMagnitudeSmall.setText("." + magnitude[1]);
        viewHolder.txtMagnitudeSmall.setTextColor(colorForIntensity);
        viewHolder.txtIntensity.setText(intensity);

        final long distance = Math.round(LatLngUtils.findDistance(item.getGeometry()
                .getCoordinates(), item.getClosestCity().getCoordinates()) / 1000);
        viewHolder.txtLocation.setText(context.getString(R.string.location, distance,
                item.getClosestCity().getName()));
        viewHolder.txtDepth.setText(context.getString(R.string.depth, item.getProperties()
                .getDepth()));
        viewHolder.txtTime.setText(DateUtils.getRelativeTimeSpanString(item.getProperties()
                .getOriginTime().getTime()));
        viewHolder.vTab.setBackgroundColor(colorForIntensity);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onFeatureClicked(view, features.get(viewHolder.getAdapterPosition()));
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
        this.features.clear();
        this.features.addAll(Arrays.asList(features));
        notifyDataSetChanged();
    }

    public interface Listener {
        void onFeatureClicked(View view, Feature feature);
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
