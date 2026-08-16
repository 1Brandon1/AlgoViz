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

        private final StackPane visualizationPane;

        private ArrayModel currentModel;

        private ArrayVisualiser visualiser;

        private List<AnimationEvent> events;

        private AlgorithmType selectedAlgorithm = AlgorithmType.BUBBLE_SORT;

        private SearchType selectedSearch = SearchType.LINEAR_SEARCH;

        private boolean searchingMode = false;

        private ComboBox<String> modeSelector;
        private ComboBox<String> sortSelector;
        private ComboBox<String> searchSelector;

        private TextField targetInput;

        private Button generateButton;
        private Button runButton;
        private Button stepForwardButton;
        private Button stepBackButton;
        private Button playPauseButton;

        private Label algorithmLabel;
        private Label arrayLabel;
        private Label stepLabel;

        private StatusBar statusBar;

        public MainView() {

                visualizationPane = new StackPane();

                visualizationPane
                                .getStyleClass()
                                .add("visualisation-container");

                BorderPane.setMargin(
                                visualizationPane,
                                new Insets(
                                                20,
                                                24,
                                                20,
                                                24));

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
                                createVisualisation());

                setBottom(
                                createControlPanel());
        }

        // =================================================
        // HEADER
        // =================================================

        private HBox createHeader() {

                Label title = new Label("Algorithm Sim");

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
        // VISUALISATION
        // =================================================

        private StackPane createVisualisation() {

                visualizationPane.setPadding(
                                new Insets(30));

                return visualizationPane;
        }

        // =================================================
        // CONTROL PANEL
        // =================================================

        private VBox createControlPanel() {

                VBox panel = new VBox(15);

                panel.setPadding(
                                new Insets(
                                                18,
                                                24,
                                                20,
                                                24));

                panel.getStyleClass()
                                .add("control-panel");

                // ---------------------------------------------
                // SECTION TITLE
                // ---------------------------------------------

                Label algorithmTitle = new Label("ALGORITHM");

                algorithmTitle.getStyleClass()
                                .add("section-title");

                // ---------------------------------------------
                // MODE
                // ---------------------------------------------

                modeSelector = new ComboBox<>();

                modeSelector.getItems().addAll(
                                "Sorting",
                                "Searching");

                modeSelector.setValue(
                                "Sorting");

                modeSelector.setPrefWidth(140);

                modeSelector.setOnAction(e -> updateMode());

                // ---------------------------------------------
                // SORTING SELECTOR
                // ---------------------------------------------

                sortSelector = new ComboBox<>();

                sortSelector.getItems().addAll(
                                "Bubble Sort",
                                "Selection Sort",
                                "Insertion Sort",
                                "Merge Sort",
                                "Quick Sort",
                                "Heap Sort");

                sortSelector.setValue(
                                "Bubble Sort");

                sortSelector.setPrefWidth(170);

                sortSelector.setOnAction(e -> updateSortingAlgorithm());

                // ---------------------------------------------
                // SEARCH SELECTOR
                // ---------------------------------------------

                searchSelector = new ComboBox<>();

                searchSelector.getItems().addAll(
                                "Linear Search",
                                "Binary Search",
                                "Jump Search",
                                "Interpolation Search");

                searchSelector.setValue(
                                "Linear Search");

                searchSelector.setPrefWidth(190);

                searchSelector.setOnAction(e -> updateSearchAlgorithm());

                // ---------------------------------------------
                // TARGET
                // ---------------------------------------------

                targetInput = new TextField();

                targetInput.setPromptText(
                                "Target");

                targetInput.setPrefWidth(100);

                // ---------------------------------------------
                // TOP CONTROL ROW
                // ---------------------------------------------

                HBox algorithmRow = new HBox(
                                10,
                                modeSelector,
                                sortSelector,
                                searchSelector,
                                targetInput);

                algorithmRow.setAlignment(
                                Pos.CENTER_LEFT);

                // ---------------------------------------------
                // PLAYBACK
                // ---------------------------------------------

                generateButton = new Button("Generate");

                runButton = new Button("Run");

                stepBackButton = new Button("←");

                playPauseButton = new Button("▶ Play");

                stepForwardButton = new Button("→");

                generateButton
                                .getStyleClass()
                                .add("button");

                runButton
                                .getStyleClass()
                                .add("primary-button");

                // ---------------------------------------------
                // BUTTON ACTIONS
                // ---------------------------------------------

                generateButton.setOnAction(
                                e -> generateArray());

                runButton.setOnAction(
                                e -> runAlgorithm());

                stepForwardButton.setOnAction(
                                e -> stepForward());

                stepBackButton.setOnAction(
                                e -> stepBack());

                playPauseButton.setOnAction(
                                e -> togglePlayPause());

                // ---------------------------------------------
                // PLAYBACK ROW
                // ---------------------------------------------

                HBox playbackRow = new HBox(
                                10,
                                generateButton,
                                runButton,
                                stepBackButton,
                                playPauseButton,
                                stepForwardButton);

                playbackRow.setAlignment(
                                Pos.CENTER_LEFT);

                // ---------------------------------------------
                // INFO
                // ---------------------------------------------

                algorithmLabel = new Label(
                                "Bubble Sort");

                algorithmLabel.getStyleClass()
                                .add("info-value");

                arrayLabel = new Label(
                                "Array: 25 elements");

                arrayLabel.getStyleClass()
                                .add("info-label");

                stepLabel = new Label(
                                "Step: 0");

                stepLabel.getStyleClass()
                                .add("info-label");

                HBox infoRow = new HBox(
                                25,
                                algorithmLabel,
                                arrayLabel,
                                stepLabel);

                infoRow.setAlignment(
                                Pos.CENTER_LEFT);

                // ---------------------------------------------
                // ADD EVERYTHING
                // ---------------------------------------------

                panel.getChildren().addAll(
                                algorithmTitle,
                                algorithmRow,
                                playbackRow,
                                infoRow);

                updateMode();

                return panel;
        }

        // =================================================
        // MODE
        // =================================================

        private void updateMode() {

                searchingMode = modeSelector
                                .getValue()
                                .equals("Searching");

                searchSelector.setVisible(
                                searchingMode);

                searchSelector.setManaged(
                                searchingMode);

                sortSelector.setVisible(
                                !searchingMode);

                sortSelector.setManaged(
                                !searchingMode);

                targetInput.setVisible(
                                searchingMode);

                targetInput.setManaged(
                                searchingMode);

                if (searchingMode) {

                        algorithmLabel.setText(
                                        getSearchDisplayName());

                } else {

                        algorithmLabel.setText(
                                        getSortDisplayName());
                }

                statusBar.setStatus(
                                searchingMode
                                                ? "Search mode"
                                                : "Sorting mode");
        }

        // =================================================
        // SORTING SELECTION
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

                        case "Heap Sort" ->
                                selectedAlgorithm = AlgorithmType.HEAP_SORT;

                        default ->
                                selectedAlgorithm = AlgorithmType.BUBBLE_SORT;
                }

                algorithmLabel.setText(
                                getSortDisplayName());
        }

        // =================================================
        // SEARCH SELECTION
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

                algorithmLabel.setText(
                                getSearchDisplayName());
        }

        // =================================================
        // RUN
        // =================================================

        private void runAlgorithm() {

                if (currentModel == null) {
                        return;
                }

                try {

                        if (searchingMode) {

                                if (targetInput.getText()
                                                .isBlank()) {

                                        statusBar.setStatus(
                                                        "Enter a target value");

                                        return;
                                }

                                int target = Integer.parseInt(
                                                targetInput
                                                                .getText()
                                                                .trim());

                                events = SearchEngine.run(
                                                selectedSearch,
                                                currentModel
                                                                .getValues(),
                                                target);

                                statusBar.setStatus(
                                                "Search ready");

                        } else {

                                events = AlgorithmEngine.run(
                                                selectedAlgorithm,
                                                currentModel
                                                                .getValues());

                                statusBar.setStatus(
                                                "Sort ready");
                        }

                        EventPlayer.load(
                                        events,
                                        visualiser);

                        updateStepLabel();

                        playPauseButton.setText(
                                        "▶ Play");

                } catch (NumberFormatException ex) {

                        statusBar.setStatus(
                                        "Target must be a number");
                }
        }

        // =================================================
        // STEP FORWARD
        // =================================================

        private void stepForward() {

                if (events == null ||
                                events.isEmpty()) {

                        statusBar.setStatus(
                                        "Run an algorithm first");

                        return;
                }

                EventPlayer.stepForward();

                updateStepLabel();
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
        }

        // =================================================
        // PLAY / PAUSE
        // =================================================

        private void togglePlayPause() {

                if (events == null ||
                                events.isEmpty()) {

                        statusBar.setStatus(
                                        "Run an algorithm first");

                        return;
                }

                if (!EventPlayer
                                .getController()
                                .isPlaying()) {

                        EventPlayer.play();

                        playPauseButton.setText(
                                        "Ⅱ Pause");

                        statusBar.setStatus(
                                        "Playing");

                } else {

                        EventPlayer
                                        .getController()
                                        .pause();

                        playPauseButton.setText(
                                        "▶ Play");

                        statusBar.setStatus(
                                        "Paused");
                }
        }

        // =================================================
        // GENERATE
        // =================================================

        private void generateArray() {

                EventPlayer.stop();

                currentModel = new ArrayModel(25);

                System.out.println(
                                Arrays.toString(
                                                currentModel
                                                                .getValues()));

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

                EventPlayer
                                .getController()
                                .reset();

                playPauseButton.setText(
                                "▶ Play");

                updateStepLabel();

                arrayLabel.setText(
                                "Array: "
                                                + currentModel
                                                                .getValues().length
                                                + " elements");

                statusBar.setStatus(
                                "New array generated");
        }

        // =================================================
        // STEP INFORMATION
        // =================================================

        private void updateStepLabel() {

                if (events == null) {

                        stepLabel.setText(
                                        "Step: 0");

                        return;
                }

                stepLabel.setText(
                                "Step: "
                                                + EventPlayer
                                                                .getController()
                                                                .getCurrentIndex()
                                                + " / "
                                                + events.size());
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