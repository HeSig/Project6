package p6;

public class Array7 {
	private int[] array;
	
	public Array7() {
		this.array = new int[7];
	}
	
	public Array7(Array7 array7) {
		this.array = new int[7];
		setArray(array7);
	}
	
	public Array7(int[] array) {
		this.array = new int[7];
		setArray(array);
	}
	
	public void setElement(int i, int value) {
		this.array[i] = value;
	}
	
	public int getElement(int i) {
		return array[i];
	}
	
	public void setArray(Array7 array7) {
		for(int i = 0; i < array.length; i++) {
			this.array[i] = array7.getElement(i);
		}
	}
	
	public void setArray(int[] array) {
		for(int i = 0; i < array.length; i++) {
			this.array[i] = array[i];
		}
	}
	
	public Array7 getArray() {
		return new Array7();
	}
	
	public int[] toIntArray() {
		return new int[7];
	}
}
