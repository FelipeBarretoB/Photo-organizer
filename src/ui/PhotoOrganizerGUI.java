package ui;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.Organizer;

public class PhotoOrganizerGUI {
	
	private Organizer organizer;
	
	public PhotoOrganizerGUI(){
		organizer = new Organizer("Photo Organizer");
	}
	
	@FXML
    private Pane mainPane;
	
	@FXML
    private Label labConfirmLogin;
	
	@FXML
    private Label labConfirmNewUser;
	
	@FXML
    private TextField txtUserName;

    @FXML
    private PasswordField txtPassword;
    
    @FXML
    private PasswordField txtCreatePassword;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private TextField txtCreateUserName;
    
    @FXML
    private TextField txtFilePath;

    @FXML
    public void loginUser(ActionEvent event) {
    	int loggedUser;
    	if(!txtUserName.getText().equals("") && !txtPassword.getText().equals("")) {
    		loggedUser = organizer.findUser(txtUserName.getText());
    		if(loggedUser >= 0) {
    			if(organizer.getUsers().get(loggedUser).getPassword().equals(txtPassword.getText())) {
					organizer.setActualUser(organizer.getUsers().get(loggedUser));
					labConfirmLogin.setText("Logeado correctamente como " + organizer.getUsers().get(loggedUser).getName());
				}else {
					labConfirmLogin.setText("El usuario no existe");
				}
    		}else {
				labConfirmLogin.setText("El usuario no existe");
			}
    	}else {
    		labConfirmLogin.setText("Por favor llene todos los espacios");
    	}
    	
    }

    @FXML
    public void openCreateUser(ActionEvent event) {
    	load("createUser-page.fxml");
    }

    @FXML
    public void createUser(ActionEvent event) {
    	if(txtCreateUserName.getText() != null && txtCreatePassword.getText() != null
    		&& txtConfirmPassword.getText() != null) {
    		if(txtConfirmPassword.getText().equals(txtCreatePassword.getText())) {
				if(organizer.findUser(txtCreateUserName.getText()) < 0) {
					organizer.addUser(txtCreateUserName.getText(),txtCreatePassword.getText());
					loadLoginPage();
				}else {
					labConfirmNewUser.setText("El usuario ya existe"); 
				}
    		}
    	}else {
    		labConfirmNewUser.setText("Por favor llene todos los espacios");
    	}
    }
	
	public void loadMainPage(){
		load("menu-page.fxml");
	}
	
	public void loadLoginPage(){
		load("logIn-pane.fxml");
	}
	
	@FXML
	public void loadOrganizeFolder(ActionEvent event) {
		load("Organize-Folder.fxml");
	}
	@FXML
	public void loadCustomizePreferences(ActionEvent event) {
		load("customise-preferences.fxml");
    }
	@FXML
	public void backToOrganizeFolder(ActionEvent event) {
		load("Organize-Folder.fxml");
    }
	@FXML
	public void loadCompareFolders(ActionEvent event) {
		load("compare-folders.fxml");
    }
	@FXML
	public void loadManageFolders(ActionEvent event) {
		load("manage-files.fxml");
    }

    @FXML
    public void loadMenuPage(ActionEvent event) {
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
	
	// TODO añadir al diagrama
	//Organize-folder
    @FXML
    public void btnLookForFile(ActionEvent event) {
    	DirectoryChooser directoryChooser = new DirectoryChooser();
    	Stage stage=new Stage();
    	File selectedDirectory = directoryChooser.showDialog(stage);
    	txtFilePath.setText(selectedDirectory.getPath());
    	
    }
	
}
