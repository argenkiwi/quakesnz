package nz.co.codebros.quakesnz.core.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Properties(
        val publicID: String,
        val time: Date,
        val depth: Double,
        val magnitude: Double,
        val locality: String,
        val mmi: Int,
        val quality: String
) : Parcelable
