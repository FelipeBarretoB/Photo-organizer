package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.util.ArrayList;

import thread.ImportFileThread;

public class Organizer {
	private String name;
	private ArrayList<User> users;
	private User actualUser;
	private Type type;//por usar TODO creo no se uso
	private Organized organized;//por usar
	private Files files;//por usar TODO creo que no se usa 
	private ImportFileThread importFileThread;

	public Organizer(String n) {
		this.name = n;
		this.users = new ArrayList<User>();
	}

	public int findUser(String name){
		int x = -1;
		if(users.size()!= 0) {
			for(int i = 0; i<users.size(); i++) {
				if(users.get(i).getName().equals(name)) {
					x = i;
				}
			}
		}
		return x;
	}

	public void addUser(String n, String p) {
		User user = new User(n,p);
		getUsers().add(user);
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	public User getActualUser() {
		return actualUser;
	}

	public void setActualUser(User actualUser) {
		this.actualUser = actualUser;
	}

	public void callImportFile(File file) {
		importFileThread = new ImportFileThread(this, file);
		importFileThread.start();
	}
	
	
	public void addFile(File file) {
		Date d= new Date(file.lastModified());
		files = new Files(file.getName(), ""+file.length(),d.toString(),file ,foldersIn(file),filesIn(file), file.getPath());
		try {
			files.addPhotos();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("added: "+file.getName());
		File[] fileList=file.listFiles();
		for(int c=0;c<fileList.length;c++) {
			if(fileList[c].list()!=null) {
				addFile(files.getNext(),fileList[c]);
			}
		}
	}

	private int foldersIn(File file) {
		File files[]= file.listFiles();
		int i=0;
		for(int c =0; c<files.length;c++) {
			if(files[c].list()!=null) {
				i++;
			}
		}
		return i;
	}

	private int filesIn(File file) {
		File files[]= file.listFiles();
		int i=0;
		for(int c =0; c<files.length;c++) {
			if(files[c].list()==null) {
				i++;
			}
		}
		return i;
	}

	private void addFile(Files currentFile,File file) {
		if(currentFile==null) {
			//TODO 
			System.out.println("added: "+file.getName());
			Date d= new Date(file.lastModified());
			currentFile = new Files(file.getName(), ""+file.length(),d.toString(),file ,foldersIn(file),filesIn(file), file.getPath());
			try {
				currentFile.addPhotos();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			File[] fileList=file.listFiles();
			for(int c=0;c<fileList.length;c++) {
				if(fileList[c].list()!=null) {
					addFile(files.getNext(),fileList[c]);
				}
			}
		}
	}
	
	public void getAllPhotosInFiles() {
		files.getPhoto();
	}
	

}
