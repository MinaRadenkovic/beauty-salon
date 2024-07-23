package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
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

import CRUD.KlijentiCRUD;
import korisniciSistema.Klijent;

public class IzmenaKlijentaMenadzer extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_5;
    private ButtonGroup buttonGroup;
	
	public IzmenaKlijentaMenadzer(JTable tabela, String korImeKli) {
		KlijentiCRUD kli = new KlijentiCRUD(".//fajlovi/klijenti.csv");
		Klijent klijent = kli.PronadjiKlijentaPoUsernameu(korImeKli);
		
		setBounds(100, 100, 450, 293);
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
		setBounds(100, 100, 450, 321);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(174, 227, 253));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("IZMENA KLIJENTA: <dynamic>");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(26, 10, 316, 17);
		contentPane.add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 37, 416, 2);
		contentPane.add(separator);
		
		JLabel lblNewLabel_1 = new JLabel("Ime:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(20, 53, 71, 24);
		contentPane.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField.setBounds(148, 56, 278, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.setText(klijent.getIme());
		
		JLabel lblNewLabel_2 = new JLabel("   Prezime:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2.setBounds(10, 83, 81, 24);
		contentPane.add(lblNewLabel_2);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField_1.setBounds(148, 85, 278, 19);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		textField_1.setText(klijent.getPrezime());
		
		JLabel lblNewLabel_3 = new JLabel("Pol:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_3.setBounds(20, 109, 61, 23);
		contentPane.add(lblNewLabel_3);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Muški");
		rdbtnNewRadioButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		rdbtnNewRadioButton.setBounds(205, 110, 103, 21);
		contentPane.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Ženski");
		rdbtnNewRadioButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		rdbtnNewRadioButton_1.setBounds(323, 110, 103, 21);
		contentPane.add(rdbtnNewRadioButton_1);
		
        buttonGroup = new ButtonGroup();
        buttonGroup.add(rdbtnNewRadioButton);
        buttonGroup.add(rdbtnNewRadioButton_1);
        if (klijent.getPol().equals("muško")) {
            rdbtnNewRadioButton.setSelected(true);
        } else {
            rdbtnNewRadioButton_1.setSelected(true);
        }		
		JLabel lblNewLabel_4 = new JLabel("   Telefon:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_4.setBounds(10, 135, 71, 24);
		contentPane.add(lblNewLabel_4);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField_2.setBounds(148, 137, 278, 19);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		textField_2.setText(klijent.getTelefon());
		
		JLabel lblNewLabel_5 = new JLabel("Adresa:");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_5.setBounds(20, 164, 71, 24);
		contentPane.add(lblNewLabel_5);
		
		textField_3 = new JTextField();
		textField_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField_3.setBounds(148, 166, 278, 19);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		textField_3.setText(klijent.getAdresa());
		
		JLabel lblNewLabel_7 = new JLabel("Šifra:");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_7.setBounds(20, 198, 88, 24);
		contentPane.add(lblNewLabel_7);
		
		textField_5 = new JTextField();
		textField_5.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField_5.setBounds(148, 200, 278, 19);
		contentPane.add(textField_5);
		textField_5.setColumns(10);
		textField_5.setText(klijent.getLozinka());
		
		JButton btnNewButton = new JButton("IZMENI");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String klijentiFile = ".//fajlovi/klijenti.csv";
				KlijentiCRUD noviKlijent = new KlijentiCRUD(klijentiFile);

				String ime = textField.getText();
				String prezime = textField_1.getText();
				String telefon = textField_2.getText();
				String adresa = textField_3.getText();
				String lozinka = textField_5.getText();
				String pol = rdbtnNewRadioButton.isSelected() ? "muško" : (rdbtnNewRadioButton_1.isSelected() ? "žensko" : "");
				if ((!ime.isEmpty() && !prezime.isEmpty() && !telefon.isEmpty() && !adresa.isEmpty() && !lozinka.isEmpty())
						&& (rdbtnNewRadioButton.isSelected() || rdbtnNewRadioButton_1.isSelected())) {	
				    noviKlijent.edit(korImeKli, lozinka, ime, prezime, pol, telefon, adresa, klijent.getLojalitiKartica());
				    
					OsvezavanjeTabelaMenadzer osvezi = new OsvezavanjeTabelaMenadzer();
					osvezi.osveziTabeluKlijenata(tabela);
				    
					dispose();			
				} else {
		            JOptionPane.showMessageDialog(getContentPane(), "Neuspešna izmena.\nProverite da li ste uneli sve podatke i pokušajte ponovo.", "Neuspešna izmena", JOptionPane.ERROR_MESSAGE);	
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBounds(292, 253, 134, 21);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("NAZAD");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();			
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
