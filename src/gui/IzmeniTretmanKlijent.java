package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import CRUD.KlijentiCRUD;
import CRUD.KozmeticariCRUD;
import CRUD.KozmetickiSalonCRUD;
import CRUD.ZakazanKozmetickiTretmanCRUD;
import Zakazivanje.StanjeZakazanogTretmana;
import korisniciSistema.Klijent;
import korisniciSistema.Kozmeticar;
import kozmetickiSalon.KozmetickiSalon;
import uslugeTretmani.ZakazanKozmetickiTretman;

public class IzmeniTretmanKlijent extends JDialog {
	JFrame parentFrame;
	private final JPanel contentPanel = new JPanel();
    private JTable table;

	public IzmeniTretmanKlijent(JFrame parent, Klijent klijent, JLabel lblNewLabel_4, JLabel lblNewLabel_3) {
		this.parentFrame = parent;	

		setTitle("Otkaži tretman");
		setBackground(new Color(174, 227, 253));
		getContentPane().setBackground(new Color(174, 227, 253));
		setIconImage(Toolkit.getDefaultToolkit().getImage(IzmeniTretmanKlijent.class.getResource("/gui/BeautySalon.png")));
		setBounds(100, 100, 1455, 412);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(174, 227, 253));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Izaberite tretman koji želite da otkažete:");
			lblNewLabel.setBounds(10, 10, 274, 16);
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
			contentPanel.add(lblNewLabel);
		}
		String zakazaniFile = ".//fajlovi/zakazani.csv";
		ZakazanKozmetickiTretmanCRUD zkt = new ZakazanKozmetickiTretmanCRUD(zakazaniFile);
		HashMap<Integer, ZakazanKozmetickiTretman> recnikZakazanih = zkt.getRecnikZakazanih();
		String kozmeticariFile = ".//fajlovi/kozmeticari.csv";
		List<Kozmeticar> sviKozmeticari = new KozmeticariCRUD(kozmeticariFile).getKozmeticari();
		
		ArrayList<ZakazanKozmetickiTretman> listaKlijentovihTretmana = new ArrayList<>();
		for (HashMap.Entry<Integer, ZakazanKozmetickiTretman> zakazanTretman: recnikZakazanih.entrySet()) {
			if (zakazanTretman.getValue().getKorisnickoImeKlijenta().equals(klijent.getKorisnickoIme()) && zakazanTretman.getValue().getStanje().equals(StanjeZakazanogTretmana.ZAKAZAN)) {
				listaKlijentovihTretmana.add(zakazanTretman.getValue());
			}
		}
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));
		DefaultTableModel modelTabele = new DefaultTableModel(
			    new Object[][] {},
			    new String[] {
			        "kozmeti\u010Dar", "usluga", "tretman", "stanje", "datum", "vreme", "trajanje tretmana", "cena"
			    }
			);
		table.setModel(modelTabele);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Omogućava selekciju samo jednog reda
