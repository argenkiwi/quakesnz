package nz.co.codebros.quakesnz.core.moshi

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.util.*

class DateTypeAdapter(pattern: String) {

    private val formatter = SimpleDateFormat(pattern, Locale.US).apply {
        timeZone = TimeZone.getTimeZone("GMT")
    }

    @FromJson
    fun fromJson(date: String) = formatter.parse(date)

    @ToJson
    fun toJson(date: Date) = formatter.format(date)
}
