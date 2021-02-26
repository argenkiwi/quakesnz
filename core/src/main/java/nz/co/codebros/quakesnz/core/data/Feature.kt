package nz.co.codebros.quakesnz.core.data

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
@JsonClass(generateAdapter = true)
data class Feature(
        val geometry: Geometry,
        val properties: Properties
) : Parcelable, Serializable
