package com.github.draylar.screens;

import com.github.draylar.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.File;

public class MainScreen extends GridPane {

    private JFXButton start = new JFXButton();
    private JFXCheckBox checkBox = new JFXCheckBox();

    public MainScreen() {
        configureGrid();
        configureChildren();
        configureClick();
    }


    /**
     * Configures the grid columns and rows.
     */
    private void configureGrid() {
        for(int i = 0; i < 100; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(1);
            this.getRowConstraints().add(rowConstraints);
        }

        for(int i = 0; i < 100; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(1);
            this.getColumnConstraints().add(columnConstraints);
        }
    }


    /**
     * Configures the children of the grid.
     */
    private void configureChildren() {
        start.setText("Start");
        checkBox.setText("Only go through files without album art");

        this.add(start, 50, 70);
        this.add(checkBox, 50, 40);
    }


    /**
     * Configures the click events of the children in the grid.
     */
    private void configureClick() {
        start.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            Main.setScene(new Scene(new SongInformationScreen(new File("C:\\Users\\samue\\Music")), 700, 600));
        });
    }

}
