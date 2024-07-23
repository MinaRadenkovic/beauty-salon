package gui;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

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

public class zakaziTretmanRecepcioner extends JDialog  implements ItemListener{

	String kozmeticariFile = ".//fajlovi/kozmeticari.csv";
	String salonFile = ".//fajlovi/saloni.csv";
	String klijentiFile = ".//fajlovi/klijenti.csv";
	String recepcineriFile = ".//fajlovi/recepcioneri.csv";
	
	String uslugeFile = ".//fajlovi/usluge.csv";
	String tretmaniFile = ".//fajlovi/tretmani.csv";
	String zakazaniFile = ".//fajlovi/zakazani.csv";
	
	List<Klijent> sviKlijenti = new KlijentiCRUD(klijentiFile).getKlijenti();
	List<Kozmeticar> koz = new KozmeticariCRUD(kozmeticariFile).getKozmeticari();
	List<KozmetickiSalon> ks = new KozmetickiSalonCRUD(salonFile).getSaloni();
	List<Recepcioner> r = new RecepcioneriCRUD(recepcineriFile).getRecepcioneri();
	
	List<KozmetickaUslugaTretman> kut = new KozmetickaUslugaCRUD(uslugeFile).getUsluge();
	HashMap<Integer, TipKozmetickogTretmana> tkt = new TipKozmetickogTretmanaCRUD(tretmaniFile).getRecnikTretmana();
	HashMap<Integer, ZakazanKozmetickiTretman> zkt = new ZakazanKozmetickiTretmanCRUD(zakazaniFile).getRecnikZakazanih();
	ZakazanKozmetickiTretmanCRUD zakazani = new ZakazanKozmetickiTretmanCRUD(zakazaniFile);
	
	private final JPanel contentPanel = new JPanel();
	Recepcioner recepcioner;
	JFrame parentFrame;

	private Choice choice = new Choice();			//usluga
	private Choice choice_1 = new Choice();		//tretman
	private Choice choice_1_1 = new Choice();		//kozmeticar
	private JDateChooser dateChooser = new JDateChooser();	//datum
	private Choice choice_1_1_1_1 = new Choice();	//vreme

	public zakaziTretmanRecepcioner(JFrame parent, Recepcioner recepcioner) {
		this.recepcioner = recepcioner;
		this.parentFrame = parent;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(zakaziTretmanRecepcioner.class.getResource("/gui/BeautySalon.png")));
		getContentPane().setBackground(new Color(174, 227, 253));
		setBounds(100, 100, 478, 343);
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
		
		JLabel lblNewLabel = new JLabel("2. Odaberite uslugu: ");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(21, 101, 201, 21);
		contentPanel.add(lblNewLabel);
		
		choice.setFont(new Font("Dialog", Font.PLAIN, 12));
		choice.setBounds(228, 101, 227, 21);
		
		for (KozmetickaUslugaTretman usluga : kut) {
			choice.add((String) usluga.getNazivUsluge());
		}
		choice.addItemListener(this);
		
		contentPanel.add(choice);
		
		JLabel lblOdaberiteTretman = new JLabel("3. Odaberite tretman: ");
		lblOdaberiteTretman.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblOdaberiteTretman.setBounds(22, 138, 201, 21);
		contentPanel.add(lblOdaberiteTretman);
		
		choice_1.setFont(new Font("Dialog", Font.PLAIN, 12));
		choice_1.setBounds(228, 138, 227, 21);
		
		if (choice.getSelectedItem() != null) {
			String nazivUsluge = choice.getSelectedItem();
			for (Map.Entry<Integer, TipKozmetickogTretmana> entry : tkt.entrySet()) {
				if (entry.getValue().getNazivUsluge().equals(nazivUsluge)) {
					choice_1.add((String) entry.getValue().getNazivTretmana());
				}
			}
		}
		contentPanel.add(choice_1);
		
		JLabel lblOdaberiteKozmetiara = new JLabel("4. Odaberite kozmetičara: ");
		lblOdaberiteKozmetiara.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblOdaberiteKozmetiara.setBounds(22, 172, 175, 21);
		contentPanel.add(lblOdaberiteKozmetiara);
		
		choice_1_1.setFont(new Font("Dialog", Font.PLAIN, 12));
		choice_1_1.setBounds(228, 172, 227, 21);
		
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
		
		JLabel lblOdaberiteDatum = new JLabel("5. Odaberite datum: ");
		lblOdaberiteDatum.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblOdaberiteDatum.setBounds(22, 206, 175, 21);
		contentPanel.add(lblOdaberiteDatum);
		
		JLabel lblOdaberiteVreme = new JLabel("6. Odaberite vreme: ");
		lblOdaberiteVreme.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblOdaberiteVreme.setBounds(22, 237, 175, 21);
		contentPanel.add(lblOdaberiteVreme);
		
		choice_1_1_1_1.setFont(new Font("Dialog", Font.PLAIN, 12));
		choice_1_1_1_1.setBounds(228, 237, 227, 21);
		contentPanel.add(choice_1_1_1_1);
		
		dateChooser.setBounds(228, 206, 227, 21);
		
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
		
		JLabel lblOdaberiteKlijenta = new JLabel("1. Odaberite klijenta: ");
		lblOdaberiteKlijenta.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblOdaberiteKlijenta.setBounds(20, 70, 201, 21);
		contentPanel.add(lblOdaberiteKlijenta);
		
		Choice choice_2 = new Choice();
		choice_2.setFont(new Font("Dialog", Font.PLAIN, 12));
		choice_2.setBounds(227, 70, 228, 21);
		
		for (Klijent klijent: sviKlijenti) {
			choice_2.add(klijent.getIme() + " " + klijent.getPrezime());
		}		
		contentPanel.add(choice_2);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(174, 227, 253));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{

				JButton okButton = new JButton("Zakaži tretman");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							String korImeKli = "";
							String imePrezKli = choice_2.getSelectedItem();
							for (Klijent k : sviKlijenti) {
								if ((k.getIme() + " " + k.getPrezime()).equals(imePrezKli)) {
									korImeKli = k.getKorisnickoIme();
									break;
								}
							}
							String korImeKoz = "";
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
							boolean zakazan = zakazivanjeTretmana.zakazivanje(korImeKli, sviKlijenti, recepcioner.getKorisnickoIme(), r, korImeKoz, koz, nazivUsluge, kut, nazivTretmana, tkt, datum, parsedTime, zkt);
							if (zakazan) {
								Component[] components = parent.getContentPane().getComponents();
	
								for (Component komponenta: components) {
									if (komponenta instanceof JScrollPane) {
										JViewport  viewport = ((JScrollPane) komponenta).getViewport();
										Component[] viewportComponents = viewport.getComponents();
										for (Component table : viewportComponents) {
											if (table instanceof JTable) {
												
												IzmeniTretmanRecepcioner izmena = new IzmeniTretmanRecepcioner(parent, recepcioner);
												izmena.osveziTabelu((JTable)table, recepcioner);
											}
										}
									}
								}
								dispose();	
							} else {
					            JOptionPane.showMessageDialog(getContentPane(), "Neuspešno zakazivanje tretmana.\nProverite unete podatke i pokušajte ponovo.", "Neuspešno zakazivanje tretmana", JOptionPane.ERROR_MESSAGE);	
							}
						} catch (Exception e1) {
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
        	Date selectedDate = dateChooser.getDate(); // Dobijte izabrani datum iz JDateChooser-a
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
