package thread;

import model.Organizer;

public class GetAllPhotosThread extends Thread{
	private Organizer orga;

	public GetAllPhotosThread(Organizer orga) {
		this.orga = orga;
	}
	
	@Override
	public void run() {	
		orga.getAllPhotosInFiles();
	}
}
