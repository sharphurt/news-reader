package ru.sharphurt.newsreader.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lombok.Getter;
import ru.sharphurt.newsreader.NewsReaderApplication;

import java.io.IOException;

@Getter
public abstract class ApplicationController<T> {

    protected final T model;

    private final Parent parent;

    public ApplicationController(T model, String viewResourceName) {
        this.model = model;
        this.parent = loadFXML(viewResourceName);
        initialize();
    }

    private Parent loadFXML(String viewResourceName) {
        try {
            var loader = new FXMLLoader(NewsReaderApplication.class.getResource(viewResourceName));
            loader.setController(this);
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void initialize() {
        bind();
    }

    protected abstract void bind();
}
