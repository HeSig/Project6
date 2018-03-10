package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ClientUI extends JPanel implements ActionListener {
    private JButton btnSend = new JButton("Tryck");
    private JTextArea taShowMessage = new JTextArea(""); 
    private Controller controller;

    public ClientUI(Controller controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        taShowMessage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        taShowMessage.setPreferredSize(new Dimension(300,80));
        btnSend.addActionListener(this);

        add(btnSend, BorderLayout.CENTER);
        add(taShowMessage, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) {
        String send = "test";
        controller.newSend(send);
    }
    
    public void setMessageReceived(String result) {
    	taShowMessage.setText(result);
    }

    public static void main(String[] args) {
    	ClientUI ui = new ClientUI(null);
    	ui.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    	JOptionPane.showMessageDialog( null, ui );
    }
}
