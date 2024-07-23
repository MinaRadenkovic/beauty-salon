package gui;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.demo.charts.ExampleChart;

import CRUD.KlijentiCRUD;
import CRUD.KozmeticariCRUD;
import CRUD.KozmetickaUslugaCRUD;
import CRUD.KozmetickiSalonCRUD;
import CRUD.MenadzeriCRUD;
import CRUD.RecepcioneriCRUD;
import CRUD.TipKozmetickogTretmanaCRUD;
import CRUD.ZakazanKozmetickiTretmanCRUD;
import izvestaji.AngazovanjeKozmeticara;
import izvestaji.AreaChart;
import izvestaji.Izvestaji;
import izvestaji.StanjePojedinacnihTretmana;
import korisniciSistema.Klijent;
import korisniciSistema.Kozmeticar;
import korisniciSistema.Menadzer;
import korisniciSistema.Recepcioner;
import kozmetickiSalon.KozmetickiSalon;
import uslugeTretmani.KozmetickaUslugaTretman;
import uslugeTretmani.TipKozmetickogTretmana;
import uslugeTretmani.ZakazanKozmetickiTretman;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

public class menadzeriMeni extends JFrame implements ItemListener{

	private JPanel contentPane;
	private JTable tabelaKlijenata = new JTable();
	private DefaultTableModel modelZaKlijente;
	private JTable tabelaRecepcionera = new JTable();
	private DefaultTableModel modelZaRecepcionere;
	private JTable tabelaUsluga = new JTable();
	private DefaultTableModel modelZaUsluge;
	private JTable tabelaTretmana = new JTable();
	private DefaultTableModel modelZaTretmane;
	private JTable tabelaMenadzera = new JTable();
	private DefaultTableModel modelZaMenadzere;
	private JTable tabelaKozmeticara = new JTable();
	private DefaultTableModel modelZaKozmeticare;
	private JTable tabelaZakazanih = new JTable();
	private DefaultTableModel modelZaZakazane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JDateChooser chooser = new JDateChooser();
	private JDateChooser chooser2 = new JDateChooser();
	private Choice choice;
	private JPanel panel_10;
	private JPanel panel_9 = new JPanel();
	private JTable tabelaAngazovanja = new JTable();
	private DefaultTableModel modelZaAngKoz;
	private JScrollPane scrollPane_4 = new JScrollPane();
	private JTable tabelaKartica = new JTable();
	private DefaultTableModel modelZaKartice;
	private JTable tabelaStanja = new JTable();
	private DefaultTableModel modelZaStanje;
	private JTable tabelaIzvestajUsluga = new JTable();
	private DefaultTableModel modelZaIzvestajUsluga;


	public menadzeriMeni(Menadzer menadzer) {
		
		String klijentiFile = ".//fajlovi/klijenti.csv";
		String kozmeticariFile = ".//fajlovi/kozmeticari.csv";
		String menadzeriFile = ".//fajlovi/menadzeri.csv";
		String salonFile = ".//fajlovi/saloni.csv";
		String recepcineriFile = ".//fajlovi/recepcioneri.csv";
		
		String uslugeFile = ".//fajlovi/usluge.csv";
		String tretmaniFile = ".//fajlovi/tretmani.csv";
		String zakazaniFile = ".//fajlovi/zakazani.csv";
		
		List<Klijent> listaKlijenata = new KlijentiCRUD(klijentiFile).getKlijenti();
		List<Kozmeticar> listaKozmeticara = new KozmeticariCRUD(kozmeticariFile).getKozmeticari();
		List<Menadzer> listaMenadzera = new MenadzeriCRUD(menadzeriFile).getMenadzeri();
		List<Recepcioner> listaRecepcionera = new RecepcioneriCRUD(recepcineriFile).getRecepcioneri();
		List<KozmetickiSalon> ks = new KozmetickiSalonCRUD(salonFile).getSaloni();
		
		List<KozmetickaUslugaTretman> kut = new KozmetickaUslugaCRUD(uslugeFile).getUsluge();
		HashMap<Integer, TipKozmetickogTretmana> tkt = new TipKozmetickogTretmanaCRUD(tretmaniFile).getRecnikTretmana();
		HashMap<Integer, ZakazanKozmetickiTretman> zkt = new ZakazanKozmetickiTretmanCRUD(zakazaniFile).getRecnikZakazanih();
		
		OsvezavanjeTabelaMenadzer osvezi = new OsvezavanjeTabelaMenadzer();
		
		setTitle("SV76-2022");
		setIconImage(Toolkit.getDefaultToolkit().getImage(kozmeticariMeni.class.getResource("/gui/BeautySalon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int maxWidth = screenSize.width;
		int maxHeight = screenSize.height;

		setBounds(100, 100, maxWidth, maxHeight);		
		setLocation(0, 0);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(174, 227, 253));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("KOMPLETAN PRISTUP POSLOVANJEM");
		lblNewLabel.setBounds(467, 21, 634, 31);
		lblNewLabel.setToolTipText("");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		contentPane.add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 73, 1515, 2);
		contentPane.add(separator);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(35, 60, 200, 200);
		lblNewLabel_1.setIcon(new ImageIcon(menadzeriMeni.class.getResource("/gui/BeautySalon.png")));
		contentPane.add(lblNewLabel_1);
		
		String ime = menadzer.getIme();
		String prezime = menadzer.getPrezime();
		JLabel lblNewLabel_2 = new JLabel("Menadžer: " + ime + " " + prezime);
		lblNewLabel_2.setBounds(283, 96, 605, 40);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 23));
		contentPane.add(lblNewLabel_2);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 247, 1515, 2);
		contentPane.add(separator_1);
		
		JButton btnNewButton = new JButton("ODJAVI ME");
		btnNewButton.setBounds(1285, 101, 171, 35);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logIN frame = new logIN();
				dispose();
				frame.setVisible(true);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 23));
		contentPane.add(btnNewButton);
		
//-------------------------------------------------------------------------------------------------
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(45, 270, 1439, 547);
		contentPane.add(tabbedPane);
		
		Font tabFont = new Font("Tahoma", Font.PLAIN, 16); 
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("korisnici", null, panel, null);
		panel.setLayout(null);
		
		tabbedPane.setFont(tabFont);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setBounds(10, 10, 1414, 500);
		panel.add(tabbedPane_1);
		
