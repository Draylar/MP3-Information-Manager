package com.github.draylar.screens;

import com.github.draylar.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.File;

public class MainScreen extends GridPane {

    private JFXButton start = new JFXButton();
    private JFXCheckBox checkBox = new JFXCheckBox();

    private Text title = new Text();
    private Text explaination = new Text();

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
        GridPane.setHalignment(title, HPos.CENTER);
        GridPane.setHalignment(explaination, HPos.CENTER);
        GridPane.setHalignment(start, HPos.CENTER);
        GridPane.setHalignment(checkBox, HPos.CENTER);

        start.setText("Start");
        checkBox.setText("Only go through files without album art");
        title.setText("MP3 Information Manager");
        explaination.setText("The MP3 Information Manager will go through the music files in a specified directory, \n and allow you to configure their properties. \n \n" +
                "The base properties include things such as file name, song name, artist name, and album art. \n" +
                "Fill out the settings, and then hit 'Start' to begin.");

        explaination.setTextAlignment(TextAlignment.CENTER);

        this.add(title, 0, 0, 100, 20);
        this.add(explaination, 0, 5, 100, 30);
        this.add(start, 40, 70, 20, 10);
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
