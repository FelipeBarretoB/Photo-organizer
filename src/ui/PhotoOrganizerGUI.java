package ui;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public class PhotoOrganizerGUI {
	
	private Pane mainPane;
	
	
	public void loadMainPage(){
		/*try {
			FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("main-page.fxml"));
			fxmlLoader.setController(this);
			Parent login;
			login = fxmlLoader.load();
			mainPane.getChildren().setAll(login);
		} catch (IOException e) {
			e.printStackTrace();
		} */
	}
	
	@FXML
	public void loadOrganizeFolder(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource("Organize-Folder.fxml"));
			fxmlLoader.setController(this);
			Parent login;
			login = fxmlLoader.load();
			mainPane.getChildren().setAll(login);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
