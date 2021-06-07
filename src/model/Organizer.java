package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.util.ArrayList;

import thread.GetAllPhotosThread;
import thread.ImportFileThread;

public class Organizer {
	private String name;
	private ArrayList<User> users;
	private User actualUser;
	private Type type;//por usar TODO creo no se uso
	private Organized organized;//por usar
	private Files files;//por usar TODO creo que no se usa 
	private ImportFileThread importFileThread;
	private Photo allPhotos;
	private GetAllPhotosThread getPhotosThread;

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
		allPhotos=null;
		importFileThread = new ImportFileThread(this, file);
		importFileThread.start();
		getPhotosThread= new GetAllPhotosThread(this);
	}
	
	public Files getFiles() {
		return files;
	}


	public void startAddFile(Files files,File file) {
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
				addFile(files,fileList[c]);
			}
		}
		System.out.println("end");
		
		
		this.files=files;
		getPhotosThread.start();
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
		if(currentFile.getNext()==null) {
			//TODO 
			System.out.println("added: "+file.getName());
			Date d= new Date(file.lastModified());
			currentFile.setNext(new Files(file.getName(), ""+file.length(),d.toString(),file ,foldersIn(file),filesIn(file), file.getPath()));

			try {
				currentFile.getNext().addPhotos();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			File[] fileList=file.listFiles();
			for(int c=0;c<fileList.length;c++) {
				if(fileList[c].list()!=null) {
					addFile(currentFile.getNext(),fileList[c]);
				}
			}
		}else {
			addFile(currentFile.getNext(),file);
		}
		
	}
	//TODO 
	int c=0;
	public void getAllPhotosInFiles() {
		System.out.println("pog 1 ");
		if(allPhotos==null && files!=null) {
			allPhotos=files.getPhoto();
			System.out.println("a"+c++);
			getAllPhotosInFiles(files.getNext(), allPhotos);
		}
			
	}
	
	private void getAllPhotosInFiles(Files currentFile, Photo photo) {
		if(currentFile!=null) {
			if(photo==null) {
				photo=currentFile.getPhoto();
				System.out.println("added");
				getAllPhotosInFiles(currentFile.getNext(), photo);
			}else {
				System.out.println("b2"+c++);
				getAllPhotosInFiles(currentFile, photo.getNextPhoto());
			}
		}
	}


}
