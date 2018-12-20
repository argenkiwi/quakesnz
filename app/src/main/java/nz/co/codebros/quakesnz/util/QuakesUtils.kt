package nz.co.codebros.quakesnz.util

import android.content.Context
import androidx.core.content.ContextCompat
import nz.co.codebros.quakesnz.R

object QuakesUtils {

    fun getColor(context: Context, mmi: Int) = when (mmi) {
        12, 11, 10, 9, 8, 7 -> ContextCompat.getColor(context, R.color.severe)
        6 -> ContextCompat.getColor(context, R.color.strong)
        5 -> ContextCompat.getColor(context, R.color.moderate)
        4 -> ContextCompat.getColor(context, R.color.light)
        3 -> ContextCompat.getColor(context, R.color.weak)
        else -> ContextCompat.getColor(context, R.color.unnoticeable)
    }

    fun getIntensity(context: Context, mmi: Int): String = when (mmi) {
        12, 11, 10, 9, 8, 7 -> context.getString(R.string.severe)
        6 -> context.getString(R.string.strong)
        5 -> context.getString(R.string.moderate)
        4 -> context.getString(R.string.light)
        3 -> context.getString(R.string.weak)
        else -> context.getString(R.string.unnoticeable)
    }
}
