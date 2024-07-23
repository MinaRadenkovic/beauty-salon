package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import CRUD.KlijentiCRUD;
import CRUD.TipKozmetickogTretmanaCRUD;
import CRUD.ZakazanKozmetickiTretmanCRUD;
import korisniciSistema.Klijent;
import korisniciSistema.Kozmeticar;
import uslugeTretmani.KozmetickaUslugaTretman;
import uslugeTretmani.TipKozmetickogTretmana;
import uslugeTretmani.ZakazanKozmetickiTretman;

import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class kozmeticariMeni extends JFrame {

	private JPanel contentPane;
	private DefaultTableModel modelTabele;
	private JTable table;
	private JTable table_1;

	public kozmeticariMeni(Kozmeticar kozmeticar) {
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
		
		JLabel lblNewLabel = new JLabel("PREGLED KOZMETIČKIH USLUGA I TRETMANA");
		lblNewLabel.setToolTipText("");
		lblNewLabel.setBounds(467, 21, 634, 31);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		contentPane.add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 73, 1515, 2);
		contentPane.add(separator);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(35, 60, 200, 200);
		lblNewLabel_1.setIcon(new ImageIcon(kozmeticariMeni.class.getResource("/gui/BeautySalon.png")));
		contentPane.add(lblNewLabel_1);
		
		String ime = kozmeticar.getIme();
		String prezime = kozmeticar.getPrezime();
		JLabel lblNewLabel_2 = new JLabel("Kozmetičar: " + ime + " " + prezime);
		lblNewLabel_2.setBounds(283, 96, 605, 40);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 23));
		contentPane.add(lblNewLabel_2);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 247, 1515, 2);
		contentPane.add(separator_1);
		
		JButton btnNewButton = new JButton("ODJAVI ME");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logIN frame = new logIN();
				dispose();			
				frame.setVisible(true);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 23));
		btnNewButton.setBounds(1285, 101, 171, 35);
		contentPane.add(btnNewButton);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(45, 270, 1439, 547);
		contentPane.add(tabbedPane);
		
		Font tabFont = new Font("Tahoma", Font.PLAIN, 16); 

		JPanel panel = new JPanel();
		tabbedPane.addTab("usluge i tretmani", null, panel, null);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 1414, 493);
		panel.add(scrollPane);
		
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));

		modelTabele = new DefaultTableModel(
			new Object[][] {},
			new String[] {
					"vrsta usluge", "vrsta tretmana", "trajanje tretmana", "cena"

			}
		);
		table.setModel(modelTabele);
		table.setEnabled(false);
		
		String tretmaniFile = ".//fajlovi/tretmani.csv";
		HashMap<Integer, TipKozmetickogTretmana> tkt = new TipKozmetickogTretmanaCRUD(tretmaniFile).getRecnikTretmana();

		List <KozmetickaUslugaTretman> usluge = kozmeticar.getTretmani();
		List <TipKozmetickogTretmana> tretmaniKojeKozObavlja = new ArrayList<>();
		
		for (Map.Entry<Integer, TipKozmetickogTretmana> entry : tkt.entrySet()) {
			for (KozmetickaUslugaTretman usluga : usluge) {
				if (usluga.getNazivUsluge().equals(entry.getValue().getNazivUsluge())) {
					tretmaniKojeKozObavlja.add(entry.getValue());
					break;
				}
			}
		}			
		for (TipKozmetickogTretmana tretman : tretmaniKojeKozObavlja) {
			Object[] red = {
				tretman.getNazivUsluge(),
				tretman.getNazivTretmana(),
				tretman.getVremeTrajanja(),
				tretman.getCena()};
			((DefaultTableModel) table.getModel()).addRow(red);
		}
		JTableHeader header = table.getTableHeader();
        Font headerFont = header.getFont();
        Font newHeaderFont = new Font(headerFont.getName(), Font.BOLD, 20);
        header.setFont(newHeaderFont);
		
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("zakazani kozmetički tretmani", null, panel_1, null);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 56, 1414, 447);
		panel_1.add(scrollPane_1);
		
		table_1 = new JTable();
		table_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		modelTabele = new DefaultTableModel(
			    new Object[][] {},
			    new String[] {
			        "kozmeti\u010Dar", "usluga", "tretman", "stanje", "datum", "vreme", "trajanje tretmana", "cena"
			    }
			);
			table_1.setModel(modelTabele);
			table_1.setEnabled(false);
		
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
			((DefaultTableModel) table_1.getModel()).addRow(red);
		}
		scrollPane_1.setViewportView(table_1);
		
		JButton btnNewButton_1 = new JButton("IZVRŠI TRETMAN");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IzmeniTretmanKozmeticar frame = new IzmeniTretmanKozmeticar(kozmeticariMeni.this, kozmeticar);
				frame.setVisible(true);
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 23));
		btnNewButton_1.setBounds(1181, 10, 243, 35);
		panel_1.add(btnNewButton_1);
		tabbedPane.setFont(tabFont);
	}
}
