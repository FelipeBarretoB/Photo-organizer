package model;

import java.io.FileNotFoundException;
import java.io.Serializable;

public class Organized extends Properties implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int numOfOrga;
	private Organized left;
	private Organized right;
	private Organized parent;
	private Files files;
	private User createdUser;

	public User getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(User createdUser) {
		this.createdUser = createdUser;
	}

	public Organized (String n, String s,String d, int nOO,User createdUser) {
		super(n,s,d);
		this.numOfOrga = nOO;
		this.left = null;
		this.right = null;
		this.parent = null;
		this.createdUser=createdUser;
	}

	public Organized (String n, String s,String d, int nOO, Organized l, Organized r, Organized p, Files f,User createdUser) {
		super(n,s,d);
		this.numOfOrga = nOO;
		this.left = l;
		this.right = r;
		this.parent = p;
		this.files = f;
		this.createdUser=createdUser;
	}

	public int getNumOfOrga() {
		return numOfOrga;
	}

	public void setNumOfOrga(int numOfOrga) {
		this.numOfOrga = numOfOrga;
	}

	public Organized getLeft() {
		return left;
	}

	public void setLeft(Organized left) {
		this.left = left;
	}

	public Organized getRight() {
		return right;
	}

	public void setRight(Organized right) {
		this.right = right;
	}

	public Organized getParent() {
		return parent;
	}

	public void setParent(Organized parent) {
		this.parent = parent;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Files getFiles() {
		return files;
	}

	public void setFiles(Files files) {
		this.files = files;

	}

	public String getAllPhotosNames() throws FileNotFoundException {
		getFiles().addPhotos();
		String names="";
		if(files.getPhoto().getName()!=null) {
			names+=files.getPhoto().getName();
			if(!files.getPhoto().getNextPhoto().equals(null)) {
				names=getAllPhotosNames( names, files.getPhoto().getNextPhoto());
			}
		}
		return names;
	}

	private String getAllPhotosNames(String names,Photo currentPhoto) {
		names+=", "+currentPhoto.getName();
		if(currentPhoto.getNextPhoto()!=null) {
			names=getAllPhotosNames(names,currentPhoto.getNextPhoto());
		}
		return names;
	}

}
