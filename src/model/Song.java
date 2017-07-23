package model;

import java.time.LocalDate;

public class Song {

	private String songName;
	private int songLength;
	private String songArtist;
	private String fileName;
	private int timesPlayedToday;
	private LocalDate date;
	
	public Song(String songName, int songLength, String songArtist, String fileName) {
		this.songName = songName;
		this.songLength = songLength;
		this.songArtist = songArtist;
		this.fileName = fileName;
		this.timesPlayedToday = 0;
	}
	
	public boolean canBePlayed() {
		if(getTimesPlayedToday() == 3)
			return false;
		
		return true;
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
