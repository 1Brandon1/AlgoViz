package com.brandon.ui;

import com.brandon.algorithms.AlgorithmEngine;
import com.brandon.algorithms.AlgorithmType;
import com.brandon.algorithms.SearchEngine;
import com.brandon.algorithms.SearchType;
import com.brandon.events.AnimationEvent;
import com.brandon.events.CompareEvent;
import com.brandon.events.SearchCompareEvent;
import com.brandon.events.SwapEvent;
import com.brandon.models.ArrayModel;
import com.brandon.visualisation.ArrayVisualiser;
import com.brandon.visualisation.EventPlayer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;

public class MainView extends BorderPane {

        // =================================================
        // MAIN UI
        // =================================================

        private final StackPane visualizationPane;

        private AlgorithmInfoPanel infoPanel;

        private Label stepLabel;

        private ComboBox<String> modeSelector;
        private ComboBox<String> sortSelector;
        private ComboBox<String> searchSelector;

        private TextField targetInput;
        private StatusBar statusBar;

        // =================================================
        // DATA
        // =================================================

        private ArrayModel currentModel;

        private ArrayVisualiser visualiser;

        private List<AnimationEvent> events;

        private AlgorithmType selectedAlgorithm = AlgorithmType.BUBBLE_SORT;

        private SearchType selectedSearch = SearchType.LINEAR_SEARCH;

        private boolean searchingMode = false;

        // =================================================
        // CONSTRUCTOR
        // =================================================

        public MainView() {

                visualizationPane = new StackPane();

                EventPlayer.setUpdateListener(
                                this::updateStatistics);

                createLayout();

                generateArray();
        }

        // =================================================
        // LAYOUT
        // =================================================

        private void createLayout() {

                setPadding(
                                new Insets(0));

                setTop(
                                createHeader());

                setCenter(
                                createMainContent());

                setBottom(
                                createControlPanel());
        }

        // =================================================
        // HEADER
        // =================================================

        private HBox createHeader() {

                Label title = new Label("AlgoViz");

                title.getStyleClass()
                                .add("title");

                Label subtitle = new Label(
                                "Interactive Algorithm Visualiser");

                subtitle.getStyleClass()
                                .add("subtitle");

                VBox titleBox = new VBox(
                                2,
                                title,
                                subtitle);

                statusBar = new StatusBar();

                Region spacer = new Region();

                // Pushes the status bar to the right
                HBox.setHgrow(
                                spacer,
                                Priority.ALWAYS);

                HBox header = new HBox(
                                15,
                                titleBox,
                                spacer,
                                statusBar);

                header.setAlignment(
                                Pos.CENTER_LEFT);

                header.setPadding(
                                new Insets(
                                                18,
                                                24,
                                                18,
                                                24));

                header.getStyleClass()
                                .add("header");

                return header;
        }

        // =================================================
        // MAIN CONTENT
        // =================================================

        private HBox createMainContent() {

                visualizationPane.setPadding(
                                new Insets(30));

                visualizationPane
                                .getStyleClass()
                                .add("visualisation-container");

                infoPanel = new AlgorithmInfoPanel();

                infoPanel.setPrefWidth(230);
                infoPanel.setMinWidth(230);

                HBox container = new HBox(
                                20,
                                visualizationPane,
                                infoPanel);

                HBox.setHgrow(
                                visualizationPane,
                                Priority.ALWAYS);

                container.setPadding(
                                new Insets(
                                                20,
                                                24,
                                                20,
                                                24));

                return container;
        }

        // =================================================
        // CONTROL PANEL
        // =================================================

