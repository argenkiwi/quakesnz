package nz.co.codebros.quakesnz.core.data

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import java.io.Serializable
import kotlinx.parcelize.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
data class FeatureCollection(
        val features: List<Feature>
) : Parcelable, Serializable
