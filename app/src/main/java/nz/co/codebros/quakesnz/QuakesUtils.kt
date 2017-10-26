package nz.co.codebros.quakesnz

import android.content.Context
import android.support.v4.content.ContextCompat

import nz.co.codebros.quakesnz.R

/**
 * Created by leandro on 12/04/16.
 */
object QuakesUtils {

    fun getColor(context: Context, mmi: Int): Int = when (mmi) {
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
