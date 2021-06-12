package model;

import java.io.File;
import java.io.IOException;

public interface Organizer_Interface {
	public void startAddFile(Files files, File file);
	
	public void callImportFile(File file);
	
	public void organize(User user, String name, String organizeMethod)throws IOException;
	
	public void addOrganized(Files files,String name, String size, String date,int nOO, User createdUser);
}
