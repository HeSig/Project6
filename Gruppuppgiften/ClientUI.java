package GU;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ClientUI extends JPanel implements ActionListener {
	private JButton btnSend = new JButton("Tryck");
	private JTextArea taShowMessage = new JTextArea("SHOWMESSAGE"); 
	private JTextArea taClients = new JTextArea("ONLINE");
	private ClientController controller;
	private JPanel panelSouth = new JPanel();
	private User user;

	public ClientUI(ClientController controller, User user) {
		this.controller = controller;
		this.user = user;
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(610, 250));
		taShowMessage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		taShowMessage.setPreferredSize(new Dimension(300,80));
		taClients.setBorder(BorderFactory.createLineBorder(Color.RED));
		taClients.setPreferredSize(new Dimension(300,80));
		btnSend.addActionListener(this);
		panelSouth.setLayout(new BorderLayout());
		panelSouth.setPreferredSize(new Dimension(600, 160));

		add(btnSend, BorderLayout.NORTH);
		add(panelSouth, BorderLayout.SOUTH);
		panelSouth.add(taShowMessage, BorderLayout.WEST);
		panelSouth.add(taClients, BorderLayout.EAST);

	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnSend) {
			Icon image = new ImageIcon("bilder/bildUser.png");
			User user1 = new User("2");
			User user2 = new User("3");
			ArrayList<User> list = new ArrayList<User>();
			list.add(user1);
			list.add(user2);
			String name = "Daniel";
			String text = "någon text";

			Message msg = new Message(user, list, text, image);

			controller.newSend(msg);
		}
	}

	public void setMessageReceived(Message msg) {
		taShowMessage.setText(msg.toString());
	}

	public void setOnlineList(ArrayList<String> list) {
		taClients.setText("");
		for(String s : list) {
			taClients.setText(taClients.getText() + "\n" + s);
		}
	}

	public static void main(String[] args) {
		ClientUI ui = new ClientUI(null, null);
		ui.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JOptionPane.showMessageDialog( null, ui );
	}
}
