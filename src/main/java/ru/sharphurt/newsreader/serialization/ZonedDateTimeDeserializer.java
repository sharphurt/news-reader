package ru.sharphurt.newsreader.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ZonedDateTimeDeserializer extends JsonDeserializer<java.time.ZonedDateTime> {

    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);

    @Override
    public java.time.ZonedDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return java.time.ZonedDateTime.parse(p.getText(), dateFormat);
    }
}