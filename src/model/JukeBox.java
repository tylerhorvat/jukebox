/*
 * Class: JukeBox.java
 * Project: JukeBox
 * CSC 335 August 6, 2017
 * Authors: Hayden Monarch
 * 			Tyler Horvat
 * 
 * This class implements the JukeBox.  Adds all users to an array list and all songs to another array list
 * Has methods to locate a user, authenticate a users credentials (case sensitive), add songs to the 
 * song queue. Uses a new thread to play all songs in the song queue.
 */

package model;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import controller_view.Iteration1Controller;
import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

@SuppressWarnings("serial")
public class JukeBox extends Thread implements Serializable {

	/********************************************************
	 * JUKEBOX GLOBALS
	 ********************************************************/
	static ArrayList<Song> songQueue;
	static ArrayList<Song> songList; 
	static ArrayList<Student> users;
	static boolean isPlaying;
	private static JukeBox jukeBox = null;
	
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
	public static int locateUser(String name) {
		
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
	public static ArrayList<Song> getSongQueue() {
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
	public static ArrayList<Student> getUsers() {
		return users;
	}
	
	/********************************************************
	 * public static JukeBox getJukeBox();
	 * returns jukeBox. sets jukeBox to new JukeBox() if
	 * jukeBox is null
	 ********************************************************/
	public static JukeBox getJukeBox() {
		if (jukeBox == null) {
			jukeBox = new JukeBox();
		}
		return jukeBox;
	}
	
	/********************************************************
	 * public static void setUserList()
	 * clears the user list and resets it
	 ********************************************************/
	public static void setUserList(ArrayList<Student> studentList) {
		users.clear();
		users = studentList;
	}
	
	/********************************************************
	 * public static void setSongQueue()
	 * sets the songQueue
	 ********************************************************/
	public static void setSongQueue(ArrayList<Song> queList) {
		songQueue = queList;
	}
	
	/********************************************************
	 * public static ArrayList<Student> getStudentList()
	 * returns the student list
	 ********************************************************/
	public static ArrayList<Student> getStudentList() {
		return users;
	}

	/********************************************************
	 * public static void setSongList()
	 * sets the songlist
	 ********************************************************/
	public static void setSongList(ArrayList<Song> songList) {
		JukeBox.songList = songList;
	}

	/********************************************************
	 * public void addStudents()
	 * adds each user to the arrayList.
	 ********************************************************/
	public static void addStudents() {
		users.add(chris);
		users.add(devon);
		users.add(river);
		users.add(ryan);
		users.add(alex);
	}

	/********************************************************
	 * public void addSongs()
	 * adds each song to the songList
	 ********************************************************/
	public static void addSongs() {
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
	static Student chris = new Student("Chris", "1", false);
	static Student devon = new Student("Devon", "22", false);
	static Student river = new Student("River", "333", false);
	static Student ryan = new Student("Ryan", "4444", false);
	static Student alex = new Student("Alex", "12345", true);
	
	/********************************************************
	 * CREATING NEW SONGS
	 ********************************************************/
	static Song pokemonCapture = new Song("Pokemon Capture", 5, "Pikachu", "Capture.mp3");
	static Song danseMacabre = new Song("Danse Macabre", 34, "Kevin MacLeod", "DanseMacabreViolinHook.mp3");
	static Song determinedTumbao = new Song("Determined Tumbao", 20, "FreePlay Music", "DeterminedTumbao.mp3");
	static Song lopingSting = new Song("Loping Sting", 5, "Kevin MacLeod", "LopingSting.mp3");
	static Song swingCheese = new Song("Swing Cheese", 15, "FreePlay Music", "SwingCheese.mp3");
	static Song theCurtainRises = new Song("The Curtain Rises", 28, "Kevin MacLeod", "TheCurtainRises.mp3");
	static Song untameableFire = new Song("Untameable Fire", 282, "Pierre Langer", "UntameableFire.mp3");

	/********************************************************
	 * PLAYS EVERY SONG IN THE QUEUE
	 ********************************************************/
	@Override
	public void run() {
		//plays all songs in queue
		try {
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
		} catch(NullPointerException e) {
			
		}
	}
}
