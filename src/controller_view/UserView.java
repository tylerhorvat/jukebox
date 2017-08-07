/*
 * Class: UserView.java
 * Project: JukeBox
 * CSC 335 August 6, 2017
 * Authors: Hayden Monarch
 * 			Tyler Horvat
 * 
 * This class implements a table view used with AddUser.java and RemoveUser.java.
 * It allows an administrator to see a current list of users while adding and
 * removing user accounts.
 */

package controller_view;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.JukeBox;
import model.Student;

@SuppressWarnings("rawtypes")
public class UserView extends TableView {

	/********************************************************
	 * UserView GLOBALS
	 ********************************************************/
	private ObservableList<Student> student;
	ArrayList<Student> studentList;
	
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
		studentList = JukeBox.getUsers();
		student = FXCollections.observableArrayList(studentList);

		this.setItems(student);
	}

	public ArrayList<Student> getStudentList() {
		return studentList;
	}
}
