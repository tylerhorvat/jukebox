package tests;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

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

}
