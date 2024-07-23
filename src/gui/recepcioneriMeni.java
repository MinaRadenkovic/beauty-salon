package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import CRUD.KlijentiCRUD;
import CRUD.KozmeticariCRUD;
import CRUD.KozmetickaUslugaCRUD;
import CRUD.ZakazanKozmetickiTretmanCRUD;
import korisniciSistema.Klijent;
import korisniciSistema.Kozmeticar;
import korisniciSistema.Recepcioner;
import uslugeTretmani.KozmetickaUslugaTretman;
import uslugeTretmani.ZakazanKozmetickiTretman;
import java.awt.Choice;

public class recepcioneriMeni extends JFrame implements ItemListener {

	private JPanel contentPane;
	private JPanel contentPane_1;
	private JTable table_1;
	private DefaultTableModel modelTabele;
	private Choice choice = new Choice();
	private Choice choice_1 = new Choice();

	public recepcioneriMeni(Recepcioner recepcioner) {
		setTitle("SV76-2022");
		setIconImage(Toolkit.getDefaultToolkit().getImage(recepcioneriMeni.class.getResource("/gui/BeautySalon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int maxWidth = screenSize.width;
		int maxHeight = screenSize.height;

		// Postavljanje dimenzija komponente
		setBounds(100, 100, maxWidth, maxHeight);        
		setLocation(0, 0);
		
		contentPane_1 = new JPanel();
		contentPane_1.setBackground(new Color(174, 227, 253));
		contentPane_1.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane_1);
		contentPane_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("PREGLED ZAKAZANIH KOZMETIČKIH TRETMANA");
		lblNewLabel.setToolTipText("");
		lblNewLabel.setBounds(457, 21, 624, 31);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		contentPane_1.add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 73, 1515, 2);
		contentPane_1.add(separator);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(41, 78, 200, 200);
		lblNewLabel_1.setIcon(new ImageIcon(recepcioneriMeni.class.getResource("/gui/BeautySalon.png")));
		contentPane_1.add(lblNewLabel_1);
		
		String ime = recepcioner.getIme();
		String prezime = recepcioner.getPrezime();
		JLabel lblNewLabel_2 = new JLabel("Recepcioner: " + ime + " " + prezime);
		lblNewLabel_2.setBounds(283, 96, 605, 40);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 23));
		contentPane_1.add(lblNewLabel_2);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 269, 1515, 2);
		contentPane_1.add(separator_1);

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
		contentPane_1.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(41, 306, 1456, 497);
		contentPane_1.add(scrollPane);
		
		table_1 = new JTable();
		table_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		modelTabele = new DefaultTableModel(
			    new Object[][] {},
			    new String[] {
			       "klijent", "kozmeti\u010Dar", "usluga", "tretman", "stanje", "datum", "vreme", "trajanje tretmana", "cena"
			    });
		table_1.setModel(modelTabele);
		table_1.setEnabled(false);
		
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
			((DefaultTableModel) table_1.getModel()).addRow(red);
		}
		JTableHeader header = table_1.getTableHeader();
        Font headerFont = header.getFont();
        Font newHeaderFont = new Font(headerFont.getName(), Font.BOLD, 20);
        header.setFont(newHeaderFont);
		
		scrollPane.setViewportView(table_1);
		
		JButton btnZakaiTretman = new JButton("AŽURIRAJ STANJE");
		btnZakaiTretman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IzmeniTretmanRecepcioner frame = new IzmeniTretmanRecepcioner(recepcioneriMeni.this, recepcioner);
				frame.setVisible(true);
			}
		});
		btnZakaiTretman.setFont(new Font("Tahoma", Font.PLAIN, 23));
		btnZakaiTretman.setBounds(1204, 161, 252, 35);
		contentPane_1.add(btnZakaiTretman);
		
		JButton btnIzmeniTretman = new JButton("ZAKAŽI TRETMAN");
		btnIzmeniTretman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zakaziTretmanRecepcioner frame = new zakaziTretmanRecepcioner(recepcioneriMeni.this, recepcioner);
				frame.setVisible(true);
			}
		});
		btnIzmeniTretman.setFont(new Font("Tahoma", Font.PLAIN, 23));
		btnIzmeniTretman.setBounds(1204, 224, 252, 35);
		contentPane_1.add(btnIzmeniTretman);
		
		choice.setFont(new Font("Dialog", Font.PLAIN, 18));
		choice.setBounds(558, 161, 200, 29);
		contentPane_1.add(choice);
		choice.add(" ");
		choice.addItemListener(this);
		
		String uslugeFile = ".//fajlovi/usluge.csv";
		List<KozmetickaUslugaTretman> usluge = new KozmetickaUslugaCRUD(uslugeFile).getUsluge();

		for (KozmetickaUslugaTretman usluga : usluge) {
			choice.add(usluga.getNazivUsluge());
		}
		JLabel lblNewLabel_3 = new JLabel("Filtriraj po tipu usluge:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_3.setBounds(283, 164, 220, 33);
		contentPane_1.add(lblNewLabel_3);
		
		JLabel lblNewLabel_3_1 = new JLabel("Filtriraj po ceni tretmana:");
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_3_1.setBounds(283, 220, 252, 33);
		contentPane_1.add(lblNewLabel_3_1);
		
		choice_1.setFont(new Font("Dialog", Font.PLAIN, 18));
		choice_1.setBounds(558, 224, 200, 29);
		contentPane_1.add(choice_1);
		choice_1.add(" ");
		choice_1.addItemListener(this);

		
		float minimalnaCena = Float.MAX_VALUE;
		float maximalnaCena = -1;
		
		for (ZakazanKozmetickiTretman tretman: recnikZakazanih.values()) {
			if (tretman.getCena() > maximalnaCena) {
				maximalnaCena = tretman.getCena();
			}
			if (tretman.getCena() < minimalnaCena) {
				minimalnaCena = tretman.getCena();
			}
		}
		for (int i = (int) minimalnaCena; i <= (int) maximalnaCena; i += 500 ) {
			choice_1.add(String.valueOf(i) + " - " + String.valueOf(i + 500));
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == choice || e.getSource() == choice_1) {
			String zakazaniFile = ".//fajlovi/zakazani.csv";
			ZakazanKozmetickiTretmanCRUD zkt = new ZakazanKozmetickiTretmanCRUD(zakazaniFile);
			HashMap<Integer, ZakazanKozmetickiTretman> recnikZakazanih = zkt.getRecnikZakazanih();

			List <ZakazanKozmetickiTretman> listaTretmana = new ArrayList<>();
			
			for (Map.Entry<Integer, ZakazanKozmetickiTretman> entry: recnikZakazanih.entrySet()) {
				listaTretmana.add(entry.getValue());
			}
			List<ZakazanKozmetickiTretman> lista = new ArrayList<>(listaTretmana);
			
			if (!choice.getSelectedItem().equals(" ")) {
				String nazivUsluge = choice.getSelectedItem();
				for (ZakazanKozmetickiTretman tretman : listaTretmana) {
					if (!tretman.getNazivUsluge().equals(nazivUsluge)) {
						lista.remove(tretman);
					}
				}
			}
			if (!choice_1.getSelectedItem().equals(" ")) {
				String opsegCena = choice_1.getSelectedItem();
				String[] minMaxCena = opsegCena.split(" - ");
				float minCena = Float.parseFloat(minMaxCena[0]);
				float maxCena = Float.parseFloat(minMaxCena[1]);
				for (ZakazanKozmetickiTretman tretman : listaTretmana) {
					if (tretman.getCena() < minCena || tretman.getCena() > maxCena) {
						lista.remove(tretman);
					}
				}
			}
	        ((DefaultTableModel) table_1.getModel()).setRowCount(0);
			String kozmeticariFile = ".//fajlovi/kozmeticari.csv";
			List<Kozmeticar> sviKozmeticari = new KozmeticariCRUD(kozmeticariFile).getKozmeticari();
			String klijentiFile = ".//fajlovi/klijenti.csv";
			List<Klijent> sviKlijenti = new KlijentiCRUD(klijentiFile).getKlijenti();
	        for (ZakazanKozmetickiTretman tretman : lista) {
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
		}
	}
}
