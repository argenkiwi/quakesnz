package nz.co.codebros.quakesnz.core.data

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
@JsonClass(generateAdapter = true)
data class Geometry(
        val type: String,
        val coordinates: Coordinates
) : Parcelable, Serializable
