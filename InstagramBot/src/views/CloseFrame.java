/**
 * 
 */
package views;

import javax.swing.JFrame;

/**
 * @author ruben
 *
 */
public class CloseFrame {
	private JFrame f;
	public CloseFrame(JFrame j) {
		f = j;
	}
	
	public JFrame getFrame() {
		return f;
	}
	
	public void close() {
		f.setVisible(false);
	}
}
