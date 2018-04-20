package gruppuppgift;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.*;

import gruppuppgift.Message;

@SuppressWarnings("serial")
public class ChatWindow extends JFrame implements ActionListener {
	private User user;
	private ChatController controller;
	private ImageIcon image = null;
	private LinkedList<User> recieverList = new LinkedList<User>();
	private JPanel panel1;
	private JPanel panel2;
	private JTextPane textPane;
	private JList<User> userList;
	private JScrollPane listScroller;
	private DefaultListModel dlm;
	private JTextField inputText = new JTextField();
	private JButton sendButton = new JButton("Send");
	private JButton connectButton = new JButton("Connect");
	private DefaultListModel<String> model;
	private BorderLayout layout1 = new BorderLayout();
	private BorderLayout layout2 = new BorderLayout();
	private BorderLayout layout3 = new BorderLayout();

	public void addReciever(User user) {
		recieverList.add(user);
	}

	public ChatWindow(User user) {
		controller = new ChatController();

		this.user = user;
		setTitle(user.getName());
		textPane = new JTextPane();
		setPreferredSize(new Dimension(400, 400));
		setLayout(layout3);
		setResizable(false);

		panel1 = new JPanel(layout1);
		panel2 = new JPanel(layout2);
		dlm = new DefaultListModel();
		userList = new JList(dlm);
		
		userList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		userList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		userList.setVisibleRowCount(-1);
		listScroller = new JScrollPane(userList);
		
		

		textPane.setPreferredSize(new Dimension(300, 300));
		listScroller.setPreferredSize(new Dimension(100, 300));

		panel1.add(textPane, layout1.CENTER);
		panel1.add(listScroller, layout1.EAST);

		inputText.setPreferredSize(new Dimension(300, 50));
		sendButton.setPreferredSize(new Dimension(100, 50));
		sendButton.addActionListener(this);

		panel2.add(inputText, layout2.CENTER);
		panel2.add(sendButton, layout2.EAST);

		add(panel1, layout3.NORTH);
		add(panel2, layout3.SOUTH);
		controller.addListener(this);

		this.pack();
		this.setVisible(true);
	}
	
	public void addUserToList(User user) {
		dlm.addElement(user.getName());
	}

	public User getUser() {
		return user;
	}

	public void sendMessage(String message) {
		Message messageToSend = new Message(getUser(), recieverList, message, image);
		printMessage(messageToSend);
	}

	public void printMessage(Message message) {
		String str = "";
		str += (message.getText());
		// Save str in chatlog.
		// Update chatlog.
		// Set textPane to new updated chatlog.
		textPane.setText(str);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == sendButton) {
			if (!inputText.getText().isEmpty()) {
				sendMessage(inputText.getText());
				// Skicka till servern.
				inputText.setText("");
			}
		}
	}

	public static void main(String[] args) {
		ChatWindow chatWindow = new ChatWindow(new User("Henrik"));
	}

}
