package nz.co.codebros.quakesnz.core.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Geometry(
        @SerializedName("type")
        val type: String,

        @SerializedName("coordinates")
        val coordinates: Coordinates
) : Parcelable
