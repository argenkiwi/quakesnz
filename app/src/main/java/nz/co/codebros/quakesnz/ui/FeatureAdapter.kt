package nz.co.codebros.quakesnz.ui

import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import nz.co.codebros.quakesnz.R
import nz.co.codebros.quakesnz.model.Feature
import nz.co.codebros.quakesnz.utils.QuakesUtils
import java.util.*

/**
 * Created by leandro on 12/07/15.
 */
class FeatureAdapter(
        private val listener: Listener
) : RecyclerView.Adapter<FeatureAdapter.ViewHolder>() {
    private val features = ArrayList<Feature>()

    fun setFeatures(features: Array<Feature>) {
        this.features.clear()
        this.features.addAll(Arrays.asList(*features))
        notifyDataSetChanged()
    }

    override fun getItemCount() = features.size

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val feature = features[i]
        val magnitude = String.format(Locale.ENGLISH, "%1$.1f", feature.properties.magnitude)
                .split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val context = viewHolder.itemView.context
        val colorForIntensity = QuakesUtils.getColor(context, feature.properties.mmi)

        viewHolder.txtMagnitudeBig.text = magnitude[0]
        viewHolder.txtMagnitudeBig.setTextColor(colorForIntensity)
        viewHolder.txtMagnitudeSmall.text = ".${magnitude[1]}"
        viewHolder.txtMagnitudeSmall.setTextColor(colorForIntensity)
        viewHolder.txtIntensity.text = QuakesUtils.getIntensity(context, feature.properties.mmi)
        viewHolder.txtLocation.text = feature.properties.locality
        viewHolder.txtDepth.text = context.getString(R.string.depth, feature.properties.depth)
        viewHolder.txtTime.text = DateUtils.getRelativeTimeSpanString(feature.properties.time.time)
        viewHolder.vTab.setBackgroundColor(colorForIntensity)

        viewHolder.itemView.setOnClickListener { view -> listener.onFeatureClicked(view, feature) }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_summary, viewGroup, false)
        return ViewHolder(view)
    }

    interface Listener {
        fun onFeatureClicked(view: View, feature: Feature)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtMagnitudeBig = itemView.findViewById(R.id.magnitude_big) as TextView
        val txtMagnitudeSmall = itemView.findViewById(R.id.magnitude_small) as TextView
        val txtIntensity = itemView.findViewById(R.id.intensity) as TextView
        val txtLocation = itemView.findViewById(R.id.location) as TextView
        val txtDepth = itemView.findViewById(R.id.depth) as TextView
        val txtTime = itemView.findViewById(R.id.time) as TextView
        val vTab: View = itemView.findViewById(R.id.colorTab)
    }
}
