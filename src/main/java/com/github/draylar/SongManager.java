package com.github.draylar;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class SongManager {

    // singleton
    private static SongManager instance;
    private SongManager() { }
    public static SongManager getInstance() {
        if (instance == null) instance = new SongManager();
        return instance;
    }


    /**
     * Returns a list of all mp3 files in a directory.
     * @param directory
     * @return
     */
    public ArrayList<File> getAllSongsInFile(File directory) {
        ArrayList<File> fileList = new ArrayList<>();

        try {
            for (File file : directory.listFiles()) {
                if (file.getName().contains(".mp3")) {
                    fileList.add(file);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Directory wasn't found!");
        }

        return fileList;
    }


    /**
     * Checks if a mp3 file has album art attached to it.
     * @param file
     * @return
     */
    public boolean hasAlbumArt(File file) {
        Mp3File mp3File;

        try {
            mp3File = new Mp3File(file.getAbsolutePath());
            ID3v2 tag = mp3File.getId3v2Tag();
            return tag.getAlbumImage() != null;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Not an mp3!");
        }

        return false;
    }


    /**
     * Retrieves the album art of an mp3 file.
     * @param file
     * @return
     */
    public BufferedImage getAlbumArt(File file) {
        Mp3File mp3File;

        try {
            mp3File = new Mp3File(file.getAbsolutePath());
            ID3v2 tag = mp3File.getId3v2Tag();
            InputStream in = new ByteArrayInputStream(tag.getAlbumImage());
            BufferedImage image = ImageIO.read(in);
            return image;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * Sets the album art for an mp3 file.
     * @param song
     * @param albumArt
     */
    public void setAlbumArt(File song, File albumArt) {
        try {
            // parent directory
            String parentDirectory = song.getParent();

            // mp3 and id3v2
            Mp3File mp3File = new Mp3File(song.getAbsolutePath());
            ID3v2 tag = mp3File.getId3v2Tag();

            // get bytes from image and set to album image
            byte[] imageBytes = Files.readAllBytes(Paths.get(albumArt.getAbsolutePath()));
            tag.setAlbumImage(imageBytes, "image/png");

            // save
            mp3File.save(parentDirectory + "\\" + song.getName() + ".new");

            // delete original file
            Files.delete(Paths.get(song.getAbsolutePath()));

            // rename the .new file
            Files.move(Paths.get(parentDirectory + "\\" +  song.getName() + ".new"), Paths.get(parentDirectory + "\\" + song.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Sets the basic information for a mp3 file, including: file name, song name, and artist name.
     * @param file mp3 file to be changed
     * @param desiredFileName desired name of mp3 file
     * @param songName desired song name
     * @param artistName desired artist name
     */
    public void setBasicInformation(File file, String desiredFileName, String songName, String artistName) {
        try {
            // parent directory location
            String parentDirectory = file.getParent();

            // set up mp3 and id3v2
            Mp3File mp3 = new Mp3File(file);
            ID3v2 tag = mp3.getId3v2Tag();

            // set values
            tag.setTitle(songName);
            tag.setAlbumArtist(artistName);

            // save the changed mp3 file
            mp3.save(parentDirectory + "\\" + desiredFileName + ".mp3" + ".new");

            // delete original file
            Files.delete(Paths.get(file.getAbsolutePath()));

            // rename .new file
            Files.move(Paths.get(parentDirectory + "\\" + desiredFileName + ".mp3" + ".new"), Paths.get(parentDirectory + "\\" + desiredFileName + ".mp3"));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }







}
