package ui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public class PhotoOrganizerGUI {
	
	@FXML
    private Pane mainPane;
	
	public void loadMainPage(){
		load("menu-page.fxml");
	}
	
	@FXML
	public void loadOrganizeFolder(ActionEvent event) {
		load("Organize-Folder.fxml");
	}
	@FXML
    void loadCustomizePreferences(ActionEvent event) {
		load("customise-preferences.fxml");
    }
	@FXML
    void backToOrganizeFolder(ActionEvent event) {
		load("Organize-Folder.fxml");
    }
	@FXML
    void loadCompareFolders(ActionEvent event) {
		load("compare-folders.fxml");
    }
	@FXML
    void loadManageFolders(ActionEvent event) {
		load("manage-files.fxml");
    }

    @FXML
    void loadMenuPage(ActionEvent event) {
    	load("menu-page.fxml");
    }
    
	public void load(String  page) {
		try {
			FXMLLoader fxmlLoader= new FXMLLoader(getClass().getResource(page));
			fxmlLoader.setController(this);
			Parent login;
			login = fxmlLoader.load();
			mainPane.getChildren().setAll(login);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
