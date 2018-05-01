package GU;

import java.io.IOException;

/**
 * @author Daniel Rosdahl
 * 
 * Keeps track of what socket belongs to which user
 */

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread{
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Server server;
	private User user;

	public ClientHandler(Socket socket, ObjectInputStream ois, ObjectOutputStream oos, Server server, User user) throws IOException {
		this.socket = socket;
		this.ois = ois;
		this.oos = oos;
		this.server = server;
		this.user = user;

		start();
	}

	public String toString() {
		return "||| ClientHandler toString coming through ||| [ " + user.getUsername() + " ]";
	}

	public void run() {
		Message msg;

		try {
			while(true) {
				try {
					msg = (Message)ois.readObject();
					msg.setServerReceivedDate();
					
					server.sendOnlineClients();
					
					server.sendMessage(msg);
				} catch (ClassNotFoundException e) {}
			}
		} catch(IOException e) {
			try {
				socket.close();
			} catch(Exception e2) {
				System.err.println("socket closed ClientHandler");
			}
		}

		server.clientDisconnected(this);
	}

	public User getUser() {
		return this.user;
	}

	public void sendOnlineList(ArrayList<String> list) {
		try {
			oos.writeObject(list);
			oos.flush();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMessageToUsers(Message msg, ArrayList<String> usernameList) {
		for(String s : usernameList) {
			if(this.user.getUsername().equals(s)) {
				msg.setMessageDeliveredDate();
				try {
					oos.writeObject(msg);
					oos.flush();
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
