package thread;

import java.io.File;

import model.Organizer;

public class ImportFileThread extends Thread{
	
	public Organizer orga;
	public File file;
 	
	public ImportFileThread(Organizer orga,File file) {
		this.orga=orga;
		this.file=file;
	}
	
	@Override
	public void run() {	
		orga.addFile(file);
	}
}
