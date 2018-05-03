package GU;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import chat.User;
import client.ClientController;
import client.UserList;
import client.ClientViewer.popupListener;

public class ClientUI extends JPanel implements ActionListener {
	private JButton btnSend = new JButton("Tryck");
	private JTextArea taShowMessage = new JTextArea("SHOWMESSAGE"); 
	private JTextArea taClients = new JTextArea("ONLINE");
	private JTextArea taTextToSend = new JTextArea("TO SEND");
	private JTextArea taReceivers = new JTextArea("1,2,3");
	private JTextArea taContacts = new JTextArea();
	private JPanel panelSouth = new JPanel();
	private JPanel panelCenter = new JPanel();
	private JPanel panelNorth = new JPanel();
	private JLabel lblIcon = new JLabel("Label för icon");
	private ClientController controller;
	private User user;
//	private UserList userList = new UserList(new popupListener());
//	private UserList contactList = new UserList(null);


	public ClientUI(ClientController controller, User user) {
		this.controller = controller;
		this.user = user;
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(605, 600));
		taShowMessage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		taShowMessage.setPreferredSize(new Dimension(300,80));
		taClients.setBorder(BorderFactory.createLineBorder(Color.RED));
		taClients.setPreferredSize(new Dimension(150,80));
		taTextToSend.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		taTextToSend.setPreferredSize(new Dimension(300,80));
		taReceivers.setBorder(BorderFactory.createLineBorder(Color.RED));
		taReceivers.setPreferredSize(new Dimension(150,80));
		taContacts.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		taContacts.setPreferredSize(new Dimension(180,80));
		btnSend.setPreferredSize(new Dimension(300, 40));
		btnSend.addActionListener(this);
		panelNorth.setLayout(new BorderLayout());
		panelNorth.setPreferredSize(new Dimension(600, 60));
		panelCenter.setLayout(new BorderLayout());
		panelCenter.setPreferredSize(new Dimension(600, 270));
		panelSouth.setLayout(new BorderLayout());
		panelSouth.setPreferredSize(new Dimension(600, 270));
		lblIcon.setPreferredSize(new Dimension(300,40));
		
		btnSend.setText(user.getUsername());

		add(panelNorth, BorderLayout.NORTH);
		add(panelCenter, BorderLayout.CENTER);
		add(panelSouth, BorderLayout.SOUTH);
		panelNorth.add(btnSend, BorderLayout.WEST);
		panelCenter.add(taTextToSend, BorderLayout.WEST);
		panelCenter.add(lblIcon, BorderLayout.EAST);
		panelSouth.add(taShowMessage, BorderLayout.WEST);
		panelSouth.add(taClients, BorderLayout.CENTER);
		panelSouth.add(taReceivers, BorderLayout.EAST);
		panelSouth.add(taContacts, BorderLayout.NORTH);

	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnSend) {
			Icon image = new ImageIcon("images/intyg.png");
			
			ArrayList<User> list = new ArrayList<User>();
			String str = taReceivers.getText();
			String[] receivers = str.split(",");
			for(String s : receivers) {
				list.add(new User(s));
			}

			String text = taTextToSend.getText();

			Message msg = new Message(user, list, text, image);

			controller.newSend(msg);
		}
	}

	public void setMessageReceived(Message msg) {
		taShowMessage.setText(msg.toString());
		lblIcon.setIcon(msg.getUser().getUserImage());
	}

	public void setOnlineList(ArrayList<String> list) {
		taClients.setText("");
		for(String s : list) {
			taClients.setText(taClients.getText() + "\n" + s);
			taContacts.setText(taContacts.getText() + "\n" + s);
		}
	}
//	public void setController(ClientController controller) {
//		this.controller = controller;
//		readInContacts();
//	}

//	public void readInContacts() {
//		contactList.clearList();
//		contactList.setUsers(new ArrayList<User>(controller.getContacts()));
//	}
//
//	public void actionPerformed1(ActionEvent e) {
//		if (e.getSource() == btnSendMessage) {
//			ArrayList<User> receivers = getSelectedActiveUsers();
//			for (User user : controller.getContacts()) {
//				if (receivers.contains(user)) {
//					continue;
//				}
//				receivers.add(user);
//			}
//			controller.newMessage(receivers);
//			tfWrite.clear();
//			repaint();
//		}
//		if (e.getSource() == btnUploadImage) {
//			uploadImage();
//		}
//		if (e.getSource() instanceof JMenuItem) {
//			ArrayList<User> selectedUsers = userList.getSelectedUsers();
//			User temp = null;
//			for (User u : selectedUsers) {
//				if (controller.compareUser(u)) {
//					temp = u;
//				}
//			}
//			selectedUsers.remove(temp);
//			controller.addContacts(selectedUsers);
//			readInContacts();
//		}
//	}
//
//	/**
//	 * If a user right-clicks on a active user, popup-menu will show the option to
//	 * add this user to your list of contacts.
//	 * 
//	 * @author Erik Lundow
//	 * 
//	 */
//	private class popupListener extends MouseAdapter {
//		public void mousePressed(MouseEvent e) {
//			if (e.getButton() == MouseEvent.BUTTON3) {
//				popupMenu.show(e.getComponent(), e.getX(), e.getY());
//			}
//		}
//	}



	public static void main(String[] args) {
		ClientUI ui = new ClientUI(null, null);
		ui.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JOptionPane.showMessageDialog( null, ui );
	}
}
