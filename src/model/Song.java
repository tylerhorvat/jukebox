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
	
	@Override
	public String toString() {
		return String.format("%-30s %30s     %20s", songName, songArtist, timeConversion());
	}

	/********************************************************
	 * SONG GLOBAL VARIABLES
	 ********************************************************/
	private String songName;
	private int songLength;
	private String songArtist;
	private String fileName;
	private int timesPlayedToday;
	private LocalDate date;
	private String songFile;
	
	/********************************************************
	 * Song(String songName, int songLength, String songArtist, String fileName)
	 * SONG CONSTRUCTOR
	 ********************************************************/
	public Song(String songName, int songLength, String songArtist, String fileName) {
		this.songName = songName;
		this.songLength = songLength;
		this.songArtist = songArtist;
		this.fileName = fileName;
		this.timesPlayedToday = 0;
		this.date = LocalDate.now();
		songFile = "songfiles/" + fileName;
	}
	
	/********************************************************
	 * public boolean canBePlayed()
	 * determines whether the song can still be
	 * played today
	 ********************************************************/
	public boolean canBePlayed() {
		this.resetTimesPlayed();
		if(getTimesPlayedToday() == 3)
			return false;
		
		return true;
	}
	
	/********************************************************
	 * public int select()
	 * this method plays increments the number of times
	 * the song has been selected today
	 ********************************************************/
	public void select() {		
			this.timesPlayedToday++;
	}
	
	/********************************************************
	 * public int resetTimesPlayed()
	 * checks to see if the saved date is the current date, if not
	 * resets the number of times played to 0.
	 ********************************************************/
	public void resetTimesPlayed() {
		if(!date.equals(LocalDate.now())) {
			this.timesPlayedToday = 0;
			date = LocalDate.now();
		}
	}
	
	/********************************************************
	 *  public int getTimesPlayedToday()
	 *  sets the date
	 *  this method is used strictly for testing 
	 *  purposes
	 ********************************************************/
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	/********************************************************
	 *  public int getTimesPlayedToday() - returns timesPlayedToday
	 ********************************************************/
	public int getTimesPlayedToday() {
		return timesPlayedToday;
	}

	/********************************************************
	 *  public int getSongName() - returns songName
	 ********************************************************/
	public String getSongName() {
		return songName;
	}

	/********************************************************
	 *  public int getSongLength() - returns songLength
	 ********************************************************/
	public int getSongLength() {
		return songLength;
	}

	/********************************************************
	 *  public int getSongArtist() - returns songArtist
	 ********************************************************/
	public String getSongArtist() {
		return songArtist;
	}

	/********************************************************
	 *  public int getFileName() - returns fileName
	 ********************************************************/
	public String getFileName() {
		return fileName;
	}
	
	/********************************************************
	 *  public int getSongFile() - returns songFile
	 ********************************************************/
	public String getSongFile() {
		return songFile;
	}
	
	private String timeConversion() {

	    //final int MINUTES_IN_AN_HOUR = 60;
	    final int SECONDS_IN_A_MINUTE = 60;

	    int seconds = songLength % SECONDS_IN_A_MINUTE;
	    int totalMinutes = songLength / SECONDS_IN_A_MINUTE;
	    //int minutes = totalMinutes % MINUTES_IN_AN_HOUR;
	    //int hours = totalMinutes / MINUTES_IN_AN_HOUR;
	    
	    String timeString = String.format("%d:%02d", totalMinutes, seconds);

	    return timeString;
	}
}
