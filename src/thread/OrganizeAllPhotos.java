package thread;

import java.io.IOException;

import model.Organizer;
import model.User;

public class OrganizeAllPhotos extends Thread{
	
	private Organizer orga;
	private User user;
	private String nameOfFile;
	private String organizeMethod;
	
	public OrganizeAllPhotos(Organizer orga, User user, String nameOfFIle,String organizeMethod) {
		this.orga=orga;
		this.user=user;
		this.nameOfFile=nameOfFIle;
		this.organizeMethod=organizeMethod;
	}
	
	@Override
	public void run(){
			
				try {
					orga.organize(user, nameOfFile, organizeMethod);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	
}
