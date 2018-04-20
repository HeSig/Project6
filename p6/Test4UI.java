package p6;

import java.awt.BorderLayout;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Test4UI extends JPanel {
	private String word;
	private ColorDisplay colorDisplay = new ColorDisplay(1, 5, Color.BLACK, Color.BLACK);
	private Controller controller;

	public Test4UI(Controller controller) {
		this.setLayout(new BorderLayout());
		this.controller = controller;
		for (int i = 0; i < 5; i++) {
			colorDisplay.setDisplay(controller.getCharArray(i).toIntArray(), 0, i);
		}
		colorDisplay.updateDisplay();
		add(colorDisplay, BorderLayout.CENTER);
	}

	public void moveLeft(String word1) {
		this.word = word1;
		int[][] temp = new int[7][word.length()];

		for (int i = 0; i < word.length(); i++) {
			for (int j = 0; j < 7; j++) {
				temp[j][i] = 0;
			}
		}
		
		final ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
		ses.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				for (int z = 0; z < word.length(); z++) {
					for (int y = 0; y < 7; y++) {
						for (int x = 0; x < 7; x++) {
							if (x == 0) {
								temp[y][z] = controller.getCharArray(z).getElement(y, x);
								if (z != word.length()-1) {
									temp[y][z + 1] = controller.getCharArray(z + 1).getElement(y, x);
								}
								controller.getCharArray(z).setElement(y, x,
										controller.getCharArray(z).getElement(y, x + 1));
							} else if (x != 6) {
								controller.getCharArray(z).setElement(y, x,
										controller.getCharArray(z).getElement(y, x + 1));
							} else if (x == 6) {
								if (z == word.length()-1) {
									controller.getCharArray(z).setElement(y, x, temp[y][0]);
								} else {
									controller.getCharArray(z).setElement(y, x, temp[y][z + 1]);
								}
							}
						}
					}
					colorDisplay.setDisplay(controller.getCharArray(z).toIntArray(), 0, z);

				}
				colorDisplay.updateDisplay();
			}
		}, 0, 500, TimeUnit.MILLISECONDS);
	}
}
