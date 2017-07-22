package controller_view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Iteration1Controller extends Application {

  public static void main(String[] args) {
    launch(args);
  }

 
  @Override
  public void start(Stage primaryStage) throws Exception {

    BorderPane all = new BorderPane();
    
    //set up song select buttons
    GridPane songSelect = new GridPane();
    Button song1 = new Button("Select song 1");
    Button song2 = new Button("Select song 2");
    songSelect.add(song1, 0, 0);
    songSelect.add(song2, 1, 0);
    songSelect.setHgap(10);
    songSelect.setAlignment(Pos.CENTER);
    
    //set up login area
    Label accountName = new Label("Account Name");
    Label password = new Label("         Password");
    TextField textAccountName = new TextField();
    TextField textPassword = new TextField();
    
    textAccountName.setMaxWidth(150);
    textPassword.setMaxWidth(150);
    textAccountName.setMaxHeight(10);
    textPassword.setMaxHeight(10);
    
    Button login = new Button("Login");
    Button logout = new Button("Log out");
    Label instruct = new Label("Login first");
    
    //add all login elements to grid
    GridPane userInput = new GridPane();
    userInput.add(accountName, 0, 0);
    userInput.add(password, 0, 1);
    userInput.add(textAccountName, 1, 0);
    userInput.add(textPassword, 1, 1);
    userInput.add(login, 1, 2);
    userInput.add(instruct, 1, 3);
    userInput.add(logout, 1, 4);
    userInput.setHgap(10);
    userInput.setVgap(10);
    userInput.setAlignment(Pos.CENTER);
    
    //add everything to borderpane
    all.setTop(songSelect);
    all.setCenter(userInput);
    Scene scene = new Scene(all, 300, 230);
    primaryStage.setScene(scene);

    // Don't forget to show the running application:
    primaryStage.show();
  }
}