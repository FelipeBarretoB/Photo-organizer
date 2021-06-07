package model;

import java.io.File;



public class Photo extends Properties implements Comparable<Photo> {
	private Type type;
	private String resolution;
	private Photo nextPhoto; //y si ponemos solo next? xd
	private File file;
	
	public Photo(String n, String s,String d, Type t, String r,File file, Photo p) {
		super(n,s,d);
		this.type = t;
		this.resolution = r;
		this.nextPhoto = p;
		this.file=file;
	}
	
	public Photo(String n, String s,String d, Type t,File file, String r) {
		super(n,s,d);
		this.type = t;
		this.resolution = r;
		this.nextPhoto = null;
		this.file=file;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public Photo getNextPhoto() {
		return nextPhoto;
	}

	public void setNextPhoto(Photo nextPhoto) {
		this.nextPhoto = nextPhoto;
	}
	public File getFile() {
		return file;
	}
	
	public int compareTo(Photo otherP) {
		return getName().compareTo(otherP.getName());
	}
	
}
