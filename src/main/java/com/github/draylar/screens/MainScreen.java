package com.github.draylar.screens;

import com.github.draylar.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
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

    private JFXButton startButton = new JFXButton();
    private JFXCheckBox onlyNoAlbumArtFilesButton = new JFXCheckBox();

    private Text titleText = new Text();
    private Text explainationText = new Text();

    private JFXTextField directoryField = new JFXTextField();
    private JFXButton directorySearchButton = new JFXButton();

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
        // set alignment
        GridPane.setHalignment(titleText, HPos.CENTER);
        GridPane.setHalignment(explainationText, HPos.CENTER);
        GridPane.setHalignment(startButton, HPos.CENTER);
        GridPane.setHalignment(onlyNoAlbumArtFilesButton, HPos.CENTER);
        explainationText.setTextAlignment(TextAlignment.CENTER);

        // set text values
        startButton.setText("Start");
        onlyNoAlbumArtFilesButton.setText("Only go through files without album art");
        titleText.setText("MP3 Information Manager");
        explainationText.setText("The MP3 Information Manager will go through the music files in a specified directory, \n and allow you to configure their properties. \n \n" +
                "The base properties include things such as file name, song name, artist name, and album art. \n" +
                "Fill out the settings, and then hit 'Start' to begin.");
        directorySearchButton.setText("Search...");
        directoryField.setPromptText("Directory");

        // set styles
        directorySearchButton.setStyle("-fx-background-color: #d8d8d8");
        startButton.setStyle("-fx-background-color: #d8d8d8");

        // add children to grid
        this.add(titleText, 0, 0, 100, 20);
        this.add(explainationText, 0, 5, 100, 30);
        this.add(startButton, 40, 70, 20, 10);
        this.add(onlyNoAlbumArtFilesButton, 50, 40);
        this.add(directoryField, 34, 50, 30, 1);
        this.add(directorySearchButton, 66, 50, 10, 1);
    }


    /**
     * Configures the click events of the children in the grid.
     */
    private void configureClick() {
        startButton.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            if (new File(directoryField.getText()).exists()) {
                Main.setScene(new Scene(new SongInformationScreen(new File(directoryField.getText()), onlyNoAlbumArtFilesButton.isSelected()), 700, 600));
            } else {
                System.out.println("Please use a valid directory!");
            }
        });

        directorySearchButton.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            directoryField.setText(Main.openDirectoryChooser().getAbsolutePath());
        });
    }

}
