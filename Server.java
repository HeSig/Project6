package danielsVersionOändrad;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.Icon;


/**
 * 
 * @author Daniel Rosdahl
 *
 *	Starta servern härifrån och fyll i 1222 som port
 */
public class Server {
	private HashMap<String, ClientHandler> clientMap = new HashMap<String, ClientHandler>();
	private ServerUI serverUI;
	private HashMap<String, Message> unsentMessagesMap = new HashMap<String, Message>();
	private ArrayList<User> listOfUsers = new ArrayList<User>();

	public static void main(String[] args) {
		new ServerUI();
	}

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
			serverUI.addLog("Server on");

			try (ServerSocket serverSocket = new ServerSocket(port)) {
				while(true) {
					try {
						socket = serverSocket.accept();
						ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
						ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
						User user = (User)ois.readObject();
						String username = user.getUsername();

						serverUI.addLog(user.getUsername());

						clientMap.put(username, new ClientHandler(socket, ois, oos, server, user));

					} catch(IOException e) {
						System.err.println(e);
						if(socket!=null)
							socket.close();
					} catch(ClassNotFoundException e2) {
						e2.printStackTrace();
					}
				}
			} catch(IOException e) {
				System.err.println(e);
			}
			System.out.println("Server stoppad");
			serverUI.addLog("Server off");
		}
	}

	public HashMap<String, ClientHandler> getClientMap(){
		return this.clientMap;
	}

	public void clientDisconnected(ClientHandler ch) {
		System.out.println("[" + ch.getUser().getUsername() + "] " + " disconnected");
		serverUI.addLog(ch.getUser().getUsername() + "disconnected");
		clientMap.remove(ch.getUser().getUsername(), ch);
		ch = null;
		sendOnlineClients();
	}

	public void sendOnlineClients() {
		if(!clientMap.isEmpty()) {
			ArrayList<String> userList = new ArrayList<String>();
			for(Entry<String, ClientHandler> entry : clientMap.entrySet()) {
				userList.add(entry.getValue().getUser().getUsername());
			}

			for(Entry<String, ClientHandler> entry : clientMap.entrySet()) {
				entry.getValue().sendOnlineList(userList);			
			}
		}
	}

	//anv�nds inte �n
	public void usersNotConnected(Message msg) {
		System.out.println(listOfUsers + "USERSNOTCONN");
		//		ArrayList<User> receivers = msg.getReceivers();
		//		for(Entry<String, ClientHandler> entry : clientMap.entrySet()) {
		//			for(User u : receivers) {
		//				if(u.getUsername().equals(entry.getKey())) {
		//					System.out.println("usersNotConnected print" + entry.getKey() + u.getUsername());
		//				}
		//			}
		//		}
	}

	public void sendMessage(Message msg) {

		ArrayList<User> receivers = msg.getReceivers();
		ArrayList<String> usernameList = new ArrayList<String>();

		for(User u : receivers) {
			usernameList.add(u.getUsername());
		}
		System.out.println("Lista med användare som ska skickas till: " + usernameList);

		for(Entry<String, ClientHandler> entry : clientMap.entrySet()) {
			entry.getValue().sendMessageToUsers(msg, usernameList);
		}
		try {
			logTrafic(msg);
		}catch(IOException e) {
			serverUI.addLog("Couldn´t log");
		}
	}

	private ArrayList<Message> msgList = new ArrayList<Message>();

	public void WriteMessage(String filename, HashMap<String, Message> unsentMessagesMap) throws IOException{
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
	
	public Date convertToDate(String time) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:MM:ss", Locale.ENGLISH);
		try {
			Date date = formatter.parse(time);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void getTraficLogs(Date fromTime, Date toTime) throws IOException {
		String message = "";
		try( BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("files/chatAppLog.txt"),"UTF-8")) ) {
			Date currentRead = convertToDate(br.readLine());
			while(currentRead != null && !currentRead.after(toTime)) {
				if(currentRead.equals(fromTime) || currentRead.after(fromTime)) {
					message += br.readLine();
					message += br.readLine();
					message += br.readLine();
					serverUI.addLog(currentRead + message);
				}
			}
		}
	}
	
	public void logTrafic(Message msg) throws IOException {
		String messageLog = msg.getServerReceivedDate() + "\nFrom: " + msg.getUser() + ", To: " + msg.getReceivers()
		+ "\nMessage: " + msg.getText() + "\nImage: " + msg.getImage().toString();
		
		try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("files/chatAppLog.txt"),"UTF-8"))) {
			bw.write(messageLog);
			bw.newLine();
			bw.flush();
			bw.close();
		}
	}
}