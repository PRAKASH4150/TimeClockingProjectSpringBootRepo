package com.tracking.timeclocking.util;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomTimeDeserializer extends JsonDeserializer<Time> {

    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");

    @Override
    public Time deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        try {
            Date date = TIME_FORMAT.parse(p.getValueAsString());
            return new Time(date.getTime());
        } catch (ParseException e) {
            throw new IOException("Unable to parse time: " + p.getValueAsString(), e);
        }
    }
}
