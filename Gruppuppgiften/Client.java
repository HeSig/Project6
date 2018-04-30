package GU;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * 
 * @author Daniel Rosdahl
 *
 *Starta klienten härifrån, User i main klassen är den usern som skickas i message till servern
 */

public class Client {
	public static void main(String[] args) {
		User user = new User("4");

		try {
			Client client = new Client("127.0.0.1", 1228, user);
			new ClientController(client);

		}catch(IOException e) {}
	}

	private ClientController controller;
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private User user;

	public Client(String ip, int port, User user) throws IOException {
		socket = new Socket(ip,port);
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());

		//Skickar användaren när klienten startar
		this.user = user;
		sendUserInformation(user);

		new Listener().start();
	}
	
	public User getUser() {
		return this.user;
	}

	public void setController(ClientController controller) {
		this.controller = controller;
	}

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

						ArrayList<User> receivers = msgReceived.getRecievers();
						ArrayList<String> usernameList = new ArrayList<String>();
						for(User u : receivers) {
							usernameList.add(u.getUsername());
						}
//						for(String s : usernameList) {
//							System.out.println(controller.getUser().getUsername() + " och " + s);
//							if(controller.getUser().getUsername().equals(s)) {
//								
//								controller.newMessageReceived(msgReceived);
//							}
//						}
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
