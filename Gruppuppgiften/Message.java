package GU;

import java.awt.Component;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Message implements Serializable{
	private User user;
	private ArrayList<User> list = new ArrayList<User>();
	private String text;
	private Icon image;
	private Calendar calendar;
	
	public Message(User user, ArrayList<User> list, Icon image) {
		this.user = user;
		this.list = list;
		this.image = image;
		this.text = null;
	}
	
	public Message(User user, ArrayList<User> list, String text) {
		this.user = user;
		this.list = list;
		this.text = text;
		this.image = null;
	}
	
	public Message(User user, ArrayList<User> list, String text, Icon image) {
		this.user = user;
		this.list = list;
		this.text = text;
		this.image = image;
	}
	
	public String toString() {
		return "(User: " + user + "), \n(UserList: " + list + "), \n(Text: " + text + "), \n(Image: " + image + ")";
	}
	
	public String getTime() {
		String message = "";
		calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		message += Calendar.YEAR + " " + Calendar.MONTH + " " + Calendar.DATE +  
		Calendar.HOUR_OF_DAY + "." + Calendar.MINUTE;
		return message;
	}
	
	public void setImage(Icon image) {
		this.image = image;
	}
	
	public Icon getImage() {
		return this.image;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
	public String setMessage(User user, LinkedList<User> list, String text) {
		String message = "";
		message += getTime() + " Från: " + user.getUsername() + " Till: ";
		
		for(User u: list) {
			message += u + ", ";
		}
		message+= ". " + text;
		return message;
		/*String receivers = "To: ";
		String message = "Message from " + this.user + ": " + this.text + "\n";
		
		for(User u : list) {
			receivers += u + ", ";
		}
		return message + receivers + addTime(message);*/
	}

	public User getUser() {
		return user;
	}

	public ArrayList<User> getRecievers() {
		return list;
	}
}