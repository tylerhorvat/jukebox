package controller_view;

import java.util.ArrayList;
import java.util.Queue;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.JukeBox;
import model.Song;
import model.Student;

public class Iteration1Controller extends Application{
	
	/* GLOBALS for JukeBox */
	
	boolean loggedIn;
	JukeBox jukeBox;
	ArrayList<Student> users;
	ArrayList<Song> songList;
	Queue<Song> songQueue;
	Button song1;
	Button song2;
	Label accountName;
	Label password;
	TextField textAccountName;
	PasswordField textPassword;
	Button login;
	Button logout;
	Label instruct;
	Student currentUser;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		/* Initializing all Variables */
		jukeBox = new JukeBox();
		loggedIn = false;
		users = jukeBox.getUsers();
		songList = jukeBox.getSongList();
		songQueue = jukeBox.getSongQueue();

		BorderPane all = new BorderPane();

		// set up song select buttons
		GridPane songSelect = new GridPane();
		song1 = new Button("Select song 1");
		song2 = new Button("Select song 2");
		songSelect.add(song1, 0, 0);
		songSelect.add(song2, 1, 0);
		songSelect.setHgap(10);
		songSelect.setAlignment(Pos.CENTER);

		// set up login area
		accountName = new Label("Account Name");
		password = new Label("         Password");
		textAccountName = new TextField();
		textPassword = new PasswordField();

		textAccountName.setMaxWidth(150);
		textPassword.setMaxWidth(150);
		textAccountName.setMaxHeight(10);
		textPassword.setMaxHeight(10);

		login = new Button("Login");
		logout = new Button("Log out");
		instruct = new Label("Login first");

		// add all login elements to grid
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
		
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setTitle("Information Dialog");
		alert.setHeaderText("Look, an Information Dialog");

		// add everything to borderpane
		all.setTop(songSelect);
		all.setCenter(userInput);
		Scene scene = new Scene(all, 300, 230);
		primaryStage.setScene(scene);
		
		// button functionality
		login.setOnAction(e -> logIn());
		logout.setOnAction(e -> logOut());
		song1.setOnAction(new ButtonListener());
		song2.setOnAction(new ButtonListener());

		// Don't forget to show the running application:
		primaryStage.show();
	}

	/********************************************************
	 *                   FOR REFERENCE
	 * 	User: Chris - Pass:  1
	 *  User: Devon - Pass: 22
	 *  User: River - Pass: 333
	 *  User: Ryan - Pass: 4444
	 ********************************************************/
	
	//Login functionality
	public void logIn() {
		if(currentUser != null) {
			instruct.setText(currentUser.getStudentName() + " must log out first");
			instruct.setTextFill(Color.RED);
		}
		else {
			String name = textAccountName.getText();
			String pass = textPassword.getText();
			if (jukeBox.authenticateUser(name, pass)) {
				instruct.setText("Successful Login!");
				loggedIn = true;
				currentUser = users.get(jukeBox.locateUser(name));
				textAccountName.setText("");
				textPassword.setText("");
				instruct.setText(currentUser.getNumberOfSongsSelectedToday() + " selected, " + timeConversion(currentUser.getSecondsRemaining()));
			}
			else {
				instruct.setText("Invalid, Try Again!");
				instruct.setTextFill(Color.RED);
			}
		}
	}
	
	//Logout functionality
	public void logOut() {
		loggedIn = false;
		textAccountName.setText("");
		textPassword.setText("");
		instruct.setText("Login first");
		instruct.setTextFill(Color.BLACK);
		currentUser = null;		
	}
	
	private class ButtonListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			if (loggedIn == false) {
				Stage window = new Stage();
				window.setTitle("Message");
				window.setMinWidth(250);
				window.setMinHeight(150);
				window.initModality(Modality.APPLICATION_MODAL);
				
				Label label = new Label();
				label.setText("User must login to play a song.");
				Button closeButton = new Button("Okay, got it!");
				closeButton.setOnAction(e -> window.close());
				
				VBox layout = new VBox(10);
				layout.getChildren().addAll(label, closeButton);
				layout.setAlignment(Pos.CENTER);
				
				Scene scene = new Scene(layout);
				window.setScene(scene);
				window.showAndWait();
			}
			else {
				if(currentUser.canSelectSong()) {
					Button buttonClicked = (Button) arg0.getSource();
					if(buttonClicked == song1) {
						Song song = songList.get(0);
						if(song.canBePlayed()) {
							song.play();
							currentUser.songSelect(song);
							jukeBox.addSongToQueue(currentUser, song);
							if(!jukeBox.isPlaying()) {
								jukeBox.playQueue();
								/*Thread thread = new Thread(jukeBox);
								thread.start();*/
							}
							instruct.setText(currentUser.getNumberOfSongsSelectedToday() + " selected, " + timeConversion(currentUser.getSecondsRemaining()));
							//System.out.println(songQueue.toString());
						}
						else {
							songPlayError(song);
						}
					}
					else if(buttonClicked == song2) {
						Song song = songList.get(3);
						if(song.canBePlayed()) {
							song.play();
							currentUser.songSelect(song);
							jukeBox.addSongToQueue(currentUser, song);
							if(!jukeBox.isPlaying()) {
								jukeBox.playQueue();
								/*Thread thread = new Thread(jukeBox);
								thread.start();*/
							}
							instruct.setText(currentUser.getNumberOfSongsSelectedToday() + " selected, " + timeConversion(currentUser.getSecondsRemaining()));
							//System.out.println(songQueue.toString());
						}
						else {
							songPlayError(song);
						}
					}
				}
				else {
					userSongError();
				}
				
			}
			
		}
		
	}
	
	private void songPlayError(Song song) {
		Stage window = new Stage();
		window.setTitle("Message");
		window.setMinWidth(250);
		window.setMinHeight(150);
		window.initModality(Modality.APPLICATION_MODAL);
		
		Label label = new Label();
		label.setText(song.getSongName() + " max plays reached");
		Button closeButton = new Button("OK");
		closeButton.setOnAction(e -> window.close());
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, closeButton);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
	
	private void userSongError() {
		Stage window = new Stage();
		window.setTitle("Message");
		window.setMinWidth(250);
		window.setMinHeight(150);
		window.initModality(Modality.APPLICATION_MODAL);
		
		Label label = new Label();
		label.setText(currentUser.getStudentName() + " has reached the limit");
		Button closeButton = new Button("OK");
		closeButton.setOnAction(e -> window.close());
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, closeButton);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
	
	private String timeConversion(int totalSeconds) {

	    final int MINUTES_IN_AN_HOUR = 60;
	    final int SECONDS_IN_A_MINUTE = 60;

	    int seconds = totalSeconds % SECONDS_IN_A_MINUTE;
	    int totalMinutes = totalSeconds / SECONDS_IN_A_MINUTE;
	    int minutes = totalMinutes % MINUTES_IN_AN_HOUR;
	    int hours = totalMinutes / MINUTES_IN_AN_HOUR;
	    
	    String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);

	    return timeString;
	}

	
}