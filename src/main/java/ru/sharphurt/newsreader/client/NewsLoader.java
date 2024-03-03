package ru.sharphurt.newsreader.client;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lombok.Getter;
import ru.sharphurt.newsreader.domain.entity.News;
import ru.sharphurt.newsreader.dto.ClientResponse;
import ru.sharphurt.newsreader.exception.UnsuccessfulResponseException;

import java.util.List;

@Getter
public class NewsLoader {
    private final NewsStorageClient client;
    private final BooleanProperty isSuccessfullyLoaded;
    private List<News> result;

    public NewsLoader(String url) {
        client = Feign.builder().decoder(new JacksonDecoder()).target(NewsStorageClient.class, url);
        isSuccessfullyLoaded = new SimpleBooleanProperty(false);
        loadingService.start();
    }

    private final Task<Void> loadTask = new Task<>() {
        @Override
        protected Void call() {
            ClientResponse<List<News>> response;
            try {
                response = client.getTodayNews();
            } catch (Exception e) {
                throw new UnsuccessfulResponseException(e);
            }

            if (!response.isSuccessful())
                throw new UnsuccessfulResponseException(response.getError());

            result = response.getResult();
            return null;
        }
    };

    private final Service<Void> loadingService = new Service<>() {
        @Override
        protected Task<Void> createTask() {
            loadTask.setOnFailed(e -> isSuccessfullyLoaded.set(false));
            loadTask.setOnCancelled(e -> isSuccessfullyLoaded.set(false));
            loadTask.setOnRunning(e -> isSuccessfullyLoaded.set(false));
            loadTask.setOnSucceeded(e -> isSuccessfullyLoaded.set(true));

            return loadTask;
        }
    };
}
