package p6;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Test2UI extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	//Dimensions of boxes
	private Dimension box = new Dimension(32, 32);
	private M2Controller controller;
	//2D arrays
	private JLabel[][] cTextBox = new JLabel[7][7];
	private JPanel[][] arrayPanelsList = new JPanel[7][7];
	private JTextField[] verticalTextLeft = new JTextField[7];
	private JTextField[] verticalTextRight = new JTextField[7];
	private JPanel[] verticalPanelsLeft = new JPanel[7];
	private JPanel[] verticalPanelsRight = new JPanel[7];
	private JPanel btnPanel = new JPanel(new BorderLayout());
	private GridBagConstraints gbc = new GridBagConstraints();
	private JPanel arrayPanels = new JPanel(new GridBagLayout());
	private JButton moveLeft = new JButton("<- Flytta vänster <-");
	private JButton moveRight = new JButton("-> Flytta Höger ->");


	public Test2UI(M2Controller controller) {
		moveLeft.addActionListener(this);
		moveRight.addActionListener(this);

		setLayout(new BorderLayout());
		gbc.insets = new Insets(8, 8, 8, 8);

		this.controller = controller;
		//draw textboxes.
		for (int i = 0; i < 8; i++) {

			for (int j = 0; j < 9; j++) {
				gbc.gridx = j;
				gbc.gridy = i;
				//Draw the center panel
				if (j > 0 && i < 7) {
					if(j < 8) {

						arrayPanelsList[i][j - 1] = new JPanel();
						arrayPanelsList[i][j - 1].setPreferredSize(box);
						cTextBox[i][j - 1] = new JLabel();
						cTextBox[i][j - 1].setText(Integer.toString(this.controller.getArray().getElement(i, j - 1)));
						arrayPanelsList[i][j - 1].add(cTextBox[i][j - 1]);
						arrayPanelsList[i][j - 1].setBackground(Color.gray);
						arrayPanels.add(arrayPanelsList[i][j - 1], gbc);
					}
				}
				//Draw the left panel
				if (j == 0 && i < 7) {
					verticalPanelsLeft[i] = new JPanel();
					verticalPanelsLeft[i].setPreferredSize(box);
					verticalTextLeft[i] = new JTextField();
					verticalTextLeft[i].setPreferredSize(box);
					verticalPanelsLeft[i].add(verticalTextLeft[i]);
					arrayPanels.add(verticalPanelsLeft[i], gbc);
				}


				//Draw the right panel
				if (j == 8 && i < 7) {
					verticalPanelsRight[i] = new JPanel();
					verticalPanelsRight[i].setPreferredSize(box);
					verticalTextRight[i] = new JTextField();
					verticalTextRight[i].setPreferredSize(box);
					verticalPanelsRight[i].add(verticalTextRight[i]);
					arrayPanels.add(verticalPanelsRight[i], gbc);
				}

			}
		}
		for(int i = 0; i < 7; i++) {
			verticalTextLeft[i].setText(Integer.toString(controller.getLeftColumn().getElement(i)));
			verticalTextRight[i].setText(Integer.toString(controller.getRightColumn().getElement(i)));
		}

		add(arrayPanels, BorderLayout.CENTER);
		add(btnPanel, BorderLayout.SOUTH);
		btnPanel.add(moveLeft, BorderLayout.WEST);
		btnPanel.add(moveRight, BorderLayout.EAST);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == moveLeft) {
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 7; j++) {
					if(i == 0) {
						verticalTextLeft[j].setText(cTextBox[j][i].getText());
					}
					if(i > 0 && i < 7) {
						cTextBox[j][i-1].setText(cTextBox[j][i].getText());
					}
					if(i == 7) {
						cTextBox[j][6].setText(verticalTextRight[j].getText());
					}
				}
			}

		}
		if (e.getSource() == moveRight) {
			for(int i = 7; i >= 0; i--) {
				for(int j = 0; j < 7; j++) {
					if(i == 7) {
						verticalTextRight[j].setText(cTextBox[j][i-1].getText());
					}
					if(i > 0 && i < 7) {
						cTextBox[j][i].setText(cTextBox[j][i-1].getText());
					}
					if(i == 0) {
						cTextBox[j][0].setText(verticalTextLeft[j].getText());
					}
				}
			}

		}
	}

}
