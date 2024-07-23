package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

import prijava.logIn;
import korisniciSistema.*;

public class logIN extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	public logIN() {
		setBackground(new Color(174, 227, 253));
		setIconImage(Toolkit.getDefaultToolkit().getImage(logIN.class.getResource("/gui/BeautySalon.png")));
		setTitle("SV76-2022");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 431, 289);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(174, 227, 253));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("PRIJAVA");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(20, 54, 155, 53);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Projektni zadatak OOP1 - 2023");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(119, 10, 181, 23);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Kozmetički salon");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2.setBounds(160, 20, 117, 33);
		contentPane.add(lblNewLabel_2);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 59, 416, 2);
		contentPane.add(separator);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(logIN.class.getResource("/gui/BeautySalon.png")));
		lblNewLabel_3.setBounds(217, 65, 190, 187);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Korisničko ime");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_4.setBounds(20, 108, 197, 23);
		contentPane.add(lblNewLabel_4);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField.setBounds(10, 134, 217, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(7, 101, 220, 18);
		contentPane.add(separator_1);
		
		JLabel lblNewLabel_5 = new JLabel("Šifra");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_5.setBounds(20, 163, 45, 13);
		contentPane.add(lblNewLabel_5);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		passwordField.setBounds(10, 180, 217, 19);
		contentPane.add(passwordField);
		
		JButton btnNewButton = new JButton("LOG IN");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logIn prijava = new logIn();
				String korisnickoIme =  textField.getText();
				char[] lozinkaArray = passwordField.getPassword();
				String lozinka = new String(lozinkaArray);
				Korisnik ulogovaniKorisnik = prijava.prijavaNaSistem(korisnickoIme, lozinka);
				if (ulogovaniKorisnik == null) {
		            JOptionPane.showMessageDialog(getContentPane(), "Neuspešna prijava na sistem.\nProverite unete podatke i pokušajte ponovo.\n\nNemate nalog - registrujte se!", "Neuspešna prijava", JOptionPane.ERROR_MESSAGE);	
				} else {
					if (ulogovaniKorisnik instanceof Klijent) {
			            klijentiMeni frame = new klijentiMeni((Klijent) ulogovaniKorisnik);
			            frame.setVisible(true);
			            dispose();
					}
					else if (ulogovaniKorisnik instanceof Kozmeticar) {
						kozmeticariMeni frame = new kozmeticariMeni((Kozmeticar) ulogovaniKorisnik);
						frame.setVisible(true);
						dispose();
					}
					else if (ulogovaniKorisnik instanceof Recepcioner) {
						recepcioneriMeni frame = new recepcioneriMeni((Recepcioner) ulogovaniKorisnik);
						frame.setVisible(true);
						dispose();
					} 
					else if (ulogovaniKorisnik instanceof Menadzer) {
						menadzeriMeni frame = new menadzeriMeni((Menadzer) ulogovaniKorisnik);
						frame.setVisible(true);
						dispose();
					}
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBounds(10, 209, 83, 21);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("REGISTRACIJA");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registracija frame = new registracija();
				dispose();
				frame.setVisible(true);
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_1.setBounds(103, 209, 124, 21);
		contentPane.add(btnNewButton_1);
	}
}
