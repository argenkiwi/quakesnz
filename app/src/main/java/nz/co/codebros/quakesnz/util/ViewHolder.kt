package nz.co.codebros.quakesnz.util

import android.view.View

interface ViewHolder<T> {
    val view: View

    fun bind(props: T)
}
