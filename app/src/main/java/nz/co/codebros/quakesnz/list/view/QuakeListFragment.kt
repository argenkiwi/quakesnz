package nz.co.codebros.quakesnz.list.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import nz.co.codebros.quakesnz.R
import nz.co.codebros.quakesnz.list.QuakeListViewModel
import nz.co.codebros.quakesnz.list.model.QuakeListEvent
import nz.co.vilemob.daggerviewmodel.ViewModelFragment

class QuakeListFragment : ViewModelFragment<QuakeListViewModel>() {

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

    override fun onCreateViewModel(viewModelProvider: ViewModelProvider) =
            viewModelProvider[QuakeListViewModel::class.java]

    override fun onViewModelCreated(viewModel: QuakeListViewModel) {
        swipeRefreshLayout.setOnRefreshListener { viewModel.refreshQuakes() }

        val featureAdapter = FeatureAdapter({ _, feature -> viewModel.selectQuake(feature) })

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = featureAdapter
        }

        viewModel.quakeListModel.state.observe(this, Observer {
            it?.apply {
                swipeRefreshLayout.isRefreshing = isLoading
                features?.let { featureAdapter.submitList(it) }
                featureAdapter.setSelectedFeature(selectedFeature)
            }
        })

        viewModel.quakeListModel.events.observe(this, Observer {
            when (it) {
                is QuakeListEvent.LoadQuakesError -> {
                    Toast.makeText(context, R.string.failed_to_download, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
