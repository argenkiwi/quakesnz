package nz.co.codebros.quakesnz.core.data

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Geometry(
        val type: String,
        val coordinates: Coordinates
) : Parcelable
