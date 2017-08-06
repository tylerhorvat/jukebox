package controller_view;

import java.io.Serializable;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.JukeBox;
import model.Student;

public class UserView extends TableView implements Serializable {

	/********************************************************
	 * SongViewTable GLOBALS
	 ********************************************************/
	private static ObservableList<Student> student;
	static ArrayList<Student> studentList;
	
	@SuppressWarnings("unchecked")
	public UserView() {
		
		/********************************************************
		 * Columns to table
		 ********************************************************/
		TableColumn<Student, String> studentName = new TableColumn<Student, String>("Name");
		TableColumn<Student, String> studentPassword = new TableColumn<Student, String>("Password");
		TableColumn<Student, String> admin = new TableColumn<Student, String>("Admin");
		
		
		this.getColumns().addAll(studentName, studentPassword, admin);
		
		studentName.setCellValueFactory(new PropertyValueFactory<Student, String>("studentName"));
		studentPassword.setCellValueFactory(new PropertyValueFactory<Student, String>("studentPassword"));
		admin.setCellValueFactory(new PropertyValueFactory<Student, String>("administrator"));
		
		
		/********************************************************
		 * Initializing
		 ********************************************************/
	
		//JukeBox jukeBox = new JukeBox();		
		studentList = JukeBox.getUsers();
		student = FXCollections.observableArrayList(studentList);

		this.setItems(student);
	}
}
