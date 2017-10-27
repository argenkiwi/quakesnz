package nz.co.codebros.quakesnz.core.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class FeatureCollection(
        @SerializedName("features")
        val features: List<Feature>
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(Feature))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(features)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<FeatureCollection> {
        override fun createFromParcel(parcel: Parcel): FeatureCollection = FeatureCollection(parcel)

        override fun newArray(size: Int): Array<FeatureCollection?> = arrayOfNulls(size)
    }
}
