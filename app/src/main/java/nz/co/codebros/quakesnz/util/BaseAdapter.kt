package nz.co.codebros.quakesnz.util

import androidx.recyclerview.widget.ListAdapter

abstract class BaseAdapter : ListAdapter<RecyclableViewHolder.Properties, RecyclableViewHolder<*>>(RecyclableViewHolder.DiffCallback) {
    override fun getItemViewType(position: Int) = getItem(position).itemViewType
}
