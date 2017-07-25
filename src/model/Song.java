package model;

import java.time.LocalDate;

public class Song {

	private String songName;
	private int songLength;
	private String songArtist;
	private String fileName;
	private int timesPlayedToday;
	private LocalDate date;
	private String songFile;
	

	public Song(String songName, int songLength, String songArtist, String fileName) {
		this.songName = songName;
		this.songLength = songLength;
		this.songArtist = songArtist;
		this.fileName = fileName;
		this.timesPlayedToday = 0;
		this.date = LocalDate.now();
		songFile = "songfiles/" + fileName;
	}
	
	public String getSongFile() {
		return songFile;
	}

	public boolean canBePlayed() {
		this.resetTimesPlayed();
		if(getTimesPlayedToday() == 3)
			return false;
		
		return true;
	}
	
	//this method plays the mp3. returns true if the song can be played.
	//returns false if the song cannot be played
	public void play() {		
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

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public int getTimesPlayedToday() {
		return timesPlayedToday;
	}

	public String getSongName() {
		return songName;
	}

	public int getSongLength() {
		return songLength;
	}

	public String getSongArtist() {
		return songArtist;
	}

	public String getFileName() {
		return fileName;
	}
}
