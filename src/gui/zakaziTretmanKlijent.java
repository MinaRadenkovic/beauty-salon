package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import CRUD.KlijentiCRUD;
import CRUD.KozmeticariCRUD;
import CRUD.KozmetickaUslugaCRUD;
import CRUD.KozmetickiSalonCRUD;
import CRUD.RecepcioneriCRUD;
import CRUD.TipKozmetickogTretmanaCRUD;
import CRUD.ZakazanKozmetickiTretmanCRUD;
import Zakazivanje.ZakazivanjeTretmana;
import korisniciSistema.Klijent;
import korisniciSistema.Kozmeticar;
import korisniciSistema.Recepcioner;
import kozmetickiSalon.KozmetickiSalon;
import uslugeTretmani.KozmetickaUslugaTretman;
import uslugeTretmani.TipKozmetickogTretmana;
import uslugeTretmani.ZakazanKozmetickiTretman;

import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTable;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.awt.event.ActionEvent;
import java.awt.Choice;
import com.toedter.calendar.JDateChooser;
import javax.swing.JViewport;

import java.time.format.DateTimeFormatter;

public class zakaziTretmanKlijent extends JDialog implements ItemListener{
	String kozmeticariFile = ".//fajlovi/kozmeticari.csv";
	String salonFile = ".//fajlovi/saloni.csv";
	String klijentiFile = ".//fajlovi/klijenti.csv";
	String recepcineriFile = ".//fajlovi/recepcioneri.csv";
	
	String uslugeFile = ".//fajlovi/usluge.csv";
	String tretmaniFile = ".//fajlovi/tretmani.csv";
	String zakazaniFile = ".//fajlovi/zakazani.csv";
	
	List<Klijent> k = new KlijentiCRUD(klijentiFile).getKlijenti();
	List<Kozmeticar> koz = new KozmeticariCRUD(kozmeticariFile).getKozmeticari();
	List<KozmetickiSalon> ks = new KozmetickiSalonCRUD(salonFile).getSaloni();
	List<Recepcioner> r = new RecepcioneriCRUD(recepcineriFile).getRecepcioneri();
	
	List<KozmetickaUslugaTretman> kut = new KozmetickaUslugaCRUD(uslugeFile).getUsluge();
	HashMap<Integer, TipKozmetickogTretmana> tkt = new TipKozmetickogTretmanaCRUD(tretmaniFile).getRecnikTretmana();
	HashMap<Integer, ZakazanKozmetickiTretman> zkt = new ZakazanKozmetickiTretmanCRUD(zakazaniFile).getRecnikZakazanih();
	ZakazanKozmetickiTretmanCRUD zakazani = new ZakazanKozmetickiTretmanCRUD(zakazaniFile);

	private final JPanel contentPanel = new JPanel();
	Klijent klijent;
	JFrame parentFrame;

	private Choice choice = new Choice();			//usluga
	private Choice choice_1 = new Choice();		//tretman
	private Choice choice_1_1 = new Choice();		//kozmeticar
	private JDateChooser dateChooser = new JDateChooser();	//datum
	private Choice choice_1_1_1_1 = new Choice();	//vreme

