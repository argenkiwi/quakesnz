package nz.co.codebros.quakesnz.list.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import nz.co.codebros.quakesnz.core.data.Feature
import nz.co.codebros.quakesnz.databinding.ItemSummaryBinding
import nz.co.codebros.quakesnz.util.RecyclableViewHolder
import nz.co.codebros.quakesnz.util.ViewHolder

class FeatureAdapter(
    private val onItemClicked: (view: View, feature: Feature) -> Unit
) : ListAdapter<ItemSummaryProperties, RecyclableViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclableViewHolder {
        return RecyclableViewHolder(ViewHolder(ItemSummaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)))
    }

    override fun onBindViewHolder(holder: RecyclableViewHolder, position: Int) {
        when (val viewBinding = holder.viewHolder.viewBinding) {
            is ItemSummaryBinding -> viewBinding.bind(getItem(position), onItemClicked)
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<ItemSummaryProperties>() {
        override fun areItemsTheSame(oldItem: ItemSummaryProperties, newItem: ItemSummaryProperties): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ItemSummaryProperties, newItem: ItemSummaryProperties): Boolean {
            return oldItem.feature.properties.publicID == newItem.feature.properties.publicID
        }
    }
}
