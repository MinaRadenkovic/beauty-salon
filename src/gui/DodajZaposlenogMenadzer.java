package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import CRUD.KozmeticariCRUD;
import CRUD.KozmetickaUslugaCRUD;
import CRUD.MenadzeriCRUD;
import CRUD.RecepcioneriCRUD;
import uslugeTretmani.KozmetickaUslugaTretman;

public class DodajZaposlenogMenadzer extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField choice;
	private JList<String> list;

	public DodajZaposlenogMenadzer(JTable tabela, String nazivZanimanja) {
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		setBackground(new Color(174, 227, 253));
		setIconImage(Toolkit.getDefaultToolkit().getImage(registracija.class.getResource("/gui/BeautySalon.png")));
		setTitle("SV76-2022");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 579);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(174, 227, 253));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("DODAVANJE ZAPOSLENOG - " + nazivZanimanja.toUpperCase());
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(62, 10, 333, 17);
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
		textField.setBounds(168, 45, 258, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("   Prezime:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2.setBounds(10, 72, 81, 24);
		contentPane.add(lblNewLabel_2);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField_1.setBounds(168, 74, 258, 19);
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
		textField_2.setBounds(168, 126, 258, 19);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Adresa:");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_5.setBounds(20, 153, 71, 24);
		contentPane.add(lblNewLabel_5);
		
		textField_3 = new JTextField();
		textField_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField_3.setBounds(168, 155, 258, 19);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Korisničko ime:");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_6.setBounds(21, 178, 117, 33);
		contentPane.add(lblNewLabel_6);
		
		textField_4 = new JTextField();
		textField_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField_4.setBounds(168, 184, 258, 19);
		contentPane.add(textField_4);
		textField_4.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("Šifra:");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_7.setBounds(20, 211, 88, 24);
		contentPane.add(lblNewLabel_7);
		
		textField_5 = new JTextField();
		textField_5.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField_5.setBounds(168, 213, 258, 19);
		contentPane.add(textField_5);
		textField_5.setColumns(10);
		
		JButton btnNewButton = new JButton("KREIRAJ NALOG");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String kozmeticariFile = ".//fajlovi/kozmeticari.csv";
				String menadzeriFile = ".//fajlovi/menadzeri.csv";
				String recepcineriFile = ".//fajlovi/recepcioneri.csv";
				
				String uslugeFile = ".//fajlovi/usluge.csv";
				List<KozmetickaUslugaTretman> kut = new KozmetickaUslugaCRUD(uslugeFile).getUsluge();
				
				String ime = textField.getText();
				String prezime = textField_1.getText();
				String telefon = textField_2.getText();
				String adresa = textField_3.getText();
				String korisnickoIme = textField_4.getText();
				String lozinka = textField_5.getText();
				String pol = rdbtnNewRadioButton.isSelected() ? "muško" : (rdbtnNewRadioButton_1.isSelected() ? "žensko" : "");
				String zanimanje = choice.getText();
				int strucnaSprema = Integer.parseInt(textField_6.getText());
				int staz = Integer.parseInt(textField_7.getText());
				double osnovnaPlata = Double.parseDouble(textField_8.getText());
	            int[] selectedIndices = list.getSelectedIndices();
	            String tretmani = "";
	            if (selectedIndices.length == 0) {
	            	tretmani = null;
	            } else {
	                for (int index : selectedIndices) {
	                	tretmani += kut.get(index) + ";";
	                }
                	tretmani = tretmani.substring(0, tretmani.length() - 1);
	            }				
				if ((!ime.isEmpty() && !prezime.isEmpty() && !telefon.isEmpty() && !adresa.isEmpty() && !korisnickoIme.isEmpty() && !lozinka.isEmpty()
						&& !textField_6.getText().isEmpty() && !textField_7.getText().isEmpty() && !textField_8.getText().isEmpty())
						&& (rdbtnNewRadioButton.isSelected() || rdbtnNewRadioButton_1.isSelected())) {
					if (zanimanje.equals("menadžer")) {
						MenadzeriCRUD noviMenadzer = new MenadzeriCRUD(menadzeriFile);
						noviMenadzer.add(korisnickoIme, lozinka, ime, prezime, pol, telefon, adresa, strucnaSprema, staz, osnovnaPlata);
						OsvezavanjeTabelaMenadzer osvezi = new OsvezavanjeTabelaMenadzer();
						osvezi.osveziTabeluMenadzera(tabela);
					} else if (zanimanje.equals("recepcioner")) {
						RecepcioneriCRUD noviRecepcioner = new RecepcioneriCRUD(recepcineriFile);
						noviRecepcioner.add(korisnickoIme, lozinka, ime, prezime, pol, telefon, adresa, strucnaSprema, staz, osnovnaPlata);
						OsvezavanjeTabelaMenadzer osvezi = new OsvezavanjeTabelaMenadzer();
						osvezi.osveziTabeluRecepcionera(tabela);
					} else if (zanimanje.equals("kozmetičar")) {
						KozmeticariCRUD noviKozmeticar = new KozmeticariCRUD(kozmeticariFile);
						noviKozmeticar.add(korisnickoIme, lozinka, ime, prezime, pol, telefon, adresa, strucnaSprema, staz, osnovnaPlata, tretmani);						
						OsvezavanjeTabelaMenadzer osvezi = new OsvezavanjeTabelaMenadzer();
						osvezi.osveziTabeluKozmeticara(tabela);
					}				    
					dispose();			
				} else {
		            JOptionPane.showMessageDialog(getContentPane(), "Neuspešna prijava zaposlenog.\nProverite da li ste uneli sve podatke i pokušajte ponovo.", "Neuspešna prijava zaposlenog", JOptionPane.ERROR_MESSAGE);	
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBounds(243, 511, 183, 21);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("NAZAD");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();			
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton_1.setBounds(148, 511, 85, 21);
		contentPane.add(btnNewButton_1);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(20, 499, 416, 2);
		contentPane.add(separator_1);
		
		JLabel lblNewLabel_7_1 = new JLabel("Zanimanje:");
		lblNewLabel_7_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_7_1.setBounds(20, 240, 88, 24);
		contentPane.add(lblNewLabel_7_1);
		
		choice = new JTextField();
		choice.setFont(new Font("Tahoma", Font.PLAIN, 11));
		choice.setBounds(168, 241, 258, 18);
		choice.setText(nazivZanimanja);
		choice.setEditable(false);
		contentPane.add(choice);
		
		JLabel lblNewLabel_7_1_1 = new JLabel("Stručna sprema:");
		lblNewLabel_7_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_7_1_1.setBounds(20, 267, 118, 24);
		contentPane.add(lblNewLabel_7_1_1);
		
		JLabel lblNewLabel_7_1_1_1 = new JLabel("Staž: ");
		lblNewLabel_7_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_7_1_1_1.setBounds(20, 295, 118, 24);
		contentPane.add(lblNewLabel_7_1_1_1);
		
		textField_6 = new JTextField();
		textField_6.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField_6.setColumns(10);
		textField_6.setBounds(168, 270, 258, 17);
		contentPane.add(textField_6);
		
		textField_7 = new JTextField();
		textField_7.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField_7.setColumns(10);
		textField_7.setBounds(168, 297, 258, 19);
		contentPane.add(textField_7);
		
		JLabel lblNewLabel_7_1_1_1_1 = new JLabel("Osnovna plata:");
		lblNewLabel_7_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_7_1_1_1_1.setBounds(20, 323, 118, 24);
		contentPane.add(lblNewLabel_7_1_1_1_1);
		
		textField_8 = new JTextField();
		textField_8.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField_8.setColumns(10);
		textField_8.setBounds(168, 325, 258, 19);
		contentPane.add(textField_8);
		
		JLabel lblNewLabel_7_1_2 = new JLabel("Tretmani kozmetičara*:");
		lblNewLabel_7_1_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_7_1_2.setBounds(20, 350, 142, 24);
		contentPane.add(lblNewLabel_7_1_2);
		
        DefaultListModel<String> choice_1 = new DefaultListModel<>();		        

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(168, 354, 258, 135);
        contentPane.add(scrollPane);
        list = new JList<>(choice_1);
        scrollPane.setViewportView(list);
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        		
		String uslugeFile = ".//fajlovi/usluge.csv";
		List<KozmetickaUslugaTretman> kut = new KozmetickaUslugaCRUD(uslugeFile).getUsluge();

		if (choice.getText().equals("kozmetičar")) {
			for(KozmetickaUslugaTretman usluga : kut) {
				choice_1.addElement(usluga.getNazivUsluge());
			}		
		}
	}
}
