package ru.sharphurt.newsreader.components.newscard;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import ru.sharphurt.newsreader.domain.entity.News;

public class NewsCardFactory implements Callback<ListView<News>, ListCell<News>> {

    @Override
    public ListCell<News> call(ListView<News> newsModelListView) {
        return new NewsCardComponent();
    }
}
