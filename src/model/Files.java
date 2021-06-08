package model;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;

import javafx.scene.image.Image;

public class Files extends Properties {

	private int filesIn;
	private int foldersIn;
	private String location;
	private Files next;
	private File file;
	private Photo photo;

	public Files(String n, String s,String d,File file, int fii, int foi, String l, Files next, Photo photo) {
		super(n,s,d);
		this.file=file;
		this.filesIn = fii;
		this.foldersIn = foi;
		this.location = l;
		this.next = next;
	}
	public Files(String n, String s,String d,File file ,int fii, int foi, String l) {
		super(n,s,d);
		this.file=file;
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

	public Photo getPhoto() {
		return photo;
	}

	public void addPhotos() throws FileNotFoundException {
		File[] photos=file.listFiles();
		for(int c=0;c<photos.length;c++) {
			if(photos[c].list()==null) {
				if(photo==null) {
					FileInputStream input;
					input = new FileInputStream(photos[c].getAbsolutePath());
					Image image= new Image(input);
					
					Date date = new Date(photos[c].lastModified());
					if(photos[c].getAbsolutePath().lastIndexOf(".")!=-1){
						photo= new Photo(photos[c].getName(), ""+photos[c].length(), date.toString(), 
								getType(photos[c].getAbsolutePath().substring(photos[c].getAbsolutePath().lastIndexOf("."),
										photos[c].getAbsolutePath().length())),photos[c], ""+(image.getWidth()*image.getHeight())/1000000);
					}
				}else {
					addNextPhotos(photo, photos[c]);
				}
			}
		}
	}

	private void addNextPhotos(Photo currentPhoto, File photo) throws FileNotFoundException {
		if(currentPhoto.getNextPhoto()==null) {
			if(photo.getAbsolutePath().lastIndexOf(".")!=-1){
				FileInputStream input;
				input = new FileInputStream(photo.getAbsolutePath());
				Image image= new Image(input);
				Date date = new Date(photo.lastModified());
				currentPhoto.setNextPhoto(new Photo(photo.getName(), ""+photo.length(), date.toString(), getType(photo.getAbsolutePath().substring(photo.getAbsolutePath().lastIndexOf("."),photo.getAbsolutePath().length())),photo, ""+(image.getWidth()*image.getHeight())/1000000));
			}
		}else {
			addNextPhotos(currentPhoto.getNextPhoto(), photo);
		}
	}

	//Types
	//JPEG,NEF,TIFF,RAW,JPG,OTHER;
	private Type getType(String filePath) {
		Type finalType=null;

		if(filePath.equalsIgnoreCase(".jpeg")) {
			finalType = Type.JPEG;
		}else if(filePath.equalsIgnoreCase(".NEF")) {
			finalType = Type.NEF;
		}else if(filePath.equalsIgnoreCase(".TIFF")) {
			finalType = Type.TIFF;
		}else if(filePath.equalsIgnoreCase(".RAW")) {
			finalType = Type.RAW;
		}else if(filePath.equalsIgnoreCase(".jpg")) {
			finalType = Type.JPG;
		}else if(filePath.equalsIgnoreCase(".CR2")) {
			finalType = Type.CR2;
		}else{
			finalType = Type.OTHER;
		}
		return finalType;
	}
}
