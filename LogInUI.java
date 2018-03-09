package gruppuppgift;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class LogInUI extends JPanel implements ActionListener, DocumentListener {
	private JLabel username = new JLabel("Username:");
	private JLabel title = new JLabel("Choose username and picture to connect");
	private JTextField tfUsername = new JTextField();
	private JButton btnLogIn = new JButton("Connect");
	private JButton btnPic = new JButton("Choose Picture");
	private JLabel titleimage = new JLabel();
	private ImageIcon profilepic = null;
	
	public LogInUI(String filename) {	
		setBackground(Color.CYAN);
		btnLogIn.setEnabled(false);
		
		ImageIcon image = new ImageIcon(filename);
		Image transImage = image.getImage();
		Image scaledImage = transImage.getScaledInstance(150, 120, Image.SCALE_SMOOTH);		
		image = new ImageIcon(scaledImage);
		
		tfUsername.getDocument().addDocumentListener(this);
        btnLogIn.addActionListener(this);
        btnPic.addActionListener(this);
        add(northPanel(image));
        add(usernamePanel());
        add(btnPanel());
	}
	
	
	public JPanel northPanel(ImageIcon image) {
		JPanel northpanel = new JPanel(new BorderLayout());
		northpanel.setBackground(Color.CYAN);
		northpanel.setPreferredSize(new Dimension(800,200));
		titleimage.setPreferredSize(new Dimension(150,150));
		titleimage.setHorizontalAlignment(JLabel.CENTER);
		titleimage.setIcon(image);
		title.setFont(new Font("SansSerif", Font.BOLD, 30));
		title.setHorizontalAlignment(JLabel.CENTER);
		northpanel.add(titleimage, BorderLayout.NORTH);
		northpanel.add(title, BorderLayout.CENTER);
		
		return northpanel;
	}
	
	public JPanel usernamePanel() {
		JPanel userpanel = new JPanel(new GridLayout(1,2));
		userpanel.setBackground(Color.CYAN);
		userpanel.setPreferredSize(new Dimension(800,50));
		username.setFont(new Font("SansSerif", Font.PLAIN, 25));
		username.setHorizontalAlignment(JLabel.CENTER);
		tfUsername.setHorizontalAlignment(JLabel.CENTER);
		tfUsername.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		userpanel.add(username);
		userpanel.add(tfUsername);
		
		return userpanel;
	}
	
	public JPanel btnPanel() {
		JPanel btnpanel = new JPanel(new BorderLayout());
		btnpanel.setBackground(Color.CYAN);
		btnpanel.setPreferredSize(new Dimension(200,200));
		btnLogIn.setPreferredSize(new Dimension(200,70));
		btnPic.setPreferredSize(new Dimension(200,70));
		btnLogIn.setHorizontalAlignment(JButton.CENTER);
		btnPic.setHorizontalAlignment(JButton.CENTER);
		btnpanel.add(btnPic, BorderLayout.NORTH);
		btnpanel.add(btnLogIn, BorderLayout.SOUTH);
		
		return btnpanel;
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
			Image scaledImage = transImage.getScaledInstance(150, 120, Image.SCALE_SMOOTH);		
			image = new ImageIcon(scaledImage);
			titleimage.setIcon(image);
			return image;
		}
		return null;
	}
	
	public void checkLogIn() {
		if(profilepic != null && !tfUsername.getText().isEmpty()) {
			btnLogIn.setEnabled(true);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnPic) {
			profilepic = choosePicture();
			checkLogIn();
		
		}
		if(e.getSource()==btnLogIn) {
			//controller.connect(new User(profilepic, tfUsername.getText()));
			//Exit
		}
	}
	
	public void insertUpdate(DocumentEvent e) {
		checkLogIn();
    }
	
    public void removeUpdate(DocumentEvent e) {
    	checkLogIn();
    	if(tfUsername.getText().isEmpty()) {
    		btnLogIn.setEnabled(false);
    	}
    }
    
    public void changedUpdate(DocumentEvent e) {
    	checkLogIn();
    }
	
	public static void main(String[] args) {
		LogInUI ui = new LogInUI("images/comment.png");
    	JFrame frame = new JFrame("LogIn application");
    	frame.setPreferredSize(new Dimension(800,500));
    	frame.setLayout(new BorderLayout());
    	frame.pack();
    	frame.add(ui);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setVisible(true);
	}

}
