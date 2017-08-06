package controller_view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.JukeBox;
import model.Student;

import java.util.ArrayList;

import controller_view.AlertMe;

public class RemoveUser {

	UserView users;
	
	public RemoveUser() {
		Stage window = new Stage();
		window.setTitle("Delete User");
		window.setMinWidth(400);
		window.setMinHeight(400);
		window.initModality(Modality.APPLICATION_MODAL);
		
		GridPane layout = new GridPane();
		Label instruction = new Label("Select user you wish to delete, then click delete button");
		instruction.setTextFill(Color.DARKSLATEBLUE);
		users = new UserView();
		
		
		
		Button cancel = new Button("Close");
		Button deleteUser = new Button("Delete");
		cancel.setOnAction(e -> window.close());
		deleteUser.setOnAction(e -> deleteUser());
		
		layout.add(new Label("   "), 0, 0);
		layout.add(new Label(" "), 3, 0);
		layout.add(instruction, 1, 1);
		layout.add(users, 1, 2);
		
		layout.setVgap(10);
		layout.setHgap(10);
		
		GridPane buttons = new GridPane();
		buttons.add(new Label("                          "), 0, 0);
		buttons.add(deleteUser, 1, 1);
		buttons.add(cancel, 2, 1);
		buttons.add(new Label("  "), 0, 3);
		buttons.setVgap(10);
		buttons.setHgap(10);
		
		BorderPane pane = new BorderPane();
		pane.setCenter(layout);
		pane.setBottom(buttons);

		
		Scene scene = new Scene(pane);
		window.setScene(scene);
		window.showAndWait();
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private void deleteUser() {
		
		Student studentClicked = (Student) users.getSelectionModel().getSelectedItem();
		
		if(studentClicked == null) {
			AlertMe alert = new AlertMe("Must select user to delete!");
		}
		else if(studentClicked.getStudentName().equals(Iteration1Controller.getCurrentUser().getStudentName())) {
			AlertMe alert = new AlertMe("You can't delete yourself!");
		}
		else {
			
			Stage window = new Stage();
			window.setTitle("Confirm Delete");
			window.setMinWidth(400);
			window.setMinHeight(150);
			window.initModality(Modality.APPLICATION_MODAL);
			
			Label message = new Label("\n       Are you sure you want to delete " + studentClicked.getStudentName() + "'s account?\n");
			Button ok = new Button("Do it");
			Button cancel = new Button("Cancel");
			
			ok.setOnAction(e -> {
				window.close();
				ArrayList<Student> students = JukeBox.getUsers();
				students.remove(studentClicked);
				
				users.setItems(null); 
				users.layout(); 

				ObservableList<Student> data = FXCollections.observableArrayList(JukeBox.getUsers());
				users.setItems(data);
			});
			
			cancel.setOnAction(e -> window.close());
			
			GridPane buttons = new GridPane();
			buttons.add(ok, 1, 1);
			buttons.add(cancel, 2, 1);
			buttons.add(new Label("                          "), 0, 0);
			buttons.add(new Label("  "), 3, 3);
			buttons.setHgap(10);
			buttons.setVgap(10);
			
			BorderPane pane = new BorderPane();
			message.setAlignment(Pos.CENTER);
			pane.setTop(message);
			pane.setCenter(buttons);
			
			Scene scene = new Scene(pane);
			window.setScene(scene);
			window.showAndWait();
			
			
			
		}
	}
}
