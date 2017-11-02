package nz.co.codebros.quakesnz.list

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.google.android.gms.analytics.HitBuilders
import com.google.android.gms.analytics.Tracker

import javax.inject.Inject
import javax.inject.Named
import nz.co.codebros.quakesnz.R
import nz.co.codebros.quakesnz.core.BaseFragment
import nz.co.codebros.quakesnz.core.BasePresenter
import nz.co.codebros.quakesnz.core.model.Feature
import nz.co.codebros.quakesnz.ui.FeatureAdapter

class QuakeListFragment : BaseFragment<Unit>(), QuakeListView, SwipeRefreshLayout.OnRefreshListener, FeatureAdapter.Listener {

    @Inject
    override lateinit var presenter: QuakeListPresenter

    @Inject
    internal lateinit var viewModel: QuakeListViewModel

    @Inject
    lateinit var featureAdapter: FeatureAdapter

    @Inject
    lateinit var listener: OnFeatureClickedListener

    @field:[Inject Named("app")]
    lateinit var tracker: Tracker

    private var swipeRefreshLayout: SwipeRefreshLayout? = null

    override fun hideProgress() {
        Log.d(TAG, "Hide progress.")
        swipeRefreshLayout!!.isRefreshing = false
    }

    override fun listQuakes(features: List<Feature>) {
        Log.d(TAG, "List quakes.")
        featureAdapter.setFeatures(features)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.features.observe(this, Observer { features -> listQuakes(features!!) })
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_quakes, container, false)

    override fun onFeatureClicked(view: View, feature: Feature) {
        Log.d(TAG, "Feature selected.")
        presenter.onFeatureSelected(feature)
        listener.onFeatureClicked(view)
    }

    override fun onRefresh() {
        tracker.send(HitBuilders.EventBuilder()
                .setCategory("Interactions")
                .setAction("Refresh")
                .setLabel("Refresh")
                .build())

        presenter.onInit(null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout = view as SwipeRefreshLayout
        swipeRefreshLayout!!.setOnRefreshListener(this)

        val recyclerView = view.findViewById<View>(R.id.recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = featureAdapter
    }

    override fun showError() {
        Log.d(TAG, "Show download failed message.")
        Toast.makeText(context, R.string.failed_to_download, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress() {
        Log.d(TAG, "Show progress.")
        swipeRefreshLayout!!.isRefreshing = true
    }

    override fun selectFeature(feature: Feature) {
        featureAdapter.setSelectedFeature(feature)
    }

    interface OnFeatureClickedListener {
        fun onFeatureClicked(view: View)
    }

    companion object {
        private val TAG = QuakeListFragment::class.java.simpleName
    }
}
