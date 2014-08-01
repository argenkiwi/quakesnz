package nz.co.codebros.quakesnz.utils;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by Leandro on 02/04/2014.
 */
public class LatLngAdapter extends TypeAdapter<LatLng>{

    @Override
    public void write(JsonWriter out, LatLng value) throws IOException {

        if (value == null) {
            out.nullValue();
            return;
        }

        out.beginArray();
        out.value(value.longitude);
        out.value(value.latitude);
        out.endArray();
    }

    @Override
    public LatLng read(JsonReader in) throws IOException {

        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        in.beginArray();
        final double lng = in.nextDouble();
        final double lat = in.nextDouble();
        in.endArray();

        return new LatLng(lat, lng);
    }
}
