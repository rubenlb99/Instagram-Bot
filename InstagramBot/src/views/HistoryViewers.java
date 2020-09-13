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
import java.util.*;
import java.awt.event.ActionEvent;

/**
 * @author ruben
 *
 */
public class HistoryViewers extends JFrame {

	private JPanel _contentPane;
	private JTextField filterField;
    private JLabel status;
    private Set<String> vlist;
	

	/**
	 * Create the frame.
	 */
	public HistoryViewers(Bot b) {
		setTitle("RLBOT");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 391, 497);
		_contentPane = new JPanel();
		_contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(_contentPane);
		_contentPane.setLayout(null);
		
		
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 385, 468);
		_contentPane.add(panel);
		
		
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(172, 11, 44, 20);
		panel.add(comboBox);
		
		JLabel lblSelectHistoryNumber = new JLabel("Select history number");
		lblSelectHistoryNumber.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSelectHistoryNumber.setBounds(-16, 14, 178, 14);
		panel.add(lblSelectHistoryNumber);
		
		filterField = new JTextField();
		filterField.setColumns(10);
		filterField.setBounds(175, 72, 86, 20);
		panel.add(filterField);
		
		
		JLabel lblLookingForSomeone = new JLabel("Looking for someone?");
		lblLookingForSomeone.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLookingForSomeone.setBounds(10, 75, 155, 14);
		panel.add(lblLookingForSomeone);
		
		
		JList viewers = new JList();
		viewers.setBounds(0, 0, 0, 0);
		panel.add(viewers);
		//viewers.setBounds(47, 100, 280, 317);
		viewers.setVisible(false);
		
		JScrollPane scrollPane_1 = new JScrollPane(viewers);
		scrollPane_1.setBounds(54, 100, 272, 308);
		panel.add(scrollPane_1);
		
		JButton btnGo = new JButton("GO");
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filter = filterField.getText();
				DefaultListModel dfl = new DefaultListModel();

				
				for (String s: vlist) {
					if(s.contains(filter)) dfl.addElement(s);
				}
				
				viewers.setModel(dfl);
				
			}
		});
		btnGo.setBounds(271, 69, 55, 23);
		panel.add(btnGo);
		
		
		status = new JLabel("");
		status.setBounds(63, 47, 251, 14);
		panel.add(status);
		
		CloseFrame cf = new CloseFrame(this);
		
		filterField.setVisible(false);
		lblLookingForSomeone.setVisible(false);
		btnGo.setVisible(false);
		status.setVisible(false);
		scrollPane_1.setVisible(false);
		
		JButton search = new JButton("Search");
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(cf.getFrame(), "Loading...");		
				
				filterField.setVisible(true);
				lblLookingForSomeone.setVisible(true);
				btnGo.setVisible(true);
				status.setVisible(true);
				scrollPane_1.setVisible(true);
				viewers.setVisible(true);
				
				
				DefaultListModel dfl = new DefaultListModel();
				int hnum = (Integer)comboBox.getSelectedItem();
				vlist = b.getStoryViewers(hnum);
				
				if(vlist.size()==0) {
					status.setText("Nobody saw this story");
				}else {
					status.setText("");
					status.setText(vlist.size()+" users saw your history number "+hnum);
					for(String s: vlist) {
						if(!s.isEmpty())
						dfl.addElement(s);
					}
				}
				viewers.setModel(dfl);
			}
		});
		search.setBounds(226, 10, 89, 23);
		panel.add(search);
		
		
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChooseOption co = new ChooseOption(b, null);
				co.setVisible(true);
				cf.close();
			}
		});
		btnBack.setBounds(286, 434, 89, 23);
		panel.add(btnBack);
		

		
		
		
		int hnum = b.countHistories();
		
		if(hnum==-1) { 
			setStatus("No history");
		}else {
			for (int i = 1; i <= hnum; i++) {
				comboBox.addItem(i);
			}
		}
		
		
	}
	
	
	
	public void setStatus(String s) {
		this.status.setText(s);
	}
}
