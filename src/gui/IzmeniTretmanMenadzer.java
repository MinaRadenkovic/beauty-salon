package gui;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import CRUD.KozmetickaUslugaCRUD;
import CRUD.TipKozmetickogTretmanaCRUD;
import uslugeTretmani.KozmetickaUslugaTretman;
import uslugeTretmani.TipKozmetickogTretmana;
import java.awt.Toolkit;

public class IzmeniTretmanMenadzer extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	
	public IzmeniTretmanMenadzer(JTable tabela, int idTretmana) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(IzmeniTretmanMenadzer.class.getResource("/gui/BeautySalon.png")));
		String tretmaniFile = ".//fajlovi/tretmani.csv";		
		TipKozmetickogTretmanaCRUD t = new TipKozmetickogTretmanaCRUD(tretmaniFile);
		TipKozmetickogTretmana izabraniTretman = t.pronadjiTretmanPoId(idTretmana);
		
		setTitle("SV76-2022");
		setBackground(new Color(174, 227, 253));
		getContentPane().setBackground(new Color(174, 227, 253));
		setBounds(100, 100, 450, 262);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(174, 227, 253));
		contentPanel.setLayout(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JLabel lblNewLabel = new JLabel("Menjanje postojećeg tretmana: ");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel.setBounds(10, 10, 285, 22);
			contentPanel.add(lblNewLabel);
		}
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 33, 416, 2);
		contentPanel.add(separator);
		
		JLabel lblNewLabel_1 = new JLabel("1. Izaberite uslugu: ");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setBounds(10, 46, 242, 22);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("2. Unesite naziv tretmana: ");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_1.setBounds(10, 78, 242, 22);
		contentPanel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("3. Unesite vreme trejanja tretmana: ");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_1_1.setBounds(10, 115, 242, 22);
		contentPanel.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("4. Unesite cenu tretmana:");
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_1_1_1.setBounds(10, 152, 242, 22);
		contentPanel.add(lblNewLabel_1_1_1_1);
		
		Choice choice = new Choice();
		choice.setFont(new Font("Dialog", Font.BOLD, 13));
		choice.setBounds(258, 46, 168, 22);
		contentPanel.add(choice);
		
		String uslugeFile = ".//fajlovi/usluge.csv";
		List<KozmetickaUslugaTretman> kut = new KozmetickaUslugaCRUD(uslugeFile).getUsluge();

		choice.add(izabraniTretman.getNazivUsluge());
		
		for (KozmetickaUslugaTretman usluga : kut) {
			if (!izabraniTretman.getNazivUsluge().equals(usluga.getNazivUsluge())) {
				choice.add(usluga.getNazivUsluge());
			}
		}
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		textField_1.setBounds(257, 78, 169, 22);
		contentPanel.add(textField_1);
		textField_1.setText(izabraniTretman.getNazivTretmana());
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		textField_2.setColumns(10);
		textField_2.setBounds(257, 115, 169, 22);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String vreme = izabraniTretman.getVremeTrajanja().format(formatter);
		textField_2.setText(vreme);
		contentPanel.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		textField_3.setColumns(10);
		textField_3.setBounds(258, 147, 169, 22);
		textField_3.setText(String.valueOf(izabraniTretman.getCena()));
		contentPanel.add(textField_3);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(174, 227, 253));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("IZMENI");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (textField_1.getText() != null && textField_2.getText() != null && textField_3.getText() != null && choice.getSelectedItem() != null) {
							String nazivUsluge = choice.getSelectedItem();
							String nazivTretmana = textField_1.getText();
							String vremeTrajanjaTretmana = textField_2.getText();
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
					        LocalTime vremeTrajanja = LocalTime.parse(vremeTrajanjaTretmana, formatter);
							Float cena = Float.parseFloat(textField_3.getText());
							
							TipKozmetickogTretmanaCRUD tretmanCRUD = new TipKozmetickogTretmanaCRUD(".//fajlovi/tretmani.csv");
							tretmanCRUD.edit(idTretmana, nazivUsluge, nazivTretmana, vremeTrajanja, cena);
							
							OsvezavanjeTabelaMenadzer osvezi = new OsvezavanjeTabelaMenadzer();
							osvezi.osveziTabeluTretmana(tabela);
							dispose();
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("OTKAŽI");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
