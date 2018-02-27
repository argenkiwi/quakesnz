package nz.co.codebros.quakesnz.core.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by leandro on 3/07/17.
 */
data class Geometry(
        @SerializedName("type")
        val type: String,

        @SerializedName("coordinates")
        val coordinates: Coordinates
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readParcelable(Coordinates::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
        parcel.writeParcelable(coordinates, flags)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Geometry> {
        override fun createFromParcel(parcel: Parcel): Geometry = Geometry(parcel)

        override fun newArray(size: Int): Array<Geometry?> = arrayOfNulls(size)
    }
}
