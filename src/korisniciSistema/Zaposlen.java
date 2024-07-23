package korisniciSistema;

import java.util.ArrayList;
import java.util.List;

public class Zaposlen extends Korisnik {
	
	private int strucnaSprema, staz, bonus;
	private double plata;
	private double osnovnaPlata;
	private List<Zaposlen> zaposleni;
	
	public Zaposlen(String ime, String prezime, String pol, String telefon, String adresa, String korisnickoIme,
			String lozinka, int strucnaSprema, int staz, double osnovnaPlata, double plata) {
		super(ime, prezime, pol, telefon, adresa, korisnickoIme, lozinka);
		this.strucnaSprema = strucnaSprema;
		this.staz = staz;
		this.zaposleni = new ArrayList<>();
		this.plata = plata;
		this.setOsnovnaPlata(osnovnaPlata);
	}

	public List<Zaposlen> getZaposleni() {
		return zaposleni;
	}

	public void setZaposleni(List<Zaposlen> zaposleni) {
		this.zaposleni = zaposleni;
	}

	public int getStrucnaSprema() {
		return strucnaSprema;
	}

	public void setStrucnaSprema(int strucnaSprema) {
		this.strucnaSprema = strucnaSprema;
	}

	public int getStaz() {
		return staz;
	}

	public void setStaz(int staz) {
		this.staz = staz;
	}

	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}

	public double getPlata() {
		return plata;
	}

	public void setPlata(double plata) {
		this.plata = plata;
	}

	public double getOsnovnaPlata() {
		return osnovnaPlata;
	}

	public void setOsnovnaPlata(double osnovnaPlata) {
		this.osnovnaPlata = osnovnaPlata;
	}
}
