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
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import thread.GetAllPhotosThread;
import thread.ImportFileThread;

public class Organizer {
	private String name;
	private UsersTree users;
	private User actualUser;
	private Organized organized;//por usar
	private Files files;
	private ImportFileThread importFileThread;
	private List<Photo> photos;
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

	public void getAllPhotosInArray() {
		photos=new ArrayList<>();
		//photos.add(files.getPhoto());
		getAllPhotosInArray(files.getPhoto(),files);
		System.out.println(photos.size());
		orderByDate();
		testDates();
		orderBySize();
		testSize();
		orderByResolution();
		testResolution();
		sortByName();
		testName();
		orderBySize();
		testType();
	}
	int i=1;
	private void getAllPhotosInArray(Photo nextPhoto,Files nextFile) {

		if(nextPhoto!=null) {
			photos.add(nextPhoto);
			getAllPhotosInArray(nextPhoto.getNextPhoto(),nextFile);
		}else {
			if(nextFile.getNext()!=null) {
				getAllPhotosInArray(nextFile.getNext().getPhoto(),nextFile.getNext());
			}
		}

	}

	public void organize(User user, String name, String organizeMethod) {
		File organized= new File("Organized\\"+name);
		organized.mkdir();
		if(organizeMethod.equals("Fecha")) {
			orderByDate();
		}else if(organizeMethod.equals("Tipo"))	{
			sortByType();
		}else if(organizeMethod.equals("Nombre")){
			sortByName();
		}else if(organizeMethod.equals("Tamaño")){
			orderBySize();
		}else if(organizeMethod.equals("Resolución")) {
			orderByResolution();
		}
		
		for(int c=0;c<photos.size();c++) {
			File fileCreator=new File(organized.getPath()+"\\"+photos.get(c).getFile().getName());
			InputStream is = null;
		    OutputStream os = null;
		        try {
					is = new FileInputStream(photos.get(c).getFile());
					os = new FileOutputStream(fileCreator);
					 byte[] buffer = new byte[1024];
				        int length;
				        while ((length = is.read(buffer)) > 0) {
				            os.write(buffer, 0, length);
				        }
			
				        is.close();
				        os.close();
				        fileCreator.createNewFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		  
		}
	}


	//TODO borrar puto gay
	public void testDates() {
		for(int c=0;c<photos.size();c++) {
			System.out.println(photos.get(c).getDate());
		}
	}

	//TODO borrar puto gay
	public void testSize() {
		for(int c=0;c<photos.size();c++) {
			System.out.println(photos.get(c).getSize());
		}
	}

	//TODO borrar puto gay
	public void testResolution() {
		for(int c=0;c<photos.size();c++) {
			System.out.println(photos.get(c).getResolution());
		}
	}

	//TODO borrar puto gay
	public void testName() {
		for(int c=0;c<photos.size();c++) {
			System.out.println(photos.get(c).getName());
		}
	}

	//TODO borrar puto gay
	public void testType() {
		for(int c=0;c<photos.size();c++) {
			System.out.println(photos.get(c).getType().toString());
		}
	}



	public void orderByDate() {
		boolean changed=true;
		Photo flag=null;
		for(int c=1;c <photos.size() && changed;c++) {
			changed=false;
			for(int i=0;i<photos.size()-c;i++) {
				if((photos.get(i).getFile().lastModified())>photos.get(i+1).getFile().lastModified()) {
					flag=photos.get(i);
					photos.set(i, photos.get(i+1));
					photos.set(i+1,flag );
					changed=true;
				}
			}
		}
	}

	public void orderBySize() {
		Photo min=null;
		Photo temp=null;
		for (int i = 0; i < photos.size(); i++) {
			min=photos.get(i);
			for (int j = 1+i; j < photos.size(); j++) {
				if(photos.get(j).getFile().length()<min.getFile().length()) {
					temp=photos.get(j);
					photos.set(j, min);
					min=temp;
				}
			}
			photos.set(i, min);
		}
	}

	public void orderByResolution() {
		for (int i = 1; i < photos.size(); i++) {
			for (int j=i;j>0 && Double.parseDouble(photos.get(j-1).getResolution())>Double.parseDouble(photos.get(j).getResolution());j--) {
				Photo temp=photos.get(j);
				photos.set(j, photos.get(j-1));
				photos.set(j-1, temp);
			}
		}
	}


	public void sortByName() {
		Collections.sort(photos);
	}

	public void sortByType() {
		Comparator<Photo> floorComparator = new Comparator<Photo>() {

			@Override
			public int compare(Photo o1, Photo o2) {
				return o1.getType().toString().compareTo(o2.getType().toString());
			}

		};
		Collections.sort(photos,floorComparator);
	}
}
