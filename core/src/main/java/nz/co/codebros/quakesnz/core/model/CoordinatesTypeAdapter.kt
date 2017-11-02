package nz.co.codebros.quakesnz.core.model

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

import java.io.IOException

/**
 * Created by Leandro on 02/04/2014.
 */
class CoordinatesTypeAdapter : TypeAdapter<Coordinates>() {

    @Throws(IOException::class)
    override fun write(out: JsonWriter, value: Coordinates?) {
        when (value) {
            null -> out.nullValue()
            else -> value.let {
                out.beginArray()
                out.value(it.longitude)
                out.value(it.latitude)
                out.endArray()
            }
        }
    }

    @Throws(IOException::class)
    override fun read(`in`: JsonReader): Coordinates? {

        if (`in`.peek() == JsonToken.NULL) {
            `in`.nextNull()
            return null
        }

        `in`.beginArray()
        val lng = `in`.nextDouble()
        val lat = `in`.nextDouble()
        `in`.endArray()

        return Coordinates(lat, lng)
    }
}
