package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class test {
	
	private Organizer organizedNotEmpty;
	
	private Organizer fileWith20Photos;
	
	private Organizer organizedEmpty;
	
	private Organizer filesEmpty;
	

	public void setUpSenario1() throws IOException {
		organizedNotEmpty=new Organizer("Photo organizer");
		organizedNotEmpty.callImportFile(new File("data/testFiles/test"));
		while(organizedNotEmpty.getImportFileThread().isAlive()) {}//this makes it so the program has to wait for the threads to finish, this way, organize and other methods will run correctly
		organizedNotEmpty.organize(organizedNotEmpty.getActualUser(), "Test", "fecha");
	}
	
	public void setUpSenario2() throws IOException {
		fileWith20Photos= new Organizer("Photo organizer");
		fileWith20Photos.callImportFile(new File("data/testFiles/test"));
		while(fileWith20Photos.getImportFileThread().isAlive()) {}
	}
	
	public void setUpSenario3() {
		organizedEmpty=new Organizer("Photo organizer");
	}
	public void setUpSenario4() {
		filesEmpty=new Organizer("Photo organizer");
	}
	

	//Todos los archivos los puedes encontrar en la carpeta organized


	@Test
	void addFileToEmptyFiles() {
		setUpSenario4();
		filesEmpty.callImportFile(new File("data/testFiles/test"));
		while(filesEmpty.getImportFileThread().isAlive()) {}
		assertTrue(filesEmpty.getFiles()!=null);
	}
	
	@Test
	void addFileToFiles() {
		try {
			setUpSenario2();
			fileWith20Photos.callImportFile(new File("data/testFiles/test2"));
			assertTrue(fileWith20Photos.getFiles()!=null);
		} catch (IOException e) {
			fail("File not found");
		}
	}
	
	@Test
	void addOrganizedNotEmpty() {
		try {
			setUpSenario1();
			organizedNotEmpty.callImportFile(new File("data/testFiles/test2"));
			while(organizedNotEmpty.getImportFileThread().isAlive()) {}//this makes so the program has to wait for the threads to finish so organize can run correctly
			organizedNotEmpty.organize(organizedNotEmpty.getActualUser(), "Test", "fecha");
			assertTrue(organizedNotEmpty.getOrganized().getLeft()!=null);
		} catch (IOException e) {
			fail("File not found");
		}
	}

	
	@Test
	void addToEmptyOrganized() {
		try {
			setUpSenario3();
			organizedEmpty.callImportFile(new File("data/testFiles/test"));
			while(organizedEmpty.getImportFileThread().isAlive()) {}
			organizedEmpty.organize(organizedEmpty.getActualUser(), "Test", "fecha");
			assertTrue(organizedEmpty.getOrganized()!=null);
		} catch (IOException e) {
			fail("File not found");
		}
	}
	
	@Test
	void organizeByDate() {
		try {
			setUpSenario3();
			organizedEmpty.callImportFile(new File("data/testFiles/test"));
			while(organizedEmpty.getImportFileThread().isAlive()) {}
			organizedEmpty.organize(organizedEmpty.getActualUser(), "TestFecha", "fecha");
			assertTrue(organizedEmpty.getOrganized()!=null);
		} catch (IOException e) {
			fail("File not found");
		}
	}
	@Test
	void organizeByType() {
		try {
			setUpSenario3();
			organizedEmpty.callImportFile(new File("data/testFiles/test"));
			while(organizedEmpty.getImportFileThread().isAlive()) {}
			organizedEmpty.organize(organizedEmpty.getActualUser(), "TestTipo", "tipo");
			assertTrue(organizedEmpty.getOrganized()!=null);
		} catch (IOException e) {
			fail("File not found");
		}
	}
	@Test
	void organizeByName() {
		try {
			setUpSenario3();
			organizedEmpty.callImportFile(new File("data/testFiles/test"));
			while(organizedEmpty.getImportFileThread().isAlive()) {}
			organizedEmpty.organize(organizedEmpty.getActualUser(), "TestNombre", "nombre");
			assertTrue(organizedEmpty.getOrganized()!=null);
		} catch (IOException e) {
			fail("File not found");
		}
	}
	@Test
	void organizeBySize() {
		try {
			setUpSenario3();
			organizedEmpty.callImportFile(new File("data/testFiles/test2"));
			while(organizedEmpty.getImportFileThread().isAlive()) {}
			organizedEmpty.organize(organizedEmpty.getActualUser(), "TestTamaño", "tamaño");
			assertTrue(organizedEmpty.getOrganized()!=null);
		} catch (IOException e) {
			fail("File not found");
		}
	}
	void organizeByResolution() {
		try {
			setUpSenario3();
			organizedEmpty.callImportFile(new File("data/testFiles/test2"));
			while(organizedEmpty.getImportFileThread().isAlive()) {}
			organizedEmpty.organize(organizedEmpty.getActualUser(), "TestResolucion", "resolucion");
			assertTrue(organizedEmpty.getOrganized()!=null);
		} catch (IOException e) {
			fail("File not found");
		}
	}
	
}
