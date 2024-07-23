package CRUD;

import korisniciSistema.Kozmeticar;
import uslugeTretmani.KozmetickaUslugaTretman;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import izvestaji.PlateIBonusi;

public class KozmeticariCRUD {

	private List<Kozmeticar> kozmeticari;
	private String kozmeticarFile;
	
	public KozmeticariCRUD(String kozmeticarFile) {
		super();
		this.kozmeticarFile = kozmeticarFile;
		this.kozmeticari = new ArrayList<Kozmeticar>();	
		loadData();
	}
	
	public List<Kozmeticar> getKozmeticari() {
		return kozmeticari;
	}

	public Kozmeticar PronadjiKozmeticaraPoUsernameu(String username){
        Kozmeticar pronadjenKozmeticar = null;
        for (int i = 0; i < kozmeticari.size(); i++) {
            Kozmeticar k = kozmeticari.get(i);
            if (k.getKorisnickoIme().equals(username)) {
                pronadjenKozmeticar = k;
                break;
            }
        }
        return pronadjenKozmeticar;
    }
	
	public List<Kozmeticar> loadData() {
		try {
			this.kozmeticari.clear();
			BufferedReader br = new BufferedReader(new FileReader(this.kozmeticarFile));
			String linija = null;
			while ((linija = br.readLine()) != null) {
				String[] celija = linija.split("\\|");
				
				String listaTretmana = celija[11].substring(1, celija[11].length()-1);
				String[] lista = listaTretmana.split(", ");	
				List<KozmetickaUslugaTretman> tretmani = new ArrayList<>();
				for (String tretman : lista) {
				    KozmetickaUslugaTretman noviTretman = new KozmetickaUslugaTretman(tretman);
				    tretmani.add(noviTretman);
				}
				this.kozmeticari.add(new Kozmeticar(celija[0], celija[1], celija[2], celija[3], celija[4], celija[5], celija[6], 
						Integer.parseInt(celija[7]), Integer.parseInt(celija[8]), Double.parseDouble(celija[9]), Double.parseDouble(celija[10]),
						tretmani));
			}
			br.close();
		} catch (IOException e) {
			return null;
		}
		return getKozmeticari();
	}
	
	public void saveData() {
		try {
			FileWriter myWriter = new FileWriter(kozmeticarFile);
			for(Kozmeticar koz: kozmeticari){
				myWriter.write(koz.toFileString() + "\n");
		}
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<KozmetickaUslugaTretman> tretmani (String lista) {
		List<KozmetickaUslugaTretman> kozmetickaUslugaLista = new ArrayList<>();
		if (lista == null) {
			return kozmetickaUslugaLista;
		} else {
			String[] listica = lista.split(";");
			for (String kozmetickaUslugaString : listica) {
				KozmetickaUslugaTretman kozmetickaUsluga = new KozmetickaUslugaTretman(kozmetickaUslugaString);
			    kozmetickaUslugaLista.add(kozmetickaUsluga);
			}
		}
		return kozmetickaUslugaLista;
	}			
	
	public void add(String korisnickoIme, String lozinka, String ime, String prezime, String pol, String telefon, String adresa, 
			int strucnaSprema, int staz, double osnovnaPlata, String tretmani) {
		Kozmeticar noviKoz = null;
		for (Kozmeticar k : this.kozmeticari) {
			if (k.getKorisnickoIme().equals(korisnickoIme)) {
				noviKoz = k;
			}
		}
		if (noviKoz == null) {
			PlateIBonusi racunajPlatu = new PlateIBonusi();
			double plata = racunajPlatu.plate(korisnickoIme, staz, strucnaSprema, osnovnaPlata);	
			this.kozmeticari.add(new Kozmeticar(korisnickoIme, lozinka, ime, prezime, pol, telefon, adresa, strucnaSprema, staz, osnovnaPlata, plata,
					tretmani(tretmani)));	
			this.saveData();
		}
	}

	public void edit(String korisnickoIme, String lozinka, String ime, String prezime, String pol, String telefon, String adresa, 
			int strucnaSprema, int staz, double osnovnaPlata, String tretmani) {
		Kozmeticar k = this.PronadjiKozmeticaraPoUsernameu(korisnickoIme);
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
		k.setTretmani(tretmani(tretmani));
		this.saveData();
	}

	public void remove(String username) {
		Kozmeticar k = PronadjiKozmeticaraPoUsernameu(username);
		this.kozmeticari.remove(k);
		this.saveData();
	}
}
