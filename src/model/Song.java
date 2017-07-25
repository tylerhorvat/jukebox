/*
 * Class: Song.java
 * Project: JukeBox
 * CSC 335 July 25, 2017
 * Authors: Hayden Monarch
 * 			Tyler Horvat
 * 
 * This class implements a Song object for JukeBox. Has method to determine if the song can be selected, select the song, reset
 * number of times played based on date, and getters for each global variable
 */

package model;

import java.time.LocalDate;

public class Song {

	/* Globals for Song class*/
	private String songName;
	private int songLength;
	private String songArtist;
	private String fileName;
	private int timesPlayedToday;
	private LocalDate date;
	private String songFile;
	
	//Song constructor
	public Song(String songName, int songLength, String songArtist, String fileName) {
		this.songName = songName;
		this.songLength = songLength;
		this.songArtist = songArtist;
		this.fileName = fileName;
		this.timesPlayedToday = 0;
		this.date = LocalDate.now();
		songFile = "songfiles/" + fileName;
	}

	//determines whether the song can still be
	//played today
	public boolean canBePlayed() {
		this.resetTimesPlayed();
		if(getTimesPlayedToday() == 3)
			return false;
		
		return true;
	}
	
	//this method plays the mp3. returns true if the song can be played.
	//returns false if the song cannot be played
	public void select() {		
			this.timesPlayedToday++;
	}
	
	//checks to see if the saved date is the current date, if not
	//resets the number of times played to 0.
	public void resetTimesPlayed() {
		if(!date.equals(LocalDate.now())) {
			this.timesPlayedToday = 0;
			date = LocalDate.now();
		}
	}

	//sets the date
	//this method is used strictly for testing 
	//purposes
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	//get number of times played
	public int getTimesPlayedToday() {
		return timesPlayedToday;
	}

	//get name of the song
	public String getSongName() {
		return songName;
	}

	//get song length
	public int getSongLength() {
		return songLength;
	}

	//get song artist
	public String getSongArtist() {
		return songArtist;
	}

	//get filename
	public String getFileName() {
		return fileName;
	}
	
	//get song file
	public String getSongFile() {
		return songFile;
	}
}
