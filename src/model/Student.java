/*
 * Class: Student.java
 * Project: JukeBox
 * CSC 335 July 25, 2017
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

	/*Globals for Student class*/
	private String studentName;
	private String studentPassword;
	private int numberOfSongsSelectedToday;
	private int secondsRemaining;
	private LocalDate date;
	
	//Student constructor
	public Student(String name, String password) {
		this.studentName = name;
		this.studentPassword = password;
		this.secondsRemaining = 90000;
		this.numberOfSongsSelectedToday = 0;
		this.date = LocalDate.now();
	}
	
	//Determines if the Student can select more songs for the day
	//returns true if the Student can, otherwise false
	public boolean canSelectSong() {
		resetSongsSelected();
		if(getNumberOfSongsSelectedToday() == 3) {
			return false;
		}
		return true;
	}
	
	//This method determines if the saved date is the current
	//date. if it is not, resets, the number of songs selected
	//to zero.
	public void resetSongsSelected() {
		if(!date.equals(LocalDate.now())) {
			this.numberOfSongsSelectedToday = 0;
			this.date = LocalDate.now();
		}
	}

	//updates number of songs selected today and decreases
	//the students time remaining based on the song length
	//that was selected
	public void songSelect(Song song) {
		this.numberOfSongsSelectedToday++;
		this.secondsRemaining -= song.getSongLength();
	}
	
	//this method sets the date. used strictly for JUnit testing
	public void setDate(LocalDate date) {
		this.date = date;
	}

	//get student name
	public String getStudentName() {
		return studentName;
	}

	//get student password
	public String getStudentPassword() {
		return studentPassword;
	}

	//get number of songs selected today
	public int getNumberOfSongsSelectedToday() {
		return numberOfSongsSelectedToday;
	}

	//get seconds remaining on student account
	public int getSecondsRemaining() {
		return secondsRemaining;
	}
}
