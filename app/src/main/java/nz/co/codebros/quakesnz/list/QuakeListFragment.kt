package nz.co.codebros.quakesnz.list

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.analytics.HitBuilders
import com.google.android.gms.analytics.Tracker
import dagger.android.support.AndroidSupportInjection
import nz.co.codebros.quakesnz.R
import nz.co.codebros.quakesnz.ui.FeatureAdapter
import javax.inject.Inject
import javax.inject.Named

class QuakeListFragment : Fragment() {

    @Inject
    internal lateinit var viewModel: QuakeListViewModel

    @Inject
    lateinit var listener: OnFeatureClickedListener

    @field:[Inject Named("app")]
    lateinit var tracker: Tracker

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_quakes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout = view as SwipeRefreshLayout
        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        AndroidSupportInjection.inject(this)

        swipeRefreshLayout.setOnRefreshListener({
            tracker.send(HitBuilders.EventBuilder()
                    .setCategory("Interactions")
                    .setAction("Refresh")
                    .setLabel("Refresh")
                    .build())

            viewModel.onRefresh()
        })

        val featureAdapter = FeatureAdapter({ itemView, feature ->
            listener.onFeatureClicked(itemView)
            viewModel.onSelectFeature(feature)
        })

        recyclerView.let {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = featureAdapter
        }

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

    interface OnFeatureClickedListener {
        fun onFeatureClicked(view: View)
    }
}
