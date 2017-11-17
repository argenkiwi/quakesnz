package nz.co.codebros.quakesnz.ui

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import nz.co.codebros.quakesnz.R
import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.codebros.quakesnz.QuakesUtils
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

/**
 * Created by leandro on 12/07/15.
 */
class FeatureAdapter @Inject constructor(
        private val listener: Listener
) : RecyclerView.Adapter<FeatureAdapter.ViewHolder>() {
    private val features: MutableList<Feature> = ArrayList()

    private var selectedPosition: Int = -1
        set(value) {
            if (value != field) {
                notifyItemChanged(selectedPosition)
                field = value
                notifyItemChanged(selectedPosition)
            }
        }

    fun setFeatures(features: List<Feature>) {
        this.features.clear()
        this.features.addAll(features)
        notifyDataSetChanged()
    }

    fun setSelectedFeature(feature: Feature) {
        selectedPosition = features.indexOf(feature)
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

        viewHolder.cardView.cardElevation = if (i == selectedPosition) 8.0f else 2.0f
        viewHolder.itemView.setOnClickListener { view ->
            selectedPosition = i
            listener.onFeatureClicked(view, feature)
        }
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
        val cardView = itemView as CardView
        val txtMagnitudeBig: TextView = itemView.findViewById<TextView>(R.id.magnitude_big)
        val txtMagnitudeSmall: TextView = itemView.findViewById<TextView>(R.id.magnitude_small)
        val txtIntensity: TextView = itemView.findViewById<TextView>(R.id.intensity)
        val txtLocation: TextView = itemView.findViewById<TextView>(R.id.location)
        val txtDepth: TextView = itemView.findViewById<TextView>(R.id.depth)
        val txtTime: TextView = itemView.findViewById<TextView>(R.id.time)
        val vTab: View = itemView.findViewById(R.id.colorTab)
    }
}
