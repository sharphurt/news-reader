package ru.sharphurt.newsreader.dto;

import lombok.Data;

@Data
public class ClientResponse<T> {
    private T result;
    private boolean successful;
    private String error;
}
