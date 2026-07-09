package com.brandon.ui;

import com.brandon.algorithms.AlgorithmEngine;
import com.brandon.algorithms.AlgorithmType;
import com.brandon.algorithms.SearchEngine;
import com.brandon.algorithms.SearchType;
import com.brandon.events.AnimationEvent;
import com.brandon.models.ArrayModel;
import com.brandon.visualisation.ArrayVisualiser;
import com.brandon.visualisation.EventPlayer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.util.Arrays;
import java.util.List;

public class MainView extends BorderPane {

    private final StackPane visualizationPane;

    private ArrayModel currentModel;
    private ArrayVisualiser visualiser;

    private List<AnimationEvent> events;

    private AlgorithmType selectedAlgorithm = AlgorithmType.BUBBLE_SORT;

    private SearchType selectedSearch = SearchType.LINEAR_SEARCH;

    private boolean searchingMode = false;

    public MainView() {

        visualizationPane = new StackPane();

        createLayout();

        generateArray();
    }

    private void createLayout() {

        Button generate = new Button("Generate");

        Button run = new Button("Run");

        Button stepForward = new Button("Step →");

        Button stepBack = new Button("← Step");

        Button playPause = new Button("Play");

        ComboBox<String> modeSelector = new ComboBox<>();

        modeSelector.getItems().addAll(
                "Sorting",
                "Searching");

        modeSelector.setValue("Sorting");

        modeSelector.setOnAction(e -> {

            searchingMode = modeSelector.getValue()
                    .equals("Searching");

        });

        ComboBox<String> sortSelector = new ComboBox<>();

        sortSelector.getItems().addAll(
                "Bubble Sort",
                "Selection Sort",
                "Insertion Sort",
                "Merge Sort",
                "Quick Sort");

        sortSelector.setValue(
                "Bubble Sort");

        sortSelector.setOnAction(e -> {

            switch (sortSelector.getValue()) {

                case "Selection Sort" ->
                    selectedAlgorithm = AlgorithmType.SELECTION_SORT;

                case "Insertion Sort" ->
                    selectedAlgorithm = AlgorithmType.INSERTION_SORT;

                case "Merge Sort" ->
                    selectedAlgorithm = AlgorithmType.MERGE_SORT;

                case "Quick Sort" ->
                    selectedAlgorithm = AlgorithmType.QUICK_SORT;

                default ->
                    selectedAlgorithm = AlgorithmType.BUBBLE_SORT;
            }

        });

        ComboBox<String> searchSelector = new ComboBox<>();

        searchSelector.getItems().addAll(
                "Linear Search",
                "Binary Search");

        searchSelector.setValue(
                "Linear Search");

        searchSelector.setOnAction(e -> {

            switch (searchSelector.getValue()) {

                case "Binary Search" ->
                    selectedSearch = SearchType.BINARY_SEARCH;

                default ->
                    selectedSearch = SearchType.LINEAR_SEARCH;
            }

        });

        TextField targetInput = new TextField();

        targetInput.setPromptText(
                "Target");

        generate.setOnAction(e -> generateArray());

        run.setOnAction(e -> {

            if (searchingMode) {

                int target = Integer.parseInt(
                        targetInput.getText());

                events = SearchEngine.run(
                        selectedSearch,
                        currentModel.getValues(),
                        target);

            } else {

                events = AlgorithmEngine.run(
                        selectedAlgorithm,
                        currentModel.getValues());
            }

            EventPlayer.load(
                    events,
                    visualiser);

        });

        stepForward.setOnAction(e -> EventPlayer.stepForward());

        stepBack.setOnAction(e -> EventPlayer.stepBack());

        playPause.setOnAction(e -> {

            if (!EventPlayer.getController()
                    .isPlaying()) {

                EventPlayer.play();

                playPause.setText(
                        "Pause");

            } else {

                EventPlayer.getController()
                        .pause();

                playPause.setText(
                        "Play");
            }

        });

        HBox toolbar = new HBox(
                10,
                generate,
                modeSelector,
                sortSelector,
                searchSelector,
                targetInput,
                run,
                stepBack,
                playPause,
                stepForward);

        toolbar.setAlignment(
                Pos.CENTER_LEFT);

        toolbar.setPadding(
                new Insets(15));

        setTop(toolbar);

        setCenter(
                visualizationPane);
    }

    private void generateArray() {

        currentModel = new ArrayModel(25);

        System.out.println(
                Arrays.toString(
                        currentModel.getValues()));

        visualiser = new ArrayVisualiser(
                currentModel);

        visualizationPane
                .getChildren()
                .clear();

        visualizationPane
                .getChildren()
                .add(
                        visualiser);

        events = null;

        EventPlayer.getController()
                .reset();
    }
}