package korisniciSistema;

import java.util.List;

import uslugeTretmani.KozmetickaUslugaTretman;

public class Kozmeticar extends Zaposlen {
	
	private List<KozmetickaUslugaTretman> tretmani;
	
	public Kozmeticar(String korisnickoIme, String lozinka, String ime, String prezime, String pol, String telefon, String adresa,
			int strucnaSprema, int staz, double osnovnaPlata, double plata, List<KozmetickaUslugaTretman> listaTretmani) {
		super(ime, prezime, pol, telefon, adresa, korisnickoIme, lozinka, strucnaSprema, staz, osnovnaPlata, plata);
		this.tretmani = listaTretmani;
	}

	public List<KozmetickaUslugaTretman> getTretmani() {
		return tretmani;
	}
	
	public void setTretmani(List<KozmetickaUslugaTretman> tretmani) {
		this.tretmani = tretmani;
	}
	
	public String toFileString() {
		return getKorisnickoIme()+"|"+getLozinka()+"|"+getIme()+"|"+getPrezime()+"|"+getPol()+"|"+getTelefon()+"|"+getAdresa()+"|"+
				getStrucnaSprema()+"|"+getStaz()+"|"+getOsnovnaPlata()+"|"+getPlata()+"|"+
				getTretmani();
	}
	
	public String tretmaniZaTabelu (List<KozmetickaUslugaTretman> tretmani) {
		String zaTabelu = "";
		for (KozmetickaUslugaTretman usluga : tretmani) {
			zaTabelu += usluga.getNazivUsluge() + ", ";
		}
		zaTabelu = zaTabelu.substring(0, zaTabelu.length() - 2);
		return zaTabelu;
	}
	
}
