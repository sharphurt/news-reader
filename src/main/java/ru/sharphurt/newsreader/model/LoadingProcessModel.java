package ru.sharphurt.newsreader.model;

import feign.Feign;
import feign.Request;
import feign.jackson.JacksonDecoder;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lombok.Getter;
import ru.sharphurt.newsreader.client.LoadingState;
import ru.sharphurt.newsreader.client.NewsStorageClient;
import ru.sharphurt.newsreader.domain.entity.News;
import ru.sharphurt.newsreader.dto.ClientResponse;
import ru.sharphurt.newsreader.exception.UnsuccessfulResponseException;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static ru.sharphurt.newsreader.constants.ApplicationConstants.NEWS_STORAGE_BASE_URL;

@Getter
public class LoadingProcessModel {
    private List<News> result;
    private final StringProperty errorMessage;
    private final ObjectProperty<LoadingState> loadingState;

    private final Service<ClientResponse<List<News>>> loadingService;

    private final NewsStorageClient client;


    public LoadingProcessModel() {
        errorMessage = new SimpleStringProperty();
        loadingState = new SimpleObjectProperty<>();

        client = Feign.builder()
                .options(new Request.Options(10, TimeUnit.SECONDS, 60, TimeUnit.SECONDS, false))
                .decoder(new JacksonDecoder())
                .target(NewsStorageClient.class, NEWS_STORAGE_BASE_URL);

        loadingService = createLoadingService();
    }

    public void runLoading() {
        var loadingService = createLoadingService();
        loadingService.start();
    }

    private Service<ClientResponse<List<News>>> createLoadingService() {
        return new Service<>() {
            @Override
            protected Task<ClientResponse<List<News>>> createTask() {
                var task = createLoadTask();
                setTaskFinishedActions(task);
                return task;
            }
        };
    }


    public Task<ClientResponse<List<News>>> createLoadTask() {
        return new Task<>() {
            @Override
            protected ClientResponse<List<News>> call() {
                ClientResponse<List<News>> response;
                try {
                    response = client.getTodayNews();
                } catch (Exception e) {
                    throw new UnsuccessfulResponseException(e);
                }

                if (!response.isSuccessful())
                    throw new UnsuccessfulResponseException(response.getError());

                return response;
            }
        };
    }

    private void setTaskFinishedActions(Task<ClientResponse<List<News>>> task) {
        task.setOnFailed(e -> ofFailedLoading(task.getException()));
        task.setOnSucceeded(e -> onSuccessLoading(task.getValue()));
        task.setOnRunning(e -> loadingState.set(LoadingState.PROCESS));
    }

    private void onSuccessLoading(ClientResponse<List<News>> response) {
        if (!response.isSuccessful()) {
            errorMessage.set(response.getError());
            loadingState.set(LoadingState.ERROR);
            return;
        }

        result = response.getResult();
        loadingState.set(LoadingState.SUCCESS);
    }

    private void ofFailedLoading(Throwable error) {
        errorMessage.set(error.getMessage());
        loadingState.set(LoadingState.ERROR);
    }
}
