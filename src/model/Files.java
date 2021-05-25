package model;

public class Files extends Properties {
	
	private int filesIn;
	private int foldersIn;
	private String location;
	private Files next;
	
	public Files(String n, String s,String d, int fii, int foi, String l, Files next) {
		super(n,s,d);
		this.filesIn = fii;
		this.foldersIn = foi;
		this.location = l;
		this.next = next;
	}
	public Files(String n, String s,String d, int fii, int foi, String l) {
		super(n,s,d);
		this.filesIn = fii;
		this.foldersIn = foi;
		this.location = l;
		this.next = null;
	}

	public int getFilesIn() {
		return filesIn;
	}

	public void setFilesIn(int filesIn) {
		this.filesIn = filesIn;
	}

	public int getFoldersIn() {
		return foldersIn;
	}

	public void setFoldersIn(int foldersIn) {
		this.foldersIn = foldersIn;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Files getNext() {
		return next;
	}

	public void setNext(Files next) {
		this.next = next;
	}
}
