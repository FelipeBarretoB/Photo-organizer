package model;

public class User {
	private String name;
	private String password;
	private String mail;
	private String lastName;
	
	public User(String name, String password, String mail, String lastName) {
		this.name = name;
		this.password = password;
		this.mail = mail;
		this.lastName = lastName;
	}
	
	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
	
}