//        table.setEnabled(false);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 36, 1421, 296);
        contentPanel.add(scrollPane);
        
		for (ZakazanKozmetickiTretman klijentovTretman: listaKlijentovihTretmana) {
			String korImeKoz = klijentovTretman.getKorisnickoImeKozmeticara();
			String kozmeticar = "";
			for (Kozmeticar k: sviKozmeticari) {
				if (k.getKorisnickoIme().equals(korImeKoz)) {
					kozmeticar = k.getIme() + " " + k.getPrezime();
					break;
				}
			}
			Object[] red = {
					kozmeticar,
					klijentovTretman.getNazivUsluge(),
					klijentovTretman.getNazivTretmana(),
					klijentovTretman.getStanje(),
					klijentovTretman.getDatumTretmana(),
					klijentovTretman.getVremeTretmana(),
					klijentovTretman.getVremeTrajanja(),
					klijentovTretman.getCena()};
			((DefaultTableModel) table.getModel()).addRow(red);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(174, 227, 253));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Otkaži tretman");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int red = table.getSelectedRow();
						if (red != -1) {
							String imeIprezimeKozmeticara = (String) table.getValueAt(red, 0);
							
							String koz = "";
							for (Kozmeticar k: sviKozmeticari) {
								if ((k.getIme() + " " + k.getPrezime()).equals(imeIprezimeKozmeticara)) {
									koz = k.getKorisnickoIme();
									break;
								}
							}
							
							String usluga = (String) table.getValueAt(red, 1);
							String tretman = (String) table.getValueAt(red, 2);
							StanjeZakazanogTretmana stanje = (StanjeZakazanogTretmana) table.getValueAt(red, 3);
							LocalDate datum = (LocalDate) table.getValueAt(red, 4);
							LocalTime vreme = (LocalTime) table.getValueAt(red, 5);
							LocalTime trajanjeTretmana = (LocalTime) table.getValueAt(red, 6);
							float cena = (float) table.getValueAt(red, 7);
							for (ZakazanKozmetickiTretman izmeniTretman: listaKlijentovihTretmana) {
								if (Float.compare(cena, izmeniTretman.getCena()) == 0 && trajanjeTretmana.equals(izmeniTretman.getVremeTrajanja()) && vreme.equals(izmeniTretman.getVremeTretmana()) 
										&& stanje.equals(StanjeZakazanogTretmana.ZAKAZAN) && tretman.equals(izmeniTretman.getNazivTretmana()) && usluga.equals(izmeniTretman.getNazivUsluge()) &&
										koz.equals(izmeniTretman.getKorisnickoImeKozmeticara()) && datum.equals(izmeniTretman.getDatumTretmana())) {
									int id = izmeniTretman.getIdZakazanogTretmana();
									zkt.edit(id, usluga, tretman, trajanjeTretmana, StanjeZakazanogTretmana.OTKAZAO_KLIJENT, klijent.getKorisnickoIme(), koz, izmeniTretman.getNacinZakazivanja(), cena, datum, vreme);
									
									Component[] components = parent.getContentPane().getComponents();

									for (Component komponenta: components) {
										if (komponenta instanceof JScrollPane) {
											JViewport  viewport = ((JScrollPane) komponenta).getViewport();
											Component[] viewportComponents = viewport.getComponents();
											for (Component table : viewportComponents) {
												if (table instanceof JTable) {
													osveziTabelu((JTable)table, klijent, lblNewLabel_4, lblNewLabel_3);
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
	
	public void osveziTabelu(JTable table,  Klijent klijent, JLabel lblNewLabel_4, JLabel lblNewLabel_3) {
		
		String lojalitiKartica = " NE ";
		if (klijent.getLojalitiKartica()) {
			lojalitiKartica = "DA";
		}
		lblNewLabel_4.setText("Posedujete lojaliti karticu: " + lojalitiKartica);
		
		String zakazaniFile = ".//fajlovi/zakazani.csv";		
		HashMap<Integer, ZakazanKozmetickiTretman> zkt = new ZakazanKozmetickiTretmanCRUD(zakazaniFile).getRecnikZakazanih();
		String klijentiFile = ".//fajlovi/klijenti.csv";
		KlijentiCRUD listaKlijenata = new KlijentiCRUD(klijentiFile);
		
		float potrosnja = listaKlijenata.stanjeNaKartici(klijent.getKorisnickoIme(), zkt);
		lblNewLabel_3.setText("Stanje na kartici: " + potrosnja);
		
		DefaultTableModel modelTabele = new DefaultTableModel(
			    new Object[][] {},
			    new String[] {
			        "kozmeti\u010Dar", "usluga", "tretman", "stanje", "datum", "vreme", "trajanje tretmana", "cena"
			    }
			);
		table.setModel(modelTabele);

        ((DefaultTableModel) table.getModel()).setRowCount(0);
        

		String kozmeticariFile = ".//fajlovi/kozmeticari.csv";
		List<Kozmeticar> sviKozmeticari = new KozmeticariCRUD(kozmeticariFile).getKozmeticari();
		
		ArrayList<ZakazanKozmetickiTretman> listaKlijentovihTretmana = new ArrayList<>();
		for (HashMap.Entry<Integer, ZakazanKozmetickiTretman> zakazanTretman: zkt.entrySet()) {
			if (zakazanTretman.getValue().getKorisnickoImeKlijenta().equals(klijent.getKorisnickoIme())) {
				listaKlijentovihTretmana.add(zakazanTretman.getValue());
			}
		}
        
		for (ZakazanKozmetickiTretman klijentovTretman: listaKlijentovihTretmana) {
			String korImeKoz = klijentovTretman.getKorisnickoImeKozmeticara();
			String kozmeticar = "";
			for (Kozmeticar k: sviKozmeticari) {
				if (k.getKorisnickoIme().equals(korImeKoz)) {
					kozmeticar = k.getIme() + " " + k.getPrezime();
					break;
				}
			}
			Object[] red = {
					kozmeticar,
					klijentovTretman.getNazivUsluge(),
					klijentovTretman.getNazivTretmana(),
					klijentovTretman.getStanje(),
					klijentovTretman.getDatumTretmana(),
					klijentovTretman.getVremeTretmana(),
					klijentovTretman.getVremeTrajanja(),
					klijentovTretman.getCena()};
			((DefaultTableModel) table.getModel()).addRow(red);
		}
		JTableHeader header = table.getTableHeader();
        Font headerFont = header.getFont();
        Font newHeaderFont = new Font(headerFont.getName(), Font.BOLD, 20);
        header.setFont(newHeaderFont);
	}
}
