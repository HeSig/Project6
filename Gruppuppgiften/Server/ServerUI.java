package Server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class ServerUI extends JFrame implements ActionListener, DocumentListener {
	private JLabel lblIP = new JLabel("IP: " + getIP(), SwingConstants.CENTER);
	private JLabel lblServerStatus = new JLabel("Server off", SwingConstants.CENTER);
	private JLabel lblPort = new JLabel("Port: ");
	private JLabel lblExample = new JLabel("Use format: 2018, June, 03, 23.02??", SwingConstants.CENTER);
	private JLabel lblFromTime = new JLabel("From: ");
	private JLabel lblToTime = new JLabel("To: ");
	private JTextField tfPort = new JTextField("1222");
	private JTextField tfFromTime = new JTextField();
	private JTextField tfToTime = new JTextField();
	private JTextArea tfTraficLog = new JTextArea();
	private JButton btnLog = new JButton("Log trafic");
	private JButton btnStartServer = new JButton("Start Server");
	private int port;
	
	public ServerUI() {
		setSize(new Dimension(600, 700));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		add(mainPanel());
		pack();
		setVisible(true);
		
		btnLog.addActionListener(this);
		btnStartServer.addActionListener(this);
		tfPort.getDocument().addDocumentListener(this);
		tfFromTime.getDocument().addDocumentListener(this);
		tfToTime.getDocument().addDocumentListener(this);
	}
	
	public JPanel mainPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(600, 700));
		panel.setBackground(Color.CYAN);
		Font fontLabels = new Font("SansSerif", Font.BOLD, 25);
		Font fontBtns = new Font("SansSerif", Font.BOLD, 18);
		
		tfTraficLog.setBounds(0, 300, 600, 400);
		tfTraficLog.setEnabled(false);
		
		JScrollPane scroll = new JScrollPane(tfTraficLog);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(0, 300, 600, 400);
		panel.add(scroll);
		
		lblIP.setFont(fontLabels);
		lblIP.setBounds(175, 0, 250, 50);
		panel.add(lblIP);
		
		lblExample.setBounds(10, 150, 590, 30);
		lblExample.setFont(fontLabels);
		lblExample.setForeground(Color.BLUE);
		lblFromTime.setBounds(0, 200, 150, 40);
		lblFromTime.setFont(fontLabels);
		tfFromTime.setBounds(100, 200, 300, 40);
		lblToTime.setBounds(0, 250, 150, 40);
		lblToTime.setFont(fontLabels);
		tfToTime.setBounds(100, 250, 300, 40);
		panel.add(lblExample);
		panel.add(lblFromTime);
		panel.add(lblToTime);
		panel.add(tfFromTime);
		panel.add(tfToTime);	
		
		btnLog.setBounds(450, 200, 100, 90);
		btnLog.setFont(fontBtns);
		btnLog.setEnabled(false);
		panel.add(btnLog);
		
		lblServerStatus.setFont(fontLabels);
		lblServerStatus.setForeground(Color.RED);
		lblServerStatus.setBounds(200, 40 , 200, 50);
		panel.add(lblServerStatus);
		
		lblPort.setFont(fontLabels);
		lblPort.setBounds(125, 100, 100, 40);
		tfPort.setBounds(200, 100, 130, 40);
		btnStartServer.setBounds(350, 100, 150, 40);
		btnStartServer.setFont(fontBtns);
		btnStartServer.setEnabled(false);
		panel.add(lblPort);
		panel.add(tfPort);
		panel.add(btnStartServer);
		
		return panel;
	}
	
	public String getIP() {
		try {
			InetAddress localIP = InetAddress.getLocalHost();
			return localIP.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return "Failed to find IP";
	}
	
	public void checkPort() {
		if(!tfPort.getText().isEmpty()) {
			btnStartServer.setEnabled(true);
		} 
	}
	
	public void checkLog() {
		if(!tfFromTime.getText().isEmpty() && !tfToTime.getText().isEmpty()) {
			btnLog.setEnabled(true);
		}
	}
	
	public void addLog(String message) {
		tfTraficLog.append(message + "\n");
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnLog) {
			
		}
		if(e.getSource()==btnStartServer) {
			port = (Integer.parseInt(tfPort.getText()));
			
			try(Socket socket = new Socket("localhost", port)) {
				tfPort.setText("");
				System.out.println(socket);
				JOptionPane.showMessageDialog(null, "Port unavailable");
			}catch (IOException e1) {
				tfPort.setEnabled(false);
				lblServerStatus.setText("Server on");
				lblServerStatus.setForeground(Color.GREEN);
				Server server =  new Server(port, this);
			}
		}
	}
	
	public void insertUpdate(DocumentEvent e) {
		checkPort();
		checkLog();
	}

	public void removeUpdate(DocumentEvent e) {
		checkPort();
		if(tfPort.getText().isEmpty()) {
			btnStartServer.setEnabled(false);
		}
		if(tfFromTime.getText().isEmpty() || tfToTime.getText().isEmpty()) {
			btnLog.setEnabled(true);
		}
	}

	public void changedUpdate(DocumentEvent e) {
		checkPort();
		checkLog();
	}
	
	public static void main(String[] args) {
		ServerUI ui = new ServerUI();
	}
}
