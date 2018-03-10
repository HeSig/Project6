package Client;

import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Controller {
	private Client client;
	private ClientUI ui = new ClientUI(this);
	private void showClientUI() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Client");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.add(ui);
				frame.pack();
				frame.setVisible(true);
			}
		});
	}
	public Controller(Client client) {
		this.client = client;
		client.setController(this);
		showClientUI();
	}
	public void newSend(String str) {
		try {
			client.newSend(str);
		} catch (IOException e) {}
	}
	public void newMessageReceived(String response) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ui.setMessageReceived(response);
			}
		});
	}
	public static void main(String[] args) {
		try {
			 Client client = new Client("127.0.0.1",1214);
			 new Controller(client);
		} catch (IOException e) {}
	}
}