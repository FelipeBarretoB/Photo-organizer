package thread;

import java.io.File;

import model.Organizer;

public class ImportFileThread extends Thread{
	
	private Organizer orga;
	private File file;
 	
	public ImportFileThread(Organizer orga,File file) {
		this.orga=orga;
		this.file=file;
	}
	
	@Override
	public void run() {	
		orga.startAddFile(orga.getFiles(),file);		
	}
}
