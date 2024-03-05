package ru.sharphurt.newsreader.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import ru.sharphurt.newsreader.client.LoadingState;
import ru.sharphurt.newsreader.model.LoadingProcessModel;

public class LoadingOverlayController extends ApplicationController<LoadingProcessModel> {

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private Label message;

    public LoadingOverlayController(LoadingProcessModel model, String viewResourceName) {
        super(model, viewResourceName);
    }

    @Override
    protected void bind() {
        message.textProperty().bind(model.getErrorMessage());
        model.getLoadingState().addListener((obs, oldValue, newValue) -> progressIndicator.setVisible(newValue == LoadingState.PROCESS));
    }
}