//----------------------------------------------------------------------------------------------
		
		JPanel panel_1 = new JPanel();
		tabbedPane_1.addTab("klijenti", null, panel_1, null);
		
		tabbedPane_1.setFont(tabFont);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 1208, 446);
		panel_1.add(scrollPane);
		
		tabelaKlijenata.setFont(new Font("Tahoma", Font.PLAIN, 15));

		modelZaKlijente = new DefaultTableModel(
				new Object[][] {},
				new String[] {
						"korisničko ime", "lozinka", "ime", "prezime", "pol", "broj telefona", "adresa", "lojaliti kartica"
				}
			);
		tabelaKlijenata.setModel(modelZaKlijente);
		
		for (Klijent k : listaKlijenata) {
			Object [] red = {
					k.getKorisnickoIme(),
					k.getLozinka(),
					k.getIme(),
					k.getPrezime(),
					k.getPol(),
					k.getTelefon(),
					k.getAdresa(),
					k.getLojalitiKartica()};
			((DefaultTableModel) tabelaKlijenata.getModel()).addRow(red);
		}
		scrollPane.setViewportView(tabelaKlijenata);
		
        tabelaKlijenata.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JButton btnDodaj = new JButton("DODAJ");
		btnDodaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DodajKlijentaMenadzer frame = new DodajKlijentaMenadzer(tabelaKlijenata);
				frame.setVisible(true);
			}
		});
		btnDodaj.setFont(new Font("Tahoma", Font.PLAIN, 23));
		btnDodaj.setBounds(1228, 40, 171, 35);
		panel_1.add(btnDodaj);
		
		JButton btnIzmeni_1 = new JButton("IZMENI");
		btnIzmeni_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
        		if (tabelaKlijenata.getSelectedRow() != -1) {
        			int red = tabelaKlijenata.getSelectedRow();
        			String korImeKli = (String) tabelaKlijenata.getValueAt(red,	0);
        			IzmenaKlijentaMenadzer frame = new IzmenaKlijentaMenadzer(tabelaKlijenata, korImeKli);
        			frame.setVisible(true);
        		}
			}
		});
		btnIzmeni_1.setFont(new Font("Tahoma", Font.PLAIN, 23));
		btnIzmeni_1.setBounds(1228, 98, 171, 35);
		panel_1.add(btnIzmeni_1);
		
		JButton btnIzmeni_1_1 = new JButton("OBRIŠI");
		btnIzmeni_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tabelaKlijenata.getSelectedRow() != -1) {
        			int red = tabelaKlijenata.getSelectedRow();
					String korImeKli = (String) tabelaKlijenata.getValueAt(red, 0);
					List<Klijent> listaKlijenata = new KlijentiCRUD(klijentiFile).getKlijenti();

					for (Klijent k : listaKlijenata) {
						if (k.getKorisnickoIme().equals(korImeKli)) {
							KlijentiCRUD klijentCRUD = new KlijentiCRUD(klijentiFile);
							klijentCRUD.remove(korImeKli);
			
							OsvezavanjeTabelaMenadzer osvezi = new OsvezavanjeTabelaMenadzer();
							osvezi.osveziTabeluKlijenata(tabelaKlijenata);
							break;
						}
					}
				}
			}
		});
		btnIzmeni_1_1.setFont(new Font("Tahoma", Font.PLAIN, 23));
		btnIzmeni_1_1.setBounds(1228, 154, 171, 35);
		panel_1.add(btnIzmeni_1_1);
		
		JTableHeader header1 = tabelaKlijenata.getTableHeader();
        Font headerFont1 = header1.getFont();
        Font newHeaderFont1 = new Font(headerFont1.getName(), Font.BOLD, 15);
        header1.setFont(newHeaderFont1);
		
//--------------------------------------------------------------------------------------------

        JPanel panel_2 = new JPanel();
		tabbedPane_1.addTab("recepcioneri", null, panel_2, null);
		panel_2.setLayout(null);
		
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(10, 10, 1208, 439);
		panel_2.add(scrollPane2);
		
		tabelaRecepcionera.setFont(new Font ("Tahoma", Font.PLAIN, 15));
		
		modelZaRecepcionere = new DefaultTableModel(
				new Object [][] {},
				new String [] {
						"korisnički ID", "lozinka", "ime", "prezime", "pol", "broj telefona", "adresa", "stručna sprema", "staž", "osnovna plata", "plata"
				});
		tabelaRecepcionera.setModel(modelZaRecepcionere);
		
		for (Recepcioner recepcioner : listaRecepcionera) {
			String[] red = {
					recepcioner.getKorisnickoIme(),
					recepcioner.getLozinka(),
					recepcioner.getIme(),
					recepcioner.getPrezime(),
					recepcioner.getPol(),
					recepcioner.getTelefon(),
					recepcioner.getAdresa(),
					Integer.toString(recepcioner.getStrucnaSprema()),
					Integer.toString(recepcioner.getStaz()),
					Double.toString(recepcioner.getOsnovnaPlata()),
					Double.toString(recepcioner.getPlata())};
			((DefaultTableModel) tabelaRecepcionera.getModel()).addRow(red);
		}		
		JTableHeader headerR = tabelaRecepcionera.getTableHeader();
		headerR.setFont(newHeaderFont1);
		
		scrollPane2.setViewportView(tabelaRecepcionera);
		tabelaRecepcionera.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JButton btnDodajR = new JButton("DODAJ");
		btnDodajR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DodajZaposlenogMenadzer dodajRecepcionera = new DodajZaposlenogMenadzer(tabelaRecepcionera, "recepcioner");
				dodajRecepcionera.setVisible(true);
			}
		});
		btnDodajR.setFont(new Font("Tahoma", Font.PLAIN,23));
		btnDodajR.setBounds(1228, 38, 171, 35);
		panel_2.add(btnDodajR);
		
		JButton btnIR = new JButton("IZMENI");
		btnIR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tabelaRecepcionera.getSelectedRow() != -1) {
					int red = tabelaRecepcionera.getSelectedRow();
					String korImeRec = (String) tabelaRecepcionera.getValueAt(red, 0);					
					RecepcioneriCRUD recCRUD = new RecepcioneriCRUD(recepcineriFile);
					Recepcioner recepcioner = recCRUD.PronadjiRecepcioneraPoUsernameu(korImeRec);
					IzmeniZaposlenogMenadzer izmenaRecep = new IzmeniZaposlenogMenadzer(tabelaRecepcionera, recepcioner, "recepcioner");
					izmenaRecep.setVisible(true);
				}
			}
		});
		btnIR.setFont(new Font("Tahoma", Font.PLAIN, 23));
		btnIR.setBounds(1228, 83, 171, 35);
		panel_2.add(btnIR);
		
		JButton btnOR = new JButton("OBRIŠI");
		btnOR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tabelaRecepcionera.getSelectedRow() != -1) {
					int red = tabelaRecepcionera.getSelectedRow();
					String korImeRec = (String) tabelaRecepcionera.getValueAt(red, 0);
					List<Recepcioner> listaRecepcionera = new RecepcioneriCRUD(recepcineriFile).getRecepcioneri();
					for (Recepcioner r : listaRecepcionera) {
						if (r.getKorisnickoIme().equals(korImeRec)) {
							RecepcioneriCRUD recepcioneriCRUD = new RecepcioneriCRUD(recepcineriFile);
							recepcioneriCRUD.remove(korImeRec);
							OsvezavanjeTabelaMenadzer osvezi = new OsvezavanjeTabelaMenadzer();
							osvezi.osveziTabeluRecepcionera(tabelaRecepcionera);
							break;
						}
					}
				}
			}
		});		
		btnOR.setFont(new Font("Tahoma", Font.PLAIN, 23));
		btnOR.setBounds(1228, 128, 171, 35);
		panel_2.add(btnOR);
		
