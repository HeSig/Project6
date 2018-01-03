package p6;

import VenterPåArray.*;

// (Evt: Bibliotek over alle mulige arrays: http://ddwap.mah.se/af8827/)

public class M2Controller {

	private UIMoment2 gui;
	private Array7x7 array;

	public M2Controller(UIMoment2 gui, Array7x7 array) {
		this.gui = gui;
		this.array = array;
		gui.setController(this);
		updateLabels();
	}

	public void readCol(int colNbr) {
		// i vores array har vi alle värden lagrade
		array.getCol(colNbr);
		gui.setLeftCol(array.getCol(colNbr));
	}

	public void readRow(int rowNbr) {
		array.getRow(rowNbr); // denna behöves vist egentligen inte, vi anropar jo eksakt samma på raden under
		gui.setBotRow(array.getRow(rowNbr));
	}

	public void writeRow(int rowNbr, Array7 arr) {
		array.setRow(rowNbr, arr);
		// Nu har vi så skrivit över en rad i array7x7 med en ny rad.
		updateLabels();
	}

	public void writeCol(int colNbr, Array7 arr) {
		array.setCol(colNbr, arr);
		updateLabels();
	}

	public void updateLabels() {

		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				gui.setArrLabels(i, j, array.getElement(i, j));
			}
		}
	}

}
