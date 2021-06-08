package model;

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
	
	public Organized (String n, String s,String d, int nOO) {
		super(n,s,d);
		this.numOfOrga = nOO;
		this.left = null;
		this.right = null;
		this.parent = null;
	}
	
	public Organized (String n, String s,String d, int nOO, Organized l, Organized r, Organized p, Files f) {
		super(n,s,d);
		this.numOfOrga = nOO;
		this.left = l;
		this.right = r;
		this.parent = p;
		this.files = f;
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
	
	
}
