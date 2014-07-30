package nz.co.codebros.quakesnz.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by leandro on 29/03/14.
 */
public class DateDeserializer implements JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        String date = json.getAsString();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