	public zakaziTretmanKlijent(JFrame parent, Klijent klijent, JLabel lblNewLabel_4, JLabel lblNewLabel_3) {
		this.klijent = klijent;
		this.parentFrame = parent;	

		setIconImage(Toolkit.getDefaultToolkit().getImage(zakaziTretmanKlijent.class.getResource("/gui/BeautySalon.png")));
		getContentPane().setBackground(new Color(174, 227, 253));
		setBounds(100, 100, 478, 320);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(174, 227, 253));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblMolomoUnesite = new JLabel("Molimo unesite potrebne podatke da biste zakazali Vaš tretman: ");
		lblMolomoUnesite.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMolomoUnesite.setBounds(21, 21, 433, 16);
		contentPanel.add(lblMolomoUnesite);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 47, 444, 5);
		contentPanel.add(separator);
		
		JLabel lblNewLabel = new JLabel("1. Odaberite uslugu: ");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(20, 65, 201, 21);
		contentPanel.add(lblNewLabel);
		
		choice.setFont(new Font("Dialog", Font.PLAIN, 12));
		choice.setBounds(289, 65, 165, 21);
		
		for (KozmetickaUslugaTretman usluga : kut) {
			choice.add((String) usluga.getNazivUsluge());
		}
		choice.addItemListener(this);
		
		contentPanel.add(choice);
		
		JLabel lblOdaberiteTretman = new JLabel("2. Odaberite tretman: ");
		lblOdaberiteTretman.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblOdaberiteTretman.setBounds(21, 102, 201, 21);
		contentPanel.add(lblOdaberiteTretman);
		
		choice_1.setFont(new Font("Dialog", Font.PLAIN, 12));
		choice_1.setBounds(289, 102, 165, 21);
		
		if (choice.getSelectedItem() != null) {
			String nazivUsluge = choice.getSelectedItem();
			for (Map.Entry<Integer, TipKozmetickogTretmana> entry : tkt.entrySet()) {
				if (entry.getValue().getNazivUsluge().equals(nazivUsluge)) {
					choice_1.add((String) entry.getValue().getNazivTretmana());
				}
			}
		}		
		contentPanel.add(choice_1);
		
		JLabel lblOdaberiteKozmetiara = new JLabel("3. Odaberite kozmetičara: ");
		lblOdaberiteKozmetiara.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblOdaberiteKozmetiara.setBounds(21, 136, 226, 21);
		contentPanel.add(lblOdaberiteKozmetiara);
		
		choice_1_1.setFont(new Font("Dialog", Font.PLAIN, 12));
		choice_1_1.setBounds(289, 136, 165, 21);
		
		if (choice_1.getSelectedItem() != null) {
			for (Kozmeticar kozmeticar : koz) {
				List <KozmetickaUslugaTretman> listaTretmana = kozmeticar.getTretmani();
				String nazivUsluge = choice.getSelectedItem();
				for (KozmetickaUslugaTretman tretman : listaTretmana) {
					if (tretman.getNazivUsluge().equals(nazivUsluge)) {
						choice_1_1.add(kozmeticar.getIme() + " " + kozmeticar.getPrezime());
					}
				}
			}
		}		
		contentPanel.add(choice_1_1);
		
		JLabel lblOdaberiteDatum = new JLabel("4. Odaberite datum: ");
		lblOdaberiteDatum.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblOdaberiteDatum.setBounds(21, 170, 269, 21);
		contentPanel.add(lblOdaberiteDatum);
		
		JLabel lblOdaberiteVreme = new JLabel("5. Odaberite vreme: ");
		lblOdaberiteVreme.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblOdaberiteVreme.setBounds(21, 201, 226, 21);
		contentPanel.add(lblOdaberiteVreme);
		
		choice_1_1_1_1.setFont(new Font("Dialog", Font.PLAIN, 12));
		choice_1_1_1_1.setBounds(289, 201, 165, 21);
		contentPanel.add(choice_1_1_1_1);
		
		dateChooser.setBounds(289, 170, 165, 21);
		
		dateChooser.addPropertyChangeListener(
				new PropertyChangeListener() {
			        @Override
			        public void propertyChange(PropertyChangeEvent e) {
			        	Date selectedDate = dateChooser.getDate(); // Dobijte izabrani datum iz JDateChooser-a
			        	LocalDate localDate = null;
			    		if (selectedDate != null) {
			    	        localDate = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();			    		
			    		}  
			    		updateChoice_1_1_1_1(choice_1_1.getSelectedItem(), localDate);
			        }});
				
		contentPanel.add(dateChooser);		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(174, 227, 253));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Zakaži tretman");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String korImeKoz = null;
						String imePrezKoz = choice_1_1.getSelectedItem();
						for (Kozmeticar k : koz) {
							if ((k.getIme() + " " + k.getPrezime()).equals(imePrezKoz)) {
								korImeKoz = k.getKorisnickoIme();
								break;
							}
						}
						String nazivUsluge = choice.getSelectedItem();
						String nazivTretmana = choice_1.getSelectedItem();
						
						LocalDate datum = dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
						
				        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
						String vreme = choice_1_1_1_1.getSelectedItem();
				        LocalTime parsedTime = LocalTime.parse(vreme, formatter);
						
						ZakazivanjeTretmana zakazivanjeTretmana = new ZakazivanjeTretmana();	
						boolean zakazan = zakazivanjeTretmana.zakazivanje(klijent.getKorisnickoIme(), k, "online", r, korImeKoz, koz, nazivUsluge, kut, nazivTretmana, tkt, datum, parsedTime, zkt);
						if (zakazan) {
							Component[] components = parent.getContentPane().getComponents();

							for (Component komponenta: components) {
								if (komponenta instanceof JScrollPane) {
									JViewport  viewport = ((JScrollPane) komponenta).getViewport();
									Component[] viewportComponents = viewport.getComponents();
									for (Component table : viewportComponents) {
										if (table instanceof JTable) {
											
											IzmeniTretmanKlijent izmena = new IzmeniTretmanKlijent(parent, klijent, lblNewLabel_4, lblNewLabel_3);
											izmena.osveziTabelu((JTable)table, klijent, lblNewLabel_4, lblNewLabel_3);
										}
									}
								}
							}
							dispose();	
						} else {
				            JOptionPane.showMessageDialog(getContentPane(), "Neuspešno zakazivanje tretmana.\nProverite unete podatke i pokušajte ponovo.", "Neuspešno zakazivanje tretmana", JOptionPane.ERROR_MESSAGE);	
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
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {

        if (e.getSource() == choice) {
            updateChoice_1(choice.getSelectedItem());
        }        
        if (e.getSource() == choice_1) {
            updateChoice_1_1(choice_1.getSelectedItem());
        }        
        if (e.getSource() == choice_1_1) {
        	Date selectedDate = dateChooser.getDate(); 
            LocalDate localDate = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            updateChoice_1_1_1_1(choice_1_1.getSelectedItem(), localDate);
        }
	}
	
    private void updateChoice_1_1_1_1(String imePrezKoz, LocalDate datum) {
    	choice_1_1_1_1.removeAll();
    	if (dateChooser.getDate() != null) {
        	choice_1_1_1_1.removeAll();

        	LocalDate selectedDate = dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            
        	List <ZakazanKozmetickiTretman> tretmani = new ArrayList<>();
            for (Entry<Integer, ZakazanKozmetickiTretman> entry : zkt.entrySet()) {
            	if (selectedDate.equals(entry.getValue().getDatumTretmana())) {
            		tretmani.add(entry.getValue());
            	}
            }
            LocalTime pocetakRada = ks.get(0).getPocetakRadnogVremena();
            LocalTime krajRada = ks.get(0).getKrajRadnogVremena();
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            
            Kozmeticar izabraniKozmeticar = null;
        
	        for (Kozmeticar k : koz) {
	        	if ((k.getIme() + " " + k.getPrezime()).equals(imePrezKoz)) {
	        		izabraniKozmeticar = k;
	        	}
	        }          
	        List <String> vremena = new ArrayList<>();
	        
	        TipKozmetickogTretmana izabraniTretman = null;
	        for (Map.Entry<Integer, TipKozmetickogTretmana> entry: tkt.entrySet()) {
	        	if (entry.getValue().getNazivTretmana().equals(choice_1.getSelectedItem())) {
	        		izabraniTretman = entry.getValue();
	        	}	
	        }	        
	        while (!pocetakRada.isAfter(krajRada)) {
	        	boolean isAvaible = true;
	        	LocalTime sat = LocalTime.parse("01:00:00");
	        	
	        	for (ZakazanKozmetickiTretman tretman : tretmani) {
	        		if (tretman.getKorisnickoImeKozmeticara().equals(izabraniKozmeticar.getKorisnickoIme())) {
	        			if (tretman.getVremeTretmana().equals(pocetakRada)) {
	        				isAvaible = false;
	        				break;
	        			}	        			
	        			int daLiJeDuzeOdSatVremena = tretman.getVremeTrajanja().compareTo(sat);
	        			if (daLiJeDuzeOdSatVremena > 0) {
	        				if (tretman.getVremeTretmana().plusHours(1).equals(pocetakRada)) {
	        					isAvaible = false;
	        					break;
	        				}
	        			}	        			
	        			if (izabraniTretman.getVremeTrajanja().compareTo(sat) > 0) {
	        				if (pocetakRada.plusHours(1).equals(tretman.getVremeTretmana())) {
	        					isAvaible = false;
	        					break;
	        				}
	        			}
	        		}
	        	}
	        	if (isAvaible) {
		        	String formattedTime = pocetakRada.format(timeFormatter);
		        	vremena.add(formattedTime);
	        	}	        
	        // Dodajte jedan sat na trenutno vreme
	        	pocetakRada = pocetakRada.plusHours(1);
	        }
	        for (String vreme : vremena) {
	        	choice_1_1_1_1.add(vreme);
	        }
       	}
	}

	private void updateChoice_1(String selectedService) {
        choice_1.removeAll(); 
        List<String> listaTretmana = new ArrayList<>();
        
        if (selectedService != null) {
            for (Map.Entry<Integer, TipKozmetickogTretmana> entry : tkt.entrySet()) {
                if (entry.getValue().getNazivUsluge().equals(selectedService)) {
                    choice_1.add((String) entry.getValue().getNazivTretmana());
                    listaTretmana.add((String) entry.getValue().getNazivTretmana());
                }
            }
        }
        choice_1_1.removeAll();
        updateChoice_1_1(listaTretmana.get(0));
    }
    
    private void updateChoice_1_1(String selectedService) {
    	choice_1_1.removeAll();
    	
        List<String> kozmeticari = new ArrayList<>();

		if (selectedService != null) {
			for (Kozmeticar kozmeticar : koz) {
				List <KozmetickaUslugaTretman> listaTretmana = kozmeticar.getTretmani();
				String nazivUsluge = choice.getSelectedItem();
				for (KozmetickaUslugaTretman tretman : listaTretmana) {
					if (tretman.getNazivUsluge().equals(nazivUsluge)) {
						choice_1_1.add(kozmeticar.getIme() + " " + kozmeticar.getPrezime());
						kozmeticari.add(kozmeticar.getKorisnickoIme());
					}
				}
			}
		}
		Date selectedDate = dateChooser.getDate(); // Dobijte izabrani datum iz JDateChooser-a
		if (selectedDate != null) {
	        LocalDate localDate = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			updateChoice_1_1_1_1(kozmeticari.get(0), localDate);
		}
    }
}
