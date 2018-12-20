package nz.co.codebros.quakesnz.list.view

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_summary.*
import nz.co.codebros.quakesnz.R
import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.codebros.quakesnz.util.QuakesUtils
import nz.co.codebros.quakesnz.util.RecyclableViewHolder
import nz.co.codebros.quakesnz.util.ViewHolder
import java.util.*

class FeatureViewHolder(
        val parent: ViewGroup,
        private val onItemClicked: (view: View, feature: Feature) -> Unit
) : ViewHolder<FeatureViewHolder.Properties> {

    override val containerView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_summary, parent, false)

    override fun bind(props: Properties) {
        with(props) {
            val properties = feature.properties
            val magnitude = String.format(Locale.ENGLISH, "%1$.1f", properties.magnitude)
                    .split("\\.".toRegex())
                    .dropLastWhile { it.isEmpty() }
                    .toTypedArray()


            val colorForIntensity = QuakesUtils.getColor(magnitudeBigTextView.context, properties.mmi)

            magnitudeBigTextView.apply {
                text = magnitude[0]
                setTextColor(colorForIntensity)
            }

            magnitudeSmallTextView.apply {
                text = ".${magnitude[1]}"
                setTextColor(colorForIntensity)
            }

            intensityTextView.text = QuakesUtils.getIntensity(intensityTextView.context, properties.mmi)
            locationTextView.text = properties.locality
            depthTextView.text = depthTextView.resources.getString(R.string.depth, properties.depth)
            timeTextView.text = DateUtils.getRelativeTimeSpanString(properties.time.time)
            colorTabView.setBackgroundColor(colorForIntensity)

            (containerView as androidx.cardview.widget.CardView).cardElevation = when {
                selected -> 8.0f
                else -> 2.0f
            }

            containerView.setOnClickListener { onItemClicked(it, feature) }
        }
    }

    data class Properties(
            val feature: Feature,
            val selected: Boolean,
            override val itemViewType: Int = -1
    ) : RecyclableViewHolder.Properties {
        override val itemId: String
            get() = feature.properties.publicId
    }
}
