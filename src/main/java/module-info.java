module ru.sharphurt.newsreader {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires java.sql;
    requires java.base;
    requires feign.core;
    requires feign.jackson;

    opens ru.sharphurt.newsreader to javafx.fxml;
    opens ru.sharphurt.newsreader.components.newscard to javafx.fxml;
    opens ru.sharphurt.newsreader.controller to javafx.fxml;

    exports ru.sharphurt.newsreader.serialization to com.fasterxml.jackson.databind;
    exports ru.sharphurt.newsreader.dto to com.fasterxml.jackson.databind;
    exports ru.sharphurt.newsreader.domain.entity to com.fasterxml.jackson.databind;
    exports ru.sharphurt.newsreader;
    exports ru.sharphurt.newsreader.model;
    exports ru.sharphurt.newsreader.controller;
    exports ru.sharphurt.newsreader.client;
}