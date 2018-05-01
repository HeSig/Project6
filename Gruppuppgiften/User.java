package GU;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.io.IOException;
import java.io.Serializable;

public class User implements Serializable{
	private static Icon defaultUserImage = new ImageIcon("bilder/bildUser.png");
    private String username; 
    private Icon userImage;
    private boolean userOnline;
    
    public User(String username){
    	this(username, defaultUserImage);
    }
    
    public User(String username, Icon userImage){
        this.username = username;
        this.userImage = userImage;
    }
    
    public String toString() {
    	return "[Name: " + username + ", Icon: " + userImage + "]";
    }
    
	public String getUsername(){
        return this.username;
    }  
    
    public Icon getUserImage(){
        return this.userImage;
    }
    
    public static void main(String[] args) {
    	User user = new User("abcd");
    	System.out.println(user.toString());
    }
    
    public boolean getOnlineStatus(){
        return this.userOnline;
    }  
    
    public void setAsOnline(){
        this.userOnline = true;
    }
    
    public void setAsOffline(){
        this.userOnline = false;
    } 
}