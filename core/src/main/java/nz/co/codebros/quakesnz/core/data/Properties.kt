package nz.co.codebros.quakesnz.core.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

import java.util.Date

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
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            Date(parcel.readLong()),
            parcel.readDouble(),
            parcel.readDouble(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(publicId)
        parcel.writeLong(time.time)
        parcel.writeDouble(depth)
        parcel.writeDouble(magnitude)
        parcel.writeString(locality)
        parcel.writeInt(mmi)
        parcel.writeString(quality)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Properties> {
        override fun createFromParcel(parcel: Parcel): Properties = Properties(parcel)

        override fun newArray(size: Int): Array<Properties?> = arrayOfNulls(size)
    }
}
