package nz.co.codebros.quakesnz.detail

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import dagger.android.support.AndroidSupportInjection
import nz.co.codebros.quakesnz.QuakesUtils
import nz.co.codebros.quakesnz.R
import nz.co.codebros.quakesnz.ViewModelFactory
import nz.co.codebros.quakesnz.core.data.Feature
import java.util.*
import javax.inject.Inject

class QuakeDetailFragment : Fragment(), QuakeDetailView {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory<QuakeDetailViewModel>
    private lateinit var viewModel: QuakeDetailViewModel

    private lateinit var mMagnitudeBigView: TextView
    private lateinit var mTabView: View
    private lateinit var mMagnitudeSmallView: TextView
    private lateinit var mIntensityView: TextView
    private lateinit var mTimeView: TextView
    private lateinit var mLocationView: TextView
    private lateinit var mDepthView: TextView
    private lateinit var mShareButton: View

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_quake_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mMagnitudeBigView = view.findViewById<View>(R.id.magnitude_big) as TextView
        mMagnitudeSmallView = view.findViewById<View>(R.id.magnitude_small) as TextView
        mIntensityView = view.findViewById<View>(R.id.intensity) as TextView
        mLocationView = view.findViewById<View>(R.id.location) as TextView
        mDepthView = view.findViewById<View>(R.id.depth) as TextView
        mTimeView = view.findViewById<View>(R.id.time) as TextView
        mTabView = view.findViewById<View>(R.id.colorTab)
        mShareButton = view.findViewById<View>(R.id.share_button);
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        AndroidSupportInjection.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(QuakeDetailViewModel::class.java)
        viewModel.stateLiveData.observe(this, QuakeDetailStatePresenter(this))
        viewModel.eventLiveData.observe(this, QuakeDetailEventPresenter(this))
    }

    override fun updateFeature(feature: Feature) {
        val properties = feature.properties
        val colorForIntensity = QuakesUtils.getColor(context!!, properties.mmi)
        val magnitude = String.format(Locale.ENGLISH, "%1$.1f", properties.magnitude)
                .split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        mMagnitudeBigView.text = magnitude[0]
        mMagnitudeBigView.setTextColor(colorForIntensity)
        mMagnitudeSmallView.text = ".${magnitude[1]}"
        mMagnitudeSmallView.setTextColor(colorForIntensity)
        mIntensityView.text = QuakesUtils.getIntensity(context!!, properties.mmi)
        mLocationView.text = properties.locality
        mDepthView.text = getString(R.string.depth, properties.depth)
        mTimeView.text = DateUtils.getRelativeTimeSpanString(properties.time.time)
        mTabView.setBackgroundColor(colorForIntensity)
        mShareButton.setOnClickListener({
            when (it.id) {
                R.id.share_button -> {
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
                }
            }
        })
    }

    override fun showLoadingError() {
        Toast.makeText(context, R.string.error_loading_feature, Toast.LENGTH_SHORT).show()
    }
}