        private VBox createControlPanel() {

                // ---------------------------------------------
                // MODE
                // ---------------------------------------------

                modeSelector = new ComboBox<>();

                modeSelector.getItems().addAll(
                                "Sorting",
                                "Searching");

                modeSelector.setValue(
                                "Sorting");

                modeSelector.setOnAction(
                                e -> updateMode());

                // ---------------------------------------------
                // SORTING
                // ---------------------------------------------

                sortSelector = new ComboBox<>();

                sortSelector.getItems().addAll(
                                "Bubble Sort",
                                "Selection Sort",
                                "Insertion Sort",
                                "Merge Sort",
                                "Quick Sort");

                sortSelector.setValue(
                                "Bubble Sort");

                sortSelector.setOnAction(
                                e -> updateSortingAlgorithm());

                // ---------------------------------------------
                // SEARCHING
                // ---------------------------------------------

                searchSelector = new ComboBox<>();

                searchSelector.getItems().addAll(
                                "Linear Search",
                                "Binary Search",
                                "Jump Search",
                                "Interpolation Search");

                searchSelector.setValue(
                                "Linear Search");

                searchSelector.setOnAction(
                                e -> updateSearchAlgorithm());

                searchSelector.setVisible(false);
                searchSelector.setManaged(false);

                // ---------------------------------------------
                // TARGET
                // ---------------------------------------------

                targetInput = new TextField();

                targetInput.setPromptText(
                                "Target");

                targetInput.setPrefWidth(100);

                targetInput.setVisible(false);
                targetInput.setManaged(false);

                // ---------------------------------------------
                // BUTTONS
                // ---------------------------------------------

                Button generate = new Button("Generate");

                Button run = new Button("Run");

                Button stepBack = new Button("←");

                Button playPause = new Button("▶");

                Button stepForward = new Button("→");

                generate.getStyleClass()
                                .add("control-button");

                run.getStyleClass()
                                .add("primary-button");

                stepBack.getStyleClass()
                                .add("control-button");

                playPause.getStyleClass()
                                .add("play-button");

                stepForward.getStyleClass()
                                .add("control-button");

                // ---------------------------------------------
                // STEP LABEL
                // ---------------------------------------------

                stepLabel = new Label("Step 0 / 0");

                stepLabel.getStyleClass()
                                .add("step-label");

                // ---------------------------------------------
                // ACTIONS
                // ---------------------------------------------

                generate.setOnAction(
                                e -> generateArray());

                run.setOnAction(
                                e -> runAlgorithm());

                stepForward.setOnAction(
                                e -> stepForward());

                stepBack.setOnAction(
                                e -> stepBack());

                playPause.setOnAction(e -> {

                        if (!EventPlayer
                                        .getController()
                                        .isPlaying()) {

                                EventPlayer.play();

                                playPause.setText("❚❚");

                        } else {

                                EventPlayer.stop();

                                playPause.setText("▶");
                        }
                });

                // ---------------------------------------------
                // TOP CONTROL ROW
                // ---------------------------------------------

                HBox selectors = new HBox(
                                10,
                                modeSelector,
                                sortSelector,
                                searchSelector,
                                targetInput);

                selectors.setAlignment(
                                Pos.CENTER_LEFT);

                // ---------------------------------------------
                // BOTTOM CONTROL ROW
                // ---------------------------------------------

                HBox controls = new HBox(
                                10,
                                generate,
                                run,
                                stepBack,
                                playPause,
                                stepForward,
                                stepLabel);

                controls.setAlignment(
                                Pos.CENTER_LEFT);

                // ---------------------------------------------
                // PANEL
                // ---------------------------------------------

                VBox panel = new VBox(
                                10,
                                selectors,
                                controls);

                panel.setPadding(
                                new Insets(
                                                16,
                                                24,
                                                18,
                                                24));

                panel.getStyleClass()
                                .add("control-panel");

                return panel;
        }

        // =================================================
        // MODE
        // =================================================

        private void updateMode() {

                searchingMode = modeSelector
                                .getValue()
                                .equals("Searching");

                EventPlayer.stop();

                EventPlayer
                                .getController()
                                .reset();

                events = null;

                if (searchingMode) {

                        sortSelector.setVisible(false);
                        sortSelector.setManaged(false);

                        searchSelector.setVisible(true);
                        searchSelector.setManaged(true);

                        targetInput.setVisible(true);
                        targetInput.setManaged(true);

                        infoPanel.setAlgorithm(
                                        getSearchDisplayName());

                } else {

                        sortSelector.setVisible(true);
                        sortSelector.setManaged(true);

                        searchSelector.setVisible(false);
                        searchSelector.setManaged(false);

                        targetInput.setVisible(false);
                        targetInput.setManaged(false);

                        infoPanel.setAlgorithm(
                                        getSortDisplayName());
                }

                statusBar.setStatus(
                                searchingMode
                                                ? "Search mode"
                                                : "Sorting mode");

                infoPanel.resetStatistics();

                updateStepLabel();
        }

        // =================================================
        // SORTING ALGORITHM
        // =================================================

        private void updateSortingAlgorithm() {

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

                infoPanel.setAlgorithm(
                                getSortDisplayName());

                infoPanel.resetStatistics();

                clearEvents();
        }

        // =================================================
        // SEARCH ALGORITHM
        // =================================================

        private void updateSearchAlgorithm() {

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

                infoPanel.setAlgorithm(
                                getSearchDisplayName());

                infoPanel.resetStatistics();

                clearEvents();
        }

        // =================================================
        // RUN
        // =================================================

