package ru.sharphurt.newsreader.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import ru.sharphurt.newsreader.domain.entity.News;

import java.time.ZoneId;

import static ru.sharphurt.newsreader.constants.ApplicationConstants.READABLE_DATE_TIME_FORMAT;

@Getter
public class NewsModel {
    private final StringProperty titleProperty = new SimpleStringProperty();
    private final StringProperty descriptionProperty = new SimpleStringProperty();
    private final StringProperty publicationDateProperty = new SimpleStringProperty();

    public void setNewsData(News news) {
        titleProperty.set(news.getTitle());
        descriptionProperty.set(news.getDescription());

        var readableDate = news.getPublicationDate().withZoneSameInstant(ZoneId.systemDefault()).format(READABLE_DATE_TIME_FORMAT);
        publicationDateProperty.set(readableDate);
    }
}
