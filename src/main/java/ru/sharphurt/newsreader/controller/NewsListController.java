package ru.sharphurt.newsreader.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import ru.sharphurt.newsreader.components.newscard.NewsCardFactory;
import ru.sharphurt.newsreader.domain.entity.News;
import ru.sharphurt.newsreader.model.NewsListModel;

public class NewsListController extends ApplicationController<NewsListModel> {

    @FXML
    private Label title;

    @FXML
    private TextArea description;

    @FXML
    private Label publicationDate;

    @FXML
    private Button nextButton;

    @FXML
    private Button previousButton;

    @FXML
    private ListView<News> newsListView;

    public NewsListController(NewsListModel model) {
        super(model, "forms/news-list-form.fxml");
    }

    @Override
    public void initialize() {
        attachEventHandlers();
        super.initialize();
    }

    @Override
    protected void bind() {
        bindNewsList();
        bindNewsDetails();
    }

    private void bindNewsList() {
        newsListView.setCellFactory(new NewsCardFactory());
        newsListView.itemsProperty().bind(model.getNewsList());

        model.getCurrentNewsIndex().addListener((obs, oldValue, newValue) -> {
            newsListView.getSelectionModel().select(newValue.intValue());
        });

        newsListView.setOnMouseClicked(e -> {
            var index = newsListView.getSelectionModel().getSelectedIndex();
            model.selectByIndex(index);
        });
    }

    private void bindNewsDetails() {
        title.textProperty().bind(model.getCurrentNews().getTitleProperty());
        description.textProperty().bind(model.getCurrentNews().getDescriptionProperty());
        publicationDate.textProperty().bind(model.getCurrentNews().getPublicationDateProperty());
    }

    private void attachEventHandlers() {
        previousButton.setOnAction(e -> model.tryGoToPreviousNews());
        nextButton.setOnAction(e -> model.tryGoToNextNews());
    }
}