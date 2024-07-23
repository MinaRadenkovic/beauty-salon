package CRUD;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import izvestaji.PlateIBonusi;
import korisniciSistema.Recepcioner;

public class RecepcioneriCRUD {
	private List<Recepcioner> recepcioneri;
	private String recepcionerFile;
	
	public RecepcioneriCRUD(String recepcionerFile) {
		super();
		this.recepcionerFile = recepcionerFile;
		this.recepcioneri = new ArrayList<Recepcioner>();
		loadData();
	}
	
	public List<Recepcioner> getRecepcioneri() {
		return recepcioneri;
	}

	public Recepcioner PronadjiRecepcioneraPoUsernameu(String username) {
        Recepcioner pronadjenRecepcioner = null;
        for (int i = 0; i < recepcioneri.size(); i++) {
            Recepcioner r = recepcioneri.get(i);
            if (r.getKorisnickoIme().equals(username)) {
                pronadjenRecepcioner = r;
                break;
            }
        }
        return pronadjenRecepcioner;
    }
	
	public List<Recepcioner> loadData() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.recepcionerFile));
			String linija = null;
			while ((linija = br.readLine()) != null) {
				String[] celija = linija.split("\\|");
				this.recepcioneri.add(new Recepcioner(celija[0], celija[1], celija[2], celija[3], celija[4], celija[5], celija[6], 
						Integer.parseInt(celija[7]), Integer.parseInt(celija[8]), Double.parseDouble(celija[9]), Double.parseDouble(celija[10])));
			}
			br.close();
		} catch (IOException e) {
			return null;
		}
		return getRecepcioneri();
	}
	
	public void readData() {
		for(Recepcioner r: recepcioneri){
			System.out.println(r.toString());
			}		
		}
	
	public void saveData() {
		try {
			FileWriter myWriter = new FileWriter(recepcionerFile);
			for(Recepcioner r: recepcioneri){
				myWriter.write(r.toFileString() + "\n");
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
		
		this.recepcioneri.add(new Recepcioner(korisnickoIme, lozinka, ime, prezime, pol, telefon, adresa, strucnaSprema, staz, osnovnaPlata, plata));
		this.saveData();
	}

	public void edit(String korisnickoIme, String lozinka, String ime, String prezime, String pol, String telefon, String adresa, 
			int strucnaSprema, int staz, double osnovnaPlata) {
		Recepcioner k = this.PronadjiRecepcioneraPoUsernameu(korisnickoIme);
		PlateIBonusi racunajPlatu = new PlateIBonusi();
		double plata = racunajPlatu.plate(korisnickoIme, staz, strucnaSprema, osnovnaPlata);	
		
		k.setLozinka(lozinka);
		k.setIme(ime);
		k.setPrezime(prezime);
		k.setPol(pol);
		k.setTelefon(telefon);
		k.setAdresa(adresa);
		k.setStrucnaSprema(strucnaSprema);
		k.setStaz(staz);
		k.setPlata(plata);
		k.setOsnovnaPlata(osnovnaPlata);
		this.saveData();
	}

	public void remove(String username) {
		Recepcioner r = PronadjiRecepcioneraPoUsernameu(username);
		this.recepcioneri.remove(r);
		this.saveData();
	}
}
