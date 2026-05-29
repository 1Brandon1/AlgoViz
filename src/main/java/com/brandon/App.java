package com.brandon;

import com.brandon.ui.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        MainView root = new MainView();

        Scene scene = new Scene(root, 1000, 600);

        stage.setTitle("Algorithm Sim");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}