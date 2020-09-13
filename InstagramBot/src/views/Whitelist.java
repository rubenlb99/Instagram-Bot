/**
 * 
 */
package views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import bot.Bot;
import javazoom.jl.player.Player;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.awt.event.ActionEvent;

/**
 * @author ruben
 *
 */
public class Whitelist extends JFrame {

	private JPanel panel;
	private Set<String> whitelist;
	private Unfollowers un;
	private JList viewers;
	private Set<String> aux;
	private Bot b;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		
//	}

	/**
	 * Create the frame.
	 */
	public Whitelist(Unfollowers unf) {
		un = unf;
		this.b = b;
		whitelist = new HashSet<String>();
		setResizable(false);
		setTitle("RLBOT");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 503);
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panel);
		panel.setLayout(null);
		
		JLabel lblWhitelist = new JLabel("Whitelist");
		lblWhitelist.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblWhitelist.setForeground(new Color(0, 0, 0));
		lblWhitelist.setHorizontalAlignment(SwingConstants.CENTER);
		lblWhitelist.setBounds(62, 25, 248, 27);
		panel.add(lblWhitelist);
		
		CloseFrame cf = new CloseFrame(this);
		
		viewers = new JList<String>();
		viewers.setBounds(0, 0, 0, 0);
		
		JScrollPane scrollPane_1 = new JScrollPane(viewers);
		scrollPane_1.setBounds(62, 73, 248, 339);
		panel.add(scrollPane_1);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String  k = (String) viewers.getSelectedValue();
				if(k==null) return;
				
				whitelist.remove(k);
				
				File wl = new File ("whitelist.txt");
				if(!wl.exists()) {
					try {
						wl.createNewFile();
					}
					catch (IOException ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
				}else {
					aux = new HashSet<String>();
					
					wl.delete();
					FileReader fr = null;
					
					FileWriter filewriter = null;
			        PrintWriter pw = null;
					
					try {
						wl.createNewFile();
										        
				        filewriter = new FileWriter(wl, true);

				        pw = new PrintWriter(filewriter);

				        for(String s: whitelist) {
				        	if(s!=null) pw.println(s);
				        	aux.add(s);
				        }


					} catch (Exception e1) {
						e1.printStackTrace();
					} finally {
						try {
							if (null != filewriter)
								filewriter.close();
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}

				}
				setWhitelist();
			}
			
		});
		btnRemove.setBounds(221, 430, 89, 23);
		panel.add(btnRemove);

		JButton btnAtrs = new JButton("Atrás");
		btnAtrs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				un.setVisible(true);
				un.setUnfollowersList();
				cf.close();
			}
		});
		btnAtrs.setBounds(62, 430, 89, 23);
		panel.add(btnAtrs);
		
		readWhitelist();
		setWhitelist();
	}
	
	public void readWhitelist() {
		whitelist.clear();
		File wl = new File ("whitelist.txt");
		if(!wl.exists()) {
			try {
				wl.createNewFile();
			}
			catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}else {
			FileReader fr = null;
			try {
				fr = new FileReader (wl);
				String uwl;
				BufferedReader br = new BufferedReader(fr);
				while((uwl=br.readLine())!=null) {
					whitelist.add(uwl);
				}
				fr.close();
				br.close();
			}
			catch (FileNotFoundException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}
	}
	
	public void setWhitelist() {
		DefaultListModel<String> dfl = new DefaultListModel<String>();
		for(String s: whitelist) {
			dfl.addElement(s);
		}
		viewers.setModel(dfl);
	}
}
