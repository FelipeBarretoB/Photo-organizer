package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import thread.GetAllPhotosThread;
import thread.ImportFileThread;

public class Organizer {
	private String name;
	private UsersTree users;
	private User actualUser;
	private Type type;//por usar TODO creo no se uso
	private Organized organized;//por usar
	private Files files;//por usar TODO creo que no se usa 
	private ImportFileThread importFileThread;

	private Photo allPhotos;
	private GetAllPhotosThread getPhotosThread;

	private ArrayList<User> usersList;


	public Organizer(String n) {
		this.usersList = new ArrayList<User>();
		this.name = n;
		this.users = new UsersTree();
	}
	/*
	a,a
	e,e
	i,i
	o,o
	u,u*/
	
	public void saveUsers(String n, String p) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("data/UsersInfo.txt"));
		String tem = "";
		String s = "";
		do {
			tem = br.readLine();
			if(tem != null) {
				s += tem + "\n";
			}
		}while(tem != null);
		br.close();
		s+=n+ ","+p;
		writeUsers(s);
	}//n+ ","+p
	
	public void writeUsers(String s) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter("data/UsersInfo.txt"));
		bw.write(s);
		bw.close();
	}
	
	public void loadUsers() throws IOException, ClassNotFoundException {
		BufferedReader br = new BufferedReader(new FileReader("data/UsersInfo.txt"));
		String line= br.readLine();
		while(line!=null) {
			String[] parts= line.split(",");
			users.add(new User(parts[0],parts[1]));
			usersList.add(new User(parts[0],parts[1]));
			line=br.readLine();
		}
		br.close();
	}
	public User findUser(String name){
		User user = users.searchUser(name);
		return user;
	}
	
	public void addUser(String n, String p) throws IOException {
		User user = new User(n,p);
		users.add(user);
		usersList.add(user);
		saveUsers(n,p);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UsersTree getUsers() {
		return users;
	}

	public void setUsers(UsersTree users) {
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
