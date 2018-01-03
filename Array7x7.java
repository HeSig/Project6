package p6;

public class Array7x7 {
	private Array7[] array7x7 = new Array7[7];
	
	public Array7x7() {
		for(int i = 0; i < 7; i++) {
			array7x7[i]  = new Array7();
		}
	}
	
	public Array7x7(int[][] array) {
		for(int i = 0; i < 7; i++) {
			array7x7[i] = new Array7();
		}
		for(int row = 0; row < array7x7.length; row++) {
			for(int col = 0; col < array7x7.length; col++) {
				array7x7[row].setElement(col, array[row][col]);
			}
		}
	}
	
	public void setElement(int row, int col, int value) {
		array7x7[row].setElement(col, value);
	}
	
	public int getElement(int row, int col) {
		return array7x7[row].getElement(col);
	}
	
	public void setRow(int row, Array7 theRow) {
		array7x7[row] = theRow;
	}
	
	public Array7 getRow(int row) {
		return array7x7[row];	
	}
	
	public void setCol(int col, Array7 theCol) {
		for(int i = 0; i < array7x7.length; i++) {
			array7x7[i].setElement(col, theCol.getElement(i) );
		}
	}
	
	public Array7 getCol(int col) {
		Array7 colarray = new Array7();
		
		for(int i = 0; i < array7x7.length; i++) {
			colarray.setElement(i, array7x7[i].getElement(col));
		}
		return colarray;
	}
	
	public void setArray(Array7x7 array) {
		for(int row = 0; row < array7x7.length; row++) {
			for(int col = 0; col < array7x7.length; col++) {
				array7x7[row].setElement(col, array.getElement(row, col));
			}
		}
	}
	
	public void setArray(int[][] array) {
		for(int row = 0; row < array7x7.length; row++) {
			for(int col = 0; col < array7x7.length; col++) {
				array7x7[row].setElement(col, array[row][col]);
			}
		}
	}
	
	public Array7x7 getArray() {
		return new Array7x7();
	}
	
	public int[][] toIntArray() {
		int[][] newArray7x7 = new int[7][7];
		for(int row = 0; row < 7; row++) {
			for(int col = 0; col < 7; col++) {
				newArray7x7[row][col]=array7x7[row].getElement(col);
			}
		}
		return newArray7x7;
	}
}
