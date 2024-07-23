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
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import CRUD.KlijentiCRUD;
import CRUD.ZakazanKozmetickiTretmanCRUD;
import Zakazivanje.StanjeZakazanogTretmana;
import korisniciSistema.Klijent;
import korisniciSistema.Kozmeticar;
import uslugeTretmani.ZakazanKozmetickiTretman;

public class IzmeniTretmanKozmeticar extends JDialog {
	JFrame parentFrame;
	private final JPanel contentPanel = new JPanel();
	private JTable table_1;
	private DefaultTableModel modelTabele;

	public IzmeniTretmanKozmeticar(JFrame parent, Kozmeticar kozmeticar) {
		this.parentFrame = parent;
		
		setTitle("Izvrši tretman");
		getContentPane().setBackground(new Color(174, 227, 253));
		setIconImage(Toolkit.getDefaultToolkit().getImage(IzmeniTretmanKozmeticar.class.getResource("/gui/BeautySalon.png")));
		setBounds(100, 100, 1455, 412);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(174, 227, 253));
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		table_1 = new JTable();
		table_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		modelTabele = new DefaultTableModel(
			    new Object[][] {},
			    new String[] {
			        "kozmeti\u010Dar", "usluga", "tretman", "stanje", "datum", "vreme", "trajanje tretmana", "cena"
			    }
			);
			table_1.setModel(modelTabele);
//			table_1.setEnabled(false);
		
		String zakazaniFile = ".//fajlovi/zakazani.csv";
		ZakazanKozmetickiTretmanCRUD zkt = new ZakazanKozmetickiTretmanCRUD(zakazaniFile);
		HashMap<Integer, ZakazanKozmetickiTretman> recnikZakazanih = zkt.getRecnikZakazanih();
		String klijentiFile = ".//fajlovi/klijenti.csv";
		List<Klijent> sviKlijenti = new KlijentiCRUD(klijentiFile).getKlijenti();
		
