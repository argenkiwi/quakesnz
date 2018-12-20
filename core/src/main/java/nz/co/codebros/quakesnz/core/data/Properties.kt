package nz.co.codebros.quakesnz.core.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Properties(
        @SerializedName("publicID")
        val publicId: String,

        @SerializedName("time")
        val time: Date,

        @SerializedName("depth")
        val depth: Double,

        @SerializedName("magnitude")
        val magnitude: Double,

        @SerializedName("locality")
        val locality: String,

        @SerializedName("mmi")
        val mmi: Int,

        @SerializedName("quality")
        val quality: String
) : Parcelable
