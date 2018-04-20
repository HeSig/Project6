package p6;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

public class TestUI3 extends JPanel {
	private ColorDisplay display = new ColorDisplay(Color.BLACK, Color.YELLOW);
	private Controller con;
	
	public TestUI3(Controller con3) {
		this.con = con3;
		display.setDisplay(con.getArray().toIntArray());		
		display.repaint();
		add(display);
	}
	
	public void changeleft() {
		int[] temp = new int[] {0, 0, 0, 0, 0, 0, 0};
		
		for(int row = 0; row < 7; row++) {
			for(int col =  0; col < 7; col++) {
				if(col == 0) {
					temp[row] = con.getArray().getElement(row, 0);
				}
				if(col == 6) {
					con.getArray().setElement(row, col,temp[row]);	
				}
				else {
					con.getArray().setElement(row, col, con.getArray().getElement(row, col+1));
				}
			}
		}
		display.setDisplay(con.getArray().toIntArray());
		display.repaint();
	}
	
}
