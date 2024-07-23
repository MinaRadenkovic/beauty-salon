package CRUD;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import izvestaji.PlateIBonusi;
import korisniciSistema.Menadzer;

public class MenadzeriCRUD {
	private List<Menadzer> menadzeri;
	private String menadzerFile;
	
	public MenadzeriCRUD(String menadzerFile) {
		super();
		this.menadzerFile = menadzerFile;
		this.menadzeri = new ArrayList<Menadzer>();
		loadData();
	}
	
	public List<Menadzer> getMenadzeri() {
		return menadzeri;
	}

	public Menadzer PronadjiMenadzeraPoUsernameu(String username) {
        Menadzer pronadjenMenadzer = null;
        for (int i = 0; i < menadzeri.size(); i++) {
            Menadzer m = menadzeri.get(i);
            if (m.getKorisnickoIme().equals(username)) {
                pronadjenMenadzer = m;
                break;
            }
        }
        return pronadjenMenadzer;
    }
	
	public List<Menadzer> loadData() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.menadzerFile));
			String linija = null;
			while ((linija = br.readLine()) != null) {
				String[] celija = linija.split("\\|");
				if (celija.length > 1) {
					this.menadzeri.add(new Menadzer(celija[0], celija[1], celija[2], celija[3], celija[4], celija[5], celija[6], 
					Integer.parseInt(celija[7]), Integer.parseInt(celija[8]), Double.parseDouble(celija[9]), Double.parseDouble(celija[10])));		
				}
			}
			br.close();
		} catch (IOException e) {
			return null;
		}
		return getMenadzeri();
	}
	
	public void saveData() {
		try {
			FileWriter myWriter = new FileWriter(menadzerFile);
			for(Menadzer m: menadzeri){
				myWriter.write(m.toFileString() + "\n");
		}
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void add(String korisnickoIme, String lozinka, String ime, String prezime, String pol, String telefon, String adresa, 
			int strucnaSprema, int staz, double osnovnaPlata) {
		PlateIBonusi racunajPlatu = new PlateIBonusi();
		double plata = racunajPlatu.plate(korisnickoIme, staz, strucnaSprema, osnovnaPlata);	
		
		this.menadzeri.add(new Menadzer(korisnickoIme, lozinka, ime, prezime, pol, telefon, adresa, strucnaSprema, staz, osnovnaPlata, plata));	
		this.saveData();
	}


	public void edit(String korisnickoIme, String lozinka, String ime, String prezime, String pol, String telefon, String adresa, 
			int strucnaSprema, int staz, double osnovnaPlata) {
		PlateIBonusi racunajPlatu = new PlateIBonusi();
		
		double plata = racunajPlatu.plate(korisnickoIme, staz, strucnaSprema, osnovnaPlata);	
		Menadzer m = this.PronadjiMenadzeraPoUsernameu(korisnickoIme);
		m.setLozinka(lozinka);
		m.setIme(ime);
		m.setPrezime(prezime);
		m.setPol(pol);
		m.setTelefon(telefon);
		m.setAdresa(adresa);
		m.setStrucnaSprema(strucnaSprema);
		m.setStaz(staz);
		m.setPlata(plata);
		m.setOsnovnaPlata(osnovnaPlata);
		this.saveData();
	}

	public void remove(String username) {
		Menadzer m = this.PronadjiMenadzeraPoUsernameu(username);
		this.menadzeri.remove(m);
		this.saveData();
	}
}
	