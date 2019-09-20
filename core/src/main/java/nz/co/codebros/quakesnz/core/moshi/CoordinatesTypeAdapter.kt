package nz.co.codebros.quakesnz.core.moshi

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.ToJson
import nz.co.codebros.quakesnz.core.data.Coordinates

class CoordinatesTypeAdapter {

    @FromJson
    fun fromJson(coordinates: Array<Double>) = when {
        coordinates.size != 2 -> throw JsonDataException("Unknown coordinates:$coordinates")
        else -> Coordinates(coordinates[1], coordinates[0])
    }

    @ToJson
    fun toJson(coordinates: Coordinates) = with(coordinates) {
        arrayOf(longitude, latitude)
    }
}
