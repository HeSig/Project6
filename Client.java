package gruppuppgift;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Client extends Thread {
	private ClientController controller;
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private User user;

	public Client(User user, String ip, int port) throws IOException {
		socket = new Socket(ip,port);
		this.user = user;
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());
		start();
	}

	public void setController(ClientController controller) {
		this.controller = controller;
	}

	//skickar iväg parametern i writeObject. Kan anropas från Controller
	public void newSend(Message msg) {
		try {
			oos.writeObject(msg);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

	}
	public void run() {
		Message msgReceived;
		try {
			oos.writeObject(user);
			oos.flush();
			while(true) {
				Object received = ois.readObject();
				if(received instanceof ArrayList) {
					ArrayList<String> list = (ArrayList<String>)received;
					//controller.updateClientList();
				}
				else if(received instanceof Message){
					msgReceived = (Message)received;
					uppdatera GUI via controllern, controller.newMessageReceived();
				}

				msgReceived = (Message)ois.readObject();
				System.out.println("Klienten tar emot: " + msgReceived);
				controller.newMessageReceived(msgReceived);
			}
		} catch(Exception e) {}
		try {
			socket.close();
		} catch(Exception e) {}
		System.out.println("Klient kopplar ner");
	}

	public static void main(String[] args) {
		try {
			Client client = new Client(new User("Deniel"), "127.0.0.1", 1245);
			new ClientController(client);
		}catch(IOException e) {}
	}
}
