package nz.co.codebros.quakesnz.core.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Geometry(
        val type: String,
        val coordinates: Coordinates
) : Parcelable
