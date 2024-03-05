package ru.sharphurt.newsreader;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.sharphurt.newsreader.client.LoadingState;
import ru.sharphurt.newsreader.controller.LoadingOverlayController;
import ru.sharphurt.newsreader.controller.NewsListController;
import ru.sharphurt.newsreader.model.LoadingProcessModel;
import ru.sharphurt.newsreader.model.NewsListModel;

public class NewsReaderApplication extends Application {

    private Stage stage;
    private LoadingProcessModel loadingModel;
    private NewsListModel newsListModel;

    @Override
    public void start(Stage stage) {
        stage.setTitle("News reader");
        this.stage = stage;

        this.loadingModel = new LoadingProcessModel();
        this.newsListModel = new NewsListModel();

        createLoadingScene();
        loadingModel.getLoadingState().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue == LoadingState.SUCCESS)
                createNewsScene();
        }));
    }

    private void createLoadingScene() {
        var loader = new LoadingOverlayController(loadingModel, "components/loading-overlay.fxml");
        loader.getModel().runLoading();

        stage.setScene(new Scene(loader.getParent()));
        stage.show();
    }

    private void createNewsScene() {
        var newsList = new NewsListController(newsListModel);
        newsListModel.initializeWithNews(loadingModel.getResult());
        stage.setScene(new Scene(newsList.getParent()));
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}