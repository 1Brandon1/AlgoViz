package com.brandon;

import com.brandon.ui.MainView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        MainView mainView = new MainView();

        Scene scene = new Scene(
                mainView,
                1200,
                800);

        String css = getClass()
                .getResource(
                        "/styles/application.css")
                .toExternalForm();

        scene.getStylesheets()
                .add(css);

        stage.setTitle("Algorithm Sim");

        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }
}