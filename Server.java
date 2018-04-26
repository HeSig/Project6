package gruppuppgift;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.Icon;

public class Server {
	
	private HashMap<String, ClientHandler> clientMap = new HashMap<String, ClientHandler>();
	private ServerUI serverUI;
	
	public Server(int port, ServerUI serverUI) {
		new Connection(port, this).start();
		this.serverUI = serverUI;
	}
	
	private class Connection extends Thread {
		private int port;
		private Server server;
		public Connection(int port, Server server) {
			this.port = port;
			this.server = server;
		}
		public void run() {
			Socket socket = null;
			System.out.println("Server startad");
			serverUI.addLog("On the go");
			try (ServerSocket serverSocket = new ServerSocket(port)) {
				while(true) {
					try {
						socket = serverSocket.accept();
						ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
						ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
						String username = (String)ois.readObject();
						System.out.println(username);
						serverUI.addLog(username);
						
						clientMap.put(username, new ClientHandler(socket, ois, oos, server, username));
						
						sendOnlineClients();
						
					} catch(IOException e){
						System.err.println(e);
						if(socket!=null)
							socket.close();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch(IOException e) {
				System.err.println(e);
			}
			System.out.println("Server stoppad");
			serverUI.addLog("Off the line");
		}
	}
	
	public void clientDisconnected(ClientHandler ch) {
		System.out.println(ch.getUsername() + " disconnected");
		clientMap.remove(ch.getUsername(), ch);
		ch = null;
		sendOnlineClients();
	}
	
	public void sendOnlineClients() {
		ArrayList<String> usernameList = new ArrayList<String>();
		for(Entry<String, ClientHandler> entry : clientMap.entrySet()) {
			usernameList.add(entry.getValue().getUsername());
		}
		
		for(Entry<String, ClientHandler> entry : clientMap.entrySet()) {
			entry.getValue().sendOnlineList(usernameList);
		}
	}
	
	private ArrayList<Message> msgList = new ArrayList<Message>();
	
	public void WriteMessage(String filename, Message msg) throws IOException{
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))){
			oos.writeInt(msgList.size());
			for(Message m: msgList) {
				oos.writeObject(m);
			}
			oos.flush();
		}
	}

	public void ReadMessage(String filename) throws IOException{
		try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename))) ) {
			Message msg;
			int n = ois.readInt();
			try {
				for(int i = 0; i < n; i++) {
					msg = (Message)ois.readObject();
					System.out.println(msg);
				}
			} catch(ClassNotFoundException e) {}
		}
	}

//	public static void main(String[] args) {
//		new Server(1226);
//	}
}

