package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class test {
	
	private Organizer organizedNotEmpty;
	
	private Organizer fileWithPhotos;
	
	private Organizer organizedEmpty;
	
	private Organizer filesEmpty;
	

	public void setUpSenario1() throws IOException {
		organizedNotEmpty=new Organizer("Photo organizer");
		organizedNotEmpty.callImportFile(new File("data/testFiles/test"));
		while(organizedNotEmpty.getImportFileThread().isAlive()) {}//this makes it so the program has to wait for the threads to finish, this way, organize and other methods will run correctly
		organizedNotEmpty.organize(organizedNotEmpty.getActualUser(), "Test", "fecha");
	}
	
	public void setUpSenario2() throws IOException {
		fileWithPhotos= new Organizer("Photo organizer");
		fileWithPhotos.callImportFile(new File("data/testFiles/test"));
		while(fileWithPhotos.getImportFileThread().isAlive()) {}
	}
	
	public void setUpSenario3() {
		organizedEmpty=new Organizer("Photo organizer");
	}
	public void setUpSenario4() {
		filesEmpty=new Organizer("Photo organizer");
	}
	

	//Todos los archivos los puedes encontrar en la carpeta organized


	@Test
	public void addFileToEmptyFiles() {
		setUpSenario4();
		filesEmpty.callImportFile(new File("data/testFiles/test"));
		while(filesEmpty.getImportFileThread().isAlive()) {}
		assertTrue(filesEmpty.getFiles()!=null);
	}
	
	@Test
	public void addFileToFiles() {
		try {
			setUpSenario2();
			fileWithPhotos.callImportFile(new File("data/testFiles/test2"));
			assertTrue(fileWithPhotos.getFiles()!=null);
		} catch (IOException e) {
			fail("File not found");
		}
	}
	
	@Test
	public void addOrganizedNotEmpty() {
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
	public void addToEmptyOrganized() {
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
	public void organizeByDate() {
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
	public void organizeByType() {
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
	public void organizeByName() {
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
	public void organizeBySize() {
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
	public void organizeByResolution() {
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
