package nz.co.codebros.quakesnz.util

import android.support.v7.recyclerview.extensions.ListAdapter

abstract class BaseAdapter : ListAdapter<RecyclableViewHolder.Properties, RecyclableViewHolder<*>>(RecyclableViewHolder.DiffCallback){
    override fun getItemViewType(position: Int) = getItem(position).itemViewType
}