package nz.co.codebros.quakesnz.core.data

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
@JsonClass(generateAdapter = true)
data class Coordinates(
        val latitude: Double,
        val longitude: Double
) : Parcelable, Serializable
