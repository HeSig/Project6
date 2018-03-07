package chatapp;

public class Server {

	// en unik id for varje connection

	private static int uniqueId;

	// en ArrayList f�r att h�lla reda p� lista av Client

	private ArrayList<ClientThread> al;


	private ServerGUI sg;

	// tid

	private SimpleDateFormat sdf;

	// portnummer f�r att lyssna p� connection

	private int port;

	// boolean som kommer att st�ngas av f�r att stoppa servern

	private boolean keepGoing;





	/*
		serverns constructor som tar emot porten f�r att lyssna p� anslutning 
		som parameter i konsolen

	 */

	public Server(int port) {

		this(port, null);

	}



	public Server(int port, ServerGUI sg) {


		this.sg = sg;

		// port

		this.port = port;

		// visa hh:mm:ss

		sdf = new SimpleDateFormat("HH:mm:ss");

		// ArrayList f�r client list

		al = new ArrayList<ClientThread>();

	}



	public void start() {

		keepGoing = true;

		// skapa socket server och v�nta f�r connection request

		try

		{

			// socket som anv�nds av servern

			ServerSocket serverSocket = new ServerSocket(port);



			// o�ndlig loop f�r att v�nta p� connection

			while(keepGoing)

			{

				display("Server waiting for Clients on port " + port + ".");



				Socket socket = serverSocket.accept();      // accept connection 

				// om fr�gad att sluta

				if(!keepGoing)

					break;

				ClientThread t = new ClientThread(socket);  // skapa en tr�d av ddet

				al.add(t);                                  // spara det i en arraylist

				t.start();

			}

			// om fr�gad att sluta

			try {

				serverSocket.close();

				for(int i = 0; i < al.size(); ++i) {

					ClientThread tc = al.get(i);

					try {

						tc.sInput.close();

						tc.sOutput.close();

						tc.socket.close();

					}

					catch(IOException ioE) {



					}

				}

			}

			catch(Exception e) {

				display("Exception closing the server and clients: " + e);

			}

		}



		catch (IOException e) {

			String msg = sdf.format(new Date()) + " Exception on new ServerSocket: " + e + "\n";

			display(msg);

		}

	}      


	protected void stop() {

		keepGoing = false;



		try {

			new Socket("localhost", port);

		}

		catch(Exception e) {



		}

	}

	private void display(String msg) {

		String time = sdf.format(new Date()) + " " + msg;

		if(sg == null)

			System.out.println(time);

		else

			sg.appendEvent(time + "\n");

	}


	private synchronized void broadcast(String message) {


		String time = sdf.format(new Date());

		String messageLf = time + " " + message + "\n";


		if(sg == null)

			System.out.print(messageLf);

		else

			sg.appendRoom(messageLf);     



		//vi sl�r i omv�nd ordning om vi skulle beh�va ta bort en klient

		// eftersom den har kopplats bort

		for(int i = al.size(); --i >= 0;) {

			ClientThread ct = al.get(i);

			// f�rs�k skriva till Client om den misslyckas ta bort den fr�n listan

			if(!ct.writeMsg(messageLf)) {

				al.remove(i);

				display("Disconnected Client " + ct.username + " removed from list.");

			}

		}

	}



	// logout

	synchronized void remove(int id) {

		// skannar arraylist tills vi hittar id

		for(int i = 0; i < al.size(); ++i) {

			ClientThread ct = al.get(i);

			// hittade id

			if(ct.id == id) {

				al.remove(i);

				return;

			}

		}

	}



	public static void main(String[] args) {
		// start server p� port 1500

		int portNumber = 1500;

	}
}
