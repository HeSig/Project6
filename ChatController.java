package client;

import java.util.*;

import javax.swing.JFrame;

import message.Message;

public class ChatController {
	private ArrayList<ChatWindow> windows;

	public ChatController() {
		windows = new ArrayList<ChatWindow>();

	}

	public void addListener(ChatWindow chatWindow) {
		windows.add(chatWindow);
	}

	public void sendMessage(Message message) {
		for (ChatWindow window : windows) {
			if (window.getUser() == message.getUser()) {
				User user = window.getUser();
			}
		}
	}

	public static void main(String[] args) {
		User user = new User("Henrik");
		ChatWindow frame = new ChatWindow(user);
		frame.setVisible(true);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
