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

import java.util.List;

public class MainView extends BorderPane {

        private final StackPane visualizationPane;

        private ArrayModel currentModel;
        private ArrayVisualiser visualiser;

        private List<AnimationEvent> events;

        private AlgorithmType selectedAlgorithm = AlgorithmType.BUBBLE_SORT;
        private SearchType selectedSearch = SearchType.LINEAR_SEARCH;

        private boolean searchingMode = false;
        private final TextField targetInput = new TextField();

        public MainView() {

                visualizationPane = new StackPane();

                createLayout();

                generateArray();
        }

        private void createLayout() {

                Button generate = new Button("Generate");

                Button run = new Button("Sort");

                Button stepForward = new Button("Step →");

                Button stepBack = new Button("← Step");

                Button playPause = new Button("Play");

                ComboBox<String> modeSelector = new ComboBox<>();

                ComboBox<String> sortSelector = new ComboBox<>();

                ComboBox<String> searchSelector = new ComboBox<>();

                // -------------------------
                // MODE SELECTOR
                // -------------------------

                modeSelector.getItems().addAll(
                                "Sorting",
                                "Searching");

                modeSelector.setValue("Sorting");

                // -------------------------
                // SORT SELECTOR
                // -------------------------

                sortSelector.getItems().addAll(
                                "Bubble Sort",
                                "Selection Sort",
                                "Insertion Sort",
                                "Merge Sort",
                                "Quick Sort");

                sortSelector.setValue("Bubble Sort");

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

                // -------------------------
                // SEARCH SELECTOR
                // -------------------------

                searchSelector.getItems().addAll(
                                "Linear Search",
                                "Binary Search",
                                "Jump Search",
                                "Interpolation Search");

                searchSelector.setValue("Linear Search");

                searchSelector.setOnAction(e -> {

                        switch (searchSelector.getValue()) {

                                case "Binary Search" ->
                                        selectedSearch = SearchType.BINARY_SEARCH;

                                case "Jump Search" ->
                                        selectedSearch = SearchType.JUMP_SEARCH;

                                case "Interpolation Search" ->
                                        selectedSearch = SearchType.INTERPOLATION_SEARCH;

                                default ->
                                        selectedSearch = SearchType.LINEAR_SEARCH;
                        }
                });

                // -------------------------
                // TARGET INPUT
                // -------------------------

                targetInput.setPromptText("Target");

                // Hidden by default

                searchSelector.setVisible(false);
                searchSelector.setManaged(false);

                targetInput.setVisible(false);
                targetInput.setManaged(false);

                // -------------------------
                // MODE CHANGED
                // -------------------------

                modeSelector.setOnAction(e -> {

                        searchingMode = modeSelector.getValue().equals("Searching");

                        sortSelector.setVisible(!searchingMode);
                        sortSelector.setManaged(!searchingMode);

                        searchSelector.setVisible(searchingMode);
                        searchSelector.setManaged(searchingMode);

                        targetInput.setVisible(searchingMode);
                        targetInput.setManaged(searchingMode);

                        run.setText(searchingMode ? "Search" : "Sort");
                });

                // -------------------------
                // BUTTONS
                // -------------------------

                generate.setOnAction(e -> generateArray());

                run.setOnAction(e -> {

                        if (searchingMode) {

                                try {

                                        int target = Integer.parseInt(targetInput.getText());

                                        events = SearchEngine.run(
                                                        selectedSearch,
                                                        currentModel.getValues(),
                                                        target);

                                } catch (NumberFormatException ex) {

                                        return;
                                }

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

                        if (!EventPlayer.getController().isPlaying()) {

                                EventPlayer.play();

                                playPause.setText("Pause");

                        } else {

                                EventPlayer.getController().pause();

                                playPause.setText("Play");
                        }
                });

                // -------------------------
                // TOOLBAR
                // -------------------------

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

                toolbar.setAlignment(Pos.CENTER_LEFT);
                toolbar.setPadding(new Insets(15));

                setTop(toolbar);
                setCenter(visualizationPane);
        }

        private void generateArray() {

                EventPlayer.stop();

                currentModel = new ArrayModel(25);

                visualiser = new ArrayVisualiser(
                                currentModel);

                visualiser.setBarClickListener(index -> {

                        if (searchingMode) {

                                targetInput.setText(
                                                String.valueOf(
                                                                visualiser.getValueAt(index)));
                        }
                });

                visualizationPane
                                .getChildren()
                                .clear();

                visualizationPane
                                .getChildren()
                                .add(visualiser);

                events = null;

                targetInput.clear();

                EventPlayer.getController()
                                .reset();
        }
}