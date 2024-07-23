package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import CRUD.KozmetickaUslugaCRUD;
import uslugeTretmani.KozmetickaUslugaTretman;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class DodajUsluguMenadzer extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;

	public DodajUsluguMenadzer(JTable tabela) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(DodajUsluguMenadzer.class.getResource("/gui/BeautySalon.png")));
		setBackground(new Color(174, 227, 253));
		getContentPane().setBackground(new Color(174, 227, 253));
		setBounds(100, 100, 320, 147);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(174, 227, 253));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Unesite naziv nove usluge: ");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel.setBounds(10, 10, 285, 22);
			contentPanel.add(lblNewLabel);
		}
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		textField.setBounds(10, 47, 285, 22);
		contentPanel.add(textField);
		textField.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(174, 227, 253));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("DODAJ");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (textField.getText() != null) {
							String novaUsluga = textField.getText();
							KozmetickaUslugaCRUD usluga = new KozmetickaUslugaCRUD(".//fajlovi/usluge.csv");
							usluga.add(novaUsluga);

							OsvezavanjeTabelaMenadzer osvezi = new OsvezavanjeTabelaMenadzer();
							osvezi.osveziTabeluUsluga(tabela);
							
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
