/**
 * 
 */
package views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import DB.BD;
import bot.Bot;
import javazoom.jl.player.Player;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.awt.event.ActionEvent;

/**
 * @author ruben
 *
 */
public class MainWindow extends JFrame {
	
	
	
	private JPanel _contentPane;
	private JTextField _textField;
	private JPasswordField _passwordField;
	private JLabel error;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public void setError(String err) {
		error.setText(err);
	}
	
	
	public MainWindow() throws InterruptedException {
		setTitle("RLBOT");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 405, 299);
		setResizable(false);
		_contentPane = new JPanel();
		_contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(_contentPane);
		_contentPane.setLayout(null);
		
		JLabel lblRlbot = new JLabel("BOT");
		lblRlbot.setForeground(Color.RED);
		lblRlbot.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblRlbot.setBounds(184, 23, 67, 25);
		_contentPane.add(lblRlbot);
		
		JLabel lblForInstagram = new JLabel("For Instagram");
		lblForInstagram.setForeground(Color.MAGENTA);
		lblForInstagram.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		lblForInstagram.setBounds(138, 43, 98, 25);
		_contentPane.add(lblForInstagram);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(109, 124, 98, 14);
		_contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(109, 152, 98, 14);
		_contentPane.add(lblPassword);
		
		_textField = new JTextField();
		_textField.setBounds(187, 121, 86, 20);
		_contentPane.add(_textField);
		_textField.setColumns(10);
		
		_passwordField = new JPasswordField();
		_passwordField.setBounds(187, 149, 86, 20);
		_contentPane.add(_passwordField);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(173, 79, 50, 20);
		_contentPane.add(comboBox);
		comboBox.addItem("No");
		comboBox.addItem("Yes");
		
		CloseFrame cf = new CloseFrame(this);
		
		JCheckBox chckbxMusic = new JCheckBox("Music");
		chckbxMusic.setBounds(229, 78, 97, 23);
		_contentPane.add(chckbxMusic);
		
		JButton btnLetsGo = new JButton("Let's Go");
		btnLetsGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					boolean music = false;
					JOptionPane.showMessageDialog(cf.getFrame(), "Loading...");
					
					if((chckbxMusic.isSelected())) music = true;
					
					Bot b;
					if(comboBox.getSelectedItem().equals("No")) {
						
						
						b = new Bot(true, music);
					}else {
						b = new Bot(false, music);
					}
					if(b.login(_textField.getText(), _passwordField.getText()) == -1) {
						setError("Incorrect username or password");
					}else {
						ChooseOption co = new ChooseOption(b, null);
						co.setVisible(true);
						cf.close();
						
					}
				}
				catch (InterruptedException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		});
		btnLetsGo.setBounds(147, 196, 89, 23);
		_contentPane.add(btnLetsGo);
		
		JLabel lblRlb = new JLabel("RLI");
		lblRlb.setForeground(Color.BLUE);
		lblRlb.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblRlb.setBounds(156, 23, 67, 25);
		_contentPane.add(lblRlb);
		
		error = new JLabel("");
		error.setForeground(Color.RED);
		error.setHorizontalAlignment(SwingConstants.RIGHT);
		error.setBounds(154, 245, 235, 14);
		_contentPane.add(error);
		
		JLabel lblShowProcess = new JLabel("Show process?");
		lblShowProcess.setHorizontalAlignment(SwingConstants.RIGHT);
		lblShowProcess.setBounds(53, 82, 110, 14);
		_contentPane.add(lblShowProcess);
		
		JLabel lblV = new JLabel("v 2.0");
		lblV.setBounds(10, 245, 46, 14);
		_contentPane.add(lblV);
		
		
		
		

	}
	
	
}
