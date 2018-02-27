package nz.co.codebros.quakesnz.detail

import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_quake_detail.*
import kotlinx.android.synthetic.main.item_summary.*
import nz.co.codebros.quakesnz.util.QuakesUtils
import nz.co.codebros.quakesnz.R
import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.vilemob.daggerviewmodel.ViewModelFragment
import java.util.*

class QuakeDetailFragment : ViewModelFragment<QuakeDetailViewModel>(), QuakeDetailView {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_quake_detail, container, false)

    override fun onCreateViewModel(viewModelProvider: ViewModelProvider) =
            viewModelProvider.get(QuakeDetailViewModel::class.java)

    override fun onViewModelCreated(viewModel: QuakeDetailViewModel) {
        viewModel.quakeDetailModel.state.observe(this, QuakeDetailStatePresenter(this))
        viewModel.quakeDetailModel.events.observe(this, QuakeDetailEventPresenter(this))
    }

    override fun updateFeature(feature: Feature) {
        val properties = feature.properties
        val colorForIntensity = QuakesUtils.getColor(context!!, properties.mmi)
        val magnitude = String.format(Locale.ENGLISH, "%1$.1f", properties.magnitude)
                .split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        magnitudeBigTextView.text = magnitude[0]
        magnitudeBigTextView.setTextColor(colorForIntensity)
        magnitudeSmallTextView.text = ".${magnitude[1]}"
        magnitudeSmallTextView.setTextColor(colorForIntensity)
        intensityTextView.text = QuakesUtils.getIntensity(context!!, properties.mmi)
        locationTextView.text = properties.locality
        depthTextView.text = getString(R.string.depth, properties.depth)
        timeTextView.text = DateUtils.getRelativeTimeSpanString(properties.time.time)
        colorTabView.setBackgroundColor(colorForIntensity)
        shareFloatingActionButton.setOnClickListener({
            startActivity(Intent()
                    .setAction(Intent.ACTION_SEND)
                    .putExtra(Intent.EXTRA_TEXT, getString(R.string.default_share_content,
                            QuakesUtils.getIntensity(context!!, properties.mmi)
                                    .toLowerCase(),
                            properties.magnitude,
                            properties.locality,
                            properties.publicId
                    ))
                    .setType("text/plain"))
        })
    }

    override fun showLoadingError() {
        Toast.makeText(context, R.string.error_loading_feature, Toast.LENGTH_SHORT).show()
    }
}
