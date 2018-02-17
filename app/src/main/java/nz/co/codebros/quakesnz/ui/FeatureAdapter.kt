package nz.co.codebros.quakesnz.ui

import android.support.v7.util.DiffUtil
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import nz.co.codebros.quakesnz.QuakesUtils
import nz.co.codebros.quakesnz.R
import nz.co.codebros.quakesnz.core.data.Feature
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by leandro on 12/07/15.
 */
class FeatureAdapter(
        private val onItemClicked: (view: View, feature: Feature) -> Unit
) : RecyclerView.Adapter<FeatureAdapter.ViewHolder>() {
    private var features: List<Feature> = ArrayList()

    private var selectedPosition: Int = -1
        set(value) {
            if (value != field) {
                notifyItemChanged(selectedPosition)
                field = value
                notifyItemChanged(selectedPosition)
            }
        }

    fun setFeatures(features: List<Feature>) {
        DiffUtil.calculateDiff(DiffCallback(this.features, features))
                .dispatchUpdatesTo(this)

        this.features = features
    }

    fun setSelectedFeature(feature: Feature?) {
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
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_summary, viewGroup, false)
        return ViewHolder(view, { onItemClicked(view, features[it]) })
    }

    class ViewHolder(
            itemView: View,
            onClick: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        val cardView = itemView as CardView
        val txtMagnitudeBig: TextView = itemView.findViewById<TextView>(R.id.magnitudeBigTextView)
        val txtMagnitudeSmall: TextView = itemView.findViewById<TextView>(R.id.magnitudeSmallTextView)
        val txtIntensity: TextView = itemView.findViewById<TextView>(R.id.intensityTextView)
        val txtLocation: TextView = itemView.findViewById<TextView>(R.id.locationTextView)
        val txtDepth: TextView = itemView.findViewById<TextView>(R.id.depthTextView)
        val txtTime: TextView = itemView.findViewById<TextView>(R.id.timeTextView)
        val vTab: View = itemView.findViewById(R.id.colorTabView)

        init {
            itemView.setOnClickListener({ onClick(adapterPosition) })
        }
    }

    private class DiffCallback(
            private val oldFeatures: List<Feature>,
            private val newFeatures: List<Feature>
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                oldFeatures[oldItemPosition].properties.publicId == newFeatures[newItemPosition].properties.publicId

        override fun getOldListSize() = oldFeatures.size

        override fun getNewListSize() = newFeatures.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                oldFeatures[oldItemPosition] == newFeatures[newItemPosition]
    }
}
