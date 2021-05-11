package nz.co.codebros.quakesnz.list.view

import android.text.format.DateUtils
import nz.co.codebros.quakesnz.R
import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.codebros.quakesnz.databinding.ItemSummaryBinding
import nz.co.codebros.quakesnz.util.QuakesUtils
import java.text.DecimalFormat

data class ItemSummaryProperties(val feature: Feature, val selected: Boolean = false)

fun ItemSummaryBinding.bind(
    props: ItemSummaryProperties,
    onItemClicked: ((feature: Feature) -> Unit)?
) {

    with(props) {
        val properties = feature.properties

        val magnitude = DecimalFormat("0.0")
            .format(properties.magnitude)
            .split(".")

        val colorForIntensity = QuakesUtils.getColor(root.context, properties.mmi)

        with(magnitudeBigTextView) {
            text = magnitude[0]
            setTextColor(colorForIntensity)
        }

        with(magnitudeSmallTextView) {
            text = ".${magnitude[1]}"
            setTextColor(colorForIntensity)
        }

        intensityTextView.text = QuakesUtils.getIntensity(intensityTextView.context, properties.mmi)
        locationTextView.text = properties.locality
        depthTextView.text = depthTextView.resources.getString(R.string.depth, properties.depth)
        timeTextView.text = DateUtils.getRelativeTimeSpanString(properties.time.time)
        colorTabView.setBackgroundColor(colorForIntensity)

        with(root) {
            cardElevation = when {
                selected -> 8.0f
                else -> 2.0f
            }
            setOnClickListener { onItemClicked?.invoke(feature) }
        }
    }
}
