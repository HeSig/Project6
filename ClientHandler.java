package gruppuppgift;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread {
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Server server;
	private String username;
	public ClientHandler(Socket socket, ObjectInputStream ois, ObjectOutputStream oos, Server server, String username) throws IOException {
		this.socket = socket;
		this.ois = ois;
		this.oos = oos;
		this.server = server;
		this.username = username;
		start();
	}
	public void run() {
		Message msg;
		try {
			while(true) {
				try {
					System.out.println("Innan tar emot");
					msg = (Message)ois.readObject();
					System.out.println("Efter tar emot");
					oos.writeObject(msg);
					oos.flush();
//					msgList.add(msg);
//					WriteMessage("files/save.dat", msg);
				} catch (ClassNotFoundException e) {}
			}
		} catch(IOException e) {
			try {
				socket.close();
			} catch(Exception e2) {
				System.err.println("Socket.close");
			}
		}
		System.out.println("Klient nerkopplad");
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void sendOnlineList(ArrayList<String> list) {
		try {
			oos.writeObject(list);
			oos.flush();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}