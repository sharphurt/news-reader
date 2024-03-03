package ru.sharphurt.newsreader.exception;

public class UnsuccessfulResponseException extends BaseException {
    public UnsuccessfulResponseException(String message) {
        super("Unsuccessful response got from server: %s".formatted(message), new Throwable());
    }

    public UnsuccessfulResponseException(Throwable e) {
        super("Unsuccessful response got from server: %s".formatted(e.getMessage()), e);
    }
}
