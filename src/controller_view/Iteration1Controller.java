/*
 * Class: Iteration1Controller.java
 * Project: JukeBox - Iteration 1
 * CSC 335 July 30, 2017
 * Authors: Hayden Monarch
 * 			Tyler Horvat
 * 
 * This class implements a GUI for JukeBox. Allows a user to login and log out. During login, the user can select from two different song.
 * If the user runs out of songs, he will be notified. The user will also be notified if a song can no longer be selected for playback
 * for the day
 */

package controller_view;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import model.JukeBox;
import model.Song;
import model.Student;
import controller_view.SongTableView;

public class Iteration1Controller extends Application {
	
	/********************************************************
	 * GLOBALS FOR JUKEBOX
	 ********************************************************/
	boolean loggedIn;
	public static Student getCurrentUser() {
		return currentUser;
	}

	static JukeBox jukeBox;
	ArrayList<Student> users;
	ArrayList<Song> songList;
	ArrayList<Song> songQueue;
	Label accountName;
	Label password;
	TextField textAccountName;
	PasswordField textPassword;
	Button login;
	Button logout;
	Label status;
	static Student currentUser;
	static ListView<Song> listView;
	ObservableList<Song> observableSongs;
	static SongTableView songTableView;
	
	public static void main(String[] args) {
		launch(args);
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		/********************************************************
		 * INITIALIZING VARIABLES
		 ********************************************************/
		jukeBox = new JukeBox();
		loggedIn = false;
		users = JukeBox.getUsers();
		songList = jukeBox.getSongList();
		songQueue = JukeBox.getSongQueue();
		listView = new ListView<>();
		songTableView = new SongTableView();
		
		BorderPane all = new BorderPane();
		

		/********************************************************
		 * LOGIN AREA
		 ********************************************************/
		accountName = new Label("Account Name");
		password = new Label("         Password");
		textAccountName = new TextField();
		textPassword = new PasswordField();

		textAccountName.setMaxWidth(185);
		textPassword.setMaxWidth(185);
		textAccountName.setMaxHeight(10);
		textPassword.setMaxHeight(10);

		login = new Button("Login");
		logout = new Button("Log out");
		status = new Label("Login first");

		/********************************************************
		 * ADD ELEMENTS TO GRID
		 ********************************************************/
		GridPane userInput = new GridPane();
		userInput.add(new Label(""), 0, 0);
		userInput.add(accountName, 0, 1);
		userInput.add(password, 0, 2);
		userInput.add(textAccountName, 1, 1);
		userInput.add(textPassword, 1, 2);
		userInput.add(login, 1, 3);
		userInput.add(status, 1, 4);
		userInput.add(logout, 1, 5);
		userInput.add(new Label(""), 0, 6);
		userInput.setHgap(10);
		userInput.setVgap(10);
		
		
		BorderPane adminButtons = new BorderPane();
		adminButtons.setTop(null);
		adminButtons.setBottom(new Label(" "));
		Button addUser = new Button("Add User");
		Button deleteUser = new Button("Delete User");
		addUser.setOnAction(e -> new AddUser());
		deleteUser.setOnAction(e -> new RemoveUser());
		
		GridPane admin = new GridPane();
		admin.add(addUser, 0, 0);
		admin.add(deleteUser, 1, 0);
		admin.setHgap(10);
		admin.setVgap(10);
		admin.setAlignment(Pos.CENTER);
		
		userInput.setAlignment(Pos.CENTER);
		userInput.add(adminButtons, 1, 6);
		userInput.add(new Label(""), 1, 7);
		Label direction = new Label("Double Click to add song to playlist                                                        ");
		direction.setTextFill(Color.DARKSLATEBLUE);
		Label listLabel = new Label("Playlist:");
		listLabel.setTextFill(Color.DARKSLATEBLUE);
		
		GridPane views = new GridPane();
		views.add(direction, 1, 1);
		views.add(songTableView, 1, 2);
		views.add(listLabel, 1, 3);
		views.add(listView, 1, 4);
		views.add(new Label("    "), 0, 5);
		
		/********************************************************
		 * SET UP LIST VIEW
		 ********************************************************/
		observableSongs = FXCollections.observableArrayList(songQueue);
		listView.setItems(observableSongs);
		listView.setCellFactory(new Callback<ListView<Song>, ListCell<Song>> () {

			@Override
			public ListCell<Song> call(ListView<Song> arg0) {

				ListCell<Song> cell = new ListCell<Song>() {
					
					@Override
					protected void updateItem(Song t, boolean bln) {
						super.updateItem(t, bln);
						if(t != null) {
							setText(t.toString());
						}
						else {
							setText(null);
						}
					}
				};
				return cell;
			}
		});
		
		/********************************************************
		 * SET UP TABLE VIEW
		 ********************************************************/
		songTableView.setRowFactory(tv -> {
		    TableRow<Song> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
		        	if (!loggedIn) {
			    		AlertMe loginAlert = new AlertMe("Must login to play songs!");
			    	}
		        	else {
		        		if (!currentUser.canSelectSong()) {
		        			AlertMe limitAleart = new AlertMe("Daily song limit reached!");
		        		}
		        		else {
		        			Song clickedRow = row.getItem();
		        			processButton(clickedRow);
		        			songTableView.refresh();
		        		}
		        	}
		        }
		    });
		    return row ;
		});
		
		/********************************************************
		 * ADD ELEMENTS TO BORDERPANE
		 ********************************************************/
		all.setTop(userInput);
		all.setCenter(views);
		//all.setCenter(songTableView);
		//all.setBottom(listView);
		Scene scene = new Scene(all, 500, 800);
		primaryStage.setScene(scene);
		
		/********************************************************
		 * BUTTON FUNCTIONALITY
		 ********************************************************/
		login.setOnAction(e -> logIn(adminButtons, admin));
		logout.setOnAction(e -> logOut(adminButtons));
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		      public void handle(WindowEvent we) {
		        // Write the student collection to a file
		        System.out.println("App closing");
		        writeJukeBoxSongs();
		        writeJukeBoxUsers();
		      }
		});

		/********************************************************
		 * SHOWING APPLICATION
		 ********************************************************/
		primaryStage.show();
		handlePersistence();
	}

	private static void handlePersistence() throws ClassNotFoundException {
	    Alert alert = new Alert(AlertType.CONFIRMATION);
	    alert.setTitle("Start Up Option");
	    alert.setHeaderText("Start with initial state?");
	    alert.setContentText("Press ok while system testing.");
	    Optional<ButtonType> result = alert.showAndWait();

	    // TODO: Either read the saved student collection or start with default
	    if (result.get() == ButtonType.OK) {
	    	
	    } else {   
	    	readJukeBoxUsers();
	    	readJukeBoxSongs();	    	
	    	
	    		if(!jukeBox.isPlaying()) {
					Thread thread = new Thread(jukeBox);
					thread.start();
				}	    	

	    }
	  }
	
	private static void readJukeBoxUsers() throws ClassNotFoundException {
		try {
			FileInputStream rawBytes = new FileInputStream("persistentUsers");
			ObjectInputStream inFile = new ObjectInputStream(rawBytes);
			
			@SuppressWarnings("unchecked")
			ArrayList<Student> studentList = (ArrayList<Student>) inFile.readObject();
			System.out.println(studentList);
			JukeBox.setUserList(studentList);
			songTableView.refresh();
			listView.refresh();
			
			inFile.close();
		} catch (IOException ioe) {
			System.out.println(ioe);
		}		
	}
	
	@SuppressWarnings("unchecked")
	private static void readJukeBoxSongs() throws ClassNotFoundException {
		try {
			FileInputStream rawBytes = new FileInputStream("persistentQueue");
			ObjectInputStream inFile = new ObjectInputStream(rawBytes);
			
			FileInputStream songRawBytes = new FileInputStream("persistentSongs");
			ObjectInputStream songInFile = new ObjectInputStream(songRawBytes);
			
			ArrayList<Song> songQueue = (ArrayList<Song>) inFile.readObject();
			System.out.println(songQueue);
			JukeBox.setSongQueue(songQueue);
			
			ArrayList<Song> songList = (ArrayList<Song>) songInFile.readObject();
			System.out.println(songList);
			JukeBox.setSongList(songList);
			
			listView.setItems(null); 
			listView.layout(); 
			ObservableList<Song> data = FXCollections.observableArrayList(songQueue);
			listView.setItems(data);
			
			songTableView.setItems(null); 
			songTableView.layout(); 
			ObservableList<Song> songData = FXCollections.observableArrayList(songList);
			songTableView.setItems(songData);

			inFile.close();
			songInFile.close();
			
		} catch (IOException ioe) {
			System.out.println(ioe);
		}		
	}
	
	private void writeJukeBoxSongs() {
		try {

			FileOutputStream queueBytesToDisk = new FileOutputStream("persistentQueue");
			ObjectOutputStream outFile = new ObjectOutputStream(queueBytesToDisk);
			
			FileOutputStream songBytesToDisk = new FileOutputStream("persistentSongs");
			ObjectOutputStream songOutFile = new ObjectOutputStream(songBytesToDisk);
			
			outFile.writeObject(JukeBox.getSongQueue());
			songOutFile.writeObject(jukeBox.getSongList());
			
			outFile.close();
			songOutFile.close();

		} catch (IOException ioe) {
			System.out.println(ioe);
		}
		
	}
	
	private void writeJukeBoxUsers() {
		try {
			FileOutputStream bytesToDisk = new FileOutputStream("persistentUsers");
			ObjectOutputStream outFile = new ObjectOutputStream(bytesToDisk);
			
			outFile.writeObject(JukeBox.getStudentList());
			
			outFile.close();
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
		
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
	public void logIn(BorderPane adminButtons, GridPane admin) {
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
				users = JukeBox.getUsers();
				currentUser = users.get(JukeBox.locateUser(name));
				textAccountName.setText("");
				textPassword.setText("");
				currentUser.resetSongsSelected();
				status.setText(currentUser.getNumberOfSongsSelectedToday() + " selected, " + timeConversion(currentUser.getSecondsRemaining()));
				status.setTextFill(Color.BLACK);
				
				if(currentUser.isAdmin()) {
					adminButtons.setTop(admin);
				}
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
	public void logOut(BorderPane adminButtons) {
		loggedIn = false;
		textAccountName.setText("");
		textPassword.setText("");
		status.setText("Login first");
		status.setTextFill(Color.BLACK);
		currentUser = null;		
		adminButtons.setTop(null);
		
	}
	
	/********************************************************
	 *               processButton(Song song)
	 *                   
	 * 	 this is a helper method for the tableView
	 *   accepts corresponding song as parameter
	 *   and processes that song
	 ********************************************************/
	private void processButton(Song song) {
		if(song.canBePlayed()) {
			song.select();
			currentUser.songSelect(song);
			jukeBox.addSongToQueue(song);
			listView.refresh();
			
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
	
	/********************************************************
	 * public static ListView<Song> getListView() - 
	 * returns listView
	 ********************************************************/
	public static ListView<Song> getListView() {
		return listView;
		
	}
	
}