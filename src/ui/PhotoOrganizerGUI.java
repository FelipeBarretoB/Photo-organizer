package ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.Organizer;
import model.User;

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
	private ComboBox<String> cBorganizeOptions;

	@FXML
	private TextField txtNameForFolder;

	@FXML
	private Label lblUserName;

	@FXML
	private ComboBox<String> cbOrganizedFiles;

	@FXML
	private TextArea txtPhotosNames;

	@FXML
	private Label lblName;

	@FXML
	private Label lblSize;

	@FXML
	private Label lblnOO;

	@FXML
	private Label lblUser;



	@FXML
	public void loginUser(ActionEvent event) {
		System.out.println(organizer.getUsers().size());
		if(!txtUserName.getText().equals("") && !txtPassword.getText().equals("")) {
			User loggedUser = organizer.findUser(txtUserName.getText());
			if(loggedUser != null) {
				if(loggedUser.getPassword().equals(txtPassword.getText())) {
					organizer.setActualUser(loggedUser);
					labConfirmLogin.setText("Logeado correctamente como " + loggedUser.getName());
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
	public void createUser(ActionEvent event) throws IOException {
		if(txtCreateUserName.getText() != null && txtCreatePassword.getText() != null
				&& txtConfirmPassword.getText() != null) {
			if(txtConfirmPassword.getText().equals(txtCreatePassword.getText())) {
				if(organizer.findUser(txtCreateUserName.getText()) == null) {
					organizer.addUser(txtCreateUserName.getText(),txtCreatePassword.getText());
					loadLoginPage();
				}else {
					labConfirmNewUser.setText("El usuario ya existe"); 
				}
			}else {
				labConfirmNewUser.setText("Las contraseñas no coinciden");
			}
		}else {
			labConfirmNewUser.setText("Por favor llene todos los espacios");
		}
	}

	public void loadMainPage(){
		load("menu-page.fxml");
	}

	public void loadLoginPage() {
		try {
			organizer.loadUsers();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		load("logIn-pane.fxml");
	}

	@FXML
	public void loadOrganizeFolder(ActionEvent event) {
		try {
			organizer.loadOrganized();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cBorganizeOptions= new ComboBox<>();
		load("Organize-Folder.fxml");
		cBorganizeOptions.getItems().add("Fecha");
		cBorganizeOptions.getItems().add("Tipo");
		cBorganizeOptions.getItems().add("Tamaño");
		cBorganizeOptions.getItems().add("Nombre");
		cBorganizeOptions.getItems().add("Resolución");
		if(organizer.getActualUser()!=null) {
			lblUserName.setText(organizer.getActualUser().getName());
		}	
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
		cbOrganizedFiles= new ComboBox<String>();
		try {
			organizer.loadOrganized();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		load("manage-files.fxml");
		ArrayList<String> cbStrings= organizer.getOrganizedNames();
		for(int c=0;c<cbStrings.size();c++) {
			cbOrganizedFiles.getItems().add(cbStrings.get(c));
		}
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
		organizer.callImportFile(selectedDirectory);

	}

	@FXML
	void btnOrganize(ActionEvent event) {
		if(!organizer.checkIfRunning()) {
			if(cBorganizeOptions.getSelectionModel().getSelectedItem()!="" && organizer.getFiles()!=null) {
				if(organizer.getActualUser()!=null) {
					organizer.callOrganizeThread(organizer.getActualUser(),txtNameForFolder.getText(),  cBorganizeOptions.getSelectionModel().getSelectedItem());
				}else {
					organizer.callOrganizeThread(null,txtNameForFolder.getText(),  cBorganizeOptions.getSelectionModel().getSelectedItem());
				}
			}
		}else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Alerta");
			alert.setHeaderText("Aun esta cargando el archivo");
			alert.showAndWait();
		}
	}

	@FXML
	public void cbOrganizedFilesUpdate(ActionEvent event) throws FileNotFoundException {
		
		txtPhotosNames.setText(organizer.getOrganizedByName(cbOrganizedFiles.getSelectionModel().getSelectedItem()).getAllPhotosNames());
		lblName.setText(organizer.getOrganizedByName(cbOrganizedFiles.getSelectionModel().getSelectedItem()).getName());
		lblSize.setText(organizer.getOrganizedByName(cbOrganizedFiles.getSelectionModel().getSelectedItem()).getSize());
		lblnOO.setText(organizer.getOrganizedByName(cbOrganizedFiles.getSelectionModel().getSelectedItem()).getNumOfOrga()+"");
		if(organizer.getOrganizedByName(cbOrganizedFiles.getSelectionModel().getSelectedItem()).getCreatedUser()!=null)
			lblUser.setText(organizer.getOrganizedByName(cbOrganizedFiles.getSelectionModel().getSelectedItem()).getName());
		else
			lblUser.setText("Anonimo");
	}
	
    @FXML
    void btnRecreateOrga(ActionEvent event) {
    	
    	if(cbOrganizedFiles.getSelectionModel().getSelectedItem()!=null) {
    		try {
				organizer.createFileAgain(organizer.getOrganizedByName(cbOrganizedFiles.getSelectionModel().getSelectedItem()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}else {
    		Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Alerta");
			alert.setHeaderText("Debe seleccionar un archivo");
			alert.showAndWait();
    	}
    }
    
    @FXML
    void btnDeleteOrganized(ActionEvent event) {
    	try {
			organizer.removeOrganized(cbOrganizedFiles.getSelectionModel().getSelectedItem());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
