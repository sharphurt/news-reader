package ru.sharphurt.newsreader.exception;

import lombok.Getter;


@Getter
public class BaseException extends RuntimeException {

    private final String message;

    public BaseException(String message, Throwable e) {
        super(message);
        e.printStackTrace();
        this.message = message;
    }
}
