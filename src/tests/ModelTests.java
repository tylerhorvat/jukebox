package tests;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import model.JukeBox;
import model.Song;

public class ModelTests {

	@Test
	public void playSongThreeTimesOnSameDay() {
		Song song = new Song("Pokemon Capture", 5, "Pikachu", "Capture.mp3");
		assertEquals(0, song.getTimesPlayedToday());
		assertTrue(song.canBePlayed());
		song.play();
		assertTrue(song.canBePlayed());
		song.play();
		assertTrue(song.canBePlayed());
		song.play();
		assertFalse(song.canBePlayed());
		assertEquals(3, song.getTimesPlayedToday());			
	}
	
	@Test
	public void playSongThreeTimesOnDifferentDays() {
		Song song = new Song("Pokemon Capture", 5, "Pikachu", "Capture.mp3");
		song.play();
		song.play();
		song.play();
		assertFalse(song.canBePlayed());
		song.setDate(LocalDate.now().minusDays(1));
		assertTrue(song.canBePlayed());
	}
	
	@Test
	public void authenticateUserTest() {
		JukeBox jukebox = new JukeBox();
		assertTrue(jukebox.authenticateUser("Chris", "1"));
		assertTrue(jukebox.authenticateUser("Devon", "22"));
		assertTrue(jukebox.authenticateUser("River", "333"));
		assertTrue(jukebox.authenticateUser("Ryan", "4444"));
		assertFalse(jukebox.authenticateUser("chris", "1"));
	}

}
