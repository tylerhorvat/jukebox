/*
 * Class: JukeBox.java
 * Project: JukeBox
 * CSC 335 July 25, 2017
 * Authors: Hayden Monarch
 * 			Tyler Horvat
 * 
 * This class implements the JukeBox.  Adds all users to an array list and all songs to another array list
 * Has methods to locate a user, authenticate a users credentials (case sensitive), add songs to the 
 * song queue. Uses a new thread to play all songs in the song queue.
 */

package model;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import controller_view.Iteration1Controller;													
import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class JukeBox extends Thread/*implements Runnable */{

	/********************************************************
	 * JUKEBOX GLOBALS
	 ********************************************************/
	ArrayList<Song> songQueue;
	ArrayList<Song> songList; 
	ArrayList<Student> users;
	boolean isPlaying;
	
	/********************************************************
	 * JukeBox()
	 * JUKEBOX CONSTRUCTOR
	 ********************************************************/
	public JukeBox() {
		songQueue = new ArrayList<Song>();
		songList = new ArrayList<>();
		users = new ArrayList<>();
		addStudents();
		addSongs();
		isPlaying = false;
	}
	
	/********************************************************
	 * int locateUser(String name)
	 * locates the user based on user name
	 * returns the index of that user
	 * if the user does not exist, returns -1
	 ********************************************************/
	public int locateUser(String name) {
		
		for(int i = 0; i < users.size(); i++) {
			if(users.get(i).getStudentName().equals(name))
				return i;
		}
		return -1;
	}
	
	
	/********************************************************
	 * boolean authenticateUser(String name, String password)
	 * verifies user exists, and confirms passwords match
	 * if user does not exist, returns false
	 * if there is a password mismatch, also returns false
	 * otherwise, returns true
	 ********************************************************/
	public boolean authenticateUser(String name, String password) {
		int index = locateUser(name);
		if(index == -1) {
			return false;
		}
		Student user = users.get(index);
		if(user.getStudentPassword().equals(password)) {
			return true;
		}
		return false;
	}
	
	/********************************************************
	 * void addSongToQueue(Song song)
	 * adds song to queue
	 ********************************************************/
	public void addSongToQueue(Song song) {
			songQueue.add(song);
			Iteration1Controller.getListView().getItems().add(song);
			Iteration1Controller.getListView().refresh();
	}
	
	/********************************************************
	 * boolean isPlaying()
	 * returns isPlaying
	 ********************************************************/
	public boolean isPlaying() {
		return isPlaying;
	}

	/********************************************************
	 * Queue<Song> getSongQueue()
	 * returns the song queue
	 ********************************************************/
	public ArrayList<Song> getSongQueue() {
		return songQueue;
	}

	/********************************************************
	 * ArrayList<Song> getSongList()
	 * returns the song list
	 ********************************************************/
	public ArrayList<Song> getSongList() {
		return songList;
	}

	/********************************************************
	 * ArrayList<Student> getUsers()
	 * returns the user list
	 ********************************************************/
	public ArrayList<Student> getUsers() {
		return users;
	}

	/********************************************************
	 * public void addStudents()
	 * adds each user to the arrayList.
	 ********************************************************/
	public void addStudents() {
		users.add(chris);
		users.add(devon);
		users.add(river);
		users.add(ryan);
	}
	
	/********************************************************
	 * public void addSongs()
	 * adds each song to the songList
	 ********************************************************/
	public void addSongs() {
		songList.add(pokemonCapture);
		songList.add(danseMacabre);
		songList.add(determinedTumbao);
		songList.add(lopingSting);
		songList.add(swingCheese);
		songList.add(theCurtainRises);
		songList.add(untameableFire);
	}
	
	/********************************************************
	 * CREATING NEW STUDENTS
	 ********************************************************/
	Student chris = new Student("Chris", "1");
	Student devon = new Student("Devon", "22");
	Student river = new Student("River", "333");
	Student ryan = new Student("Ryan", "4444");
	
	/********************************************************
	 * CREATING NEW SONGS
	 ********************************************************/
	Song pokemonCapture = new Song("Pokemon Capture", 5, "Pikachu", "Capture.mp3");
	Song danseMacabre = new Song("Danse Macabre", 34, "Kevin MacLeod", "DanseMacabreViolinHook.mp3");
	Song determinedTumbao = new Song("Determined Tumbao", 20, "FreePlay Music", "DeterminedTumbao.mp3");
	Song lopingSting = new Song("Loping Sting", 5, "Kevin MacLeod", "LopingSting.mp3");
	Song swingCheese = new Song("Swing Cheese", 15, "FreePlay Music", "SwingCheese.mp3");
	Song theCurtainRises = new Song("The Curtain Rises", 28, "Kevin MacLeod", "TheCurtainRises.mp3");
	Song untameableFire = new Song("Untameable Fire", 282, "Pierre Langer", "UntameableFire.mp3");

	/********************************************************
	 * PLAYS EVERY SONG IN THE QUEUE
	 ********************************************************/
	@Override
	public void run() {
		//plays all songs in queue
		while(!songQueue.isEmpty()) {
			isPlaying = true;
			Song song = songQueue.get(0);
			File file = new File(song.getSongFile());
		    URI uri = file.toURI();
		    
		    Media media = new Media(uri.toString());
		    MediaPlayer mediaPlayer = new MediaPlayer(media);
		    mediaPlayer.setAutoPlay(true);
		    mediaPlayer.play();
		    
			try {
				Thread.sleep((song.getSongLength() + 2) *1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		    
			songQueue.remove(0);

			 Platform.runLater(new Runnable() {
		            @Override public void run() {
		            	Iteration1Controller.getListView().getItems().remove(0);
		            	Iteration1Controller.getListView().refresh();
		            }
		        });
		}
		isPlaying = false;
	}
}
