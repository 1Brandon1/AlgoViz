package com.brandon.ui;

import com.brandon.algorithms.sorting.BubbleSort;
import com.brandon.models.ArrayModel;
import com.brandon.visualization.ArrayVisualizer;
import com.brandon.visualization.EventPlayer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class MainView extends BorderPane {

    private final StackPane visualizationPane;
    private ArrayModel currentModel;
    private ArrayVisualizer currentVisualizer;

    public MainView() {

        visualizationPane = new StackPane();

        createLayout();
        generateNewArray();
    }

    private void createLayout() {

        Button generateButton = new Button("Generate Array");
        Button sortButton = new Button("Bubble Sort");

        generateButton.setOnAction(e -> generateNewArray());

        sortButton.setOnAction(e -> {

            var events = BubbleSort.generateEvents(
                    currentModel.getValues()
            );

            EventPlayer.play(
                    events,
                    currentVisualizer
            );
        });

        HBox topBar = new HBox(10, generateButton, sortButton);

        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(15));

        setTop(topBar);
        setCenter(visualizationPane);
    }

    private void generateNewArray() {

        currentModel = new ArrayModel(60);

        currentVisualizer = new ArrayVisualizer(currentModel);

        visualizationPane.getChildren().clear();
        visualizationPane.getChildren().add(currentVisualizer);
    }
}