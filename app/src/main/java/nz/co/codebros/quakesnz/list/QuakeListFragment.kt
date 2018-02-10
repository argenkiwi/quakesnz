package nz.co.codebros.quakesnz.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import dagger.android.support.AndroidSupportInjection
import nz.co.codebros.quakesnz.R
import nz.co.codebros.quakesnz.ui.FeatureAdapter
import javax.inject.Inject

class QuakeListFragment : Fragment() {

    @Inject
    internal lateinit var viewModel: QuakeListViewModel

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

        try {
            viewModel = ViewModelProviders.of(this).get(QuakeListViewModel::class.java)
        } catch (t: Throwable) {
            AndroidSupportInjection.inject(this)
        }

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.quakeListModel.publish(QuakeListEvent.RefreshQuakes)
        }

        val featureAdapter = FeatureAdapter({ _, feature ->
            viewModel.quakeListModel.publish(QuakeListEvent.SelectQuake(feature))
        })

        recyclerView.let {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = featureAdapter
        }

        viewModel.quakeListModel.liveState.observe(this, Observer {
            it?.apply {
                swipeRefreshLayout.isRefreshing = isLoading
                features?.let { featureAdapter.setFeatures(it) }
                selectedFeature?.let { featureAdapter.setSelectedFeature(it) }
                error?.let {
                    Toast.makeText(context, R.string.failed_to_download, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
