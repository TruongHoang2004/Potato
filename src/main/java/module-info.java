module application {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.web;


    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    exports application;

    opens application to javafx.fxml;

    exports application.controller;

    opens application.controller to javafx.fxml;
}