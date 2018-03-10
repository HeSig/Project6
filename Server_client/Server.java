package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * Skickar bara String objekt just nu
 *
 */

public class Server {
	public Server(int port) {
		new Connection(port).start();
	}
	private class Connection extends Thread {
		private int port;
		public Connection(int port) {
			this.port = port;
		}
		public void run() {
			Socket socket = null;
			System.out.println("Server startad");
			try (ServerSocket serverSocket = new ServerSocket(port)) {
				while(true) {
					try {
						socket = serverSocket.accept();
						new ClientHandler(socket);
					} catch(IOException e) {
						System.err.println(e);
						if(socket!=null)
							socket.close();
					}
				}
			} catch(IOException e) {
				System.err.println(e);
			}
			System.out.println("Server stoppad");
		}
	}
	private class ClientHandler extends Thread {
		private Socket socket;
		private ObjectInputStream ois;
		private ObjectOutputStream oos;
		public ClientHandler(Socket socket) throws IOException {
			this.socket = socket;
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
			start();
		}
		public void run() {
			String str = "Läggs på tillbaka";
			try {
				while(true) {
					try {
						str = str + (String)ois.readObject();
						oos.writeObject(str);
						oos.flush();
					} catch (ClassNotFoundException e) {}
				}
			} catch(IOException e) {
				try {
					socket.close();
				} catch(Exception e2) {}
			}
			System.out.println("Klient nerkopplad");
		}
	}
	public static void main(String[] args) {
		new Server(1214);
	}
}