		ArrayList<ZakazanKozmetickiTretman> listaKozTretmana = new ArrayList<>();
		for (HashMap.Entry<Integer, ZakazanKozmetickiTretman> zakazanTretman: recnikZakazanih.entrySet()) {
			if (zakazanTretman.getValue().getKorisnickoImeKozmeticara().equals(kozmeticar.getKorisnickoIme()) && (zakazanTretman.getValue().getStanje()).equals(StanjeZakazanogTretmana.ZAKAZAN)) {
				listaKozTretmana.add(zakazanTretman.getValue());
			}
		}
		for (ZakazanKozmetickiTretman tretman : listaKozTretmana) {
			String korImeKlijenta = tretman.getKorisnickoImeKlijenta();
			String klijent = "";
			for (Klijent k : sviKlijenti) {
				if (k.getKorisnickoIme().equals(korImeKlijenta)) {
					klijent = k.getIme() + " " + k.getPrezime();
					break;
				}
			}
			Object[] red = {
					klijent,
					tretman.getNazivUsluge(),
					tretman.getNazivTretmana(),
					tretman.getStanje(),
					tretman.getDatumTretmana(),
					tretman.getVremeTretmana(),
					tretman.getVremeTrajanja(),
					tretman.getCena()};
			((DefaultTableModel) table_1.getModel()).addRow(red);
		}
        table_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Omogućava selekciju samo jednog reda
        JScrollPane scrollPane = new JScrollPane(table_1);
        scrollPane.setBounds(10, 36, 1421, 296);
        contentPanel.add(scrollPane);
		scrollPane.setViewportView(table_1);
		{
			JLabel lblNewLabel = new JLabel("Izaberite tretman koji želite da izvršite:");
			lblNewLabel.setBounds(10, 10, 274, 16);
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
			contentPanel.add(lblNewLabel);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(174, 227, 253));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int red = table_1.getSelectedRow();
						if (red != -1) {
							String imeIprezimeKlijenta = (String) table_1.getValueAt(red, 0);
							
							String klijent = "";
							for (Klijent k: sviKlijenti) {
								if ((k.getIme() + " " + k.getPrezime()).equals(imeIprezimeKlijenta)) {
									klijent = k.getKorisnickoIme();
									break;
								}
							}			
							String usluga = (String) table_1.getValueAt(red, 1);
							String tretman = (String) table_1.getValueAt(red, 2);
							StanjeZakazanogTretmana stanje = (StanjeZakazanogTretmana) table_1.getValueAt(red, 3);
							LocalDate datum = (LocalDate) table_1.getValueAt(red, 4);
							LocalTime vreme = (LocalTime) table_1.getValueAt(red, 5);
							LocalTime trajanjeTretmana = (LocalTime) table_1.getValueAt(red, 6);
							float cena = (float) table_1.getValueAt(red, 7);
							for (ZakazanKozmetickiTretman izmeniTretman: listaKozTretmana) {
								if (Float.compare(cena, izmeniTretman.getCena()) == 0 && trajanjeTretmana.equals(izmeniTretman.getVremeTrajanja()) && vreme.equals(izmeniTretman.getVremeTretmana()) 
										&& stanje.equals(StanjeZakazanogTretmana.ZAKAZAN) && tretman.equals(izmeniTretman.getNazivTretmana()) && usluga.equals(izmeniTretman.getNazivUsluge()) &&
										klijent.equals(izmeniTretman.getKorisnickoImeKlijenta()) && datum.equals(izmeniTretman.getDatumTretmana())) {
									int id = izmeniTretman.getIdZakazanogTretmana();
									zkt.edit(id, usluga, tretman, trajanjeTretmana, StanjeZakazanogTretmana.IZVRSEN, klijent, kozmeticar.getKorisnickoIme(), izmeniTretman.getNacinZakazivanja(), cena, datum, vreme);
									
									JTable tabela = new JTable();
									Component[] components = parent.getContentPane().getComponents();

									for (Component component: components) {
										if (component instanceof JTabbedPane) {
											Component [] komponente = ((JTabbedPane) component).getComponents();
											for (Component komponenta : komponente) {
												if (komponenta instanceof JPanel) {
													Component [] kompo = ((JPanel) komponenta).getComponents();
													for (Component komp : kompo) {
														if (komp instanceof JScrollPane) {
															JViewport  viewport = ((JScrollPane) komp).getViewport();
															Component[] viewportComponents = viewport.getComponents();
															for (Component table : viewportComponents) {
																if (table instanceof JTable) {
																	tabela = (JTable) table;
																}
															}
														}
													}
												}
											}
										}

									}
									osveziTabelu(tabela, kozmeticar);
								}
							}
							dispose();
						}
					}

					private void osveziTabelu(JTable tabela, Kozmeticar kozmeticar) {
						tabela.setFont(new Font("Tahoma", Font.PLAIN, 15));
						modelTabele = new DefaultTableModel(
							    new Object[][] {},
							    new String[] {
							        "kozmeti\u010Dar", "usluga", "tretman", "stanje", "datum", "vreme", "trajanje tretmana", "cena"
							    }
							);
						tabela.setModel(modelTabele);
				        ((DefaultTableModel) tabela.getModel()).setRowCount(0);
	
						String zakazaniFile = ".//fajlovi/zakazani.csv";
						ZakazanKozmetickiTretmanCRUD zkt = new ZakazanKozmetickiTretmanCRUD(zakazaniFile);
						HashMap<Integer, ZakazanKozmetickiTretman> recnikZakazanih = zkt.getRecnikZakazanih();
						String klijentiFile = ".//fajlovi/klijenti.csv";
						List<Klijent> sviKlijenti = new KlijentiCRUD(klijentiFile).getKlijenti();
						
						ArrayList<ZakazanKozmetickiTretman> listaKozTretmana = new ArrayList<>();
						for (HashMap.Entry<Integer, ZakazanKozmetickiTretman> zakazanTretman: recnikZakazanih.entrySet()) {
							if (zakazanTretman.getValue().getKorisnickoImeKozmeticara().equals(kozmeticar.getKorisnickoIme())) {
								listaKozTretmana.add(zakazanTretman.getValue());
							}
						}
						for (ZakazanKozmetickiTretman tretman : listaKozTretmana) {
							String korImeKlijenta = tretman.getKorisnickoImeKlijenta();
							String klijent = "";
							for (Klijent k : sviKlijenti) {
								if (k.getKorisnickoIme().equals(korImeKlijenta)) {
									klijent = k.getIme() + " " + k.getPrezime();
									break;
								}
							}
							Object[] red = {
									klijent,
									tretman.getNazivUsluge(),
									tretman.getNazivTretmana(),
									tretman.getStanje(),
									tretman.getDatumTretmana(),
									tretman.getVremeTretmana(),
									tretman.getVremeTrajanja(),
									tretman.getCena()};
							((DefaultTableModel) tabela.getModel()).addRow(red);
						}
						JTableHeader header = tabela.getTableHeader();
				        Font headerFont = header.getFont();
				        Font newHeaderFont = new Font(headerFont.getName(), Font.BOLD, 20);
				        header.setFont(newHeaderFont);
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
}
