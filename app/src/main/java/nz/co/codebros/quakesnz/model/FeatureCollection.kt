package nz.co.codebros.quakesnz.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class FeatureCollection(
        @SerializedName("features")
        val features: Array<Feature>
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createTypedArray(Feature))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedArray(features, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FeatureCollection> {
        override fun createFromParcel(parcel: Parcel): FeatureCollection {
            return FeatureCollection(parcel)
        }

        override fun newArray(size: Int): Array<FeatureCollection?> {
            return arrayOfNulls(size)
        }
    }
}
