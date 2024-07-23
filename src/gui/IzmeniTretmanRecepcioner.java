package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import CRUD.KlijentiCRUD;
import CRUD.KozmeticariCRUD;
import CRUD.ZakazanKozmetickiTretmanCRUD;
import Zakazivanje.StanjeZakazanogTretmana;
import korisniciSistema.Klijent;
import korisniciSistema.Kozmeticar;
import korisniciSistema.Recepcioner;
import uslugeTretmani.ZakazanKozmetickiTretman;
import java.awt.Choice;

public class IzmeniTretmanRecepcioner extends JDialog {

	private final JPanel contentPanel = new JPanel();
	Choice choice = new Choice();
	JFrame parentFrame;
	private JTable table_1;
	private DefaultTableModel modelTabele;

	public IzmeniTretmanRecepcioner(JFrame parent, Recepcioner recepcioner) {
		this.parentFrame = parent;
		
		setTitle("Ažuriraj stanje tretmana");
		setBackground(new Color(174, 227, 253));
		getContentPane().setBackground(new Color(174, 227, 253));
		setIconImage(Toolkit.getDefaultToolkit().getImage(IzmeniTretmanRecepcioner.class.getResource("/gui/BeautySalon.png")));
		setBounds(100, 100, 1455, 421);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(174, 227, 253));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Izaberite tretman kome želite da ažurirate stanje:");
			lblNewLabel.setBounds(10, 10, 346, 16);
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
			contentPanel.add(lblNewLabel);
		}
		
		String zakazaniFile = ".//fajlovi/zakazani.csv";
		ZakazanKozmetickiTretmanCRUD zkt = new ZakazanKozmetickiTretmanCRUD(zakazaniFile);
		HashMap<Integer, ZakazanKozmetickiTretman> recnikZakazanih = zkt.getRecnikZakazanih();
		String klijentiFile = ".//fajlovi/klijenti.csv";
		List<Klijent> sviKlijenti = new KlijentiCRUD(klijentiFile).getKlijenti();
		String kozmeticariFile = ".//fajlovi/kozmeticari.csv";
		List<Kozmeticar> sviKozmeticari = new KozmeticariCRUD(kozmeticariFile).getKozmeticari();
		
		ArrayList<ZakazanKozmetickiTretman> listaTretmana = new ArrayList<>();
		for (HashMap.Entry<Integer, ZakazanKozmetickiTretman> zakazanTretman: recnikZakazanih.entrySet()) {
			if (zakazanTretman.getValue().getStanje().equals(StanjeZakazanogTretmana.ZAKAZAN)) {
				listaTretmana.add(zakazanTretman.getValue());
			}
		}		
		table_1 = new JTable();
		table_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		modelTabele = new DefaultTableModel(
			    new Object[][] {},
			    new String[] {
			       "klijent", "kozmeti\u010Dar", "usluga", "tretman", "stanje", "datum", "vreme", "trajanje tretmana", "cena"
			    }
			);
		table_1.setModel(modelTabele);
