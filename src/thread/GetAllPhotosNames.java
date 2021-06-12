package thread;

import java.io.FileNotFoundException;
import java.io.Serializable;

import model.Organized;

public class GetAllPhotosNames extends Thread implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Organized orga;
	
	public GetAllPhotosNames(Organized orga) {
		this.orga = orga;
	}
	
	@Override
	public void run() {	
		try {
	
			orga.setAllNames(orga.getAllPhotosNames());
		} catch (FileNotFoundException e) {
			System.out.println("F");
		}
	}
	
}
