package GU;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class ClientController {
	private User user;
	private Client client;
	private ClientUI ui;
	
	private Contacts contacts;
	
	private void showClientUI() {
		JFrame frame = new JFrame("Client");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(ui);
		frame.pack();
		frame.setVisible(true);
	}
	
	public ClientController(ClientUI ui) {
		contacts = new Contacts();
		//newUser();
		this.ui = ui;
	}
	
	public ClientController(Client client) {
	
		this.client = client;
		this.user = client.getUser();
		ui = new ClientUI(this, user);
		client.setController(this);
		showClientUI();
	}
	public void newSend(Message msg) {
		try {
			client.newSend(msg);
		} catch (IOException e) {
			System.err.println("error i newSend Controller");
		}
	}
	public void newMessageReceived(Message msg) {
		ui.setMessageReceived(msg);
	}

	public void setOnlineList(ArrayList<String> list) {
		ui.setOnlineList(list);
	}

	public User getUser() {
		return this.user;
	}
	
	public boolean compareUser(User u) {
		return this.user.equals(u);
	}

	public void addContacts(ArrayList<User> users) {
		contacts.addContact(users);
	}
	
	public void addContacts(LinkedList<User> users) {
		for(User user : users) {
			contacts.addContact(user);
		}
	}
	
	public LinkedList<User> getContacts() {
		return contacts.getList();
	}

	//	public static void main(String[] args) {
	//		try {
	//			Client client = new Client("127.0.0.1",1210);
	//			new ClientController(client);
	//		} catch (IOException e) {}
	//	}
}