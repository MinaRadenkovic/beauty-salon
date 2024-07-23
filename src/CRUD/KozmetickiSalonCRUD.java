package CRUD;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Zakazivanje.StanjeZakazanogTretmana;
import kozmetickiSalon.KozmetickiSalon;
import uslugeTretmani.ZakazanKozmetickiTretman;

public class KozmetickiSalonCRUD {
	private List<KozmetickiSalon> saloni;
	private String salonFile;
	
	public KozmetickiSalonCRUD(String salonFile) {
		super();
		this.salonFile = salonFile;
		this.saloni = new ArrayList<KozmetickiSalon>();
		loadData();
	}
	
	public List<KozmetickiSalon> loadData() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.salonFile));
			String linija = null;
			while ((linija = br.readLine()) != null) {
				String[] celija = linija.split("\\|");
				this.saloni.add(new KozmetickiSalon(celija[0], LocalTime.parse(celija[1]), LocalTime.parse(celija[2]), celija[3], celija[4], celija[5], LocalDate.parse(celija[6]), LocalDate.parse(celija[7])));
			}
			br.close();
		} catch (IOException e) {
			return null;
		}
		return getSaloni();
	}
	
	public List<KozmetickiSalon> getSaloni() {
		return saloni;
	}

	public void saveData() {
		try {
			FileWriter myWriter = new FileWriter(salonFile);
			for(KozmetickiSalon ks: saloni){
				myWriter.write(ks.toFileString() + "\n");
		}
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void add(String naziv,LocalTime pocetakRadnogVremena, LocalTime krajRadnogVremena, String iznosLojalitiKartice, String brojTretmanaZaBonus, String ostvarenPrihodZaBonus,
			LocalDate datumPocetkaRacunanjaPrihodaZaBonus, LocalDate datumKrajaRacunanjaPrihodaZaBonus) {
		this.saloni.add(new KozmetickiSalon(naziv, pocetakRadnogVremena, krajRadnogVremena, iznosLojalitiKartice, brojTretmanaZaBonus, ostvarenPrihodZaBonus, datumPocetkaRacunanjaPrihodaZaBonus, datumKrajaRacunanjaPrihodaZaBonus));
		this.saveData();
	}

	public void edit(String naziv,LocalTime pocetakRadnogVremena, LocalTime krajRadnogVremena, String iznosLojalitiKartice, String brojTretmanaZaBonus, String ostvarenPrihodZaBonus,
			LocalDate datumPocetkaRacunanjaPrihodaZaBonus, LocalDate datumKrajaRacunanjaPrihodaZaBonus) {
		KozmetickiSalon ks = this.getSaloni().get(0);
		ks.setNaziv(naziv);
		ks.setPocetakRadnogVremena(pocetakRadnogVremena);
		ks.setKrajRadnogVremena(krajRadnogVremena);
		ks.setBrojTretmanaZaBonus(brojTretmanaZaBonus);
		ks.setIznosLojalitiKartice(iznosLojalitiKartice);
		ks.setOstvarenPrihodZaBonus(ostvarenPrihodZaBonus);
		ks.setDatumKrajaRacunanjaPrihodaZaBonus(datumKrajaRacunanjaPrihodaZaBonus);
		ks.setDatumPocetkaRacunanjaPrihodaZaBonus(datumPocetkaRacunanjaPrihodaZaBonus);
		this.saveData();
	}

//	public void remove(String ime) {
//		this.saloni.remove(this.getSaloni().get(0));
//		this.saveData();
//	}
	
	public float bilansNovcaSalona (HashMap<Integer, ZakazanKozmetickiTretman> recnikZakazanih) {
		//za klijenta je u klijentiCRUD
		//bilans novca salona == prihod
		float novci = 0;
		for (ZakazanKozmetickiTretman zkt: recnikZakazanih.values()) {
			if (zkt.getStanje() == StanjeZakazanogTretmana.IZVRSEN || zkt.getStanje() == StanjeZakazanogTretmana.ZAKAZAN || 
						zkt.getStanje() == StanjeZakazanogTretmana.NIJE_SE_POJAVIO) {
				novci += zkt.getCena();
			} else if (zkt.getStanje() == StanjeZakazanogTretmana.OTKAZAO_KLIJENT){
				novci += 0.1 * zkt.getCena();					
			} else {
				novci += 0;
			}
		}
		return novci;
	}
}
