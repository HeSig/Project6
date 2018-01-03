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

public class Test1UI extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	// Dimensions of boxes
	private Dimension box = new Dimension(32, 32);
	private Controller controller;
	// 2D arrays
	// private Array7x7 array7x7 = new Array7x7();
	private Array7x7 savedArray = new Array7x7();
	private JLabel[][] cTextBox = new JLabel[7][7];
	private JPanel[][] arrayPanelsList = new JPanel[7][7];
	private JTextField[] verticalText = new JTextField[7];
	private JTextField[] horizontalText = new JTextField[7];
	private JPanel[] verticalPanels = new JPanel[7];
	private JPanel[] horizontalPanels = new JPanel[7];
	private GridBagConstraints gbc = new GridBagConstraints();
	private JPanel arrayPanels = new JPanel(new GridBagLayout());
	private JPanel eastPanel = new JPanel(new BorderLayout());
	private JPanel eastNorth = new JPanel(new BorderLayout());
	private JPanel eastNorthNorth = new JPanel(new BorderLayout());
	private JPanel eastSouthNorth = new JPanel(new BorderLayout());
	private JPanel eastSouth = new JPanel(new BorderLayout());
	private JPanel eastCenter = new JPanel(new BorderLayout());
	private JTextField rowText = new JTextField("Rad nr:");
	private JTextField colText = new JTextField("Kolumn nr: ");
	private JTextField rowNo = new JTextField();
	private JTextField colNo = new JTextField();
	private JButton readRow = new JButton("Läs rad");
	private JButton writeRow = new JButton("Skriv rad");
	private JButton readCol = new JButton("Läs kolumn");
	private JButton writeCol = new JButton("Skriv kolumn");
	private JButton save = new JButton("Spara array");
	private JButton load = new JButton("Ladda array");

	public Test1UI(Controller controller) {
		readRow.addActionListener(this);
		writeRow.addActionListener(this);
		readCol.addActionListener(this);
		writeCol.addActionListener(this);
		save.addActionListener(this);
		load.addActionListener(this);

		setLayout(new BorderLayout());
		gbc.insets = new Insets(8, 8, 8, 8);

		this.controller = controller;
		// draw textboxes.
		for (int i = 0; i < 8; i++) {
			// Draw bottom panels
			if (i == 7) {
				for (int j = 0; j < 7; j++) {
					horizontalPanels[j] = new JPanel();
					horizontalPanels[j].setPreferredSize(box);
					horizontalText[j] = new JTextField();
					horizontalText[j].setPreferredSize(box);
					horizontalPanels[j].add(horizontalText[j]);
					gbc.gridx = j + 1;
					gbc.gridy = i;
					arrayPanels.add(horizontalPanels[j], gbc);
				}
			}

			for (int j = 0; j < 8; j++) {
				gbc.gridx = j;
				gbc.gridy = i;
				// Draw the center panels
				if (j > 0 && i < 7) {
					arrayPanelsList[i][j - 1] = new JPanel();
					arrayPanelsList[i][j - 1].setPreferredSize(box);
					cTextBox[i][j - 1] = new JLabel();
					cTextBox[i][j - 1].setBackground(Color.GRAY);
					cTextBox[i][j - 1].setText(Integer.toString(controller.getArray().getElement(i, j - 1)));
					arrayPanelsList[i][j - 1].add(cTextBox[i][j - 1]);
					arrayPanelsList[i][j - 1].setBackground(Color.gray);
					arrayPanels.add(arrayPanelsList[i][j - 1], gbc);
				}
				// Draw the left panels
				if (j == 0 && i < 7) {
					verticalPanels[i] = new JPanel();
					verticalPanels[i].setPreferredSize(box);
					verticalText[i] = new JTextField();
					verticalText[i].setPreferredSize(box);
					verticalPanels[i].add(verticalText[i]);
					arrayPanels.add(verticalPanels[i], gbc);
				}

			}
		}
		for (int i = 0; i < 7; i++) {
			verticalText[i].setText(Integer.toString(controller.getLeftColumn().getElement(i)));
			horizontalText[i].setText(Integer.toString(controller.getRightColumn().getElement(i)));
		}

		// East;
		rowText.setEditable(false);
		colText.setEditable(false);
		rowNo.setPreferredSize(box);
		colNo.setPreferredSize(box);
		readRow.setPreferredSize(new Dimension(124, 32));
		readCol.setPreferredSize(new Dimension(124, 32));
		writeRow.setPreferredSize(new Dimension(124, 32));
		writeCol.setPreferredSize(new Dimension(124, 32));
		eastNorthNorth.add(rowText, BorderLayout.WEST);
		eastNorthNorth.add(rowNo, BorderLayout.EAST);
		eastNorth.add(readRow, BorderLayout.CENTER);
		eastNorth.add(writeRow, BorderLayout.SOUTH);
		eastNorth.add(eastNorthNorth, BorderLayout.NORTH);
		eastSouthNorth.add(colText, BorderLayout.WEST);
		eastSouthNorth.add(colNo, BorderLayout.EAST);
		eastSouth.add(eastSouthNorth, BorderLayout.NORTH);
		eastSouth.add(readCol, BorderLayout.CENTER);
		eastSouth.add(writeCol, BorderLayout.SOUTH);
		eastCenter.add(save, BorderLayout.NORTH);
		eastCenter.add(load, BorderLayout.SOUTH);

		eastPanel.add(eastCenter, BorderLayout.CENTER);
		eastPanel.add(eastSouth, BorderLayout.SOUTH);
		eastPanel.add(eastNorth, BorderLayout.NORTH);
		add(arrayPanels, BorderLayout.CENTER);
		add(eastPanel, BorderLayout.EAST);
	}

	public void reDraw() {
		for (int col = 0; col < 7; col++) {
			for (int row = 0; row < 7; row++) {
				cTextBox[row][col].setText(Integer.toString(controller.getArray().getElement(row, col)));
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == readCol) {
			try {
				int col = Integer.parseInt(colNo.getText()) - 1;
				for (int i = 0; i < 7; i++) {
					verticalText[i].setText(cTextBox[i][col].getText());

				}
			} catch (NumberFormatException e1) {
				System.out.println("Ingen kolumn vald");
			}catch (ArrayIndexOutOfBoundsException e1) {
				System.out.println("Skriv en kolumn mellan 1 och 7");
			}

		}
		if (e.getSource() == writeCol) {
			try {
				int col = Integer.parseInt(colNo.getText()) - 1;
				for (int i = 0; i < 7; i++) {
					controller.getArray().setElement(i, col, Integer.parseInt(verticalText[i].getText()));
					reDraw();
				}
			} catch (NumberFormatException e1) {
				System.out.println("Ingen kolumn vald");
			}catch (ArrayIndexOutOfBoundsException e1) {
				System.out.println("Skriv en kolumn mellan 1 och 7");
			}

		}
		if (e.getSource() == readRow)

		{
			try {
				int row = Integer.parseInt(rowNo.getText()) - 1;
				for (int i = 0; i < 7; i++) {
					horizontalText[i].setText(cTextBox[row][i].getText());
				}
			} catch (NumberFormatException e1) {
				System.out.println("Ingen rad vald");
			}catch (ArrayIndexOutOfBoundsException e1) {
				System.out.println("Skriv en rad mellan 1 och 7");
			}

		}
		if (e.getSource() == writeRow) {
			try {
				int row = Integer.parseInt(rowNo.getText()) - 1;
				for (int i = 0; i < 7; i++) {
					controller.getArray().setElement(row, i, Integer.parseInt(horizontalText[i].getText()));
					reDraw();
				}
			} catch (NumberFormatException e1) {
				System.out.println("Ingen rad vald");
			} catch (ArrayIndexOutOfBoundsException e1) {
				System.out.println("Skriv en rad mellan 1 och 7");
			}

		}
		if (e.getSource() == save) {
			savedArray.setArray(controller.getArray());
		}

		if (e.getSource() == load) {
			controller.getArray().setArray(savedArray);
			reDraw();
		}
	}
}
