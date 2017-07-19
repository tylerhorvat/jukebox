package controller_view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Iteration1Controller extends Application {

  public static void main(String[] args) {
    launch(args);
  }

 
  @Override
  public void start(Stage primaryStage) throws Exception {

    BorderPane all = new BorderPane();
    Scene scene = new Scene(all, 300, 230);
    primaryStage.setScene(scene);

    // Don't forget to show the running application:
    primaryStage.show();
  }
}