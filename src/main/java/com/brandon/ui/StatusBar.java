package com.brandon.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class StatusBar extends HBox {

    private final Label statusLabel;

    public StatusBar() {

        statusLabel = new Label("Ready");

        statusLabel.getStyleClass()
                .add("status-text");

        setAlignment(Pos.CENTER_RIGHT);

        getChildren().add(statusLabel);
    }

    public void setStatus(String status) {

        statusLabel.setText(status);
    }
}