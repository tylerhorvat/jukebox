package model;

import java.time.LocalDate;

public class Student {

	private String studentName;
	private String studentPassword;
	private int numberOfSongsSelectedToday;
	private int secondsRemaining;
	private LocalDate date;
	
	public Student(String name, String password) {
		this.studentName = name;
		this.studentPassword = password;
		this.secondsRemaining = 90000;
		this.numberOfSongsSelectedToday = 0;
		this.date = LocalDate.now();
	}
	
	public boolean canSelectSong() {
		resetSongsSelected();
		if(getNumberOfSongsSelectedToday() == 3) {
			return false;
		}
		return true;
	}
	
	public void resetSongsSelected() {
		if(!date.equals(LocalDate.now())) {
			this.numberOfSongsSelectedToday = 0;
			this.date = LocalDate.now();
		}
	}

	public void songSelect(Song song) {
		this.numberOfSongsSelectedToday++;
		this.secondsRemaining -= song.getSongLength();
	}
	
	//this method sets the date. used strictly for JUnit testing
	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getStudentName() {
		return studentName;
	}

	public String getStudentPassword() {
		return studentPassword;
	}

	public int getNumberOfSongsSelectedToday() {
		return numberOfSongsSelectedToday;
	}

	public int getSecondsRemaining() {
		return secondsRemaining;
	}
}
