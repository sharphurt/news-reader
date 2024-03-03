package ru.sharphurt.newsreader.client;

import feign.RequestLine;
import ru.sharphurt.newsreader.domain.entity.News;
import ru.sharphurt.newsreader.dto.ClientResponse;

import java.util.List;

public interface NewsStorageClient {

    @RequestLine("GET ")
    ClientResponse<List<News>> getTodayNews();
}
