package Server;

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

import Extra.Message;
import Extra.User;

/**
 * 
 * @author Daniel Rosdahl
 *
 *ClientHandler keeps track of the sockets between a specific client and the server
 */
public class ClientHandler extends Thread{
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Server server;
	private User user;

	/**
	 * Starts its own thread
	 * 
	 * @param socket
	 * @param ois
	 * @param oos
	 * @param server
	 * @param user
	 * @throws IOException
	 */
	public ClientHandler(Socket socket, ObjectInputStream ois, ObjectOutputStream oos, Server server, User user) throws IOException {
		this.socket = socket;
		this.ois = ois;
		this.oos = oos;
		this.server = server;
		this.user = user;

		start();
	}

	/**
	 * the threads run() method that waits for messages to be sent and then sends them to their respective users
	 */
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

	/**
	 * gets the ClientHandlers user
	 * 
	 * @return
	 */
	public User getUser() {
		return this.user;
	}

	/**
	 * sends the list of currently connected users to the client
	 * 
	 * @param list from the server
	 */
	public void sendOnlineList(ArrayList<String> list) {
		try {
			oos.writeObject(list);
			oos.flush();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * sends messages that the client could not receive earlier
	 * 
	 * @param msg
	 */
	public void sendSavedMessagesToUser(Message msg) {
		try{
			System.out.println(msg);
			msg.setMessageDeliveredDate();
			oos.writeObject(msg);
			oos.flush();
			System.out.println("flushar från savedmessages ch");
		}catch(IOException e) {}
	}

	/**
	 * sends the message to the user
	 * 
	 * @param msg
	 * @param usernameList list used to check if this instance of ClientHandler is the correct user
	 */
	public void sendMessageToConnectedUsers(Message msg, ArrayList<String> usernameList) {
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
