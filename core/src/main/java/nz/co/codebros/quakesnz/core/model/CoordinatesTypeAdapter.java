package nz.co.codebros.quakesnz.core.model;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by Leandro on 02/04/2014.
 */
public class CoordinatesTypeAdapter extends TypeAdapter<Coordinates>{

    @Override
    public void write(JsonWriter out, Coordinates value) throws IOException {

        if (value == null) {
            out.nullValue();
            return;
        }

        out.beginArray();
        out.value(value.getLongitude());
        out.value(value.getLatitude());
        out.endArray();
    }

    @Override
    public Coordinates read(JsonReader in) throws IOException {

        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        in.beginArray();
        final double lng = in.nextDouble();
        final double lat = in.nextDouble();
        in.endArray();

        return new Coordinates(lat, lng);
    }
}
