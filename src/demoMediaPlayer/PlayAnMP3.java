package demoMediaPlayer;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class PlayAnMP3 extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    String baseDir = System.getProperty("user.dir") + System.getProperty("file.separator")
        + "songfiles" + System.getProperty("file.separator");
    String songFile = "file:" + baseDir + "LopingSting.mp3";

    System.out.println(songFile);
    Media media = new Media(songFile);
    MediaPlayer mediaPlayer = new MediaPlayer(media);
    mediaPlayer.setAutoPlay(true);
  }

}