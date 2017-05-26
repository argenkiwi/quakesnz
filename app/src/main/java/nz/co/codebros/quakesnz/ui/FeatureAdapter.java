package nz.co.codebros.quakesnz.ui;

import android.content.Context;
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
import nz.co.codebros.quakesnz.utils.QuakesUtils;

/**
 * Created by leandro on 12/07/15.
 */
public class FeatureAdapter extends RecyclerView.Adapter<FeatureAdapter.ViewHolder> {

    private final Listener listener;
    private ArrayList<Feature> features = new ArrayList<>();

    public FeatureAdapter(Listener listener) {
        this.listener = listener;
    }

    public Feature[] getFeatures() {
        Feature[] features = new Feature[this.features.size()];
        this.features.toArray(features);
        return features;
    }

    public void setFeatures(Feature[] features) {
        this.features.clear();
        this.features.addAll(Arrays.asList(features));
        notifyDataSetChanged();
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

        final Context context = viewHolder.itemView.getContext();
        final int colorForIntensity = QuakesUtils.getColor(context, item.getProperties().getMmi());
        viewHolder.txtMagnitudeBig.setTextColor(colorForIntensity);
        viewHolder.txtMagnitudeSmall.setText("." + magnitude[1]);
        viewHolder.txtMagnitudeSmall.setTextColor(colorForIntensity);
        viewHolder.txtIntensity.setText(QuakesUtils.getIntensity(context, item.getProperties().getMmi()));

        viewHolder.txtLocation.setText(item.getProperties().getLocality());
        viewHolder.txtDepth.setText(context.getString(R.string.depth, item.getProperties()
                .getDepth()));
        viewHolder.txtTime.setText(DateUtils.getRelativeTimeSpanString(item.getProperties()
                .getTime().getTime()));
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

    public interface Listener {
        void onFeatureClicked(View view, Feature feature);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtMagnitudeBig;
        private final TextView txtMagnitudeSmall;
        private final TextView txtIntensity;
        private final TextView txtLocation;
        private final TextView txtDepth;
        private final TextView txtTime;
        private final View vTab;

        ViewHolder(View itemView) {
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
