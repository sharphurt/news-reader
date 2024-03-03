package ru.sharphurt.newsreader.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
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

    @FXML
    private VBox progressIndicator;

    public NewsListController(NewsListModel model) {
        super(model, "forms/news-list-form.fxml");
    }

    @Override
    public void initialize() {
        super.initialize();
        attachEventHandlers();
    }

    @Override
    protected void bind() {
        bindNewsList();
        bindNewsDetails();

        progressIndicator.visibleProperty().bind(model.getNewsLoader().getIsSuccessfullyLoaded().not());
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