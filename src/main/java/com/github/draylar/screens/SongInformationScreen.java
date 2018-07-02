package com.github.draylar.screens;

import com.github.draylar.Main;
import com.github.draylar.SongManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;

public class SongInformationScreen extends GridPane {

    // bottom controls
    private JFXButton confirmButton = new JFXButton();
    private JFXButton previousButton = new JFXButton();
    private JFXButton skipButton = new JFXButton();

    // song file
    private ArrayList<File> songList;
    private File mainDirectory;
    private int currentSongIndex = 0;

    // song information
    private Text fileNameDescription = new Text("File Name");
    private JFXTextField fileName = new JFXTextField();

    private Text songNameDescription = new Text("Song Name");
    private JFXTextField songName = new JFXTextField();

    private Text artistNameDescription = new Text("Artist Name");
    private JFXTextField artistName = new JFXTextField();

    private ImageView albumArt = new ImageView();


    public SongInformationScreen(File mainDirectory) {
        this.mainDirectory = mainDirectory;
        this.songList = SongManager.getInstance().getAllSongsInFile(mainDirectory);

        configureGrid();
        configureChildren();
        configureClick();

        displaySongInformation(currentSongIndex);
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
     * Configures the children in the grid.
     */
    private void configureChildren() {
        confirmButton.setText("Confirm");
        previousButton.setText("Previous");
        skipButton.setText("Skip");

        confirmButton.setStyle("-fx-background-color: #d8d8d8");
        previousButton.setStyle("-fx-background-color: #d8d8d8");

        albumArt.setFitWidth(300);
        albumArt.setPreserveRatio(true);
        GridPane.setHalignment(albumArt, HPos.CENTER);
        GridPane.setValignment(albumArt, VPos.TOP);
        GridPane.setMargin(albumArt, new Insets(40 , 0, 0, 0));

        this.add(skipButton, 90, 82, 10, 10);
        this.add(confirmButton, 90, 90, 10, 10);
        this.add(previousButton, 3, 90, 10, 10);

        this.add(fileNameDescription, 25, 60, 10, 10);
        this.add(fileName, 35, 60, 40,  10);

        this.add(songNameDescription, 25, 70, 10, 10);
        this.add(songName, 35, 70, 40, 10);

        this.add(artistNameDescription, 25, 80, 10, 10);
        this.add(artistName, 35, 80, 40, 10);

        this.add(albumArt, 0, 0, 100, 100);
    }


    /**
     * Configures click events for children.
     */
    private void configureClick() {
        confirmButton.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            SongManager.getInstance().setBasicInformation(songList.get(currentSongIndex), fileName.getText(), songName.getText(), artistName.getText());
        });

        previousButton.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            currentSongIndex--;
            displaySongInformation(currentSongIndex);
        });

        skipButton.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            currentSongIndex++;
            displaySongInformation(currentSongIndex);
        });

        albumArt.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            File albumArtFile = Main.openImageFileChooser();
            albumArt.setImage(new Image("file::" + albumArtFile.getAbsolutePath()));
            SongManager.getInstance().setAlbumArt(songList.get(currentSongIndex), albumArtFile);
        });
    }


    /**
     * Displays the information of a music file on the screen.
     * @param index index of the music file in the song ArrayList
     */
    private void displaySongInformation(int index) {
        // todo: make sure to check Array bounds for IndexOutOfBoundsException

        try {
            // set up mp3 file and tag objects
            Mp3File mp3File = new Mp3File(songList.get(index));
            ID3v2 tag = mp3File.getId3v2Tag();

            // get song file name without extension
            String name = songList.get(index).getName();
            if (name.lastIndexOf(".") != 0) name = name.substring(0, name.lastIndexOf("."));

            // set song information
            fileName.setText(name);
            songName.setText(tag.getTitle());
            artistName.setText(tag.getArtist());

            // get album art-- if it does exist, set it, otherwise set the art to the "no album image" placeholder
            byte[] imageByteArray = tag.getAlbumImage();
            if (imageByteArray != null) {
                BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageByteArray));
                albumArt.setImage(SwingFXUtils.toFXImage(image, null));
            } else {
                albumArt.setImage(new Image("NoAlbumArt.png"));
            }


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Invalid song!");
            currentSongIndex++;
            displaySongInformation(currentSongIndex);
        }
    }


}
