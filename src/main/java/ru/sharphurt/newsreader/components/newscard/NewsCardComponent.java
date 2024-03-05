package ru.sharphurt.newsreader.components.newscard;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import ru.sharphurt.newsreader.NewsReaderApplication;
import ru.sharphurt.newsreader.domain.entity.News;

import java.io.IOException;

import static ru.sharphurt.newsreader.constants.ApplicationConstants.READABLE_DATE_TIME_FORMAT;

public class NewsCardComponent extends ListCell<News> {

    @FXML
    private Label titleLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label publicationDateLabel;

    public NewsCardComponent() {
        loadFXML();
    }

    private void loadFXML() {
        try {
            var loader = new FXMLLoader(NewsReaderApplication.class.getResource("components/news-card.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void updateItem(News item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        } else {
            titleLabel.setText(item.getTitle());
            descriptionLabel.setText(item.getDescription());
            publicationDateLabel.setText(item.getPublicationDate().format(READABLE_DATE_TIME_FORMAT));

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }
}