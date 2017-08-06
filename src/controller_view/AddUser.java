package controller_view;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.JukeBox;
import model.Student;

import controller_view.AlertMe;

public class AddUser {

	UserView users;
	Boolean admin = null;
	
	public AddUser() {
		Stage window = new Stage();
		window.setTitle("Add User");
		window.setMinWidth(400);
		window.setMinHeight(400);
		window.initModality(Modality.APPLICATION_MODAL);
		
		GridPane layout = new GridPane();
		Label instruction = new Label("Current Users:                                                                   ");
		instruction.setTextFill(Color.DARKSLATEBLUE);
		
		users = new UserView();
		
		Button close = new Button("Close");
		Button addUser = new Button("Add");
		close.setOnAction(e -> window.close());
		
		
		layout.add(new Label("    "), 0, 0);
		layout.add(new Label(" "), 3, 0);
		layout.add(instruction, 1, 1);
		layout.add(users, 1, 2);
		
		layout.setVgap(10);
		layout.setHgap(10);
		
		GridPane fields = new GridPane();
		Label name = new Label("      New user name: ");
		Label password = new Label("New user password: ");
		TextField nameField = new TextField();
		TextField passwordField = new TextField();
		addUser.setOnAction(e -> addUser(nameField, passwordField));
		
		nameField.setMaxWidth(100);
		passwordField.setMaxWidth(100);
		fields.add(new Label("             "), 0, 0);
		
		fields.add(name, 1, 1);
		fields.add(nameField, 2, 1);
		fields.add(password, 1, 2);
		fields.add(passwordField, 2, 2);
		
		fields.setHgap(10);
		fields.setVgap(10);
		
		
		GridPane radioButtons = new GridPane();
	    
		Label accountType = new Label("Account type:\n");
        RadioButton admin = new RadioButton("Administrative account");
        RadioButton user = new RadioButton("Regular account");
        
        admin.setOnAction(new ButtonHandler());
        user.setOnAction(new ButtonHandler());
        
        radioButtons.add(new Label("           "), 0, 0);
        radioButtons.add(accountType, 1, 1);
        radioButtons.add(user, 1, 2);
        radioButtons.add(admin, 2, 2);
        radioButtons.setHgap(10);
        
        ToggleGroup radioGroup = new ToggleGroup();
        
        admin.setToggleGroup(radioGroup);
        user.setToggleGroup(radioGroup);
		
		GridPane buttons = new GridPane();
		buttons.add(addUser, 1, 2);
		buttons.add(close, 2, 2);
		buttons.add(new Label("                                  "), 0, 3);
		buttons.setVgap(10);
		buttons.setHgap(10);
		
		BorderPane addPanel = new BorderPane();
		addPanel.setTop(radioButtons);
		addPanel.setCenter(fields);
		addPanel.setBottom(buttons);
		BorderPane pane = new BorderPane();
		pane.setTop(layout);
		pane.setCenter(addPanel);

		
		Scene scene = new Scene(pane);
		window.setScene(scene);
		window.showAndWait();
	}

	private void addUser(TextField nameField, TextField passwordField) {
		
		String name = nameField.getText();
		String password = passwordField.getText();
		name.trim();
		password.trim();
		
		if(name.isEmpty()) {
			AlertMe alert = new AlertMe("You must enter a user name!");
		}
		else if(password.isEmpty()) {
			AlertMe alert = new AlertMe("You must enter a user password!");
		}
		else if(admin == null) {
			AlertMe alert = new AlertMe("You must select an account type!");
		}
		else {
			ArrayList<Student> students = JukeBox.getUsers();
			
			if(JukeBox.locateUser(name) != -1) {
				AlertMe alert = new AlertMe("User " + name + " already exists!");
			}
			else {
				Student newStudent;
				if(admin) {
					newStudent = new Student(name, password, true);
				}
				else {
					newStudent = new Student(name, password, false);
				}
				students.add(newStudent);
				
				
				nameField.setText("");
				passwordField.setText("");
				
				//users.refresh();
				users.setItems(null); 
				users.layout(); 

				ObservableList<Student> data = FXCollections.observableArrayList(JukeBox.getUsers());
				users.setItems(data);
			}
			
		}
	}
	
	class ButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			RadioButton button = (RadioButton) arg0.getSource();
			String str = button.getText();
			
			if(str.equals("Regular account"))
				admin = false;
			else {
				admin = true;
			}
			
		}
		
	}
}