        private void runAlgorithm() {

                EventPlayer.stop();

                if (searchingMode) {

                        statusBar.setStatus(
                                        "Running " + getSearchDisplayName());

                        String targetText = targetInput.getText().trim();

                        if (targetText.isEmpty()) {
                                statusBar.setStatus(
                                                "Enter or click a target value");
                                return;
                        }

                        int target;

                        try {

                                target = Integer.parseInt(
                                                targetText);

                        } catch (NumberFormatException e) {

                                statusBar.setStatus(
                                                "Target must be a number");
                                return;
                        }

                        events = SearchEngine.run(
                                        selectedSearch,
                                        currentModel.getValues(),
                                        target);

                } else {

                        statusBar.setStatus(
                                        "Running " + getSortDisplayName());

                        events = AlgorithmEngine.run(
                                        selectedAlgorithm,
                                        currentModel.getValues());
                }

                EventPlayer.load(
                                events,
                                visualiser);

                updateStepLabel();

                updateStatistics();
        }

        // =================================================
        // STEP FORWARD
        // =================================================

        private void stepForward() {

                if (events == null ||
                                events.isEmpty()) {

                        return;
                }

                EventPlayer.stepForward();

                updateStepLabel();

                updateStatistics();
        }

        // =================================================
        // STEP BACK
        // =================================================

        private void stepBack() {

                if (events == null ||
                                events.isEmpty()) {

                        return;
                }

                EventPlayer.stepBack();

                updateStepLabel();

                updateStatistics();
        }

        // =================================================
        // GENERATE ARRAY
        // =================================================

        private void generateArray() {

                EventPlayer.stop();

                currentModel = new ArrayModel(25);

                System.out.println(
                                Arrays.toString(
                                                currentModel.getValues()));

                visualiser = new ArrayVisualiser(
                                currentModel);

                visualiser.setBarClickListener(
                                this::handleBarClick);

                visualizationPane
                                .getChildren()
                                .clear();

                visualizationPane
                                .getChildren()
                                .add(
                                                visualiser);

                events = null;

                EventPlayer
                                .getController()
                                .reset();

                infoPanel.setAlgorithm(
                                searchingMode
                                                ? getSearchDisplayName()
                                                : getSortDisplayName());

                infoPanel.resetStatistics();

                updateStepLabel();

                statusBar.setStatus("Ready");
        }

        private void handleBarClick(int index) {

                if (!searchingMode) {
                        return;
                }

                int value = currentModel.getValues()[index];

                targetInput.setText(
                                String.valueOf(value));

                statusBar.setStatus(
                                "Selected value: " + value);
        }

        // =================================================
        // CLEAR EVENTS
        // =================================================

        private void clearEvents() {

                EventPlayer.stop();

                EventPlayer
                                .getController()
                                .reset();

                events = null;

                if (visualiser != null) {
                        visualiser.reset();
                }

                updateStepLabel();
        }

        // =================================================
        // STATISTICS
        // =================================================

        private void updateStatistics() {

                updateStepLabel();

                if (events == null) {

                        infoPanel.resetStatistics();

                        return;
                }

                int currentStep = EventPlayer
                                .getController()
                                .getCurrentIndex();

                int comparisons = 0;
                int swaps = 0;

                int limit = Math.min(
                                currentStep,
                                events.size());

                for (int i = 0; i < limit; i++) {

                        AnimationEvent event = events.get(i);

                        if (event instanceof CompareEvent) {

                                comparisons++;

                        } else if (event instanceof SearchCompareEvent) {

                                comparisons++;

                        } else if (event instanceof SwapEvent) {

                                swaps++;
                        }
                }

                infoPanel.setStatistics(
                                comparisons,
                                swaps,
                                currentStep,
                                events.size());
        }

        // =================================================
        // STEP LABEL
        // =================================================

        private void updateStepLabel() {

                if (stepLabel == null) {
                        return;
                }

                int current = EventPlayer
                                .getController()
                                .getCurrentIndex();

                int total = events == null
                                ? 0
                                : events.size();

                stepLabel.setText(
                                "Step "
                                                + current
                                                + " / "
                                                + total);
        }

        // =================================================
        // DISPLAY NAMES
        // =================================================

        private String getSortDisplayName() {

                return switch (selectedAlgorithm) {

                        case BUBBLE_SORT ->
                                "Bubble Sort";

                        case SELECTION_SORT ->
                                "Selection Sort";

                        case INSERTION_SORT ->
                                "Insertion Sort";

                        case MERGE_SORT ->
                                "Merge Sort";

                        case QUICK_SORT ->
                                "Quick Sort";

                        case HEAP_SORT ->
                                "Heap Sort";
                };
        }

        private String getSearchDisplayName() {

                return switch (selectedSearch) {

                        case LINEAR_SEARCH ->
                                "Linear Search";

                        case BINARY_SEARCH ->
                                "Binary Search";

                        case JUMP_SEARCH ->
                                "Jump Search";

                        case INTERPOLATION_SEARCH ->
                                "Interpolation Search";
                };
        }
}