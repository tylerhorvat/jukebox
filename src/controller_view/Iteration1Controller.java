/*
 * Class: Iteration1Controller.java
 * Project: JukeBox - Iteration 1
 * CSC 335 July 25, 2017
 * Authors: Hayden Monarch
 * 			Tyler Horvat
 * 
 * This class implements a GUI for JukeBox. Allows a user to login and log out. During login, the user can select from two different song.
 * If the user runs out of songs, he will be notified. The user will also be notified if a song can no longer be selected for playback
 * for the day
 */

package controller_view;

import java.util.ArrayList;
import java.util.Queue;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
	
	/********************************************************
	 * GLOBALS FOR JUKEBOX
	 ********************************************************/
	
	boolean loggedIn;
	JukeBox jukeBox;
	ArrayList<Student> users;
	ArrayList<Song> songList;
	Queue<Song> songQueue;
	Button capture;
	Button lopingSting;
	Label accountName;
	Label password;
	TextField textAccountName;
	PasswordField textPassword;
	Button login;
	Button logout;
	Label status;
	Student currentUser;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		/********************************************************
		 * INITIALIZING VARIABLES
		 ********************************************************/
		jukeBox = new JukeBox();
		loggedIn = false;
		users = jukeBox.getUsers();
		songList = jukeBox.getSongList();
		songQueue = jukeBox.getSongQueue();

		BorderPane all = new BorderPane();

		/********************************************************
		 * SETTING UP SONG BUTTONS
		 ********************************************************/
		GridPane songSelect = new GridPane();
		capture = new Button("Select song 1");
		lopingSting = new Button("Select song 2");
		songSelect.add(capture, 0, 0);
		songSelect.add(lopingSting, 1, 0);
		songSelect.setHgap(10);
		songSelect.setAlignment(Pos.CENTER);

		/********************************************************
		 * LOGIN AREA
		 ********************************************************/
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
		status = new Label("Login first");

		/********************************************************
		 * ADD ELEMENTS TO GRID
		 ********************************************************/
		GridPane userInput = new GridPane();
		userInput.add(accountName, 0, 0);
		userInput.add(password, 0, 1);
		userInput.add(textAccountName, 1, 0);
		userInput.add(textPassword, 1, 1);
		userInput.add(login, 1, 2);
		userInput.add(status, 1, 3);
		userInput.add(logout, 1, 4);
		userInput.setHgap(10);
		userInput.setVgap(10);
		userInput.setAlignment(Pos.CENTER);

		/********************************************************
		 * ADD ELEMENTS TO BORDERPANE
		 ********************************************************/
		all.setTop(songSelect);
		all.setCenter(userInput);
		Scene scene = new Scene(all, 300, 230);
		primaryStage.setScene(scene);
		
		/********************************************************
		 * BUTTON FUNTIONALITY
		 ********************************************************/
		login.setOnAction(e -> logIn());
		logout.setOnAction(e -> logOut());
		capture.setOnAction(new ButtonListener());
		lopingSting.setOnAction(new ButtonListener());

		/********************************************************
		 * SHOWING APPLICATION
		 ********************************************************/
		primaryStage.show();
	}

	/********************************************************
	 *                   FOR REFERENCE
	 * 	User: Chris - Pass:  1
	 *  User: Devon - Pass: 22
	 *  User: River - Pass: 333
	 *  User: Ryan - Pass: 4444
	 ********************************************************/
	
	/********************************************************
	 *                   public void logIn()
	 * 	Checks to see if the login credentials are 
	 *  validated. If they are, successful login will occur
	 *  else, error.
	 ********************************************************/
	public void logIn() {
		if(currentUser != null) {
			status.setText(currentUser.getStudentName() + " must log out first");
			status.setTextFill(Color.RED);
		}
		else {
			String name = textAccountName.getText();
			String pass = textPassword.getText();
			if (jukeBox.authenticateUser(name, pass)) {
				status.setText("Successful Login!");
				loggedIn = true;
				currentUser = users.get(jukeBox.locateUser(name));
				textAccountName.setText("");
				textPassword.setText("");
				status.setText(currentUser.getNumberOfSongsSelectedToday() + " selected, " + timeConversion(currentUser.getSecondsRemaining()));
				status.setTextFill(Color.BLACK);
			}
			else {
				status.setText("Invalid, Try Again!");
				status.setTextFill(Color.RED);
			}
		}
	}
	
	/********************************************************
	 *                   public void logOut()
	 * 	Logs out the current user if logged in.
	 ********************************************************/
	public void logOut() {
		loggedIn = false;
		textAccountName.setText("");
		textPassword.setText("");
		status.setText("Login first");
		status.setTextFill(Color.BLACK);
		currentUser = null;		
	}
	
	/********************************************************
	 *               handle(ActionEvent arg0)
	 * 	Implements a button listener for button clicks.
	 ********************************************************/
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
					if(buttonClicked == capture) {
						processButton(songList.get(0));
					}
					else if(buttonClicked == lopingSting) {
						processButton(songList.get(3));
					}
				}
				else {
					songError(null, 1);
				}	
			}
		}
	}
	
	/********************************************************
	 *               processButton(Song song)
	 *                   
	 * 	 this is a helper method for the buttonListener
	 *   accepts corresponding song as parameter
	 *   and processes that song
	 ********************************************************/
	private void processButton(Song song) {
		if(song.canBePlayed()) {
			song.select();
			currentUser.songSelect(song);
			jukeBox.addSongToQueue(song);
			if(!jukeBox.isPlaying()) {
				Thread thread = new Thread(jukeBox);
				thread.start();
			}
			status.setText(currentUser.getNumberOfSongsSelectedToday() + " selected, " + timeConversion(currentUser.getSecondsRemaining()));	
		}
		else {
			songError(song, 2);
		}
	}
	
	/********************************************************
	 *         void songError(Song song, int errorMessage)
	 *                   
	 * 	this method alerts the user if the user is out of song
	 *  picks or the song can no longer be selected for the day
	 *  errorMessage == 1 means the user is out of selections 
	 *  errorMessage == 2 means the song can no longer be selected
	 ********************************************************/
	private void songError(Song song, int errorMessage) {
		Stage window = new Stage();
		window.setTitle("Message");
		window.setMinWidth(280);
		window.setMinHeight(150);
		window.initModality(Modality.APPLICATION_MODAL);
		
		Label label = new Label();
		if(errorMessage == 1) {
			label.setText(currentUser.getStudentName() + " has reached the limit");
		}
		else if(errorMessage == 2) {
			label.setText(song.getSongName() + " max plays reached");
		}
		
		Button closeButton = new Button("OK");
		closeButton.setOnAction(e -> window.close());
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, closeButton);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
	
	/********************************************************
	 *      timeConversion(int totalSeconds)
	 *                   
	 * 	this method is a helper to display the remaining
	 *  time the user has on his account in h:mm:ss format
	 ********************************************************/
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