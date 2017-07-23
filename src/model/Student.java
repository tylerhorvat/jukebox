package model;

import java.time.LocalDate;

public class Student {

	private String studentName;
	private String studentPassword;
	private int numberOfSongsPlayedToday;
	private int secondsRemaining;
	private LocalDate date;
	
	public Student(String name, String password) {
		this.studentName = name;
		this.studentPassword = password;
		this.secondsRemaining = 90000;
		this.numberOfSongsPlayedToday = 0;
	}
	
	

	public String getStudentName() {
		return studentName;
	}

	public String getStudentPassword() {
		return studentPassword;
	}

	public int getNumberOfSongsPlayedToday() {
		return numberOfSongsPlayedToday;
	}

	public int getSecondsRemaining() {
		return secondsRemaining;
	}
	
	
}
