package korisniciSistema;

public class Klijent extends Korisnik{
	
	private boolean lojalitiKartica;
	
	public Klijent(String korisnickoIme, String lozinka, String ime, String prezime, String pol, String telefon, String adresa, 
			 boolean lojalitiKartica) {
		super(ime, prezime, pol, telefon, adresa, korisnickoIme, lozinka);
		this.lojalitiKartica = lojalitiKartica;
	}

	public boolean getLojalitiKartica() {
		return lojalitiKartica;
	}

	public void setLojalitiKartica(boolean lojalitiKartica) {
		this.lojalitiKartica = lojalitiKartica;
	}
	
	@Override
	public String toString() {
		return "Klijent [korisnicko_ime=" + getKorisnickoIme() + ", lozinka=" + getLozinka() + ", ime=" + getIme() + ", prezime=" + getPrezime() + ", pol=" + getPol() +
				", telefon=" + getTelefon() + ", adresa=" + getAdresa() + ", lojalitiKartica=" + lojalitiKartica + "]";
	}
	
	public String toFileString() {
		return getKorisnickoIme()+"|"+getLozinka()+"|"+getIme()+"|"+getPrezime()+"|"+getPol()+"|"+getTelefon()+"|"+getAdresa()+"|"+lojalitiKartica;
	}
}
