package CRUD;

import korisniciSistema.Klijent;
import kozmetickiSalon.KozmetickiSalon;
import uslugeTretmani.ZakazanKozmetickiTretman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Zakazivanje.StanjeZakazanogTretmana;

import java.io.*;

public class KlijentiCRUD {

	private List<Klijent> klijenti;
	private String klijentFile;
	
	public KlijentiCRUD (String klijentFile) {
		super();
		this.klijentFile = klijentFile;
		this.klijenti = new ArrayList<Klijent>();
		loadData();
	}
	
	public List<Klijent> getKlijenti() {
		return klijenti;
	}

	public Klijent PronadjiKlijentaPoUsernameu(String username) {
        Klijent pronadjenKlijent = null;
        for (int i = 0; i < klijenti.size(); i++) {
            Klijent k = klijenti.get(i);
            if (k.getKorisnickoIme().equals(username)) {
                pronadjenKlijent = k;
                break;
            }
        }
        return pronadjenKlijent;
    }
	
	public List<Klijent> loadData() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.klijentFile));
			String linija = null;
			while ((linija = br.readLine()) != null) {
				String[] celija = linija.split("\\|");
				this.klijenti.add(new Klijent(celija[0], celija[1], celija[2], celija[3], celija[4], celija[5], celija[6], 
						Boolean.parseBoolean(celija[7])));
			}
			br.close();
		} catch (IOException e) {
			return null;
		}
		return getKlijenti();
	}
	
	public void saveData() {
		try {
			FileWriter myWriter = new FileWriter(klijentFile);
			for(Klijent k: klijenti){
				myWriter.write(k.toFileString() + "\n");
		}
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		  
	}
	
	public void add(String korisnickoIme, String lozinka, String ime, String prezime, String pol, String telefon, String adresa, 
			 boolean lojalitiKartica) {
		Klijent klijent = null;
		for (Klijent k : this.klijenti) {
			if (k.getKorisnickoIme().equals(korisnickoIme)) {
				klijent = k;
			}
		}
		if (klijent == null) {
			this.klijenti.add(new Klijent(korisnickoIme, lozinka, ime, prezime, pol, telefon, adresa, lojalitiKartica));	
			this.saveData();	
		}

	}

	public void edit(String korisnickoIme, String lozinka, String ime, String prezime, String pol, String telefon, String adresa, 
			 boolean lojalitiKartica) {
		Klijent k = this.PronadjiKlijentaPoUsernameu(korisnickoIme);
		k.setLozinka(lozinka);
		k.setIme(ime);
		k.setPrezime(prezime);
		k.setPol(pol);
		k.setTelefon(telefon);
		k.setAdresa(adresa);
		k.setLojalitiKartica(lojalitiKartica);
		this.saveData();
	}

	public void remove(String username){
		Klijent k = PronadjiKlijentaPoUsernameu(username);
		this.klijenti.remove(k);
		this.saveData();
	}
	
	public boolean lojalitiKartica(String korisnickoImeKlijenta, HashMap<Integer, ZakazanKozmetickiTretman> recnikZakazanih) {
		boolean lojalitiKartica = false;
		Float potrosnja = this.stanjeNaKartici(korisnickoImeKlijenta, recnikZakazanih);
		
		String salonFile = ".//fajlovi/saloni.csv";
		List<KozmetickiSalon> ks = new KozmetickiSalonCRUD(salonFile).getSaloni();
		if (!ks.get(0).getIznosLojalitiKartice().equals("-")) {
			float iznos = Float.parseFloat(ks.get(0).getIznosLojalitiKartice());
		
			if (iznos <= potrosnja) {
				lojalitiKartica = true;
			}
		}
		return lojalitiKartica;
	}
	
	public float stanjeNaKartici(String korisnickoImeKlijenta, HashMap<Integer, ZakazanKozmetickiTretman> recnikZakazanih) {
		float stanjeKartice = 0;
		for (ZakazanKozmetickiTretman zkt: recnikZakazanih.values()) {
			if (korisnickoImeKlijenta.equals(zkt.getKorisnickoImeKlijenta())) {
				if (zkt.getStanje().equals(StanjeZakazanogTretmana.IZVRSEN) || zkt.getStanje().equals(StanjeZakazanogTretmana.ZAKAZAN) || 
						zkt.getStanje().equals(StanjeZakazanogTretmana.NIJE_SE_POJAVIO)) {
					stanjeKartice += zkt.getCena();
				} else if (zkt.getStanje().equals(StanjeZakazanogTretmana.OTKAZAO_KLIJENT)) {
					stanjeKartice += 0.1 * zkt.getCena();					
				} else {
					stanjeKartice += 0;
				}
			}
		}
		return stanjeKartice;
	}
}
