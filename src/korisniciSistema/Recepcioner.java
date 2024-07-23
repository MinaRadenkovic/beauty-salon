package korisniciSistema;

public class Recepcioner extends Zaposlen{

	public Recepcioner(String korisnickoIme, String lozinka, String ime, String prezime, String pol, String telefon, String adresa, 
			 int strucnaSprema, int staz, double osnovnaPlata, double plata) {
		super(ime, prezime, pol, telefon, adresa, korisnickoIme, lozinka, strucnaSprema, staz, osnovnaPlata, plata);
	}

//	@Override
//	public String toString() {
//		return "Recepcioner [korisnicko_ime=" + getKorisnickoIme() + ", lozinka=" + getLozinka() + ", ime=" + getIme() + ", prezime=" + getPrezime() + 
//				", pol=" + getPol() + ", telefon=" + getTelefon() + ", adresa=" + getAdresa() + ", strucna_sprema=" + getStrucnaSprema() + 
//				", staz=" + getStaz() + ", plata=" + getPlata() + "]";
//	}
	
	public String toFileString() {
		return getKorisnickoIme()+"|"+getLozinka()+"|"+getIme()+"|"+getPrezime()+"|"+getPol()+"|"+getTelefon()+"|"+getAdresa()+"|"+
				getStrucnaSprema()+"|"+getStaz()+"|"+getOsnovnaPlata()+"|"+getPlata();
	}
}
