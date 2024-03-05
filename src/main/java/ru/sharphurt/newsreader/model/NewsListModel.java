package ru.sharphurt.newsreader.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import lombok.Getter;
import ru.sharphurt.newsreader.domain.entity.News;
import ru.sharphurt.newsreader.model.comparator.PublicationDateAscending;

import java.util.Comparator;
import java.util.List;

@Getter
public class NewsListModel {

    private final NewsModel currentNews;
    private final ListProperty<News> newsList;
    private final IntegerProperty currentNewsIndex;

    public NewsListModel() {
        currentNews = new NewsModel();
        newsList = new SimpleListProperty<>(FXCollections.observableArrayList());
        currentNewsIndex = new SimpleIntegerProperty();
    }

    public void initializeWithNews(List<News> news) {
        addNews(news);
        sortNewsList(new PublicationDateAscending());
        selectByIndex(0);
    }

    public void addNews(List<News> newsList) {
        this.newsList.addAll(newsList);
    }

    public void selectByIndex(int index) {
        if (index < 0 || index > newsList.size())
            return;

        currentNewsIndex.set(index);
        currentNews.setNewsData(newsList.get(index));
    }

    public void sortNewsList(Comparator<News> comparator) {
        newsList.sort(comparator);
    }

    public boolean tryGoToPreviousNews() {
        if (currentNewsIndex.lessThanOrEqualTo(0).get())
            return false;

        selectByIndex(currentNewsIndex.get() - 1);
        return true;
    }

    public boolean tryGoToNextNews() {
        if (currentNewsIndex.greaterThanOrEqualTo(newsList.size() - 1).get())
            return false;

        selectByIndex(currentNewsIndex.get() + 1);
        return true;
    }
}
