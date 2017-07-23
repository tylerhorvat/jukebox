package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class JukeBox {

	Queue<Song> songQueue;
	ArrayList<Song> songList; 
	ArrayList<Student> users;
	
	public JukeBox() {
		songQueue = new LinkedList<>();
		songList = new ArrayList<>();
		users = new ArrayList<>();
		addStudents();
		addSongs();
	}
	
	//locates the user based on user name
	//returns the index of that user
	//if the user does not exist, returns -1
	public int locateUser(String name) {
		
		for(int i = 0; i < users.size(); i++) {
			if(users.get(i).getStudentName().equals(name))
				return i;
		}
		return -1;
	}
	
	//verifies user exists, and confirms passwords match
	//if user does not exist, returns false
	//if there is a password mismatch, also returns false
	//otherwise, returns true
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
	
	//checks to see if a song can be selected for the day.
	//if so adds to queue and returns true
	//if not, does not add to queue and returns false
	public boolean addSongToQueue(Song song) {
		if(song.canBePlayed()) {
			songQueue.add(song);
			return true;
		}
		return false;
	}
	
	//plays all songs that are in the songQueue
	public void playSongQueue() {
		while(!songQueue.isEmpty()) {
			Song song = songQueue.peek();
			song.play();
			songQueue.remove();
		}
	}
	
	public Queue<Song> getSongQueue() {
		return songQueue;
	}

	public ArrayList<Song> getSongList() {
		return songList;
	}

	public ArrayList<Student> getUsers() {
		return users;
	}

	//add all students to user list
	public void addStudents() {
		users.add(chris);
		users.add(devon);
		users.add(river);
		users.add(ryan);
	}
	
	//add all songs to songList
	public void addSongs() {
		songList.add(pokemonCapture);
		songList.add(danseMacabre);
		songList.add(determinedTumbao);
		songList.add(lopingSting);
		songList.add(swingCheese);
		songList.add(theCurtainRises);
		songList.add(untameableFire);
	}
	
	//Students
	Student chris = new Student("Chris", "1");
	Student devon = new Student("Devon", "22");
	Student river = new Student("River", "333");
	Student ryan = new Student("Ryan", "4444");
	
	//Songs
	Song pokemonCapture = new Song("Pokemon Capture", 5, "Pikachu", "Capture.mp3");
	Song danseMacabre = new Song("Danse Macabre", 34, "Kevin MacLeod", "DanseMacabreViolinHook.mp3");
	Song determinedTumbao = new Song("Determined Tumbao", 20, "FreePlay Music", "DeterminedTumbao.mp3");
	Song lopingSting = new Song("Loping Sting", 5, "Kevin MacLeod", "LopingSting.mp3");
	Song swingCheese = new Song("Swing Cheese", 15, "FreePlay Music", "SwingCheese.mp3");
	Song theCurtainRises = new Song("The Curtain Rises", 28, "Kevin MacLeod", "TheCurtainRises.mp3");
	Song untameableFire = new Song("Untameable Fire", 282, "Pierre Langer", "UntameableFire.mp3");
}