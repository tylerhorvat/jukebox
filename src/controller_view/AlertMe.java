/*
 * Class: AlertMe.java
 * Project: JukeBox
 * CSC 335 August 6, 2017
 * Authors: Hayden Monarch
 * 			Tyler Horvat
 * 
 * This class implements an Alert object that displays an alert when created.
 */

package controller_view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertMe {
	public AlertMe(String message) {
		Stage window = new Stage();
		window.setTitle("Message");
		window.setMinWidth(250);
		window.setMinHeight(150);
		window.initModality(Modality.APPLICATION_MODAL);
		
		Label label = new Label();
		label.setText(message);
		Button closeButton = new Button("Okay, got it!");
		closeButton.setOnAction(e -> window.close());
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, closeButton);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
}
