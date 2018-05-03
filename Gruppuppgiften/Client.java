package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import Extra.Message;
import Extra.User;

/**
 * 
 * @author Daniel Rosdahl
 *
 *Client handles the connection to the server and sends messages to it
 */

public class Client {
	public static void main(String[] args) {
		User user = new User("2");

		try {
			Client client = new Client("127.0.0.1", 1222, user);
			new ClientController(client);
		}catch(IOException e) {}
	}

	private ClientController controller;
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private User user;

	/**
	 * Constructor that gives the client its socket and object streams.
	 * Also starts the listener and sends a User object to the server
	 * 
	 * @param ip
	 * @param port
	 * @param user
	 * @throws IOException
	 */
	public Client(String ip, int port, User user) throws IOException {
		socket = new Socket(ip,port);
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());
		
		new Listener().start();
		
		//Skickar användaren när klienten startar
		this.user = user;
		sendUserInformation(user);

	}
	
	/**
	 * Gets the User from Client
	 * 
	 * @return
	 */
	public User getUser() {
		return this.user;
	}

	/**
	 * sets a controller for the client
	 * 
	 * @param controller
	 */
	public void setController(ClientController controller) {
		this.controller = controller;
	}

	/**
	 * 
	 * 
	 * @param user
	 * @throws IOException
	 */
	public void sendUserInformation(User user) throws IOException {
		oos.writeObject(user);
		oos.flush();
	}

	//skickar iväg parametern i writeObject. Kan anropas från Controller
	public void newSend(Message msg) throws IOException {
		oos.writeObject(msg);
		oos.flush();
	}
	private class Listener extends Thread {
		public void run() {
			Message msgReceived;
			ArrayList<String> userList;
			try {
				while(true) {
					Object received = ois.readObject();
					if(received instanceof ArrayList) {
						userList = (ArrayList<String>)received;
						controller.setOnlineList(userList);
					}
					else if(received instanceof Message){
						msgReceived = (Message)received;

						ArrayList<User> receivers = msgReceived.getReceivers();
						ArrayList<String> usernameList = new ArrayList<String>();
						for(User u : receivers) {
							usernameList.add(u.getUsername());
						}

						controller.newMessageReceived(msgReceived);
					}
				}
			} catch(Exception e) {}
			try {
				socket.close();
			} catch(Exception e) {}
		}
	}


}
