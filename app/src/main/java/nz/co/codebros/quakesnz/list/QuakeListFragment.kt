package nz.co.codebros.quakesnz.list

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import nz.co.codebros.quakesnz.R
import ar.soflete.daggerlifecycle.ViewModelFragment
import nz.co.codebros.quakesnz.ui.FeatureAdapter

class QuakeListFragment : ViewModelFragment<QuakeListViewModel>() {

    override val viewModelClass: Class<QuakeListViewModel>
        get() = QuakeListViewModel::class.java

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_quakes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        recyclerView = view.findViewById(R.id.recyclerView)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.quakeListModel.publish(QuakeListEvent.RefreshQuakes)
        }

        val featureAdapter = FeatureAdapter({ _, feature ->
            viewModel.quakeListModel.publish(QuakeListEvent.SelectQuake(feature))
        })

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = featureAdapter
        }

        viewModel.stateLiveData.observe(this, Observer {
            it?.apply {
                swipeRefreshLayout.isRefreshing = isLoading
                features?.let { featureAdapter.setFeatures(it) }
                selectedFeature?.let { featureAdapter.setSelectedFeature(it) }
            }
        })

        viewModel.eventLiveData.observe(this, Observer {
            it?.apply {
                Toast.makeText(context, R.string.failed_to_download, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
