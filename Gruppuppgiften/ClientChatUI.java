package gruppuppgift;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ClientChatUI extends JPanel implements ActionListener, ListSelectionListener, DocumentListener {
	private User user;
	private ChatController controller;
	private JFrame frame;
	private DefaultListModel dlm;
	private DefaultListModel dlmContacts;
	private JList<User> onlineList;
	private JList<User> contactList;
	private ArrayList<String> stringList = new ArrayList<String>();
	private JLabel profileImage = new JLabel();
	private JLabel messageImage = new JLabel();
	private JTextArea tfrecievedMessages = new JTextArea();
	private JTextField tfwriteMessage = new JTextField();
	private JButton btnAddContact = new JButton("Add contact");
	private JButton btnAddPicture = new JButton("Add picture");
	private JButton btnSend = new JButton("Send");
	private JButton btnDisconnect = new JButton("Disconnect");
	private Font btnFont = new Font("SansSerif", Font.PLAIN, 10);
	private ImageIcon image;
	
	public ClientChatUI(User user, ChatController controller) {
		this.user = user;
		this.controller = controller;
		setBackground(Color.CYAN);
		dlmContacts = new DefaultListModel();
		dlm = new DefaultListModel();
		onlineList = new JList(dlm);
		onlineList.addListSelectionListener(this);
		onlineList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		onlineList.setLayoutOrientation(JList.VERTICAL);
		onlineList.setVisibleRowCount(-1);
		
		contactList = new JList(dlmContacts);
		contactList.addListSelectionListener(this);
		contactList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		contactList.setLayoutOrientation(JList.VERTICAL);
		contactList.setVisibleRowCount(-1);
		
		btnDisconnect.setBounds(800, 0, 100, 50);
		btnDisconnect.setFont(btnFont);
		add(btnDisconnect);
		
		setLayout(null);
		add(upperPanel(contactList));
		add(bottomPanel(onlineList));
		btnSend.addActionListener(this);
		btnAddPicture.addActionListener(this);
		btnAddContact.addActionListener(this);
		btnDisconnect.addActionListener(this);
		tfwriteMessage.getDocument().addDocumentListener(this);
		
		frame = new JFrame(user.getUsername());
		frame.setPreferredSize(new Dimension(900, 600));
		frame.pack();
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   		frame.setVisible(true);
   		frame.setResizable(false);
		
	}
	
	public JPanel upperPanel(JList<User> contactList) {
		JPanel panel = new JPanel(null);
		panel.setBounds(0, 0, 800, 340);
		panel.setBackground(Color.CYAN);
		
		JScrollPane contactScroller  = new JScrollPane(contactList);
		contactScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		contactScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contactScroller.setBounds(0, 120, 150, 220);
		contactScroller.setBorder(BorderFactory.createTitledBorder("Contacts"));
		contactScroller.setBackground(Color.CYAN);
		
		profileImage.setBounds(0, 0, 150, 120);
		profileImage.setIcon(user.getUserImage());
		
		tfrecievedMessages.setBounds(150, 0, 680, 340);
		tfrecievedMessages.setEnabled(false);
		JScrollPane messageScroller = new JScrollPane(tfrecievedMessages);
		messageScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		messageScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		messageScroller.setBounds(150, 0, 680, 340);
		
		panel.add(messageScroller);
		panel.add(profileImage);
		panel.add(contactScroller);
		return panel;
	}
	
	public void messageRecieved(String msg) {
		tfrecievedMessages.append(msg + "\n");
	}
	
	public void addUserToOnlineList(String user) {
		dlm.addElement(user);
	}
	
	public void addUserToContacts(User user) {
		dlmContacts.addElement(user.getUsername());
	}
	
	public JPanel bottomPanel(JList<User> onlineList) {
		JPanel panel = new JPanel(null);
		panel.setBounds(0, 340, 900, 260);
		panel.setBackground(Color.CYAN);
				
		JScrollPane onlineScroller  = new JScrollPane(onlineList);
		onlineScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		onlineScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		onlineScroller.setBounds(0, 30, 150, 210);
		onlineScroller.setBorder(BorderFactory.createTitledBorder("Online"));
		onlineScroller.setBackground(Color.CYAN);
		
		btnAddContact.setBounds(0, 0, 150, 30);
		btnAddContact.setEnabled(false);
		btnAddPicture.setBounds(800, 10, 100, 100);
		btnAddPicture.setFont(btnFont);
		btnSend.setEnabled(false);
		btnSend.setBounds(800, 130, 100, 100);
		btnSend.setFont(btnFont);
		
		tfwriteMessage.setBounds(150, 0, 500, 240);
		messageImage.setBounds(648, 4, 150, 232);
		messageImage.setOpaque(true);
		messageImage.setBackground(Color.WHITE);
		messageImage.setForeground(Color.WHITE);
		

		panel.add(btnSend);
		panel.add(btnAddPicture);
		panel.add(tfwriteMessage);
		panel.add(btnAddContact);
		panel.add(onlineScroller);
		panel.add(messageImage);
		return panel;
	}
	
	public void checkLogIn() {
		if(!tfwriteMessage.getText().isEmpty() || messageImage.getIcon() != null) {
			btnSend.setEnabled(true);
		}else {
			btnSend.setEnabled(false);
		}
	}
	
	public ImageIcon choosePicture() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG Bilder", "jpg", "png");
		chooser.setFileFilter(filter);
		int returnval = chooser.showOpenDialog(getParent());
		if(returnval == JFileChooser.APPROVE_OPTION) {
			String filename = chooser.getSelectedFile().getAbsolutePath();
			ImageIcon image = new ImageIcon(filename);
			Image transImage = image.getImage();
			Image scaledImage = transImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH);		
			image = new ImageIcon(scaledImage);
			messageImage.setIcon(image);
			return image;
		}
		return null;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnSend) {
			LinkedList<User> recieverList = new LinkedList<User>();
			recieverList = controller.getRecieverList(stringList);
			controller.sendMessage(new Message(user, recieverList, tfwriteMessage.getText(), image));
			System.out.println("Message sent");
			tfwriteMessage.setText("");
			messageImage.setIcon(null);
			checkLogIn();
		}
		if(e.getSource()==btnDisconnect) {
			ChatClient client = controller.getClient();
			client.disconnect(user);
			frame.dispose();
			LogInUI logInUI = new LogInUI();
		}
		if(e.getSource()==btnAddPicture) {
			 image = choosePicture();
			messageImage.setIcon(image);
			checkLogIn();
		}
		if(e.getSource()==btnAddContact) {
			User user = (User)dlm.get(onlineList.getSelectedIndex());
			controller.addContact(user);
		}
	}
	
	public void valueChanged(ListSelectionEvent e) {
		if(e.getSource()==onlineList) {
			 if(!e.getValueIsAdjusting()) {
		          //  System.out.println(dlm.get(onlineList.getSelectedIndex()));
			 		String username = (String)dlm.get(onlineList.getSelectedIndex());
			 		stringList.add(username);
			 		System.out.println(stringList.size());
			 		//onlineList.setSelectedValue(username, true);
			 		
			 		
			 		
			 		if(username == this.user.getUsername()) {
			 			btnAddContact.setEnabled(false);
			 		}else {
			 			btnAddContact.setEnabled(true);
			 		}
			}
		}
		
		if(e.getSource()==contactList) {
			
		}
	}
	
	public void insertUpdate(DocumentEvent e) {
		checkLogIn();
	}

	public void removeUpdate(DocumentEvent e) {
		checkLogIn();
	}

	public void changedUpdate(DocumentEvent e) {}
	
	public static void main(String[] args) {
		ImageIcon image = new ImageIcon("images/gubbe.jpg");
		ClientChatUI ui = new ClientChatUI(new User(image, "Jakob"), new ChatController());
		ui.addUserToOnlineList("Henke");
		ui.addUserToOnlineList("Folke");
		ui.addUserToOnlineList("Djordo");
		ui.addUserToOnlineList("Henke");
		ui.addUserToOnlineList("Folke");
	}
}
