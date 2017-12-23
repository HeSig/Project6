package p6;

public class Array7x7 {
	private int[][] array2x;
	private Array7 array7;
	
	public Array7x7() {
		this.array2x = new int[7][7];
	}
	
	public Array7x7(Array7x7 array7x7) {
		this.array2x = new int[7][7];
		setArray(array7x7);
	}
	
	public Array7x7(int[][] array2x) {
		this.array2x = new int[7][7];
		setArray(array2x);
	}
	
	public void setElement(int row, int col, int value) {
		this.array2x[row][col] = value;
	}
	
	public int getElement(int row, int col) {
		return array2x[row][col];
	}
	
	public void setRow(int row, Array7 theRow) {
		for(int i = 0; i < array2x.length; i++) {
			this.array2x[row][i] = theRow.getElement(i);
		}
	}
	
	public Array7 getRow(int row) {
		Array7 rowarray = new Array7();
		rowarray.setArray(array2x[row]);
		return rowarray;
		
	}
	
	public void setCol(int col, Array7 theCol) {
		for(int i = 0; i < array2x[col].length; i++) {
			this.array2x[i][col] = theCol.getElement(i);
		}
	}
	
	public Array7 getCol(int col) {
		Array7 colarray = new Array7();
		
		for(int i = 0; i < array2x[i].length; i++) {
			colarray.setElement(i, array2x[i][col]);
		}
		return colarray;
	}
	
	public void setArray(Array7x7 array7x7) {
		for(int row = 0; row < array2x.length; row++) {
			for(int col = 0; col < array2x[row].length; col++) {
				this.array2x[row][col] = array7x7.getElement(row, col);
			}
		}
	}
	
	public void setArray(int[][] array2x) {
		for(int row = 0; row < array2x.length; row++) {
			for(int col = 0; col < array2x[row].length; col++) {
				this.array2x[row][col] = array2x[row][col];
			}
		}
	}
	
	public Array7x7 getArray() {
		return new Array7x7();
	}
	
	public int[][] toIntArray() {
		return new int[7][7];
	}
}
