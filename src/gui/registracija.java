package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import CRUD.KlijentiCRUD;

public class registracija extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	
	public registracija() {
		setBackground(new Color(174, 227, 253));
		setIconImage(Toolkit.getDefaultToolkit().getImage(registracija.class.getResource("/gui/BeautySalon.png")));
		setTitle("SV76-2022");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 321);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(174, 227, 253));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("REGISTRACIJA");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(154, 10, 141, 17);
		contentPane.add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 37, 416, 2);
		contentPane.add(separator);
		
		JLabel lblNewLabel_1 = new JLabel("Ime:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(20, 42, 71, 24);
		contentPane.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField.setBounds(148, 45, 278, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("   Prezime:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2.setBounds(10, 72, 81, 24);
		contentPane.add(lblNewLabel_2);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField_1.setBounds(148, 74, 278, 19);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Pol:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_3.setBounds(20, 98, 61, 23);
		contentPane.add(lblNewLabel_3);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Muški");
		rdbtnNewRadioButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		rdbtnNewRadioButton.setBounds(205, 99, 103, 21);
		contentPane.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Ženski");
		rdbtnNewRadioButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		rdbtnNewRadioButton_1.setBounds(323, 99, 103, 21);
		contentPane.add(rdbtnNewRadioButton_1);
		
		JLabel lblNewLabel_4 = new JLabel("   Telefon:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_4.setBounds(10, 124, 71, 24);
		contentPane.add(lblNewLabel_4);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField_2.setBounds(148, 126, 278, 19);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Adresa:");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_5.setBounds(20, 153, 71, 24);
		contentPane.add(lblNewLabel_5);
		
		textField_3 = new JTextField();
		textField_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField_3.setBounds(148, 155, 278, 19);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Korisničko ime:");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_6.setBounds(21, 178, 117, 33);
		contentPane.add(lblNewLabel_6);
		
		textField_4 = new JTextField();
		textField_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField_4.setBounds(148, 184, 278, 19);
		contentPane.add(textField_4);
		textField_4.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("Šifra:");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_7.setBounds(20, 211, 88, 24);
		contentPane.add(lblNewLabel_7);
		
		textField_5 = new JTextField();
		textField_5.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField_5.setBounds(148, 213, 278, 19);
		contentPane.add(textField_5);
		textField_5.setColumns(10);
		
		JButton btnNewButton = new JButton("REGISTRUJ SE");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String klijentiFile = ".//fajlovi/klijenti.csv";
				KlijentiCRUD noviKlijent = new KlijentiCRUD(klijentiFile);

				String ime = textField.getText();
				String prezime = textField_1.getText();
				String telefon = textField_2.getText();
				String adresa = textField_3.getText();
				String korisnickoIme = textField_4.getText();
				String lozinka = textField_5.getText();
				String pol = rdbtnNewRadioButton.isSelected() ? "muško" : (rdbtnNewRadioButton_1.isSelected() ? "žensko" : "");
				if ((!ime.isEmpty() && !prezime.isEmpty() && !telefon.isEmpty() && !adresa.isEmpty() && !korisnickoIme.isEmpty() && !lozinka.isEmpty())
						&& (rdbtnNewRadioButton.isSelected() || rdbtnNewRadioButton_1.isSelected())) {	
				    noviKlijent.add(korisnickoIme, lozinka, ime, prezime, pol, telefon, adresa, false);
					logIN frame = new logIN();
					dispose();			
					frame.setVisible(true);
				} else {
		            JOptionPane.showMessageDialog(getContentPane(), "Neuspešna registracija.\nProverite da li ste uneli sve podatke i pokušajte ponovo.", "Neuspešna registracija", JOptionPane.ERROR_MESSAGE);	
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBounds(292, 253, 134, 21);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("NAZAD");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logIN frame = new logIN();
				dispose();			
				frame.setVisible(true);
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_1.setBounds(197, 253, 85, 21);
		contentPane.add(btnNewButton_1);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 245, 416, 2);
		contentPane.add(separator_1);
	}
}
