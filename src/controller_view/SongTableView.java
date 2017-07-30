package controller_view;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.JukeBox;
import model.Song;

public class SongTableView extends TableView{
	
	/********************************************************
	 * SongViewTable GLOBALS
	 ********************************************************/
	private ObservableList<Song> songs;
	private JukeBox jukeBox;
	
	ArrayList<Song> songList;
	
	public SongTableView() {
		
		/********************************************************
		 * Columns to table
		 ********************************************************/
		TableColumn<Song, Integer> timesPlayedToday = new TableColumn<Song, Integer>("Plays");
		TableColumn<Song, String> songName = new TableColumn<Song, String>("Title");
		TableColumn<Song, String> songArtist = new TableColumn<Song, String>("Artist");
		TableColumn<Song, Integer> songLength = new TableColumn<Song, Integer>("Time");
		
		this.getColumns().addAll(timesPlayedToday,songName,songArtist,songLength);
		
		timesPlayedToday.setCellValueFactory(new PropertyValueFactory<Song, Integer>("timesPlayedToday"));
		songName.setCellValueFactory(new PropertyValueFactory<Song, String>("songName"));
		songArtist.setCellValueFactory(new PropertyValueFactory<Song, String>("songArtist"));
		songLength.setCellValueFactory(new PropertyValueFactory<Song, Integer>("songLength"));
		
		/********************************************************
		 * Initializing
		 ********************************************************/
	
		jukeBox = new JukeBox();		
		songList = jukeBox.getSongList();
		songs = FXCollections.observableArrayList(songList);
		
		this.setItems(songs);
	}
}
