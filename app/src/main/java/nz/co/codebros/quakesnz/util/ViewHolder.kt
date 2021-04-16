package nz.co.codebros.quakesnz.util

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class ViewHolder<T: ViewBinding>(
    val viewBinding: T
) : RecyclerView.ViewHolder(viewBinding.root)
