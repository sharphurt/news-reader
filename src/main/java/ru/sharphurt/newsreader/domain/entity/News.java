package ru.sharphurt.newsreader.domain.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import ru.sharphurt.newsreader.serialization.ZonedDateTimeDeserializer;
import ru.sharphurt.newsreader.serialization.ZonedDateTimeSerializer;

import java.time.ZonedDateTime;

@Data
public class News {

    private String guid;
    private String title;
    private String description;
    @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    private ZonedDateTime publicationDate;
}
