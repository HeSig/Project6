package danielsV2;

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
import java.util.Date;
import java.util.Map.Entry;
import javax.swing.Icon;


/**
 * 
 * @author Daniel Rosdahl
 *
 *Server sends messages to their users and stores ClientHandler instances and Messages that could not be sent in HashMaps
 */
public class Server {
	private SynchronizedHashMap<String, ClientHandler> clientMap = new SynchronizedHashMap<String, ClientHandler>();
	private ServerUI serverUI;
	private SynchronizedHashMap<String, ArrayList<Message>> unsentMessagesMap = new SynchronizedHashMap<String, ArrayList<Message>>();

	public static void main(String[] args) {
		new ServerUI();
	}

	/**
	 * Constructor that gives the server ip and a serverUI instance
	 * 
	 * @param port
	 * @param serverUI
	 */
	public Server(int port, ServerUI serverUI) {
		new Connection(port, this).start();
		this.serverUI = serverUI;
	}

	/**
	 * 
	 * @author Daniel
	 *
	 *Connection is a thread that handles what the server does when a client is connecting.
	 */
	private class Connection extends Thread {
		private int port;
		private Server server;

		/**
		 * Constructor that gives the class a port and server
		 * 
		 * @param port
		 * @param server
		 */
		public Connection(int port, Server server) {
			this.port = port;
			this.server = server;
		}

		/**
		 * The threads run() method that gets the clients user when one connects
		 * it also creates and stores a ClientHandler in a HashMap and checks to see if the user has received messages when it was offline
		 */
		public void run() {
			Socket socket = null;
			System.out.println("Server startad");
			serverUI.addLog("on the go");

			try (ServerSocket serverSocket = new ServerSocket(port)) {
				while(true) {
					try {
						socket = serverSocket.accept();
						ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
						ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
						User user = (User)ois.readObject();
						String username = user.getUsername();

						clientMap.put(username, new ClientHandler(socket, ois, oos, server, user));
						
						Thread.sleep(500);
						sendSavedMessages(user);

					} catch(IOException e) {
						System.err.println(e);
						if(socket!=null)
							socket.close();
					} catch(ClassNotFoundException e2) {
						e2.printStackTrace();
					} catch (InterruptedException e3) {
						e3.printStackTrace();
					}
				}
			} catch(IOException e) {
				System.err.println(e);
			}
			System.out.println("Server stoppad");
		}
	}

	/**
	 * Updates Connected Clients with a new list of the currently connected clients whenever a client disconnects
	 * @param ch the ClientHandler thread that disconnected
	 */
	public void clientDisconnected(ClientHandler ch) {
		System.out.println("[" + ch.getUser().getUsername() + "] " + " disconnected");
		clientMap.remove(ch.getUser().getUsername());
		ch = null;
		sendOnlineClients();
	}

	/**
	 * Sends a list of the currently connected clients to every connected client
	 */
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

	/**
	 * Saves messages whos receiver was not connected to the server
	 * 
	 * @param msg
	 * @param usernameList list of the currently connected users
	 */
	public void saveUnsentMessages(Message msg, ArrayList<String> usernameList) {
		for(String s : usernameList) {
			if(!clientMap.containsKey(s)) {
				if(!unsentMessagesMap.containsKey(s)) {
					unsentMessagesMap.put(s, new ArrayList<Message>());
				}
				if(unsentMessagesMap.containsKey(s)) {
					for(Entry<String, ArrayList<Message>> entry : unsentMessagesMap.entrySet()) {
						if(entry.getKey().equals(s)) {
							entry.getValue().add(msg);
						}
					}
				}
			}
		}
	}

	/**
	 * Sends messages that was not delivered because the user was not connected to the server
	 * 
	 * @param user
	 */
	public void sendSavedMessages(User user) {
		String username = user.getUsername();
		ArrayList<Message> msgList = new ArrayList<Message>();
		for (String s : unsentMessagesMap.keySet()) {
			if (s.equals(username)) {
				for(Message msg : unsentMessagesMap.get(username)) {
					clientMap.get(username).sendSavedMessagesToUser(msg);
				}
				unsentMessagesMap.get(username).clear();
			}
		}
	}

	/**
	 * Sends the message to the clients
	 * 
	 * @param msg
	 */
	public void sendMessage(Message msg) {
		ArrayList<User> receivers = msg.getReceivers();
		ArrayList<String> usernameList = new ArrayList<String>();
		for(User u : receivers) {
			usernameList.add(u.getUsername());
		}

		for(Entry<String, ClientHandler> entry : clientMap.entrySet()) {
			entry.getValue().sendMessageToConnectedUsers(msg, usernameList);
		}

		ArrayList<Message> putList = new ArrayList<Message>();

		saveUnsentMessages(msg, usernameList);
		try {
			logTrafic(msg);
		}catch(IOException e) {
			serverUI.addLog("Couldn´t log");
		}
	}
	
	/**
	 * Method which converts a string to a date 
	 * @param time, String to be converted
	 * @return the date object
	 */
	public Date convertToServerDate(String time) {
		DateFormat sdf = new SimpleDateFormat("E MMM dd HH:mm:ss z y"); 
		try {
			Date date = sdf.parse(time);
			return date;
		}catch (ParseException e){
			System.out.println("Couldn´t convert string");
		}
		return null;
	}
	
	/**
	 * Method which reads from the log file and compares them to the inserted dates
	 * @param fromTime
	 * @param toTime
	 * @throws IOException
	 */
	public void getTraficLogs(Date fromTime, Date toTime) throws IOException {
		String message = "";
		String nextline;
		try( BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("files/chatAppLog.txt"),"UTF-8")) ) {
            nextline = br.readLine();
            if(nextline != null) {
            	Date currentRead = convertToServerDate(nextline);
            	System.out.println(currentRead);
			
            	while(currentRead != null && !currentRead.after(toTime)) {
            		if(currentRead.equals(fromTime) || currentRead.after(fromTime)) {
            			message += br.readLine() +"\n";
            			message += br.readLine() +"\n";
            			message += br.readLine() +"\n";
            			serverUI.addLog(currentRead + message);
            			message = "";
            		}else {
            			br.readLine();
            			br.readLine();
            			br.readLine();
            		}
            		nextline = br.readLine();
            		if(nextline != null) {
            			currentRead = convertToServerDate(nextline);
            		}else {
            			currentRead = null;
            		}
            	}
            	br.close();
            }
        }
	}
	
	/**
	 * Method which writes the message being sent to a file
	 * @param msg, Message to be logged
	 * @throws IOException
	 */
	public void logTrafic(Message msg) throws IOException {
		String messageLog = msg.getServerReceivedDate() + "\nFrom: " + msg.getUser() + ", To: " + msg.getReceivers()
		+ "\nMessage: " + msg.getText() + "\nImage: " + msg.getImage().toString();
		
		try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("files/chatAppLog.txt", true),"UTF-8"))) {
			bw.write(messageLog);
			bw.newLine();
			bw.flush();
			bw.close();
		}
	}
}
