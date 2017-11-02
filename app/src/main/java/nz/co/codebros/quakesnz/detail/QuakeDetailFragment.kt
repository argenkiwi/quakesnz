package nz.co.codebros.quakesnz.detail

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

import com.google.android.gms.analytics.HitBuilders
import com.google.android.gms.analytics.Tracker

import java.util.Locale

import javax.inject.Inject
import javax.inject.Named

import nz.co.codebros.quakesnz.R
import nz.co.codebros.quakesnz.core.model.Feature
import nz.co.codebros.quakesnz.core.model.Properties
import nz.co.codebros.quakesnz.core.BasePresenter
import nz.co.codebros.quakesnz.core.BaseFragment
import nz.co.codebros.quakesnz.QuakesUtils

class QuakeDetailFragment : BaseFragment<QuakeDetailProps>(), QuakeDetailView, View.OnClickListener {

    @Inject
    override lateinit var presenter: QuakeDetailPresenter

    @Inject
    internal lateinit var viewModel: QuakeDetailViewModel


    @field:[Inject Named("app")]
    internal lateinit var tracker: Tracker

    private var mMagnitudeBigView: TextView? = null
    private var mTabView: View? = null
    private var mMagnitudeSmallView: TextView? = null
    private var mIntensityView: TextView? = null
    private var mTimeView: TextView? = null
    private var mLocationView: TextView? = null
    private var mDepthView: TextView? = null
    private var feature: Feature? = null

    override fun onClick(view: View) {
        when (view.id) {
            R.id.share_button -> {
                tracker?.send(HitBuilders.EventBuilder()
                        .setCategory("Interactions")
                        .setAction("Share")
                        .setLabel("Share")
                        .build())

                presenter.onShare(feature!!)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.feature.observe(this, Observer { feature -> if (feature != null) showDetails(feature) })
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_quake_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mMagnitudeBigView = view.findViewById<View>(R.id.magnitude_big) as TextView
        mMagnitudeSmallView = view.findViewById<View>(R.id.magnitude_small) as TextView
        mIntensityView = view.findViewById<View>(R.id.intensity) as TextView
        mLocationView = view.findViewById<View>(R.id.location) as TextView
        mDepthView = view.findViewById<View>(R.id.depth) as TextView
        mTimeView = view.findViewById<View>(R.id.time) as TextView
        mTabView = view.findViewById(R.id.colorTab)

        view.findViewById<View>(R.id.share_button).setOnClickListener(this)
    }

    override fun fromArguments(bundle: Bundle): QuakeDetailProps? {
        val publicId = bundle.getString(ARG_PUBLIC_ID)
        return if (publicId != null) QuakeDetailProps(publicId) else null
    }

    override fun share(feature: Feature) {
        val properties = feature.properties
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

    override fun showDetails(feature: Feature) {
        this.feature = feature

        val properties = feature.properties
        val colorForIntensity = QuakesUtils.getColor(context!!, properties.mmi)
        val magnitude = String.format(Locale.ENGLISH, "%1$.1f", properties.magnitude)
                .split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        mMagnitudeBigView!!.text = magnitude[0]
        mMagnitudeBigView!!.setTextColor(colorForIntensity)
        mMagnitudeSmallView!!.text = "." + magnitude[1]
        mMagnitudeSmallView!!.setTextColor(colorForIntensity)
        mIntensityView!!.text = QuakesUtils.getIntensity(context!!, properties.mmi)
        mLocationView!!.text = properties.locality
        mDepthView!!.text = getString(R.string.depth, properties.depth)
        mTimeView!!.text = DateUtils.getRelativeTimeSpanString(properties.time.time)
        mTabView!!.setBackgroundColor(colorForIntensity)
    }

    override fun showLoadingError() {
        Toast.makeText(context, R.string.error_loading_feature, Toast.LENGTH_SHORT).show()
    }

    companion object {

        private val ARG_PUBLIC_ID = "arg_public_id"

        fun newInstance() = QuakeDetailFragment()

        fun newInstance(publicID: String): Fragment {
            val args = Bundle()
            args.putString(ARG_PUBLIC_ID, publicID)

            val fragment = newInstance()
            fragment.arguments = args
            return fragment
        }
    }
}
