package nz.co.codebros.quakesnz.core.data

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Coordinates(
        val latitude: Double,
        val longitude: Double
) : Parcelable
