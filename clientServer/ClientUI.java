package gruppuppgift;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
    private JTextArea taShowMessage = new JTextArea(""); 
    private ClientController controller;

    public ClientUI(ClientController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        taShowMessage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        taShowMessage.setPreferredSize(new Dimension(300,80));
        btnSend.addActionListener(this);

        add(btnSend, BorderLayout.CENTER);
        add(taShowMessage, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) {
    	Icon image = new ImageIcon("bilder/bildUser.png");
    	User user = new User("Daniel");
    	LinkedList<User> list = new LinkedList<User>();
    	list.add(user);
    	String name = "Daniel";
    	String text = "någon text";
    	
    	Message msg = new Message(user, list, text);
    	
    	System.out.println(msg);
    	
        controller.newSend(msg);
    }
    
    public void setMessageReceived(Message msg) {
    	taShowMessage.setText(msg.toString());
    }

    public static void main(String[] args) {
    	ClientUI ui = new ClientUI(null);
    	ui.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    	JOptionPane.showMessageDialog( null, ui );
    }
}
