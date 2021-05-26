package model;

public class Photo extends Properties {
	private Type type;
	private String resolution;
	private Photo nextPhoto; //y si ponemos solo next? xd
	
	public Photo(String n, String s,String d, Type t, String r, Photo p) {
		super(n,s,d);
		this.type = t;
		this.resolution = r;
		this.nextPhoto = p;
	}
	
	public Photo(String n, String s,String d, Type t, String r) {
		super(n,s,d);
		this.type = t;
		this.resolution = r;
		this.nextPhoto = null;
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
	
	
}
