package com.brandon.ui;

import com.brandon.algorithms.AlgorithmEngine;
import com.brandon.algorithms.AlgorithmType;
import com.brandon.events.AnimationEvent;
import com.brandon.models.ArrayModel;
import com.brandon.visualisation.ArrayVisualiser;
import com.brandon.visualisation.EventPlayer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.util.List;

public class MainView extends BorderPane {

    private final StackPane visualizationPane;

    private ArrayModel currentModel;

    private ArrayVisualiser visualiser;

    private List<AnimationEvent> events;

    private AlgorithmType selectedAlgorithm;

    public MainView() {

        visualizationPane = new StackPane();

        createLayout();

        generateArray();
    }

    private void createLayout() {

        Button generate = new Button("Generate");

        Button sort = new Button("Sort");

        Button stepForward = new Button("Step →");

        Button stepBack = new Button("← Step");

        Button playPause = new Button("Play");

        ComboBox<String> selector = new ComboBox<>();

        selector.getItems().addAll(
                "Bubble Sort",
                "Selection Sort",
                "Insertion Sort",
                "Merge Sort",
                "Quick Sort");

        selector.setValue("Bubble Sort");

        selectedAlgorithm = AlgorithmType.BUBBLE_SORT;

        selector.setOnAction(e -> {

            switch (selector.getValue()) {

                case "Insertion Sort" ->
                    selectedAlgorithm = AlgorithmType.INSERTION_SORT;

                case "Selection Sort" ->
                    selectedAlgorithm = AlgorithmType.SELECTION_SORT;

                case "Merge Sort" ->
                    selectedAlgorithm = AlgorithmType.MERGE_SORT;

                case "Quick Sort" ->
                    selectedAlgorithm = AlgorithmType.QUICK_SORT;

                default ->
                    selectedAlgorithm = AlgorithmType.BUBBLE_SORT;
            }

        });

        generate.setOnAction(e -> generateArray());

        sort.setOnAction(e -> {

            events = AlgorithmEngine.run(
                    selectedAlgorithm,
                    currentModel.getValues());

            EventPlayer.load(
                    events,
                    visualiser);

        });

        stepForward.setOnAction(e -> {

            if (events != null) {

                EventPlayer.stepForward(
                        events,
                        visualiser);
            }

        });

        stepBack.setOnAction(e -> {

            if (events != null) {

                EventPlayer.stepBack(
                        events,
                        visualiser);
            }

        });

        playPause.setOnAction(e -> {

            if (events == null) {
                return;
            }

            if (!EventPlayer.getController().isPlaying()) {

                EventPlayer.play(events, visualiser);
                playPause.setText("Pause");

            } else {

                EventPlayer.getController().pause();
                playPause.setText("Play");
            }
        });

        HBox toolbar = new HBox(
                10,
                generate,
                selector,
                sort,
                stepBack,
                playPause,
                stepForward);

        toolbar.setAlignment(Pos.CENTER_LEFT);

        toolbar.setPadding(
                new Insets(15));

        setTop(toolbar);

        setCenter(visualizationPane);
    }

    private void generateArray() {

        EventPlayer.stop();

        currentModel = new ArrayModel(25);

        visualiser = new ArrayVisualiser(currentModel);

        visualizationPane.getChildren().clear();
        visualizationPane.getChildren().add(visualiser);

        events = null;

        EventPlayer.getController().reset();
    }
}