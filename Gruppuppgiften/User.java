package Extra;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.io.IOException;
import java.io.Serializable;

public class User implements Serializable{
	private static Icon defaultUserImage = new ImageIcon("bilder/bildUser.png");
	private String username; 
	private Icon userImage;

	public User(String username){
		this(username, defaultUserImage);
	}

	public User(String username, Icon userImage){
		this.username = username;
		this.userImage = userImage;
	}

	public String toString() {
		return username;
	}

	public String getUsername(){
		return this.username;
	}  

	public Icon getUserImage(){
		return this.userImage;
	}

	public int hashCode() {
		return username.hashCode();
	}
	public boolean equals(Object obj) {
		return username.equals(obj);
	}
}