/*
 * Class: Student.java
 * Project: JukeBox
 * CSC 335 July 30, 2017
 * Authors: Hayden Monarch
 * 			Tyler Horvat
 * 
 * This class implements a student object for JukeBox. Has methods to determine if the user can select a song today,
 * resets the number of songs selected, and song selection to update number of songs played and seconds remaining
 * on the student account.
 */

package model;

import java.time.LocalDate;

public class Student {

	/********************************************************
	 * GLOBALS
	 ********************************************************/
	private String studentName;
	private String studentPassword;
	private int numberOfSongsSelectedToday;
	private int secondsRemaining;
	private LocalDate date;
	private boolean admin;
	private String administrator = "No";

	public String getAdministrator() {
		return administrator;
	}

	/********************************************************
	 * public boolean Student(String name, String password)
	 * Student constructor
	 ********************************************************/
	public Student(String name, String password, boolean admin) {
		this.studentName = name;
		this.studentPassword = password;
		this.secondsRemaining = 90000;
		this.numberOfSongsSelectedToday = 0;
		this.date = LocalDate.now();
		setAdmin(admin);
	}
	
	/********************************************************
	 * public boolean canSelectSong() 
	 * Determines if the Student can select more songs for the day
	 * returns true if the Student can, otherwise false
	 ********************************************************/
	public boolean canSelectSong() {
		resetSongsSelected();
		if(getNumberOfSongsSelectedToday() == 3) {
			return false;
		}
		return true;
	}
	
	/********************************************************
	 * public void resetSongsSelected() 
	 * This method determines if the saved date is the current
	 * date. if it is not, resets the number of songs selected
	 * to zero.
	 ********************************************************/
	public void resetSongsSelected() {
		if(!date.equals(LocalDate.now())) {
			this.numberOfSongsSelectedToday = 0;
			this.date = LocalDate.now();
		}
	}
	
	/********************************************************
	 * public void songSelect() 
	 * updates number of songs selected today and decreases
	 * the students time remaining based on the song length
	 * that was selected
	 ********************************************************/
	public void songSelect(Song song) {
		this.numberOfSongsSelectedToday++;
		this.secondsRemaining -= song.getSongLength();
	}
	
	/********************************************************
	 * public boolean getStudentName() 
	 * used for J-Unit Testing
	 ********************************************************/
	public void setDate(LocalDate date) {
		this.date = date;
	}

	/********************************************************
	 * public boolean getStudentName() 
	 * get studentName
	 ********************************************************/
	public String getStudentName() {
		return studentName;
	}

	/********************************************************
	 * public boolean getStudentPassword() 
	 * get studentPassword
	 ********************************************************/
	public String getStudentPassword() {
		return studentPassword;
	}

	/********************************************************
	 * public boolean getNumberOfSongsSelectedToday() 
	 * get numberOfSongsSelectedToday
	 ********************************************************/
	public int getNumberOfSongsSelectedToday() {
		return numberOfSongsSelectedToday;
	}

	/********************************************************
	 * public boolean getSecondsRemaining()
	 * get seconds remaining on student account
	 ********************************************************/
	public int getSecondsRemaining() {
		return secondsRemaining;
	}
	
	public boolean isAdmin() {
		return admin;
	}

	private void setAdmin(boolean admin) {
		this.admin = admin;
		if(admin) {
			this.administrator = "Yes";
		}
		
	}
}
