package ru.sharphurt.newsreader;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.sharphurt.newsreader.controller.NewsListController;
import ru.sharphurt.newsreader.model.NewsListModel;

import java.io.IOException;

public class NewsReaderApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("News reader");

        var newsList = new NewsListController(new NewsListModel());
        stage.setScene(new Scene(newsList.getParent()));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}