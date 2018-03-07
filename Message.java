package gruppuppgift;

import java.util.Calendar;
import java.util.LinkedList;
import javax.swing.ImageIcon;

public class Message {
	private User user;
	private LinkedList<User> list = new LinkedList<User>();
	private String text;
	private ImageIcon image;
	private Calendar calendar;
	
	public Message(User user, LinkedList<User> list, ImageIcon image) {
		this.user = user;
		this.list = list;
		this.image = image;
		this.text = null;
	}
	
	public Message(User user, LinkedList<User> list, String text) {
		this.user = user;
		this.list = list;
		this.text = text;
		this.image = null;
	}
	
	public Message(User user, LinkedList<User> list, String text, ImageIcon image) {
		this.user = user;
		this.list = list;
		this.text = text;
		this.image = image;
	}
	
	public void addTime(String message) {
		calendar = Calendar.getInstance();
		message += "\n" + Calendar.YEAR + " " + Calendar.MONTH + " " + Calendar.DATE + "\n " + 
		Calendar.HOUR_OF_DAY + "." + Calendar.MINUTE;
	}
	
	public void setImage(ImageIcon image) {
		this.image = image;
	}
	
	public ImageIcon getImage() {
		return this.image;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return setMessage(this.user, this.list, this.text);
	}
	
	public String setMessage(User user, LinkedList<User> list, String text) {
		String receivers = "To: ";
		String message = "Message from " + this.user + ": " + this.text + "\n";
		
		for(User u : list) {
			receivers += u + ", ";
		}
		return message + receivers;
	}
}