//----------------------------------------------------------------------------------------------------
		
		JPanel panel_3 = new JPanel();
		tabbedPane_1.addTab("kozmetičari", null, panel_3, null);
		panel_3.setLayout(null);
		
		JScrollPane scrollPane4 = new JScrollPane();
		scrollPane4.setBounds(10, 10, 1208, 439);
		panel_3.add(scrollPane4);
		
		tabelaKozmeticara.setFont(new Font ("Tahoma", Font.PLAIN, 15));

		modelZaKozmeticare = new DefaultTableModel(
				new Object [][] {},
				new String [] {
						"korisnički ID", "lozinka", "ime", "prezime", "pol", "broj telefona", "adresa", "stručna sprema", "staž", "osnovna plata", "plata", "usluge"
				});		
		tabelaKozmeticara.setModel(modelZaKozmeticare);
		
		for (Kozmeticar kozmeticar : listaKozmeticara) {
			List<KozmetickaUslugaTretman> tretmani = kozmeticar.getTretmani();
			String[] red = {
					kozmeticar.getKorisnickoIme(),
					kozmeticar.getLozinka(),
					kozmeticar.getIme(),
					kozmeticar.getPrezime(),
					kozmeticar.getPol(),
					kozmeticar.getTelefon(),
					kozmeticar.getAdresa(),
					Integer.toString(kozmeticar.getStrucnaSprema()),
					Integer.toString(kozmeticar.getStaz()),
					Double.toString(kozmeticar.getOsnovnaPlata()),
					Double.toString(kozmeticar.getPlata()),
					kozmeticar.tretmaniZaTabelu(tretmani)};
			((DefaultTableModel) tabelaKozmeticara.getModel()).addRow(red);
		}		
		JTableHeader headerKoz = tabelaKozmeticara.getTableHeader();
		headerKoz.setFont(newHeaderFont1);
		
		scrollPane4.setViewportView(tabelaKozmeticara);
		tabelaKozmeticara.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JButton btnDodajKoz = new JButton("DODAJ");
		btnDodajKoz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DodajZaposlenogMenadzer dodajKoz = new DodajZaposlenogMenadzer(tabelaKozmeticara, "kozmetičar");
				dodajKoz.setVisible(true);
			}
		});
		btnDodajKoz.setFont(new Font("Tahoma", Font.PLAIN,23));
		btnDodajKoz.setBounds(1228, 38, 171, 35);
		panel_3.add(btnDodajKoz);
		
		JButton btnIKoz = new JButton("IZMENI");
		btnIKoz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tabelaKozmeticara.getSelectedRow() != -1) {
					int red = tabelaKozmeticara.getSelectedRow();
					String korImeRec = (String) tabelaKozmeticara.getValueAt(red, 0);					
					KozmeticariCRUD kozCRUD = new KozmeticariCRUD(kozmeticariFile);
					Kozmeticar kozmeticar = kozCRUD.PronadjiKozmeticaraPoUsernameu(korImeRec);
					IzmeniZaposlenogMenadzer izmenaKoz = new IzmeniZaposlenogMenadzer(tabelaKozmeticara, kozmeticar, "kozmetičar");
					izmenaKoz.setVisible(true);
				}
			}
		});
		btnIKoz.setFont(new Font("Tahoma", Font.PLAIN, 23));
		btnIKoz.setBounds(1228, 83, 171, 35);
		panel_3.add(btnIKoz);
		
		JButton btnOKoz = new JButton("OBRIŠI");
		btnOKoz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tabelaKozmeticara.getSelectedRow() != -1) {
					int red = tabelaKozmeticara.getSelectedRow();
					String korImeRec = (String) tabelaKozmeticara.getValueAt(red, 0);
					List<Kozmeticar> listaKozmeticara = new KozmeticariCRUD(kozmeticariFile).getKozmeticari();
					for (Kozmeticar k : listaKozmeticara) {
						if (k.getKorisnickoIme().equals(korImeRec)) {
							KozmeticariCRUD kozmeticariCRUD = new KozmeticariCRUD(kozmeticariFile);
							kozmeticariCRUD.remove(korImeRec);
							OsvezavanjeTabelaMenadzer osvezi = new OsvezavanjeTabelaMenadzer();
							osvezi.osveziTabeluKozmeticara(tabelaKozmeticara);
							break;
						}
					}
				}
			}
		});	
		btnOKoz.setFont(new Font("Tahoma", Font.PLAIN, 23));
		btnOKoz.setBounds(1228, 128, 171, 35);
		panel_3.add(btnOKoz);
				
//-----------------------------------------------------------------------------------------------------

		JPanel panel_4 = new JPanel();
		tabbedPane_1.addTab("menadžeri", null, panel_4, null);
		panel_4.setLayout(null);
		
		JScrollPane scrollPane3 = new JScrollPane();
		scrollPane3.setBounds(10, 10, 1208, 439);
		panel_4.add(scrollPane3);
		
		tabelaMenadzera.setFont(new Font ("Tahoma", Font.PLAIN, 15));

		modelZaMenadzere = new DefaultTableModel(
				new Object [][] {},
				new String [] {
						"korisnički ID", "lozinka", "ime", "prezime", "pol", "broj telefona", "adresa", "stručna sprema", "staž", "osnovna plata", "plata"
				});
		tabelaMenadzera.setModel(modelZaMenadzere);
		
		for (Menadzer m : listaMenadzera) {
			String[] red = {
					m.getKorisnickoIme(),
					m.getLozinka(),
					m.getIme(),
					m.getPrezime(),
					m.getPol(),
					m.getTelefon(),
					m.getAdresa(),
					Integer.toString(m.getStrucnaSprema()),
					Integer.toString(m.getStaz()),
					Double.toString(m.getOsnovnaPlata()),
					Double.toString(m.getPlata())};
			((DefaultTableModel) tabelaMenadzera.getModel()).addRow(red);
		}		
		JTableHeader headerM = tabelaMenadzera.getTableHeader();
		headerM.setFont(newHeaderFont1);
		
		scrollPane3.setViewportView(tabelaMenadzera);
		tabelaMenadzera.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JButton btnDodajM = new JButton("DODAJ");
		btnDodajM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DodajZaposlenogMenadzer dodajMenadzera = new DodajZaposlenogMenadzer(tabelaMenadzera, "menadžer");
				dodajMenadzera.setVisible(true);
			}
		});
		btnDodajM.setFont(new Font("Tahoma", Font.PLAIN, 23));
		btnDodajM.setBounds(1228, 38, 171, 35);
		panel_4.add(btnDodajM);
		
		JButton btnIM = new JButton("IZMENI");
		btnIM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tabelaMenadzera.getSelectedRow() != -1) {
					int red = tabelaMenadzera.getSelectedRow();
					String korImeRec = (String) tabelaMenadzera.getValueAt(red, 0);					
					MenadzeriCRUD menadzCRUD = new MenadzeriCRUD(menadzeriFile);
					Menadzer menadzer = menadzCRUD.PronadjiMenadzeraPoUsernameu(korImeRec);
					IzmeniZaposlenogMenadzer izmenaMenadz = new IzmeniZaposlenogMenadzer(tabelaMenadzera, menadzer, "menadžer");
					izmenaMenadz.setVisible(true);
				}
			}
		});
		btnIM.setFont(new Font("Tahoma", Font.PLAIN, 23));
		btnIM.setBounds(1228, 83, 171, 35);
		panel_4.add(btnIM);
		
		JButton btnOM = new JButton("OBRIŠI");
		btnOM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tabelaMenadzera.getSelectedRow() != -1) {
					int red = tabelaMenadzera.getSelectedRow();
					String korImeRec = (String) tabelaMenadzera.getValueAt(red, 0);
					List<Menadzer> listaMenadzera = new MenadzeriCRUD(menadzeriFile).getMenadzeri();
					for (Menadzer m : listaMenadzera) {
						if (m.getKorisnickoIme().equals(korImeRec)) {
							MenadzeriCRUD menadzeriCRUD = new MenadzeriCRUD(menadzeriFile);
							menadzeriCRUD.remove(korImeRec);
							OsvezavanjeTabelaMenadzer osvezi = new OsvezavanjeTabelaMenadzer();
							osvezi.osveziTabeluMenadzera(tabelaMenadzera);
							break;
						}
					}
				}
			}
		});
		btnOM.setFont(new Font("Tahoma", Font.PLAIN, 23));
		btnOM.setBounds(1228, 128, 171, 35);
		panel_4.add(btnOM);
		
