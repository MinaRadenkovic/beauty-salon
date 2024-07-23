package gui;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

import korisniciSistema.Klijent;
import korisniciSistema.Kozmeticar;
import uslugeTretmani.ZakazanKozmetickiTretman;
import CRUD.*;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.JScrollPane;

public class klijentiMeni extends JFrame {

	private JPanel contentPane;
	private JTable table_1;
    private DefaultTableModel modelTabele;

	public klijentiMeni(Klijent klijent) {
		String kozmeticariFile = ".//fajlovi/kozmeticari.csv";
		List<Kozmeticar> sviKozmeticari = new KozmeticariCRUD(kozmeticariFile).getKozmeticari();

		setTitle("SV76-2022");
		setIconImage(Toolkit.getDefaultToolkit().getImage(klijentiMeni.class.getResource("/gui/BeautySalon.png")));
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
		
		JLabel lblNewLabel = new JLabel("PREGLED ZAKAZANIH KOZMETIČKIH TRETMANA");
		lblNewLabel.setToolTipText("");
		lblNewLabel.setBounds(457, 21, 624, 31);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		contentPane.add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 73, 1515, 2);
		contentPane.add(separator);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(41, 78, 200, 200);
		lblNewLabel_1.setIcon(new ImageIcon(klijentiMeni.class.getResource("/gui/BeautySalon.png")));
		contentPane.add(lblNewLabel_1);
		
		String ime = klijent.getIme();
		String prezime = klijent.getPrezime();
		JLabel lblNewLabel_2 = new JLabel("Klijent: " + ime + " " + prezime);
		lblNewLabel_2.setBounds(283, 96, 605, 40);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 23));
		contentPane.add(lblNewLabel_2);
		
		String klijentiFile = ".//fajlovi/klijenti.csv";
		KlijentiCRUD noviKlijent = new KlijentiCRUD(klijentiFile);
		
		String zakazaniFile = ".//fajlovi/zakazani.csv";
		ZakazanKozmetickiTretmanCRUD zkt = new ZakazanKozmetickiTretmanCRUD(zakazaniFile);
		HashMap<Integer, ZakazanKozmetickiTretman> recnikZakazanih = zkt.getRecnikZakazanih();
		
		float potrosnja = noviKlijent.stanjeNaKartici(klijent.getKorisnickoIme(), recnikZakazanih);
		JLabel lblNewLabel_3 = new JLabel("Stanje na kartici: " + potrosnja);
		lblNewLabel_3.setBounds(283, 146, 605, 40);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 23));
		contentPane.add(lblNewLabel_3);
		
		String lojalitiKartica = " NE ";
		if (klijent.getLojalitiKartica()) {
			lojalitiKartica = "DA";
		}
		JLabel lblNewLabel_4 = new JLabel("Posedujete lojaliti karticu: " + lojalitiKartica);
		lblNewLabel_4.setBounds(283, 196, 652, 40);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 23));
		contentPane.add(lblNewLabel_4);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 269, 1515, 2);
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
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(41, 306, 1456, 497);
		contentPane.add(scrollPane);
		
		ArrayList<ZakazanKozmetickiTretman> listaKlijentovihTretmana = new ArrayList<>();
		for (HashMap.Entry<Integer, ZakazanKozmetickiTretman> zakazanTretman: recnikZakazanih.entrySet()) {
			if (zakazanTretman.getValue().getKorisnickoImeKlijenta().equals(klijent.getKorisnickoIme())) {
				listaKlijentovihTretmana.add(zakazanTretman.getValue());
			}
		}		
		table_1 = new JTable();
		table_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		modelTabele = new DefaultTableModel(
			    new Object[][] {},
			    new String[] {
			        "kozmeti\u010Dar", "usluga", "tretman", "stanje", "datum", "vreme", "trajanje tretmana", "cena"
			    }
			);
		table_1.setModel(modelTabele);
		
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
			((DefaultTableModel) table_1.getModel()).addRow(red);
		}
		
		JTableHeader header = table_1.getTableHeader();
        Font headerFont = header.getFont();
        Font newHeaderFont = new Font(headerFont.getName(), Font.BOLD, 20);
        header.setFont(newHeaderFont);
        table_1.setEnabled(false);
		
		scrollPane.setViewportView(table_1);
		
		JButton btnZakaiTretman = new JButton("ZAKAŽI TRETMAN");
		btnZakaiTretman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zakaziTretmanKlijent frame = new zakaziTretmanKlijent(klijentiMeni.this, klijent, lblNewLabel_4, lblNewLabel_3);
				frame.setVisible(true);
			}
		});
		btnZakaiTretman.setFont(new Font("Tahoma", Font.PLAIN, 23));
		btnZakaiTretman.setBounds(1204, 161, 252, 35);
		contentPane.add(btnZakaiTretman);
		
		JButton btnIzmeniTretman = new JButton("OTKAŽI TRETMAN");
		btnIzmeniTretman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IzmeniTretmanKlijent frame = new IzmeniTretmanKlijent(klijentiMeni.this, klijent, lblNewLabel_4, lblNewLabel_3);
				frame.setVisible(true);
			}
		});
		btnIzmeniTretman.setFont(new Font("Tahoma", Font.PLAIN, 23));
		btnIzmeniTretman.setBounds(1204, 224, 252, 35);
		contentPane.add(btnIzmeniTretman);	
	}
}
