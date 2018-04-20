package GU;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Client {
	private ClientController controller;
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	public Client(String ip, int port) throws IOException {
		socket = new Socket(ip,port);
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());
		new Listener().start();
	}
	
	public void setController(ClientController controller) {
		this.controller = controller;
	}
	
	//skickar iväg parametern i writeObject. Kan anropas från Controller
	public void newSend(Message msg) throws IOException {
		oos.writeObject(msg);
		oos.flush();
	}
	private class Listener extends Thread {
		public void run() {
			Message msgReceived;
			try {
				while(true) {
					msgReceived = (Message)ois.readObject();
					System.out.println("Clienten tar emot: " + msgReceived);
					controller.newMessageReceived(msgReceived);
				}
			} catch(Exception e) {}
			try {
				socket.close();
			} catch(Exception e) {}
			System.out.println("Klient kopplar ner");
		}
	}
	
	public static void main(String[] args) {
		try {
		Client client = new Client("127.0.0.1", 1213);
		new ClientController(client);
		}catch(IOException e) {}
		
	}
}