//---------------------------------------------------------------------------------------------------
		
		JPanel panel_5 = new JPanel();
		tabbedPane.addTab("usluge i tretmani", null, panel_5, null);
		panel_5.setLayout(null);
		
		JTabbedPane tabbedPane_2 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_2.setBounds(10, 10, 1414, 493);
		panel_5.add(tabbedPane_2);
		tabbedPane_2.setFont(tabFont);
		
//-------------------------------------------------------------------------------------------------
		
		JPanel panel_6 = new JPanel();
		tabbedPane_2.addTab("usluge", null, panel_6, null);
		panel_6.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 10, 1208, 439);
		panel_6.add(scrollPane_1);
		
		tabelaUsluga.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		modelZaUsluge = new DefaultTableModel(
				new Object [][] {},
				new String [] {
						"naziv usluge"
				});		
		tabelaUsluga.setModel(modelZaUsluge);
		
		for (KozmetickaUslugaTretman usluga: kut) {
			String[] red = {
				usluga.getNazivUsluge()};
			((DefaultTableModel) tabelaUsluga.getModel()).addRow(red);
		}		
		JTableHeader header = tabelaUsluga.getTableHeader();
        header.setFont(headerFont1);
		
		scrollPane_1.setViewportView(tabelaUsluga);
		
		JButton btnDodajUslugu = new JButton("DODAJ");
		btnDodajUslugu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DodajUsluguMenadzer frame = new DodajUsluguMenadzer(tabelaUsluga);
				frame.setVisible(true);
			}
		});
		btnDodajUslugu.setFont(new Font("Tahoma", Font.PLAIN, 23));
		btnDodajUslugu.setBounds(1228, 44, 171, 35);
		panel_6.add(btnDodajUslugu);
		
        tabelaUsluga.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JButton btnDodajUslugu_1 = new JButton("OBRIŠI");
		btnDodajUslugu_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tabelaUsluga.getSelectedRow() != -1) {
					String usluga = (String) tabelaUsluga.getValueAt(tabelaUsluga.getSelectedRow(), 0);
					for  (KozmetickaUslugaTretman u : kut) {
						if (u.getNazivUsluge().equals(usluga)) {
							
							TipKozmetickogTretmanaCRUD tretmaniCRUD = new TipKozmetickogTretmanaCRUD(tretmaniFile);
							tretmaniCRUD.remove2(usluga);
							KozmetickaUslugaCRUD uslugaCRUD = new KozmetickaUslugaCRUD(uslugeFile);
							uslugaCRUD.remove(usluga);
														
							osvezi.osveziTabeluUsluga(tabelaUsluga);
							osvezi.osveziTabeluTretmana(tabelaTretmana);
							break;
						}
					}
				}
			}
		});
		btnDodajUslugu_1.setFont(new Font("Tahoma", Font.PLAIN, 23));
		btnDodajUslugu_1.setBounds(1228, 89, 171, 35);
		panel_6.add(btnDodajUslugu_1);
		
//--------------------------------------------------------------------------------------------------
				
		JPanel panel_7 = new JPanel();
		tabbedPane_2.addTab("tretmani", null, panel_7, null);
		panel_7.setLayout(null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 10, 1208, 439);
		panel_7.add(scrollPane_2);
		
		tabelaTretmana.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		modelZaTretmane = new DefaultTableModel(
				new Object [][] {},
				new String [] {
						"naziv usluge", "naziv tretmana", "vreme trajanja", "cena"
				});		
		tabelaTretmana.setModel(modelZaTretmane);
		
		for (TipKozmetickogTretmana tretman: tkt.values()) {
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
			String vremeTrajanja = tretman.getVremeTrajanja().format(formatter);
			String[] red = {
				tretman.getNazivUsluge(),
				tretman.getNazivTretmana(),
				vremeTrajanja,
				Float.toString(tretman.getCena())};
			((DefaultTableModel) tabelaTretmana.getModel()).addRow(red);
		}		
		JTableHeader header2 = tabelaTretmana.getTableHeader();
        header2.setFont(headerFont1);
		
		scrollPane_2.setViewportView(tabelaTretmana);
		
        tabelaTretmana.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JButton btnD = new JButton("DODAJ");
        btnD.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		DodajTretmanMenadzer frame = new DodajTretmanMenadzer(tabelaTretmana);
        		frame.setVisible(true);
        	}
        });
        btnD.setFont(new Font("Tahoma", Font.PLAIN, 23));
        btnD.setBounds(1228, 38, 171, 35);
        panel_7.add(btnD);
        
        JButton btnIzmeni = new JButton("IZMENI");
        btnIzmeni.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (tabelaTretmana.getSelectedRow() != -1) {
        			int red = tabelaTretmana.getSelectedRow();
        			String nazivUsluge = (String) tabelaTretmana.getValueAt(red, 0);
        			String nazivTretmana = (String) tabelaTretmana.getValueAt(red, 1);
        			String vreme = (String) tabelaTretmana.getValueAt(red, 2);
        			LocalTime vremeTrajanja = LocalTime.parse(vreme, DateTimeFormatter.ofPattern("HH:mm"));
        			float cena = Float.parseFloat((String) tabelaTretmana.getValueAt(red, 3));
        			
        			int idTretmana;
        			
        			for (Map.Entry<Integer, TipKozmetickogTretmana> entry : tkt.entrySet()) {
        				if (nazivUsluge.equals(entry.getValue().getNazivUsluge()) && nazivTretmana.equals(entry.getValue().getNazivTretmana())
        						&& vremeTrajanja.equals(entry.getValue().getVremeTrajanja()) && cena - entry.getValue().getCena() == 0.0) {
        					idTretmana=entry.getKey();
                    		IzmeniTretmanMenadzer frame = new IzmeniTretmanMenadzer(tabelaTretmana, idTretmana);
                    		frame.setVisible(true);
        				}
        			}
        		}
        	}
        });
        btnIzmeni.setFont(new Font("Tahoma", Font.PLAIN, 23));
        btnIzmeni.setBounds(1228, 83, 171, 35);
        panel_7.add(btnIzmeni);
		
		JButton btnDodajUslugu_2 = new JButton("OBRIŠI");
		btnDodajUslugu_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tabelaTretmana.getSelectedRow() != -1) {
					String tretman = (String) tabelaTretmana.getValueAt(tabelaTretmana.getSelectedRow(), 1);
					for (Map.Entry<Integer, TipKozmetickogTretmana> t : tkt.entrySet()) {
						if (t.getValue().getNazivTretmana().equals(tretman)) {
							TipKozmetickogTretmanaCRUD tretmanCRUD = new TipKozmetickogTretmanaCRUD(tretmaniFile);
							tretmanCRUD.remove1(t.getKey());
							
							osvezi.osveziTabeluTretmana(tabelaTretmana);
							break;
						}
					}
				}
			}
		});
		btnDodajUslugu_2.setFont(new Font("Tahoma", Font.PLAIN, 23));
		btnDodajUslugu_2.setBounds(1228, 128, 171, 35);
		panel_7.add(btnDodajUslugu_2);
		
