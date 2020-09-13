/**
 * 
 */
package views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import bot.Bot;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * @author ruben
 *
 */
public class ChooseOption extends JFrame {

	private JPanel _contentPane;
	private Bot b;
	private Unfollowers un;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ChooseOption frame = new ChooseOption(null);
//					frame.setVisible(true);
//				}
//				catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public ChooseOption(Bot b, Unfollowers unf) {
		this.un = unf;
		setTitle("RLBOT");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		_contentPane = new JPanel();
		_contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(_contentPane);
		_contentPane.setLayout(null);
		
		this.b = b;
		
		CloseFrame cf = new CloseFrame(this);
		
		JButton btnHistoryFilter = new JButton("History Filter");
		btnHistoryFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HistoryViewers sf = new HistoryViewers(b);
				sf.setVisible(true);
				cf.close();
			}
		});
		btnHistoryFilter.setBounds(119, 70, 177, 23);
		_contentPane.add(btnHistoryFilter);
		
		JButton btnUnfollowers = new JButton("Unfollowers");
		btnUnfollowers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(cf.getFrame(), "This may take some minutes,"
						+ "\ndepending on your internet speed"
						+ "\nand the amount of followers/following"
						+ "\nyour account has.");
				if(un==null) {
					un = new Unfollowers(b);
				}
				un.setVisible(true);
				cf.close();
			}
		});
		btnUnfollowers.setBounds(119, 132, 177, 23);
		_contentPane.add(btnUnfollowers);
		
		JButton btnDiscconect = new JButton("Disconnect");
		btnDiscconect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				b.disconnect();
				try {
					MainWindow mw = new MainWindow();
					mw.setVisible(true);
				}
				catch (InterruptedException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
				cf.close();
			}
		});
		btnDiscconect.setBounds(326, 227, 98, 23);
		_contentPane.add(btnDiscconect);
	}

}
