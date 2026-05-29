package com.brandon.ui;

import com.brandon.models.ArrayModel;
import com.brandon.visualization.ArrayVisualizer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class MainView extends BorderPane {

    private final StackPane visualizationPane;

    public MainView() {

        visualizationPane = new StackPane();

        createLayout();
        generateNewArray();
    }

    private void createLayout() {

        Button generateButton = new Button("Generate Array");

        generateButton.setOnAction(e -> generateNewArray());

        HBox topBar = new HBox(generateButton);

        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(15));

        setTop(topBar);
        setCenter(visualizationPane);
    }

    private void generateNewArray() {

        ArrayModel model = new ArrayModel(60);

        ArrayVisualizer visualizer = new ArrayVisualizer(model);

        visualizationPane.getChildren().clear();
        visualizationPane.getChildren().add(visualizer);
    }
}