//		table_1.setEnabled(false);
			
		for (ZakazanKozmetickiTretman tretman: listaTretmana) {
			String korImeKoz = tretman.getKorisnickoImeKozmeticara();
			String kozmeticar = "";
			for (Kozmeticar k: sviKozmeticari) {
				if (k.getKorisnickoIme().equals(korImeKoz)) {
					kozmeticar = k.getIme() + " " + k.getPrezime();
					break;
				}
			}
			String korImeKli = tretman.getKorisnickoImeKlijenta();
			String klijent = "";
			for (Klijent k : sviKlijenti) {
				if (k.getKorisnickoIme().equals(korImeKli)) {
					klijent = k.getIme() + " " + k.getPrezime();
					break;
				}
			}			
			Object[] red = {
					klijent,
					kozmeticar,
					tretman.getNazivUsluge(),
					tretman.getNazivTretmana(),
					tretman.getStanje(),
					tretman.getDatumTretmana(),
					tretman.getVremeTretmana(),
					tretman.getVremeTrajanja(),
					tretman.getCena()};
			((DefaultTableModel) table_1.getModel()).addRow(red);
		}		
		JTableHeader header = table_1.getTableHeader();
        Font headerFont = header.getFont();
        Font newHeaderFont = new Font(headerFont.getName(), Font.BOLD, 20);
        header.setFont(newHeaderFont);
		
        table_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Omogućava selekciju samo jednog reda
        JScrollPane scrollPane = new JScrollPane(table_1);
        scrollPane.setBounds(10, 36, 1421, 277);
        contentPanel.add(scrollPane);
		scrollPane.setViewportView(table_1);
		{
			choice.setFont(new Font("Dialog", Font.PLAIN, 13));
			choice.setBounds(1274, 319, 157, 33);
			contentPanel.add(choice);
			choice.add("NIJE SE POJAVIO");
			choice.add("OTKAZAO KLIJENT");
			choice.add("OTKAZAO SALON");
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Izaberite novo stanje izabranog tretmana: ");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel_1.setBounds(964, 323, 292, 18);
			contentPanel.add(lblNewLabel_1);
		}		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(174, 227, 253));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Ažuriraj stanje tretmana");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int red = table_1.getSelectedRow();
						if (red != -1) {
							
							String imeIPrezimeKlijenta = (String) table_1.getValueAt(red, 0);
							String kli = "";
							for (Klijent k: sviKlijenti) {
								if((k.getIme() + " " + k.getPrezime()).equals(imeIPrezimeKlijenta)) {
									kli = k.getKorisnickoIme();
									break;
								}
							}							
							String imeIprezimeKozmeticara = (String) table_1.getValueAt(red, 1);
							
							String koz = "";
							for (Kozmeticar k: sviKozmeticari) {
								if ((k.getIme() + " " + k.getPrezime()).equals(imeIprezimeKozmeticara)) {
									koz = k.getKorisnickoIme();
									break;
								}
							}							
							String usluga = (String) table_1.getValueAt(red, 2);
							String tretman = (String) table_1.getValueAt(red, 3);
							StanjeZakazanogTretmana stanje = (StanjeZakazanogTretmana) table_1.getValueAt(red, 4);
							LocalDate datum = (LocalDate) table_1.getValueAt(red, 5);
							LocalTime vreme = (LocalTime) table_1.getValueAt(red, 6);
							LocalTime trajanjeTretmana = (LocalTime) table_1.getValueAt(red, 7);
							float cena = (float) table_1.getValueAt(red, 8);
							for (ZakazanKozmetickiTretman izmeniTretman: listaTretmana) {
								if (Float.compare(cena, izmeniTretman.getCena()) == 0 && trajanjeTretmana.equals(izmeniTretman.getVremeTrajanja()) && vreme.equals(izmeniTretman.getVremeTretmana()) 
										&& stanje.equals(StanjeZakazanogTretmana.ZAKAZAN) && tretman.equals(izmeniTretman.getNazivTretmana()) && usluga.equals(izmeniTretman.getNazivUsluge()) &&
										koz.equals(izmeniTretman.getKorisnickoImeKozmeticara()) && datum.equals(izmeniTretman.getDatumTretmana())) {
									int id = izmeniTretman.getIdZakazanogTretmana();
									
									String izabranoStanje = choice.getSelectedItem();
									if (izabranoStanje.equals("NIJE SE POJAVIO")) {
										stanje = StanjeZakazanogTretmana.NIJE_SE_POJAVIO;
									} else if (izabranoStanje.equals("OTKAZAO KLIJENT")) {
										stanje = StanjeZakazanogTretmana.OTKAZAO_KLIJENT;
									} else if (izabranoStanje.equals("OTKAZAO SALON")) {
										stanje = StanjeZakazanogTretmana.OTKAZAO_SALON;												
									}									
									zkt.edit(id, usluga, tretman, trajanjeTretmana, stanje, kli, koz, izmeniTretman.getNacinZakazivanja(), cena, datum, vreme);
									
									Component[] components = parent.getContentPane().getComponents();

									for (Component komponenta: components) {
										if (komponenta instanceof JScrollPane) {
											JViewport  viewport = ((JScrollPane) komponenta).getViewport();
											Component[] viewportComponents = viewport.getComponents();
											for (Component table : viewportComponents) {
												if (table instanceof JTable) {
													osveziTabelu((JTable)table, recepcioner);
												}
											}
										}
									}
								}
							}
							dispose();
						}
					}
				});
				okButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Odustani");
				cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
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
	public void osveziTabelu(JTable table, Recepcioner recepcioner) {
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));
		modelTabele = new DefaultTableModel(
			    new Object[][] {},
			    new String[] {
			       "klijent", "kozmeti\u010Dar", "usluga", "tretman", "stanje", "datum", "vreme", "trajanje tretmana", "cena"
			    }
			);
		table.setModel(modelTabele);
		
		String kozmeticariFile = ".//fajlovi/kozmeticari.csv";
		List<Kozmeticar> sviKozmeticari = new KozmeticariCRUD(kozmeticariFile).getKozmeticari();
		String klijentiFile = ".//fajlovi/klijenti.csv";
		List<Klijent> sviKlijenti = new KlijentiCRUD(klijentiFile).getKlijenti();
		String zakazaniFile = ".//fajlovi/zakazani.csv";
		ZakazanKozmetickiTretmanCRUD zkt = new ZakazanKozmetickiTretmanCRUD(zakazaniFile);
		HashMap<Integer, ZakazanKozmetickiTretman> recnikZakazanih = zkt.getRecnikZakazanih();	
			
		for (ZakazanKozmetickiTretman tretman: recnikZakazanih.values()) {
			String korImeKoz = tretman.getKorisnickoImeKozmeticara();
			String kozmeticar = "";
			for (Kozmeticar k: sviKozmeticari) {
				if (k.getKorisnickoIme().equals(korImeKoz)) {
					kozmeticar = k.getIme() + " " + k.getPrezime();
					break;
				}
			}
			String korImeKli = tretman.getKorisnickoImeKlijenta();
			String klijent = "";
			for (Klijent k : sviKlijenti) {
				if (k.getKorisnickoIme().equals(korImeKli)) {
					klijent = k.getIme() + " " + k.getPrezime();
					break;
				}
			}			
			Object[] red = {
					klijent,
					kozmeticar,
					tretman.getNazivUsluge(),
					tretman.getNazivTretmana(),
					tretman.getStanje(),
					tretman.getDatumTretmana(),
					tretman.getVremeTretmana(),
					tretman.getVremeTrajanja(),
					tretman.getCena()};
			((DefaultTableModel) table.getModel()).addRow(red);
		}
	}
}
