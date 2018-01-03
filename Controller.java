package p6;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Controller {

	private static JPanel ui;
	private Array7x7 array;
	private Array7x7[] charArray = new Array7x7[5];
	private Array7 leftColumn;
	private Array7 rightColumn;

	// private static int[][] charA =
	// {{Color.BLUE,Color.BLUE,Color.BLUE,Color.WHITE,Color.BLUE,Color.BLUE,Color.BLUE},
	// {Color.BLUE,Color.BLUE,Color.WHITE,Color.BLUE,Color.WHITE,Color.BLUE,Color.BLUE},
	// {Color.BLUE,Color.BLUE,Color.WHITE,Color.BLUE,Color.WHITE,Color.BLUE,Color.BLUE},
	// {Color.BLUE,Color.BLUE,Color.WHITE,Color.WHITE,Color.WHITE,Color.BLUE,Color.BLUE},
	// {Color.BLUE,Color.WHITE,Color.BLUE,Color.BLUE,Color.BLUE,Color.WHITE,Color.BLUE},
	// {Color.BLUE,Color.WHITE,Color.BLUE,Color.BLUE,Color.BLUE,Color.WHITE,Color.BLUE},
	// {Color.BLUE,Color.WHITE,Color.BLUE,Color.BLUE,Color.BLUE,Color.WHITE,Color.BLUE}};

	public Array7x7 getCharArray(int i) {
		return charArray[i];
	}

	public void setWord(String word) {
		for (int z = 0; z < word.length(); z++) {
			charArray[z].setArray(Chars.getChar(word.charAt(z)));
			for (int y = 0; y < 7; y++) {
				for (int x = 0; x < 7; x++) {
					if (charArray[z].getElement(y, x) == 1) {
						charArray[z].setElement(y, x, Color.WHITE);
					} else {
						charArray[z].setElement(y, x, Color.BLUE);
					}
				}
			}
		}
	}

	public void convertToColor() {
		for (int y = 0; y < 7; y++) {
			for (int x = 0; x < 7; x++) {
				if (array.getElement(y, x) == 1) {
					array.setElement(y, x, Color.WHITE);
				} else {
					array.setElement(y, x, Color.BLUE);
				}
			}
		}

	}

	public Controller(Array7x7 array7x7) {
		this.array = array7x7;
	}

	public Controller(Array7x7[] arr) {
		this.charArray = arr;

	}

	public Controller(Array7x7 array, Array7 leftColumn, Array7 rightColumn) {
		this.array = array;
		this.leftColumn = leftColumn;
		this.rightColumn = rightColumn;
	}

	public JPanel getUi() {
		return ui;
	}

	@SuppressWarnings("static-access")
	public void setUi(JPanel ui) {
		this.ui = ui;
	}

	public Array7x7 getArray() {
		return array;
	}

	public void setArray(Array7x7 array) {
		this.array = array;
	}

	public Array7 getLeftColumn() {
		return leftColumn;
	}

	public void setLeftColumn(Array7 leftColumn) {
		this.leftColumn = leftColumn;
	}

	public Array7 getRightColumn() {
		return rightColumn;
	}

	public void setRightColumn(Array7 rightColumn) {
		this.rightColumn = rightColumn;
	}

	public static void main(String[] args) {
		int[] leftC = new int[] { 3, 2, 1, 4, 6, 4, 8 };
		int[] rightC = new int[] { 3, 1, 5, 3, 5, 1, 5 };
		Array7x7[] charArr = new Array7x7[5];
		for (int i = 0; i < 5; i++) {
			charArr[i] = new Array7x7();
		}
		Array7x7 arr7x7 = new Array7x7();
		int answer = Integer.parseInt(JOptionPane
				.showInputDialog("1. Test1UI\n" + "2. Test2UI\n" + "3. Test3UI\n" + "4. Test4UI\n" + "0. avsluta."));
		JFrame frame = new JFrame("Test2UI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		switch (answer) {
		case 1:
			Controller con1 = new Controller(arr7x7, new Array7(leftC), new Array7(rightC));
			Test1UI ui1 = new Test1UI(con1);
			con1.setUi(ui1);
			frame.add(ui1);
			break;
		case 2:
			Controller con2 = new Controller(arr7x7, new Array7(leftC), new Array7(rightC));
			Test2UI ui2 = new Test2UI(con2);
			con2.setUi(ui2);
			frame.add(ui2);
			break;
		case 3:
			Controller con3 = new Controller(arr7x7);
			arr7x7.setArray(Chars.getChar('B'));
			Test3UI ui3 = new Test3UI(con3);

			con3.setUi(ui3);
			frame.add(ui3);
			break;
		case 4:
			
			String word = JOptionPane.showInputDialog("Skriv ett ord på 5 bokstäver");
			while (word.length() != 5) {
				word = JOptionPane.showInputDialog("Skriv ett ord på 5 bokstäver");
			}
			Controller con4 = new Controller(charArr);
			con4.setWord(word.toUpperCase());
			Test4UI ui4 = new Test4UI(con4);
			con4.setUi(ui4);
			frame.add(ui4);
			final ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
			ses.scheduleAtFixedRate(new Runnable(){
				@Override
				public void run() {
					ui4.moveLeft();
				}
				
			}, 0, 500, TimeUnit.MILLISECONDS);
			break;
		case 0:
			System.exit(0);
		}
		frame.pack();
		frame.setVisible(true);
	}

}
