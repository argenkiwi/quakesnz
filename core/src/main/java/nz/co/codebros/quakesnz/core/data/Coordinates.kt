package nz.co.codebros.quakesnz.core.data

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by leandro on 3/07/17.
 */

data class Coordinates(
        val latitude: Double,
        val longitude: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readDouble(),
            parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Coordinates> {
        override fun createFromParcel(parcel: Parcel): Coordinates = Coordinates(parcel)

        override fun newArray(size: Int): Array<Coordinates?> = arrayOfNulls(size)
    }
}
