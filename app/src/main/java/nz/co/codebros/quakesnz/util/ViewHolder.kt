package nz.co.codebros.quakesnz.util

import kotlinx.android.extensions.LayoutContainer

interface ViewHolder<T> : LayoutContainer {
    fun bind(props: T)
}
