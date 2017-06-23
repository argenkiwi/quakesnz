package nz.co.codebros.quakesnz.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Feature(
        @SerializedName("geometry")
        val geometry: Geometry? = null,

        @SerializedName("properties")
        val properties: Properties? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readParcelable(Geometry::class.java.classLoader),
            parcel.readParcelable(Properties::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(geometry, flags)
        parcel.writeParcelable(properties, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Feature> {
        override fun createFromParcel(parcel: Parcel): Feature {
            return Feature(parcel)
        }

        override fun newArray(size: Int): Array<Feature?> {
            return arrayOfNulls(size)
        }
    }
}
