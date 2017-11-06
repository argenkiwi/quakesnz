package nz.co.codebros.quakesnz.list

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
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
import dagger.android.support.DaggerFragment
import nz.co.codebros.quakesnz.R
import nz.co.codebros.quakesnz.core.model.Feature
import nz.co.codebros.quakesnz.ui.FeatureAdapter
import javax.inject.Inject
import javax.inject.Named

class QuakeListFragment : DaggerFragment(), FeatureAdapter.Listener {

    @Inject
    internal lateinit var viewModel: QuakeListViewModel

    @Inject
    lateinit var featureAdapter: FeatureAdapter

    @Inject
    lateinit var listener: OnFeatureClickedListener

    @field:[Inject Named("app")]
    lateinit var tracker: Tracker

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.state.observe(this, Observer {
            it?.let { swipeRefreshLayout.isRefreshing = it.isLoading }
            it?.features?.let { featureAdapter.setFeatures(it) }
            it?.selectedFeature?.let { featureAdapter.setSelectedFeature(it) }
            it?.error?.let {
                Toast.makeText(context, R.string.failed_to_download, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.onRefresh()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_quakes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout = view as SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener({
            tracker.send(HitBuilders.EventBuilder()
                    .setCategory("Interactions")
                    .setAction("Refresh")
                    .setLabel("Refresh")
                    .build())

            viewModel.onRefresh()
        })

        val recyclerView = view.findViewById<View>(R.id.recycler_view) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = featureAdapter
    }

    override fun onFeatureClicked(view: View, feature: Feature) {
        viewModel.onSelectFeature(feature)
        listener.onFeatureClicked(view)
    }

    interface OnFeatureClickedListener {
        fun onFeatureClicked(view: View)
    }
}
