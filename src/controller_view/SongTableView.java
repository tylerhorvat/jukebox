/*
 * Class: SongTableView.java
 * Project: JukeBox
 * CSC 335 August 6, 2017
 * Authors: Hayden Monarch
 * 			Tyler Horvat
 * 
 * This class implements a TableView for the GUI.
 */

package controller_view;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.JukeBox;
import model.Song;

@SuppressWarnings("rawtypes")
public class SongTableView extends TableView {
	
	/********************************************************
	 * SongViewTable GLOBALS
	 ********************************************************/
	private ObservableList<Song> songs;
	private JukeBox jukeBox;
	
	ArrayList<Song> songList;
	
	@SuppressWarnings("unchecked")
	public SongTableView() {
		
		/********************************************************
		 * Columns to table
		 ********************************************************/
		TableColumn<Song, Integer> timesPlayedToday = new TableColumn<Song, Integer>("Plays");
		TableColumn<Song, String> songName = new TableColumn<Song, String>("Title");
		TableColumn<Song, String> songArtist = new TableColumn<Song, String>("Artist");
		TableColumn<Song, String> songLength = new TableColumn<Song, String>("Time");
		
		this.getColumns().addAll(timesPlayedToday,songName,songArtist,songLength);
		
		timesPlayedToday.setCellValueFactory(new PropertyValueFactory<Song, Integer>("timesPlayedToday"));
		songName.setCellValueFactory(new PropertyValueFactory<Song, String>("songName"));
		songArtist.setCellValueFactory(new PropertyValueFactory<Song, String>("songArtist"));
		songLength.setCellValueFactory(new PropertyValueFactory<Song, String>("length"));
		
		/********************************************************
		 * Initializing
		 ********************************************************/
	
		jukeBox = JukeBox.getJukeBox();		
		songList = jukeBox.getSongList();
		songs = FXCollections.observableArrayList(songList);
		
		this.setItems(songs);
	}
}
