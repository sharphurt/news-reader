package ru.sharphurt.newsreader.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import lombok.Getter;
import ru.sharphurt.newsreader.client.NewsLoader;
import ru.sharphurt.newsreader.domain.entity.News;
import ru.sharphurt.newsreader.model.comparator.PublicationDateAscending;

import java.util.Comparator;
import java.util.List;

import static ru.sharphurt.newsreader.constants.ApplicationConstants.NEWS_STORAGE_BASE_URL;

@Getter
public class NewsListModel {

    private final NewsModel currentNews;
    private final ListProperty<News> newsList;
    private final IntegerProperty currentNewsIndex;
    private final NewsLoader newsLoader;

    public NewsListModel() {

        currentNews = new NewsModel();
        newsList = new SimpleListProperty<>(FXCollections.observableArrayList());
        currentNewsIndex = new SimpleIntegerProperty();
        newsLoader = new NewsLoader(NEWS_STORAGE_BASE_URL);

        newsLoader.getIsSuccessfullyLoaded().addListener((obs, oldValue, newValue) -> handleNewsLoading(newValue));
    }

    public void addNews(List<News> newsList) {
        this.newsList.addAll(newsList);
    }

    public void selectByIndex(int index) {
        currentNewsIndex.set(index);
        currentNews.setNewsData(newsList.get(index));
    }

    private void sortNewsList(Comparator<News> comparator) {
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

    public void handleNewsLoading(boolean state) {
        if (!state)
            return;

        addNews(newsLoader.getResult());
        sortNewsList(new PublicationDateAscending());
        selectByIndex(0);
    }
}
