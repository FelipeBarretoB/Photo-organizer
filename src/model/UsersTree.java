package model;

import Exceptions.UserNotFoundException;

public class UsersTree {
	public User first;
	public int size;
	
	
	public UsersTree() {
		this.size = 0;
	}
	
	public void add(User user) {
		User aux = first;
		boolean added = false;
		if(aux != null) {
			while(added == false) {
				if(aux.getName().compareTo(user.getName()) < 0) {
					if(aux.getLeft() == null) {
						aux.setLeft(user);
						added = true;
					}else {
						aux = aux.getLeft();
					}
				}else {
					if(aux.getRight() == null) {
						aux.setRight(user);
						added = true;
					}else {
						aux = aux.getRight();
					}
				}
			}
			size++;
		}else {
			first = user;
			size = 1;
		}
	}
	
	public User searchUser(String name) throws UserNotFoundException {
		User aux = first;
		boolean find = false;
		while(find == false && aux != null) {
			if(aux.getName().compareTo(name) == 0) {
				find = true;
			}else if(aux.getName().compareTo(name) < 0) {
				aux = aux.getLeft();
			}else {
				aux = aux.getRight();
			}
		}
		if(aux == null && !find) {
			throw new UserNotFoundException();
		}
		return aux;
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
