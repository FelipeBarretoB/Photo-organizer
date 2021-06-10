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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import thread.GetAllPhotosThread;
import thread.ImportFileThread;
import thread.OrganizeAllPhotos;

public class Organizer {
	private String name;
	private UsersTree users;
	private User actualUser;
	private Organized organized;
	private Files files;
	private ImportFileThread importFileThread;
	private List<Photo> photos;
	private GetAllPhotosThread getPhotosThread;
	private ArrayList<User> usersList;
	private OrganizeAllPhotos orgaThread;

	public Organizer(String n) {
		this.name = n;
		this.users = new UsersTree();
		organized=null;
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
		saveUsers(n,p);
	}

	public String getName() {
		return name;
	}

	public Organized getOrganized() {
		return organized;
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
	}
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

	public void callOrganizeThread(User user, String name, String organizeMathod) {
		orgaThread= new OrganizeAllPhotos(this, user, name, organizeMathod);
		orgaThread.start();
	}

	public boolean checkIfRunning() {
		return importFileThread.isAlive();
	}

	public void saveOrganized() throws FileNotFoundException, IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data\\organizedData.ap2"));
		oos.writeObject(organized);
		oos.close();
	}

	public void loadOrganized() throws FileNotFoundException, IOException, ClassNotFoundException {
		File f = new File("data\\organizedData.ap2");
		if(f.exists()){
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
			organized = (Organized) ois.readObject();
			ois.close();
		}
	
	}
	
	public void deleteAllOrganized() throws FileNotFoundException, IOException {
		organized=null;
		saveOrganized();
	}

	public void organize(User user, String name, String organizeMethod) throws IOException {
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
		File csvFile=new File(organized.getPath()+"\\"+name+".csv");
		File sign=new File(organized.getPath()+"\\creado por.txt");
		PrintWriter pw= new PrintWriter(sign.getPath());
		if(user==null) {
			pw.println("Creado por: Anonimo");
		}else {
			pw.println("Creado por:"+user.getName());
		}
		pw.println("Creado el:"+LocalDateTime.now().toString());
		pw.close();
		csvFile.createNewFile();
		pw= new PrintWriter(csvFile.getPath());
		pw.println("Nombre;Tamaño;Resolución(mp);Tipo;Fecha");
		for(int c=0;c<photos.size();c++) {
			File fileCreator=new File(organized.getPath()+"\\"+photos.get(c).getFile().getName());
			InputStream is = null;
			OutputStream os = null;
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
			pw.println(photos.get(c).getName()+";"+photos.get(c).getSize()+";"+photos.get(c).getResolution()+";"+photos.get(c).getType()+";"+photos.get(c).getDate());

		}
		pw.close();
		Date d= new Date(organized.lastModified());

		Files organizedFile= new Files(organized.getName(), ""+organized.length(),d.toString(),organized ,foldersIn(organized),filesIn(organized), organized.getPath());
		organizedFile.addPhotos();
		addOrganized(organizedFile,organizedFile.getName(),organizedFile.getSize(),organizedFile.getDate(),photos.size(),user );
		saveOrganized();

	}


	public void addOrganized(Files files,String name, String size, String date,int nOO, User createdUser) {
		if(organized==null) {
			System.out.println("added parent");
			organized=new Organized(name, size, date, nOO, null, null, null, files, createdUser);
		}else {
			if(Integer.parseInt(organized.getSize())>=Integer.parseInt(files.getSize())){
				addOrganized( files, name,  size,  date, nOO,  createdUser,  organized.getLeft(),organized);
			}else if(Integer.parseInt(organized.getSize())<=Integer.parseInt(files.getSize())) {
				addOrganized( files, name,  size,  date, nOO,  createdUser,  organized.getRight(),organized);
			}
		}
	}

	private void addOrganized(Files files,String name, String size, String date,int nOO, User createdUser, Organized currentOrganized, Organized parent) {
		if(currentOrganized==null) {
			System.out.println("added to side");
			currentOrganized=new Organized(name, size, date, nOO, null, null, null, files, createdUser);
			currentOrganized.setParent(parent);
			if(Integer.parseInt(parent.getSize())>=Integer.parseInt(currentOrganized.getSize())){
				parent.setLeft(currentOrganized);
			}else if(Integer.parseInt(parent.getSize())<=Integer.parseInt(currentOrganized.getSize())) {
				parent.setRight(currentOrganized);
			}
		}else {
			if(Integer.parseInt(currentOrganized.getSize())>=Integer.parseInt(files.getSize())){
				addOrganized( files, name,  size,  date, nOO,  createdUser,  currentOrganized.getLeft(),currentOrganized);
			}else if(Integer.parseInt(currentOrganized.getSize())<=Integer.parseInt(files.getSize())) {
				addOrganized( files, name,  size,  date, nOO,  createdUser,  currentOrganized.getRight(),currentOrganized);
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

	public ArrayList<String> getOrganizedNames(){
		ArrayList<String> returnValue= new ArrayList<>();
		if(organized!=null) {

			getOrganizedNames(organized.getLeft(),returnValue);

			returnValue.add(organized.getName());

			getOrganizedNames(organized.getRight(),returnValue);
		}	
		return returnValue;
	}

	private ArrayList<String> getOrganizedNames(Organized root, ArrayList<String> returnValue){
		if(root!=null) {

			getOrganizedNames(root.getLeft(),returnValue);

			returnValue.add(root.getName());

			getOrganizedNames(root.getRight(),returnValue);
		}	
		return returnValue;
	}

	public Organized getOrganizedByName(String name) {
		Organized found=null;
		if(organized!=null) {
			if(organized.getName().equals(name)) {
				found=organized;
			}else {
				found=getOrganizedByName( name, organized.getLeft(),  found);
				found=getOrganizedByName( name, organized.getRight(),  found);
			}
		}
		return found;
	}

	private Organized getOrganizedByName(String name,Organized currentOrga, Organized found) {
		if(currentOrga!=null) {
			if(currentOrga.getName().equals(name)) {
				found=currentOrga;
			}else {
				found=getOrganizedByName( name, currentOrga.getLeft(),  found);
				found=getOrganizedByName( name, currentOrga.getRight(),  found);
			}
		}
		return found;
	}

	public void createFileAgain(Organized rebornOrganized) throws IOException {
		rebornOrganized.getFiles().getFile().mkdir();
		createPhotosOfFile(rebornOrganized);

	}

	private void createPhotosOfFile(Organized rebornOrganized) throws IOException {
		ArrayList<Photo> photo=getAllPhotosInArrayForCreateFileAgain(rebornOrganized.getFiles().getPhoto(),  rebornOrganized.getFiles()); 
		System.out.println(photo.size());
		File csvFile=new File((rebornOrganized.getFiles().getFile().getPath()+"\\"+rebornOrganized.getName()+".csv"));
		File sign=new File((rebornOrganized.getFiles().getFile().getPath()+"\\creado por.txt"));
		PrintWriter pw= new PrintWriter(sign.getPath());
		if(rebornOrganized.getCreatedUser()==null) {
			pw.println("Creado por: Anonimo");
		}else {
			pw.println("Creado por:"+rebornOrganized.getCreatedUser().getName());
		}
		pw.println("Creado el:"+LocalDateTime.now().toString());
		pw.close();
		csvFile.createNewFile();
		pw= new PrintWriter(csvFile.getPath());
		pw.println("Nombre;Tamaño;Resolución(mp);Tipo;Fecha");
		for(int c=0;c<photo.size();c++) {
			pw.println(photo.get(c).getName()+";"+photo.get(c).getSize()+";"+photo.get(c).getResolution()+";"+photo.get(c).getType()+";"+photo.get(c).getDate());
		}
		pw.close();
	}


	public ArrayList<Photo> getAllPhotosInArrayForCreateFileAgain(Photo photos, Files files) {

		ArrayList<Photo> photo=new ArrayList<>();
		getAllPhotosInArray(files.getPhoto(),files,photo);
		return photo;
	}

	private void getAllPhotosInArray(Photo nextPhoto,Files nextFile,ArrayList<Photo> photos) {
		if(nextPhoto!=null) {
			photos.add(nextPhoto);
			getAllPhotosInArray(nextPhoto.getNextPhoto(),nextFile,photos);
		}else {
			if(nextFile.getNext()!=null) {
				getAllPhotosInArray(nextFile.getNext().getPhoto(),nextFile.getNext(),photos);
			}
		}

	}

	public void removeOrganized(String name) throws FileNotFoundException, IOException {
		ArrayList<Organized> organizedArray=new ArrayList<>();
		organizedArray=getOrganizedInArray( organized,  organizedArray);
		organized=null;
		for(int c=0;c<organizedArray.size();c++) {
			organizedArray.get(c).setLeft(null);
			organizedArray.get(c).setRight(null);
			organizedArray.get(c).setParent(null);
		}
		for(int c=0;c<organizedArray.size();c++) {

			if(organizedArray.get(c).getName().equals(name)) {

				organizedArray.remove(c);

			}
		}
		for(int c=0;c<organizedArray.size();c++) {
			addOrganized(organizedArray.get(c));
		}
		saveOrganized();
	}

	private ArrayList<Organized> getOrganizedInArray(Organized root, ArrayList<Organized> returnValue){
		if(root!=null) {

			returnValue=getOrganizedInArray(root.getLeft(),returnValue);

			returnValue.add(root);

			returnValue=getOrganizedInArray(root.getRight(),returnValue);
		}	
		return returnValue;
	}

	private void addOrganized(Organized orga) throws FileNotFoundException, IOException {
		if(organized==null) {
			organized=orga;
		}else {
			if(Integer.parseInt(organized.getSize())>=Integer.parseInt(orga.getSize())){
				addOrganized(orga,organized.getLeft(),organized);
			}else if(Integer.parseInt(organized.getSize())<=Integer.parseInt(files.getSize())) {
				addOrganized( orga,organized.getRight(),organized);
			}
		}
		saveOrganized();
	}

	private void addOrganized(Organized orga,Organized currentOrganized ,Organized parent) {
		if(currentOrganized==null) {
			currentOrganized=orga;
			currentOrganized.setParent(parent);
			if(Integer.parseInt(parent.getSize())>=Integer.parseInt(currentOrganized.getSize())){
				parent.setLeft(currentOrganized);
			}else if(Integer.parseInt(parent.getSize())<=Integer.parseInt(currentOrganized.getSize())) {
				parent.setRight(currentOrganized);
			}
		}else {
			if(Integer.parseInt(currentOrganized.getSize())>=Integer.parseInt(files.getSize())){
				addOrganized( orga,  currentOrganized.getLeft(),currentOrganized);
			}else if(Integer.parseInt(currentOrganized.getSize())<=Integer.parseInt(files.getSize())) {
				addOrganized( orga,  currentOrganized.getRight(),currentOrganized);
			}
		}
	}
	
}
