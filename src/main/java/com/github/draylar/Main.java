package com.github.draylar;

import com.github.draylar.screens.MainScreen;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;


public class Main extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;

        primaryStage.setScene(new Scene(new MainScreen(), 700, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


    /**
     * Changes or sets the scene for the main stage.
     * @param scene
     */
    public static void setScene(Scene scene) {
        stage.setScene(scene);
    }


    /**
     * Opens the file chooser/dialog window so the user can select a image file for album art.
     * @return
     */
    public static File openImageFileChooser() {
        // set up filter
        FileChooser.ExtensionFilter fileFilter = new FileChooser.ExtensionFilter("Images", ".png", ".jpg");

        // set up file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setSelectedExtensionFilter(fileFilter);
        File selectedFile = fileChooser.showOpenDialog(stage);
        return selectedFile;
    }
}
