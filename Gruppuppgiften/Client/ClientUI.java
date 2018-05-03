package Client;

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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Extra.Message;
import Extra.User;

public class ClientUI extends JPanel implements ActionListener {
	private JButton btnSend = new JButton("Send");
	private JTextArea taShowMessage = new JTextArea(""); 
	private JTextArea taClients = new JTextArea("ONLINE");
	private JTextArea taTextToSend = new JTextArea("TO SEND");
	private JTextArea taReceivers = new JTextArea("1, 2, 3");
	private JPanel panelSouth = new JPanel();
	private JPanel panelNorthWest = new JPanel();
	private JPanel panelNorth = new JPanel();
	private JLabel lblIcon = new JLabel();
	private JLabel lblUsername = new JLabel();
	private JLabel lblUserIcon = new JLabel();
	private ClientController controller;
	private User user;

	public ClientUI(ClientController controller, User user) {
		this.controller = controller;
		this.user = user;
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(705, 600));
		taShowMessage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		taShowMessage.setPreferredSize(new Dimension(200,80));
		taClients.setBorder(BorderFactory.createLineBorder(Color.RED));
		taClients.setPreferredSize(new Dimension(150,80));
		taTextToSend.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		taTextToSend.setPreferredSize(new Dimension(400,80));
		taReceivers.setBorder(BorderFactory.createLineBorder(Color.RED));
		taReceivers.setPreferredSize(new Dimension(150,80));
		btnSend.setSize(new Dimension(100, 40));
		btnSend.addActionListener(this);
		panelNorth.setLayout(new BorderLayout());
		panelNorth.setPreferredSize(new Dimension(600, 300));
		panelNorthWest.setLayout(new BorderLayout());
		panelNorthWest.setSize(new Dimension(100, 40));
		panelSouth.setLayout(new BorderLayout());
		panelSouth.setPreferredSize(new Dimension(600, 270));
		lblIcon.setPreferredSize(new Dimension(50,40));
		lblUserIcon.setSize(new Dimension(50, 50));
		
		taReceivers.setEnabled(false);
		taReceivers.setDisabledTextColor(Color.BLACK);
		taClients.setEnabled(false);
		taClients.setDisabledTextColor(Color.BLACK);
		taShowMessage.setEnabled(false);
		taShowMessage.setDisabledTextColor(Color.BLACK);
		
		taTextToSend.setLineWrap(true);
		taTextToSend.setWrapStyleWord(true);
		taShowMessage.setLineWrap(true);
		taShowMessage.setWrapStyleWord(true);
		taReceivers.setLineWrap(true);
		taReceivers.setWrapStyleWord(true);

		lblUsername.setText(user.getUsername());
		lblUserIcon.setIcon(user.getUserImage());;
		lblUserIcon.setSize(new Dimension(50, 50));

		add(panelNorth, BorderLayout.NORTH);
		panelNorth.add(panelNorthWest, BorderLayout.WEST);
		add(panelSouth, BorderLayout.CENTER);
		add(btnSend, BorderLayout.SOUTH);
		panelNorth.add(taShowMessage, BorderLayout.CENTER);
		panelNorth.add(lblIcon, BorderLayout.EAST);
		
		panelNorthWest.add(lblUsername, BorderLayout.NORTH);
		panelNorthWest.add(lblUserIcon, BorderLayout.SOUTH);
		
		panelSouth.add(taTextToSend, BorderLayout.WEST);
		panelSouth.add(taClients, BorderLayout.CENTER);
		panelSouth.add(taReceivers, BorderLayout.EAST);
		

	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnSend) {
			
			if(taReceivers.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Du måste fylla i mottagare");
				
			}else {
				Icon image = new ImageIcon("bilder/enBild.png");
				ArrayList<User> list = new ArrayList<User>();
				
				String str = taReceivers.getText();
				String[] receivers = str.split(", ");
				for(String s : receivers) {
					list.add(new User(s));
				}

				String text = taTextToSend.getText();

				Message msg = new Message(user, list, text, image);

				controller.newSend(msg);
			}
		}
	}

	public void setMessageReceived(Message msg) {
		User user = msg.getUser();
		String oldText = taShowMessage.getText();
		String toPrint = oldText + user.getUsername() + "(" + msg.getServerReceivedDate() + "): " + msg.getText() + "\n";
		taShowMessage.setText(toPrint);
		lblIcon.setIcon(msg.getImage());
	}

	public void setOnlineList(ArrayList<String> list) {
		taClients.setText("");
		for(String s : list) {
			taClients.setText(taClients.getText() + "\n" + s);
		}
	}

//	public static void main(String[] args) {
//		ClientUI ui = new ClientUI(null, null);
//		ui.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//		JOptionPane.showMessageDialog( null, ui );
//	}
}
