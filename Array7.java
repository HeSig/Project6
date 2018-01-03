package p6;

public class Array7 {
	private int[] array;
	
	public Array7() {
		this.array = new int[] {0, 0, 0, 0, 0, 0, 0};
	}
	
	public Array7(Array7 array7) {
		this.array = array7.toIntArray();
	}
	
	public Array7(int[] array) {
		this.array = array;
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
		return Array7.copy(array);
	}
	
	public static int[] copy(int[] array) {
		int[] newArray = new int[array.length];
		
		for(int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		
		return newArray;
	}
}
