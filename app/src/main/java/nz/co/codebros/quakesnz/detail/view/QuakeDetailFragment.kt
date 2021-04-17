package nz.co.codebros.quakesnz.detail.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint
import nz.co.codebros.quakesnz.R
import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.codebros.quakesnz.databinding.FragmentQuakeDetailBinding
import nz.co.codebros.quakesnz.databinding.ItemSummaryBinding
import nz.co.codebros.quakesnz.detail.QuakeDetailViewModel
import nz.co.codebros.quakesnz.list.view.ItemSummaryProperties
import nz.co.codebros.quakesnz.list.view.bind
import nz.co.codebros.quakesnz.util.QuakesUtils
import java.util.*

@AndroidEntryPoint
class QuakeDetailFragment : Fragment(), QuakeDetailView {

    private lateinit var binding: FragmentQuakeDetailBinding
    private lateinit var itemSummaryBinding: ItemSummaryBinding
    private val viewModel: QuakeDetailViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentQuakeDetailBinding.inflate(inflater, container, false)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.itemSummaryBinding = ItemSummaryBinding.bind(view.findViewById(R.id.item_summary))
        viewModel.quakeDetailModel.state.observe(viewLifecycleOwner, QuakeDetailStatePresenter(this))
        viewModel.quakeDetailModel.events.observe(viewLifecycleOwner, QuakeDetailEventPresenter(this))
    }

    override fun updateFeature(feature: Feature) {
        binding.shareFloatingActionButton.setOnClickListener {
            with(feature.properties) {
                FirebaseAnalytics.getInstance(requireContext()).logEvent(
                        FirebaseAnalytics.Event.SHARE,
                        bundleOf(
                                FirebaseAnalytics.Param.CONTENT_TYPE to "quake",
                                FirebaseAnalytics.Param.ITEM_ID to publicID
                        )
                )

                val intensity = QuakesUtils.getIntensity(requireContext(), mmi)
                        .toLowerCase(Locale.getDefault())
                val shareContent = getString(R.string.default_share_content, intensity, magnitude, locality, publicID)
                val intent = Intent().setAction(Intent.ACTION_SEND)
                        .putExtra(Intent.EXTRA_TEXT, shareContent)
                        .setType("text/plain")

                startActivity(intent)
            }
        }
        itemSummaryBinding.bind(ItemSummaryProperties(feature), null)
    }

    override fun showLoadingError() {
        Toast.makeText(context, R.string.error_loading_feature, Toast.LENGTH_SHORT).show()
    }
}
