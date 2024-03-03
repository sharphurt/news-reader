package ru.sharphurt.newsreader.model.comparator;

import ru.sharphurt.newsreader.domain.entity.News;

import java.util.Comparator;

public class PublicationDateAscending implements Comparator<News> {

    @Override
    public int compare(News o1, News o2) {
        return o1.getPublicationDate().compareTo(o2.getPublicationDate());
    }
}
