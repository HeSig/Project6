package client;

import javax.swing.ImageIcon;

public class User{
	private String name;
	private ImageIcon image;
	
	public User(String name) {
		setName(name);
		this.image = null;
	}
	public User(String name, ImageIcon image) {
		setName(name);
		setImage(image);
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ImageIcon getImage() {
		return image;
	}
	public void setImage(ImageIcon image) {
		this.image = image;
	}
	
	

}
