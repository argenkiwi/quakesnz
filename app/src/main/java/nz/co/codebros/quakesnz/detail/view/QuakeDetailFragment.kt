package nz.co.codebros.quakesnz.detail.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.analytics.FirebaseAnalytics
import nz.co.codebros.quakesnz.R
import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.codebros.quakesnz.databinding.ItemSummaryBinding
import nz.co.codebros.quakesnz.detail.QuakeDetailViewModel
import nz.co.codebros.quakesnz.list.view.ItemSummaryProperties
import nz.co.codebros.quakesnz.list.view.bind
import nz.co.codebros.quakesnz.util.QuakesUtils
import nz.co.vilemob.daggerviewmodel.ViewModelFragment
import java.util.Locale

class QuakeDetailFragment : ViewModelFragment<QuakeDetailViewModel>(), QuakeDetailView {

    private lateinit var itemSummaryViewBinding: ItemSummaryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_quake_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.itemSummaryViewBinding = ItemSummaryBinding.bind(view.findViewById(R.id.item_summary))
    }

    override fun onCreateViewModel(viewModelProvider: ViewModelProvider) =
        viewModelProvider.get(QuakeDetailViewModel::class.java)

    override fun onViewModelCreated(viewModel: QuakeDetailViewModel) {
        viewModel.quakeDetailModel.state.observe(this, QuakeDetailStatePresenter(this))
        viewModel.quakeDetailModel.events.observe(this, QuakeDetailEventPresenter(this))
    }

    override fun updateFeature(feature: Feature) {
        itemSummaryViewBinding.bind(ItemSummaryProperties(feature)) { v, f ->
            with(f.properties) {
                FirebaseAnalytics.getInstance(v.context).logEvent(
                    FirebaseAnalytics.Event.SHARE,
                    bundleOf(
                        FirebaseAnalytics.Param.CONTENT_TYPE to "quake",
                        FirebaseAnalytics.Param.ITEM_ID to publicID
                    )
                )

                startActivity(
                    Intent().setAction(Intent.ACTION_SEND).putExtra(
                        Intent.EXTRA_TEXT, getString(
                            R.string.default_share_content, QuakesUtils.getIntensity(v.context, mmi)
                                .toLowerCase(Locale.getDefault()), magnitude, locality, publicID
                        )
                    ).setType("text/plain")
                )
            }
        }
    }

    override fun showLoadingError() {
        Toast.makeText(context, R.string.error_loading_feature, Toast.LENGTH_SHORT).show()
    }
}
