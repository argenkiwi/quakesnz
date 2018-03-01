package nz.co.codebros.quakesnz.list.view

import android.support.v7.recyclerview.extensions.AsyncListDiffer
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nz.co.codebros.quakesnz.R
import nz.co.codebros.quakesnz.core.data.Feature

/**
 * Created by leandro on 12/07/15.
 */
class FeatureAdapter(
        private val onItemClicked: (view: View, feature: Feature) -> Unit
) : RecyclerView.Adapter<FeatureViewHolder>() {

    private val differ = AsyncListDiffer(this, Companion)

    private var selectedPosition: Int = -1
        set(value) {
            if (value != field) {
                notifyItemChanged(selectedPosition)
                field = value
                notifyItemChanged(selectedPosition)
            }
        }

    override fun getItemCount() = differ.currentList.size

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int) = viewGroup
            .run { LayoutInflater.from(context) }
            .run { inflate(R.layout.item_summary, viewGroup, false) }
            .let { view -> FeatureViewHolder(view, onItemClicked) }

    override fun onBindViewHolder(viewHolder: FeatureViewHolder, i: Int) {
        viewHolder.bind(differ.currentList[i], selectedPosition == i)
    }

    fun submitList(features: List<Feature>) {
        differ.submitList(features)
    }

    fun setSelectedFeature(feature: Feature?) {
        selectedPosition = differ.currentList.indexOf(feature)
    }

    companion object : DiffUtil.ItemCallback<Feature>() {
        override fun areItemsTheSame(oldItem: Feature, newItem: Feature) =
                oldItem.properties.publicId == newItem.properties.publicId

        override fun areContentsTheSame(oldItem: Feature?, newItem: Feature?) =
                oldItem == newItem
    }
}
