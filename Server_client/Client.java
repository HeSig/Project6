package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.Icon;

/**
 * 
 * Skickar bara String objekt just nu
 *
 */

public class Client {
	private Controller controller;
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	public Client(String ip, int port) throws IOException {
		socket = new Socket(ip,port);
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());
		new Listener().start();
	}
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	//skickar iväg parametern i writeObject. Kan anropas från Controller
	public void newSend(String str) throws IOException {
		oos.writeObject(str);
		oos.flush();
	}
	private class Listener extends Thread {
		public void run() {
			String response;
			try {
				while(true) {
					response = (String)ois.readObject();
					controller.newMessageReceived(response);
				}
			} catch(Exception e) {}
			try {
				socket.close();
			} catch(Exception e) {}
			controller.newMessageReceived("Klient kopplar ner");
		}
	}
	
	public static void main(String[] args) {
		try {
		new Client("127.0.0.1", 1215);
		}catch(IOException e) {}
		
	}
}
