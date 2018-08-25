package nz.co.codebros.quakesnz.util

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView

class RecyclableViewHolder<T : RecyclableViewHolder.Properties>(
        viewHolder: ViewHolder<T>
) : RecyclerView.ViewHolder(viewHolder.view), ViewHolder<T> by viewHolder {

    interface Properties {
        val itemId: String
        val itemViewType: Int
    }

    object DiffCallback : DiffUtil.ItemCallback<Properties>() {

        override fun areItemsTheSame(
                oldItem: Properties?,
                newItem: Properties?
        ) = oldItem?.itemId == newItem?.itemId && oldItem?.itemViewType == newItem?.itemViewType

        override fun areContentsTheSame(
                oldItem: Properties?,
                newItem: Properties?
        ) = oldItem == newItem
    }
}