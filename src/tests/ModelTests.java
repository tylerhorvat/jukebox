/*
 * Class: ModelTests.java
 * Project: JukeBox
 * CSC 335 July 25, 2017
 * Authors: Hayden Monarch
 * 			Tyler Horvat
 * 
 * This class implements JUnit testing to test methods within the model package of JukeBox
 */

package tests;

import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.Test;
import model.JukeBox;
import model.Song;
import model.Student;

public class ModelTests {

	@Test
	public void userSelectSongThreeTimes() {
		JukeBox jukebox = JukeBox.getJukeBox();
		ArrayList<Song> songs = jukebox.getSongList();
		Song theSong = songs.get(0);
		Student student = new Student("Chris", "1");
		assertTrue(student.canSelectSong());
		student.songSelect(theSong);
		assertTrue(student.canSelectSong());
		assertEquals(89995, student.getSecondsRemaining());
		student.songSelect(theSong);
		assertTrue(student.canSelectSong());
		assertEquals(89990, student.getSecondsRemaining());
		student.songSelect(theSong);
		assertFalse(student.canSelectSong());
		assertEquals(89985, student.getSecondsRemaining());		
		
		student.setDate(LocalDate.now().minusDays(1));
		assertTrue(student.canSelectSong());
		
		student.songSelect(theSong);
		assertTrue(student.canSelectSong());
		assertEquals(89980, student.getSecondsRemaining());
	}
	
	@Test
	public void playSongThreeTimesOnSameDay() {
		Song song = new Song("Pokemon Capture", 5, "Pikachu", "Capture.mp3");
		assertEquals(0, song.getTimesPlayedToday());
		assertTrue(song.canBePlayed());
		song.select();
		assertTrue(song.canBePlayed());
		song.select();
		assertTrue(song.canBePlayed());
		song.select();
		assertFalse(song.canBePlayed());
		assertEquals(3, song.getTimesPlayedToday());			
	}
	
	@Test
	public void playSongThreeTimesOnDifferentDays() {
		Song song = new Song("Pokemon Capture", 5, "Pikachu", "Capture.mp3");
		song.select();
		song.select();
		song.select();
		assertFalse(song.canBePlayed());
		song.setDate(LocalDate.now().minusDays(1));
		assertTrue(song.canBePlayed());
	}
	
	@Test
	public void locateUserTest() {
		JukeBox jukebox = JukeBox.getJukeBox();
		assertEquals(-1, jukebox.locateUser("chris"));
		assertEquals(-1, jukebox.locateUser("devon"));
		assertEquals(-1, jukebox.locateUser("river"));
		assertEquals(-1, jukebox.locateUser("ryan"));
	}
	
	@Test
	public void authenticateUserTest() {
		JukeBox jukebox = JukeBox.getJukeBox();
		assertTrue(jukebox.authenticateUser("Chris", "1"));
		assertTrue(jukebox.authenticateUser("Devon", "22"));
		assertTrue(jukebox.authenticateUser("River", "333"));
		assertTrue(jukebox.authenticateUser("Ryan", "4444"));
		assertFalse(jukebox.authenticateUser("chris", "1"));
		assertFalse(jukebox.authenticateUser("devon", "22"));
		assertFalse(jukebox.authenticateUser("river", "333"));
		assertFalse(jukebox.authenticateUser("ryan", "4444"));
		assertFalse(jukebox.authenticateUser("Chris", "22"));
		assertFalse(jukebox.authenticateUser("Devon", "1"));
		assertFalse(jukebox.authenticateUser("Ryan", "333"));
		assertFalse(jukebox.authenticateUser("River", "4444"));
	}
	
	@Test
	public void getSongInfo() {
		JukeBox jukebox = JukeBox.getJukeBox();
		ArrayList<Song> songs = jukebox.getSongList();
		Song songCheck = songs.get(0);
		assertEquals("Pokemon Capture", songCheck.getSongName());
		assertEquals(5, songCheck.getSongLength());
		assertEquals("Pikachu", songCheck.getSongArtist());
		assertEquals("Capture.mp3", songCheck.getFileName());
		jukebox.addSongToQueue(songCheck);
		if(!jukebox.isPlaying()) {
			Thread thread = new Thread(jukebox);
			thread.start();
		}
	}
	
	@Test
	public void getStudentInfo() {
		JukeBox jukebox = JukeBox.getJukeBox();
		ArrayList<Student> students = jukebox.getUsers();
		Student studentCheck = students.get(0);
		assertEquals(90000, studentCheck.getSecondsRemaining());
		assertEquals(0, studentCheck.getNumberOfSongsSelectedToday());
	}
}
