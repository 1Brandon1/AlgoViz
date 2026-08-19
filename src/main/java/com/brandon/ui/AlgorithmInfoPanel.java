package com.brandon.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class AlgorithmInfoPanel extends VBox {

        private final Label algorithmName;

        private final Label bestValue;
        private final Label averageValue;
        private final Label worstValue;
        private final Label spaceValue;
        private final Label stableValue;

        private final Label comparisonsValue;
        private final Label swapsValue;
        private final Label stepValue;

        public AlgorithmInfoPanel() {

                setSpacing(10);
                setPadding(new Insets(16));

                getStyleClass().add("info-panel");

                // ---------------------------------------------
                // TITLE
                // ---------------------------------------------

                algorithmName = new Label("Bubble Sort");
                algorithmName.getStyleClass().add("info-panel-title");

                // ---------------------------------------------
                // COMPLEXITY
                // ---------------------------------------------

                Label complexityTitle = new Label("COMPLEXITY");

                complexityTitle
                                .getStyleClass()
                                .add("section-title");

                bestValue = createValueLabel();
                averageValue = createValueLabel();
                worstValue = createValueLabel();
                spaceValue = createValueLabel();
                stableValue = createValueLabel();

                GridPane complexityGrid = new GridPane();

                complexityGrid.setHgap(30);
                complexityGrid.setVgap(7);

                addRow(complexityGrid, 0, "Best", bestValue);
                addRow(complexityGrid, 1, "Average", averageValue);
                addRow(complexityGrid, 2, "Worst", worstValue);
                addRow(complexityGrid, 3, "Space", spaceValue);
                addRow(complexityGrid, 4, "Stable", stableValue);

                // ---------------------------------------------
                // STATISTICS
                // ---------------------------------------------

                Label statisticsTitle = new Label("STATISTICS");
                statisticsTitle.getStyleClass().add("section-title");

                comparisonsValue = createValueLabel();
                swapsValue = createValueLabel();
                stepValue = createValueLabel();

                GridPane statisticsGrid = new GridPane();

                statisticsGrid.setHgap(30);
                statisticsGrid.setVgap(7);

                addRow(statisticsGrid, 0, "Comparisons", comparisonsValue);
                addRow(statisticsGrid, 1, "Swaps", swapsValue);
                addRow(statisticsGrid, 2, "Step", stepValue);

                // ---------------------------------------------
                // LAYOUT
                // ---------------------------------------------

                getChildren().addAll(
                                algorithmName,
                                complexityTitle,
                                complexityGrid,
                                statisticsTitle,
                                statisticsGrid);

                setAlignment(Pos.TOP_LEFT);

                setAlgorithm("Bubble Sort");
                resetStatistics();
        }

        private Label createValueLabel() {

                Label label = new Label("-");
                label.getStyleClass().add("info-value");
                return label;
        }

        private void addRow(GridPane grid, int row, String name, Label value) {

                Label label = new Label(name);
                label.getStyleClass().add("info-label");

                grid.add(label, 0, row);
                grid.add(value, 1, row);
        }

        // =================================================
        // ALGORITHM
        // =================================================

        public void setAlgorithm(String algorithm) {

                algorithmName.setText(algorithm);

                switch (algorithm) {
                        case "Bubble Sort" -> {
                                setComplexity(
                                                "O(n)",
                                                "O(n²)",
                                                "O(n²)",
                                                "O(1)",
                                                "Yes");
                        }

                        case "Selection Sort" -> {
                                setComplexity(
                                                "O(n²)",
                                                "O(n²)",
                                                "O(n²)",
                                                "O(1)",
                                                "No");
                        }

                        case "Insertion Sort" -> {
                                setComplexity(
                                                "O(n)",
                                                "O(n²)",
                                                "O(n²)",
                                                "O(1)",
                                                "Yes");
                        }

                        case "Merge Sort" -> {
                                setComplexity(
                                                "O(n log n)",
                                                "O(n log n)",
                                                "O(n log n)",
                                                "O(n)",
                                                "Yes");
                        }

                        case "Quick Sort" -> {
                                setComplexity(
                                                "O(n log n)",
                                                "O(n log n)",
                                                "O(n²)",
                                                "O(log n)",
                                                "No");
                        }

                        case "Heap Sort" -> {
                                setComplexity(
                                                "O(n log n)",
                                                "O(n log n)",
                                                "O(n log n)",
                                                "O(1)",
                                                "No");
                        }

                        case "Linear Search" -> {
                                setComplexity(
                                                "O(1)",
                                                "O(n)",
                                                "O(n)",
                                                "O(1)",
                                                "N/A");
                        }

                        case "Binary Search" -> {
                                setComplexity(
                                                "O(1)",
                                                "O(log n)",
                                                "O(log n)",
                                                "O(1)",
                                                "N/A");
                        }

                        case "Jump Search" -> {
                                setComplexity(
                                                "O(1)",
                                                "O(√n)",
                                                "O(√n)",
                                                "O(1)",
                                                "N/A");
                        }

                        case "Interpolation Search" -> {
                                setComplexity(
                                                "O(1)",
                                                "O(log log n)",
                                                "O(n)",
                                                "O(1)",
                                                "N/A");
                        }

                        default -> {
                                setComplexity(
                                                "-",
                                                "-",
                                                "-",
                                                "-",
                                                "-");
                        }
                }
        }

        private void setComplexity(
                        String best,
                        String average,
                        String worst,
                        String space,
                        String stable) {

                bestValue.setText(best);
                averageValue.setText(average);
                worstValue.setText(worst);
                spaceValue.setText(space);
                stableValue.setText(stable);
        }

        // =================================================
        // STATISTICS
        // =================================================

        public void setStatistics(
                        int comparisons,
                        int swaps,
                        int currentStep,
                        int totalSteps) {

                comparisonsValue.setText(String.valueOf(comparisons));
                swapsValue.setText(String.valueOf(swaps));
                stepValue.setText(currentStep + " / " + totalSteps);
        }

        public void resetStatistics() {
                setStatistics(0, 0, 0, 0);
        }
}