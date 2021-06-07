package model;

public class UsersTree {
	public User first;
	public int size;
	
	
	public UsersTree() {
		this.size = 0;
	}
	
	public void add(User user) {
		if(first != null) {
			first.addUser(user);
			size++;
		}else {
			first = user;
			size = 1;
		}
	}
	
	public User searchUser(String name) {
		System.out.println(first.getName());
		if(first != null) {
			return first.find(name);
		}
		return null;
	}

	public User getFirst() {
		return first;
	}

	public void setFirst(User first) {
		this.first = first;
	}

	public int size() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	
}
