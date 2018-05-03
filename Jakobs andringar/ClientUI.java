package danielsV2;

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
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Class which represents a GUI which the user will interact with when using the chat
 * @author Jakob Kennerberg, Daniel Rosdahl
 *
 */
public class ClientUI extends JPanel implements ActionListener {
	private JButton btnSend = new JButton("Send");
	private JButton btnAddPicture = new JButton("Add Picture");
	private JTextArea taShowMessage = new JTextArea(""); 
	private JTextArea taClients = new JTextArea("ONLINE");
	private JTextArea taTextToSend = new JTextArea("TO SEND");
	private JTextArea taReceivers = new JTextArea("1, 2, 3");
	private JPanel panelSouth = new JPanel();
	private JPanel panelNorthWest = new JPanel();
	private JPanel panelNorth = new JPanel();
	private JPanel buttonPanel = new JPanel();
	private JLabel lblIcon = new JLabel();
	private JLabel lblUsername = new JLabel();
	private JLabel lblUserIcon = new JLabel();
	private ClientController controller;
	private User user;
	private Icon image;

	/**
	 * Constructor
	 * @param controller
	 * @param user
	 */
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
		taTextToSend.setPreferredSize(new Dimension(250,80));
		taReceivers.setBorder(BorderFactory.createLineBorder(Color.RED));
		taReceivers.setPreferredSize(new Dimension(150,80));
		btnSend.setSize(new Dimension(100, 40));
		btnSend.addActionListener(this);
		btnAddPicture.setSize(new Dimension(100,40));
		btnAddPicture.addActionListener(this);
		panelNorth.setLayout(new BorderLayout());
		panelNorth.setPreferredSize(new Dimension(600, 300));
		panelNorthWest.setLayout(new BorderLayout());
		panelNorthWest.setSize(new Dimension(100, 40));
		panelSouth.setLayout(new BorderLayout());
		panelSouth.setPreferredSize(new Dimension(600, 270));
		buttonPanel.setPreferredSize(new Dimension(600, 80));
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
		panelNorth.add(taShowMessage, BorderLayout.CENTER);
		panelNorth.add(lblIcon, BorderLayout.EAST);
		
		panelNorthWest.add(lblUsername, BorderLayout.NORTH);
		panelNorthWest.add(lblUserIcon, BorderLayout.SOUTH);
		
		buttonPanel.add(btnSend, BorderLayout.EAST);
		buttonPanel.add(btnAddPicture, BorderLayout.WEST);
		
		panelSouth.add(taTextToSend, BorderLayout.WEST);
		panelSouth.add(taClients, BorderLayout.CENTER);
		panelSouth.add(taReceivers, BorderLayout.EAST);
		panelSouth.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Method which allows the user to choose a picture from the computer
	 * @return
	 */
	public Icon choosePicture() {
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
			return image;
		}
		return null;
	}

	/**
	 * ActionListeners
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnSend) {
			
			if(taReceivers.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Du måste fylla i mottagare");
				
			}else {
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
		if(e.getSource()==btnAddPicture) {
			image = choosePicture();
		}
	}

	/**
	 * Method that updates the GUI when a message is received
	 * @param msg
	 */
	public void setMessageReceived(Message msg) {
		User user = msg.getUser();
		String oldText = taShowMessage.getText();
		String toPrint = oldText + user.getUsername() + "(" + msg.getServerReceivedDate() + "): " + msg.getText() + "\n";
		taShowMessage.setText(toPrint);
		lblIcon.setIcon(msg.getImage());
	}

	/**
	 * Method that updates the GUI with users online in the system
	 * @param list
	 */
	public void setOnlineList(ArrayList<String> list) {
		taClients.setText("");
		for(String s : list) {
			taClients.setText(taClients.getText() + "\n" + s);
		}
	}

//	public static void main(String[] args) {
//		try {
//			User user = new User("Benke");
//			ClientUI ui = new ClientUI(new ClientController(new Client("127.0.0.1", 1222, user)), user);
//			ui.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//			JOptionPane.showMessageDialog( null, ui );
//		}catch(IOException e) {}
//	}
}
