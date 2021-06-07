package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import thread.ImportFileThread;

public class Organizer {
	private String name;
	private UsersTree users;
	private User actualUser;
	private Type type;//por usar
	private Organized organized;//por usar
	private Files files;//por usar
	private ImportFileThread importFileThread;
	public ArrayList<User> usersList;

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
		importFileThread = new ImportFileThread(this, file);
		importFileThread.start();
	}
	
	
	public void addFile(File file) {
		Date d= new Date(file.lastModified());
		files = new Files(file.getName(), ""+file.length(),d.toString(),file ,foldersIn(file),filesIn(file), file.getPath());
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
			System.out.println("added: "+file.getName());
			Date d= new Date(file.lastModified());
			currentFile = new Files(file.getName(), ""+file.length(),d.toString(),file ,foldersIn(file),filesIn(file), file.getPath()); 
			File[] fileList=file.listFiles();
			for(int c=0;c<fileList.length;c++) {
				if(fileList[c].list()!=null) {
					addFile(files.getNext(),fileList[c]);
				}
			}
		}
	}

}
