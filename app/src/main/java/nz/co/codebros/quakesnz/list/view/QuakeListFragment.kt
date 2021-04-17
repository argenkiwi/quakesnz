package nz.co.codebros.quakesnz.list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import nz.co.codebros.quakesnz.R
import nz.co.codebros.quakesnz.list.QuakeListViewModel
import nz.co.codebros.quakesnz.list.model.QuakeListEvent

@AndroidEntryPoint
class QuakeListFragment : Fragment() {

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView

    private val viewModel: QuakeListViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_quakes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        recyclerView = view.findViewById(R.id.recyclerView)

        swipeRefreshLayout.setOnRefreshListener { viewModel.refreshQuakes() }

        val featureAdapter = FeatureAdapter { feature -> viewModel.selectQuake(feature) }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = featureAdapter
        }

        viewModel.liveEvents.observe(viewLifecycleOwner) {
            if (it is QuakeListEvent.LoadQuakesError) {
                Toast.makeText(context, R.string.failed_to_download, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.liveState.observe(viewLifecycleOwner) {
            it?.apply {
                swipeRefreshLayout.isRefreshing = isLoading
                features?.map { feature ->
                    ItemSummaryProperties(
                            feature,
                            feature == selectedFeature
                    )
                }?.let { propertiesList ->
                    featureAdapter.submitList(propertiesList)
                }
            }
        }
    }
}