//-------------------------------------------------------------------------------------------------------------------------------
		
		JPanel panel_8 = new JPanel();
		tabbedPane.addTab("zakazani tretmani", null, panel_8, null);
		panel_8.setLayout(null);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(10, 10, 1414, 493);
		panel_8.add(scrollPane_3);
		
		tabelaZakazanih.setFont(new Font ("Tahoma", Font.PLAIN, 15));

		modelZaZakazane = new DefaultTableModel(
				new Object [][] {},
				new String[] {
					       "klijent", "kozmeti\u010Dar", "usluga", "tretman", "stanje", "datum", "vreme", "trajanje tretmana", "cena"
				});
		
		tabelaZakazanih.setModel(modelZaZakazane);
		
		for (ZakazanKozmetickiTretman tretman: zkt.values()) {
			String korImeKoz = tretman.getKorisnickoImeKozmeticara();
			String kozmeticar = "";
			for (Kozmeticar k: listaKozmeticara) {
				if (k.getKorisnickoIme().equals(korImeKoz)) {
					kozmeticar = k.getIme() + " " + k.getPrezime();
					break;
				}
			}
			String korImeKli = tretman.getKorisnickoImeKlijenta();
			String klijent = "";
			for (Klijent k : listaKlijenata) {
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
			((DefaultTableModel) tabelaZakazanih.getModel()).addRow(red);
		}		
		JTableHeader headerZakazanih = tabelaZakazanih.getTableHeader();
		headerZakazanih.setFont(newHeaderFont1);
		
		scrollPane_3.setViewportView(tabelaZakazanih);
		tabelaZakazanih.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
//-----------------------------------------------------------------------------------------------------------
		
		panel_9.setBackground(new Color(231, 247, 254));
		tabbedPane.addTab("kozmetički salon", null, panel_9, null);
		panel_9.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("KOZMETIČKI SALON");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblNewLabel_3.setBounds(110, 10, 221, 48);
		panel_9.add(lblNewLabel_3);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 56, 467, 2);
		panel_9.add(separator_2);
		
		JLabel lblNewLabel_4 = new JLabel("Naziv salona:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_4.setBounds(35, 68, 125, 31);
		panel_9.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Početak radnog vremena: ");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_5.setBounds(35, 110, 200, 36);
		panel_9.add(lblNewLabel_5);
		
		JLabel lblNewLabel_5_1 = new JLabel("Kraj radnog vremena: ");
		lblNewLabel_5_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_5_1.setBounds(35, 156, 200, 36);
		panel_9.add(lblNewLabel_5_1);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField.setBounds(241, 76, 200, 23);
		panel_9.add(textField);
		textField.setColumns(10);
		textField.setText(ks.get(0).getNaziv());
		textField.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				updateNazivSalona();
			}
			
			private void updateNazivSalona() {
				KozmetickiSalonCRUD salonCRUD = new KozmetickiSalonCRUD(salonFile);
				salonCRUD.edit(textField.getText(), ks.get(0).getPocetakRadnogVremena(), ks.get(0).getKrajRadnogVremena(), ks.get(0).getIznosLojalitiKartice(), ks.get(0).getBrojTretmanaZaBonus(),
						ks.get(0).getOstvarenPrihodZaBonus(), ks.get(0).getDatumPocetkaRacunanjaPrihodaZaBonus(), ks.get(0).getDatumKrajaRacunanjaPrihodaZaBonus());
				OsvezavanjeTabelaMenadzer osvezi = new OsvezavanjeTabelaMenadzer();
				osvezi.osveziTabeluKlijenata(tabelaKlijenata);
				osvezi.osveziTabeluKozmeticara(tabelaKozmeticara);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				updateNazivSalona();				
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				updateNazivSalona();				
			}
		});
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_1.setColumns(10);
		textField_1.setBounds(241, 117, 200, 23);
		panel_9.add(textField_1);
		LocalTime pocRadVremena = ks.get(0).getPocetakRadnogVremena();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		String pocVreme = pocRadVremena.format(formatter);
		textField_1.setText(pocVreme);
		textField_1.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				updatePocRadVreme();
			}
			
			private void updatePocRadVreme() {
				String text = textField_1.getText();
		        Pattern pattern = Pattern.compile("^([01]?[0-9]|2[0-3]):[0-5][0-9]$");
		        Matcher matcher = pattern.matcher(text);
				if (matcher.matches()) {
					try {
						LocalTime vreme = LocalTime.parse(text);
						int razlika = vreme.compareTo(ks.get(0).getKrajRadnogVremena());
						if (razlika < 0) {
							KozmetickiSalonCRUD salonCRUD = new KozmetickiSalonCRUD(salonFile);
							salonCRUD.edit(ks.get(0).getNaziv(), vreme, ks.get(0).getKrajRadnogVremena(), ks.get(0).getIznosLojalitiKartice(), ks.get(0).getBrojTretmanaZaBonus(),
									ks.get(0).getOstvarenPrihodZaBonus(), ks.get(0).getDatumPocetkaRacunanjaPrihodaZaBonus(), ks.get(0).getDatumKrajaRacunanjaPrihodaZaBonus());
							OsvezavanjeTabelaMenadzer osvezi = new OsvezavanjeTabelaMenadzer();
							osvezi.osveziTabeluKlijenata(tabelaKlijenata);
							osvezi.osveziTabeluKozmeticara(tabelaKozmeticara);
						}
					} finally {						
					}
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				updatePocRadVreme();				
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				updatePocRadVreme();				
			}
		});
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_2.setColumns(10);
		textField_2.setBounds(241, 160, 200, 23);
		panel_9.add(textField_2);
		textField_2.setText(ks.get(0).getKrajRadnogVremena().format(formatter));
		textField_2.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				updateKrajRadVremena();
			}
			
			private void updateKrajRadVremena() {
				String text = textField_2.getText();
		        Pattern pattern = Pattern.compile("^([01]?[0-9]|2[0-3]):[0-5][0-9]$");
		        Matcher matcher = pattern.matcher(text);
				if (matcher.matches()) {
					try {
						LocalTime vreme = LocalTime.parse(text);
						int razlika = vreme.compareTo(ks.get(0).getPocetakRadnogVremena());
						if (razlika > 0) {
							KozmetickiSalonCRUD salonCRUD = new KozmetickiSalonCRUD(salonFile);
							salonCRUD.edit(ks.get(0).getNaziv(), ks.get(0).getPocetakRadnogVremena(), vreme, ks.get(0).getIznosLojalitiKartice(), ks.get(0).getBrojTretmanaZaBonus(),
									ks.get(0).getOstvarenPrihodZaBonus(), ks.get(0).getDatumPocetkaRacunanjaPrihodaZaBonus(), ks.get(0).getDatumKrajaRacunanjaPrihodaZaBonus());
							OsvezavanjeTabelaMenadzer osvezi = new OsvezavanjeTabelaMenadzer();
							osvezi.osveziTabeluKlijenata(tabelaKlijenata);
							osvezi.osveziTabeluKozmeticara(tabelaKozmeticara);
						}						
					} finally {		
					}
				}				
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				updateKrajRadVremena();				
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				updateKrajRadVremena();
			}
		});
		
		JSeparator separator_2_1 = new JSeparator();
		separator_2_1.setBounds(10, 202, 467, 2);
		panel_9.add(separator_2_1);
		
		JLabel lblNewLabel_5_2 = new JLabel("Iznos za karticu lojalnosti:");
		lblNewLabel_5_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_5_2.setBounds(35, 209, 200, 36);
		panel_9.add(lblNewLabel_5_2);
		
		JLabel lblNewLabel_5_2_1 = new JLabel("Broj tretmana za bonus:");
		lblNewLabel_5_2_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_5_2_1.setBounds(35, 267, 200, 36);
		panel_9.add(lblNewLabel_5_2_1);
		
		textField_3 = new JTextField();
		textField_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_3.setColumns(10);
		textField_3.setBounds(241, 216, 200, 23);
		panel_9.add(textField_3);
		textField_3.setText(ks.get(0).getIznosLojalitiKartice());
		
		textField_4 = new JTextField();
		textField_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_4.setColumns(10);
		textField_4.setBounds(241, 278, 200, 23);
		panel_9.add(textField_4);
		textField_4.setText(ks.get(0).getBrojTretmanaZaBonus());
		
		JSeparator separator_2_1_1 = new JSeparator();
		separator_2_1_1.setBounds(10, 255, 467, 2);
		panel_9.add(separator_2_1_1);
		
		JLabel lblNewLabel_5_2_1_1 = new JLabel("Ostvaren prihod za bonus:");
		lblNewLabel_5_2_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_5_2_1_1.setBounds(35, 313, 200, 36);
		panel_9.add(lblNewLabel_5_2_1_1);
		
		textField_5 = new JTextField();
		textField_5.setText((String) null);
		textField_5.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_5.setColumns(10);
		textField_5.setBounds(241, 320, 200, 23);
		panel_9.add(textField_5);
		textField_5.setText(ks.get(0).getOstvarenPrihodZaBonus());
		textField_5.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				updateOstvarenPrihodZaBonus();
			}

			private void updateOstvarenPrihodZaBonus() {
				String regex = "^(\\d+|-)$";		        
		        Pattern pattern = Pattern.compile(regex);
		        String broj;
				if (pattern.matcher(textField_5.getText()).matches()) {
					broj = textField_5.getText();					
					if (broj.equals("-")) {
						chooser.setEnabled(false);
						chooser2.setEnabled(false);
					} else {
						chooser.setEnabled(true);
						chooser2.setEnabled(true);
					}
				}				
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				updateOstvarenPrihodZaBonus();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				updateOstvarenPrihodZaBonus();
			}
		});
		
		JLabel lblNewLabel_5_2_1_1_1 = new JLabel("Ostvaren prihod za bonus od:");
		lblNewLabel_5_2_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_5_2_1_1_1.setBounds(35, 353, 221, 36);
		panel_9.add(lblNewLabel_5_2_1_1_1);
		if (ks.get(0).getOstvarenPrihodZaBonus().equals("-")) {
			chooser.setEnabled(false);
			chooser2.setEnabled(false);
		}
		JLabel lblNewLabel_5_2_1_1_1_1 = new JLabel("Ostvaren prihod za bonus do:");
		lblNewLabel_5_2_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_5_2_1_1_1_1.setBounds(35, 391, 221, 36);
		panel_9.add(lblNewLabel_5_2_1_1_1_1);
		
		if (!lblNewLabel_5_2_1_1_1.getText().equals("-")) {
	        Date date = (Date) Date.from(ks.get(0).getDatumPocetkaRacunanjaPrihodaZaBonus().atStartOfDay(ZoneId.systemDefault()).toInstant());
			chooser.setDate(date);
		}
		if (!lblNewLabel_5_2_1_1_1.getText().equals("-")) {
	        Date date = (Date) Date.from(ks.get(0).getDatumKrajaRacunanjaPrihodaZaBonus().atStartOfDay(ZoneId.systemDefault()).toInstant());
			chooser2.setDate(date);
		}
		chooser.setBounds(251, 361, 190, 23);
		chooser2.setBounds(251, 399, 190, 23);
		panel_9.add(chooser);
		panel_9.add(chooser2);
		
		JLabel lblNewLabel_6 = new JLabel("*da se iznos za lojaliti karticu u ne računa stavite -");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_6.setBounds(10, 437, 299, 31);
		panel_9.add(lblNewLabel_6);
		
		JLabel lblNewLabel_6_1 = new JLabel("*da se bonus po broju tretmana/po ostvarenom prihodu ne računa stavite -");
		lblNewLabel_6_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_6_1.setBounds(10, 461, 406, 31);
		panel_9.add(lblNewLabel_6_1);
		
		KozmetickiSalonCRUD salonCRUD = new KozmetickiSalonCRUD(salonFile);
		float bilans = salonCRUD.bilansNovcaSalona(zkt);
		JLabel lblNewLabel_4_1 = new JLabel("Ukupni prihodi: " + bilans);
		lblNewLabel_4_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_4_1.setBounds(529, 68, 200, 31);
		panel_9.add(lblNewLabel_4_1);
		
		JButton btnNewButton_1 = new JButton("izmeni karticu i bonus");
		btnNewButton_1.setBounds(271, 432, 170, 21);
		panel_9.add(btnNewButton_1);
		
		JLabel lblNewLabel_4_1_1 = new JLabel("IZVEŠTAJI I DIJAGRAMI: ");
		lblNewLabel_4_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_4_1_1.setBounds(786, 72, 190, 31);
		panel_9.add(lblNewLabel_4_1_1);
		
		choice = new Choice();
		choice.add(" ");
		choice.add("izveštaj o kozmetičarima i prihodima");
		choice.add("izveštaj stanja kozmetičkih tretmana");
		choice.add("izveštaj o kozmetičkim tretmanima");
		choice.add("izveštaj o lojaliti karticama");
		
		choice.add("prihod salona");
		choice.add("angažovanje kozmetičara");
		choice.add("zastupljenost usluga po stanju");
		choice.addItemListener(this);
		
		panel_10 = new JPanel();
		panel_10.setBounds(498, 118, 926, 374);
		panel_9.add(panel_10);
		panel_10.setLayout(null);
		
		scrollPane_4.setBounds(10, 10, 906, 354);
		panel_10.add(scrollPane_4);

		choice.setFont(new Font("Tahoma", Font.PLAIN, 14));
		choice.setBounds(1010, 77, 353, 22);
		panel_9.add(choice);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				KozmetickiSalon salon = new KozmetickiSalonCRUD(salonFile).getSaloni().get(0);
				KozmetickiSalonCRUD salonCRUD = new KozmetickiSalonCRUD(salonFile);
				Date kraj = chooser2.getDate();
	        	Date pocetak = chooser.getDate();
	        	LocalDate localDatePoc = null;
	        	LocalDate localDateKraj = null;
	    		if (kraj != null && pocetak != null) {
	    	        localDatePoc = pocetak.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();		
	    	        localDateKraj = kraj.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();	
	    	        if (localDatePoc.isBefore(localDateKraj)) {
	    	        	salonCRUD.edit(salon.getNaziv(), salon.getPocetakRadnogVremena(), salon.getKrajRadnogVremena(), textField_3.getText(),
	    	        			textField_4.getText(), textField_5.getText(), localDatePoc, localDateKraj);
	    	        }
	    		} else {
	    			salonCRUD.edit(salon.getNaziv(), salon.getPocetakRadnogVremena(), salon.getKrajRadnogVremena(), textField_3.getText(),
    	        			textField_4.getText(), textField_5.getText(), salon.getDatumPocetkaRacunanjaPrihodaZaBonus(), salon.getDatumKrajaRacunanjaPrihodaZaBonus());
	    		}
				KlijentiCRUD klijenti = new KlijentiCRUD(klijentiFile);
				
				for (Klijent k : klijenti.getKlijenti()) {
					if (!k.getLojalitiKartica()) {
						if (klijenti.lojalitiKartica(k.getKorisnickoIme(), zkt)) {
							klijenti.edit(k.getKorisnickoIme(), k.getLozinka(), k.getIme(), k.getPrezime(), k.getPol(), k.getTelefon(),
									k.getAdresa(), klijenti.lojalitiKartica(k.getKorisnickoIme(), zkt));
						} 
					}
				}
				KozmeticariCRUD kozmeticari = new KozmeticariCRUD(kozmeticariFile);
				for (Kozmeticar k : kozmeticari.getKozmeticari()) {
					String tretmani = "";
					for (KozmetickaUslugaTretman t : k.getTretmani()) {
						tretmani += t.getNazivUsluge() + ";";
					}
					if (!tretmani.equals("")) {
						tretmani = tretmani.substring(0, tretmani.length() - 1);
					}
					kozmeticari.edit(k.getKorisnickoIme(), k.getLozinka(), k.getIme(), k.getPrezime(), k.getPol(),
							k.getTelefon(), k.getAdresa(), k.getStrucnaSprema(),k.getStaz(), k.getOsnovnaPlata(), tretmani);
				}
				OsvezavanjeTabelaMenadzer osvezi = new OsvezavanjeTabelaMenadzer();
				osvezi.osveziTabeluKlijenata(tabelaKlijenata);
				osvezi.osveziTabeluKozmeticara(tabelaKozmeticara);
			}
		});
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == choice) {
			if (choice.getSelectedItem().equals("prihod salona")) {
			    ExampleChart<XYChart> exampleChart = new AreaChart();
			    XYChart chart = exampleChart.getChart();
			    chart.getStyler().setChartBackgroundColor(new Color(174,227,253));
			    Thread t = new Thread(() -> {
		            JFrame frame = new SwingWrapper<XYChart>(chart).displayChart();
		            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		            frame.setIconImage(new ImageIcon(menadzeriMeni.class.getResource("/gui/BeautySalon.png")).getImage());
		            frame.setTitle("prihod salona");
		        });				    
			    t.start();
			} else if (choice.getSelectedItem().equals("angažovanje kozmetičara")) {
			    ExampleChart<PieChart> exampleChart = new AngazovanjeKozmeticara();
			    PieChart chart = (exampleChart).getChart();
			    chart.getStyler().setChartBackgroundColor(new Color(174,227,253));
			    Thread t = new Thread(() -> {
		            JFrame frame = new SwingWrapper<PieChart>(chart).displayChart();
		            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		            frame.setIconImage(new ImageIcon(menadzeriMeni.class.getResource("/gui/BeautySalon.png")).getImage());
		            frame.setTitle("angazovanje kozmeticara");
		        });					
			    t.start();
			} else if (choice.getSelectedItem().equals("zastupljenost usluga po stanju")) {
		        ExampleChart<PieChart> exampleChart = new StanjePojedinacnihTretmana();
		        PieChart chart = exampleChart.getChart();
			    chart.getStyler().setChartBackgroundColor(new Color(174,227,253));
			    Thread t = new Thread(() -> {
		            JFrame frame = new SwingWrapper<PieChart>(chart).displayChart();
		            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		            frame.setIconImage(new ImageIcon(menadzeriMeni.class.getResource("/gui/BeautySalon.png")).getImage());
		            frame.setTitle("zastupljenost usluga po stanju");
		        });	
		        t.start();
			} else if (choice.getSelectedItem().equals("izveštaj o lojaliti karticama")) {
				tabelaKartica.setFont(new Font("Tahoma", Font.PLAIN, 12));
				modelZaKartice = new DefaultTableModel(
						new Object [][] {},
						new String [] {
								"broj", "klijent"
						});
				tabelaKartica.setModel(modelZaKartice);
				
				Izvestaji izvestaj = new Izvestaji();
				List<Klijent> klijentiSaKaritcom = izvestaj.klijentiKojiImajuKarticu();
				int brojac = 1;
				for (Klijent k : klijentiSaKaritcom) {
					String[] red = {
							Integer.toString(brojac),
							k.getIme() + " " + k.getPrezime()};
					((DefaultTableModel) tabelaKartica.getModel()).addRow(red);
				}
				JTableHeader headerAngazovanja = tabelaKartica.getTableHeader();
				headerAngazovanja.setFont(new Font("Tahoma", Font.BOLD, 15));
				scrollPane_4.setViewportView(tabelaKartica);
			} else if (choice.getSelectedItem().equals("izveštaj o kozmetičkim tretmanima")) {
				JPanel panel = new JPanel();
		        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		        panel.setBackground(new Color(174, 227, 253));
				JDateChooser startDateChooser = new JDateChooser();
				JDateChooser endDateChooser = new JDateChooser();
				panel.add(new JLabel("Izaberite početni datum: "));
				panel.add(startDateChooser);
				panel.add(new JLabel("Izaberite krajnji datum: "));
				panel.add(endDateChooser);
				UIManager.put("OptionPane.background", new Color(174, 227, 253));
		        UIManager.put("Panel.background", new Color(174, 227, 253)); 
		        UIManager.put("Button.background", new Color(174, 227, 253));
		        UIManager.put("Button.background", new Color(228, 228, 232));
		        UIManager.put("Button.foreground", Color.BLACK);
		        Icon customIcon = null;
				int result = JOptionPane.showConfirmDialog(null, panel, "Izaberite datume",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, customIcon);
				
				if (result == JOptionPane.OK_OPTION) {
				    Date startDate = startDateChooser.getDate();
				    Date endDate = endDateChooser.getDate();

				    if (startDate != null && endDate != null) {	
					    LocalDate start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					    LocalDate end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						tabelaIzvestajUsluga.setFont(new Font("Tahoma", Font.PLAIN, 12));
						modelZaIzvestajUsluga = new DefaultTableModel(
								new Object [][] {},
								new String [] {
										"usluga", "tretman", "vreme trajanja", "redovna cena", "broj zakazanih tretmana", "prihod"
								});
						tabelaIzvestajUsluga.setModel(modelZaIzvestajUsluga);
						
						Izvestaji izvestaji = new Izvestaji();
						String tretmaniFile = ".//fajlovi/tretmani.csv";
						HashMap<Integer, TipKozmetickogTretmana> tkt = new TipKozmetickogTretmanaCRUD(tretmaniFile).getRecnikTretmana();

						for (TipKozmetickogTretmana t : tkt.values()) {
							int broj = izvestaji.brojZakazanihTretmanaZaOdredjenuTretman(t.getNazivTretmana(), start, end);
							float prihod = izvestaji.ostvarenPrihodZaOdredjenTretman(t.getNazivTretmana(), start, end);
					        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
							String vreme =  t.getVremeTrajanja().format(formatter);
							String[] red = {
									t.getNazivUsluge(),
									t.getNazivTretmana(),
									vreme,
									Float.toString(t.getCena()),
									Integer.toString(broj),
									Float.toString(prihod)};
							((DefaultTableModel) tabelaIzvestajUsluga.getModel()).addRow(red);
						}		
						JTableHeader headerAngazovanja = tabelaIzvestajUsluga.getTableHeader();
						headerAngazovanja.setFont(new Font("Tahoma", Font.BOLD, 15));
						scrollPane_4.setViewportView(tabelaIzvestajUsluga);
				    }
				}
			} else if (choice.getSelectedItem().equals("izveštaj stanja kozmetičkih tretmana")) {
				JPanel panel = new JPanel();
		        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		        panel.setBackground(new Color(174, 227, 253));
				JDateChooser startDateChooser = new JDateChooser();
				JDateChooser endDateChooser = new JDateChooser();
				panel.add(new JLabel("Izaberite početni datum: "));
				panel.add(startDateChooser);
				panel.add(new JLabel("Izaberite krajnji datum: "));
				panel.add(endDateChooser);
				UIManager.put("OptionPane.background", new Color(174, 227, 253));
		        UIManager.put("Panel.background", new Color(174, 227, 253)); 
		        UIManager.put("Button.background", new Color(174, 227, 253));
		        UIManager.put("Button.background", new Color(228, 228, 232));
		        UIManager.put("Button.foreground", Color.BLACK);
		        Icon customIcon = null;
				int result = JOptionPane.showConfirmDialog(null, panel, "Izaberite datume",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, customIcon);
				
				if (result == JOptionPane.OK_OPTION) {
				    Date startDate = startDateChooser.getDate();
				    Date endDate = endDateChooser.getDate();

				    if (startDate != null && endDate != null) {	
					    LocalDate start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					    LocalDate end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						tabelaStanja.setFont(new Font("Tahoma", Font.PLAIN, 12));
						modelZaStanje = new DefaultTableModel(
								new Object [][] {},
								new String [] {
										"stanje", "broj tretmana"
								});
						tabelaStanja.setModel(modelZaStanje);
						
						Izvestaji izvestaji = new Izvestaji();

						int brojIzvrsenih = izvestaji.brojIzvrsenihTretmana(start, end);
						int brojZakazanih = izvestaji.brojZakazanihTretmana(start, end);
						int brojKlijentOtkazao = izvestaji.brojKlijentOtkazaoTretmana(start, end);
						int brojSalonOtkazao = izvestaji.brojSalonOtkazaoTretmana(start, end);
						int brojNijeSePojavio = izvestaji.brojNijeSePojavioTretmana(start, end);
						
						String[] red1 = {"Izvršeni tretmani", Integer.toString(brojIzvrsenih)};
						String[] red2 = {"Zakazani tretmani", Integer.toString(brojZakazanih)};
						String[] red3 = {"Klijent otkazao tretmani", Integer.toString(brojKlijentOtkazao)};
						String[] red4 = {"Salon otkazao tretmani", Integer.toString(brojSalonOtkazao)};
						String[] red5 = {"Nije se pojavio tretmani", Integer.toString(brojNijeSePojavio)};

						((DefaultTableModel) tabelaStanja.getModel()).addRow(red1);
						((DefaultTableModel) tabelaStanja.getModel()).addRow(red2);
						((DefaultTableModel) tabelaStanja.getModel()).addRow(red3);
						((DefaultTableModel) tabelaStanja.getModel()).addRow(red4);
						((DefaultTableModel) tabelaStanja.getModel()).addRow(red5);
						
						JTableHeader headerAngazovanja = tabelaStanja.getTableHeader();
						headerAngazovanja.setFont(new Font("Tahoma", Font.BOLD, 15));
						scrollPane_4.setViewportView(tabelaStanja);
				    }
				}				
			} else if (choice.getSelectedItem().equals("izveštaj o kozmetičarima i prihodima")) {
				JPanel panel = new JPanel();
		        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		        panel.setBackground(new Color(174, 227, 253));
				JDateChooser startDateChooser = new JDateChooser();
				JDateChooser endDateChooser = new JDateChooser();
				panel.add(new JLabel("Izaberite početni datum: "));
				panel.add(startDateChooser);
				panel.add(new JLabel("Izaberite krajnji datum: "));
				panel.add(endDateChooser);
				UIManager.put("OptionPane.background", new Color(174, 227, 253));
		        UIManager.put("Panel.background", new Color(174, 227, 253)); 
		        UIManager.put("Button.background", new Color(174, 227, 253));
		        UIManager.put("Button.background", new Color(228, 228, 232));
		        UIManager.put("Button.foreground", Color.BLACK);
		        Icon customIcon = null;
				int result = JOptionPane.showConfirmDialog(null, panel, "Izaberite datume",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, customIcon);
				
				if (result == JOptionPane.OK_OPTION) {
				    Date startDate = startDateChooser.getDate();
				    Date endDate = endDateChooser.getDate();

				    if (startDate != null && endDate != null) {	
					    LocalDate start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
					    LocalDate end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						tabelaAngazovanja.setFont(new Font("Tahoma", Font.PLAIN, 12));
						modelZaAngKoz = new DefaultTableModel(
								new Object [][] {},
								new String [] {
										"kozmetičar", "broj izvršenih tretmana", "prihod"
								});
						tabelaAngazovanja.setModel(modelZaAngKoz);
						String kozmeticariFile = ".//fajlovi/kozmeticari.csv";
		
						List<Kozmeticar> kozmeticari = new KozmeticariCRUD(kozmeticariFile).getKozmeticari();
						for (Kozmeticar k : kozmeticari) {
							Izvestaji izvestaji = new Izvestaji();
							int brojTretmana = izvestaji.brojIzvrsenihTretmana(k.getKorisnickoIme(), start, end);
							float prihod = izvestaji.prihodKozmeticara(k.getKorisnickoIme(), start, end);
							String[] red = {
								k.getIme() + " " + k.getPrezime(),
								Integer.toString(brojTretmana),
								Float.toString(prihod)};
							((DefaultTableModel) tabelaAngazovanja.getModel()).addRow(red);
						}						
						JTableHeader headerAngazovanja = tabelaAngazovanja.getTableHeader();
						headerAngazovanja.setFont(new Font("Tahoma", Font.BOLD, 15));
						scrollPane_4.setViewportView(tabelaAngazovanja);
				    }
				}
			}
		}
	}
}
