package ru.sharphurt.newsreader.constants;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ApplicationConstants {
    public static final String NEWS_STORAGE_BASE_URL = "http://localhost:8181/api";

    public static final DateTimeFormatter READABLE_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("Опубликовано dd.MM.yyyy в HH:mm:ss", Locale.forLanguageTag("ru_RU"));
}
