/**
 * 
 */
package views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import bot.Bot;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.awt.event.ActionEvent;

/**
 * @author ruben
 *
 */
public class Unfollowers extends JFrame {

	private JPanel panel;
	private Bot b;
	private Set<String> unfollowers;
	private Set<String> whitelist;
	private JList<String> viewers;
	private JLabel lblNotFollowingYou;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Unfollowers frame = new Unfollowers(null);
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
	 * @wbp.parser.constructor
	 */
	public Unfollowers(Bot bo) {
		this.b = bo;
		unfollowers = new HashSet<String>();
		whitelist = new HashSet<String>();
		setResizable(false);
		setTitle("RLBOT");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 379, 513);
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panel);
		panel.setLayout(null);
		
		CloseFrame cf = new CloseFrame(this);
		
		viewers = new JList<String>();
		viewers.setBounds(0, 0, 0, 0);
		
		JScrollPane scrollPane_1 = new JScrollPane(viewers);
		scrollPane_1.setBounds(60, 90, 250, 308);
		panel.add(scrollPane_1);
		
		JButton btnUnfollow = new JButton("Unfollow");
		btnUnfollow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!whitelist.contains(viewers.getSelectedValue())) {
					b.unfollow(viewers.getSelectedValue());
					unfollowers.remove(viewers.getSelectedValue());	
					setUnfollowersList();
				}
			}
		});
		btnUnfollow.setBounds(60, 409, 89, 23);
		panel.add(btnUnfollow);
		
		JButton btnAddToWhitelist = new JButton("Add to whitelist");
		btnAddToWhitelist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addToWhitelist(viewers.getSelectedValue());
			}
		});
		btnAddToWhitelist.setBounds(159, 409, 151, 23);
		panel.add(btnAddToWhitelist);
		
		JButton btnWhitelist = new JButton("Whitelist");
		btnWhitelist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Whitelist wview = new Whitelist((Unfollowers) cf.getFrame());
				wview.setVisible(true);
				cf.close();
			}
		});
		btnWhitelist.setBounds(187, 450, 123, 23);
		panel.add(btnWhitelist);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChooseOption co = new ChooseOption(b, (Unfollowers) cf.getFrame());
				co.setVisible(true);
				cf.close();
			}
		});
		btnBack.setBounds(60, 11, 89, 23);
		panel.add(btnBack);
		
		JButton btnHelp = new JButton("Help?");
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(cf.getFrame(), "This list shows the people that you follow and doesn´t follow you back. \n"
						+ "If they are in the whitelist they won´t be shown here. \nYou can either unfollow manually or add them to the whitelist."
						+ " \nAlso, you can unfollow all at once. \nYou can see the whitelist by clicking the whitelist button."
						+ "\nUsers with 5000+ followers are added to the whitelist automatically.");
			}
		});
		btnHelp.setBounds(221, 11, 89, 23);
		panel.add(btnHelp);
		
		lblNotFollowingYou = new JLabel("Not following you back");
		lblNotFollowingYou.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNotFollowingYou.setHorizontalAlignment(SwingConstants.CENTER);
		lblNotFollowingYou.setBounds(60, 56, 250, 23);
		panel.add(lblNotFollowingYou);
		
		JButton btnUnfollowAll = new JButton("Unfollow all");
		btnUnfollowAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] s = unfollowers.toArray();
				for (int i=0 ; i<s.length; i++) {
					if(!whitelist.contains((String)s[i])) {
						b.unfollow((String)s[i]);
						unfollowers.remove((String)s[i]);	
						setUnfollowersList();
					}
				}
			}
		});
		btnUnfollowAll.setBounds(60, 450, 117, 23);
		panel.add(btnUnfollowAll);
		
		DefaultListModel<String> dfl = new DefaultListModel<String>();
		Set<String> followers = b.getFollowers();
		Set<String> following = b.getFollowing();

		readWhitelist();
		
		for(String s: following) {
			if(!followers.contains(s) && !whitelist.contains(s)) {
				unfollowers.add(s);
				dfl.addElement(s);
			}
		}
		
		System.out.println(unfollowers.size());
		
		setUnfollowersList();
	}
	
	
	/**
	 * @return the unfollowers
	 */
	public Set<String> getUnfollowers() {
		return unfollowers;
	}




	/**
	 * @return the whitelist
	 */
	public Set<String> getWhitelist() {
		return whitelist;
	}



	public void addToWhitelist(String us) {
		if(us == null) return;
		
		File file = new File("whitelist.txt");
		if(!file.exists())
			try {
				file.createNewFile();
			}
			catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		FileWriter filewriter = null;
        PrintWriter pw = null;
        
        try
        {
            filewriter = new FileWriter(file, true);
            
            pw = new PrintWriter(filewriter);

            pw.println(us);
            
            whitelist.add(us);
                
            	
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           if (null != filewriter)
              filewriter.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
        setUnfollowersList();
	}
	
	public void setUnfollowersList() {
		readWhitelist();
		DefaultListModel<String> dfl = new DefaultListModel<String>();
		for(String s: unfollowers) {
			if(!whitelist.contains(s)) dfl.addElement(s);
		}
		viewers.setModel(dfl);
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
	
}
