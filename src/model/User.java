package model;

public class User {
	private String name;
	private String password;
	private User left;
	private User right;
	
	public User(String name, String password) {
		this.name = name;
		this.password = password;
		this.left = null;
		this.right = null;
	}
	
	public User find(String nameX) {
		int i = 0;
		int o = 0;
		User tem = null;
		System.out.println("thisname: "+this.getName()+"\nname: "+name);
		if(name.equals(nameX)) {
			return this;
		} else {
			if(name.length() > nameX.length()) {
				o = nameX.length();
			}else {
				o = name.length();
			}
			while( i < o && name.charAt(i) == nameX.charAt(i)) {
				i++;
			}
			if(i>0) {
				i--;
			}
			if(name.charAt(i) < nameX.charAt(i)) {
				if(right != null) {
					tem = right.find(nameX);
				}
			}else {
				if(left != null) {
					tem = left.find(nameX);
				}
			}
		}
		return tem;
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

	public User getLeft() {
		return left;
	}

	public void setLeft(User left) {
		this.left = left;
	}

	public User getRight() {
		return right;
	}

	public void setRight(User right) {
		this.right = right;
	}
	
	
	
}
