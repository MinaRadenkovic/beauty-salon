package prijava;

import CRUD.KlijentiCRUD;
import CRUD.KozmeticariCRUD;
import CRUD.MenadzeriCRUD;
import CRUD.RecepcioneriCRUD;
import korisniciSistema.Klijent;
import korisniciSistema.Korisnik;
import korisniciSistema.Kozmeticar;
import korisniciSistema.Menadzer;
import korisniciSistema.Recepcioner;

public class logIn {
	
	public Korisnik prijavaNaSistem (String korisnickoIme, String lozinka) {
		
		String klijentiFile = ".//fajlovi/klijenti.csv";
		String kozmeticariFile = ".//fajlovi/kozmeticari.csv";
		String menadzeriFile = ".//fajlovi/menadzeri.csv";
		String recepcineriFile = ".//fajlovi/recepcioneri.csv";
		
		KlijentiCRUD klijenti = new KlijentiCRUD(klijentiFile);
		KozmeticariCRUD kozmeticari = new KozmeticariCRUD(kozmeticariFile);
		MenadzeriCRUD menadzeri = new MenadzeriCRUD(menadzeriFile);
		RecepcioneriCRUD recepcioneri = new RecepcioneriCRUD(recepcineriFile);
		
		if (menadzeri.PronadjiMenadzeraPoUsernameu(korisnickoIme) != null) {
			Menadzer menadzer = menadzeri.PronadjiMenadzeraPoUsernameu(korisnickoIme);
			if (menadzer.getLozinka().equals(lozinka)) {
				return menadzer;
			}
		} else if (recepcioneri.PronadjiRecepcioneraPoUsernameu(korisnickoIme) != null) {
			Recepcioner recepcioner = recepcioneri.PronadjiRecepcioneraPoUsernameu(korisnickoIme);
			if (recepcioner.getLozinka().equals(lozinka)) {
				return recepcioner;
			}
		} else if (kozmeticari.PronadjiKozmeticaraPoUsernameu(korisnickoIme) != null) {
			Kozmeticar kozmeticar = kozmeticari.PronadjiKozmeticaraPoUsernameu(korisnickoIme);
			if (kozmeticar.getLozinka().equals(lozinka)) {
				return kozmeticar;
			}
		} else if (klijenti.PronadjiKlijentaPoUsernameu(korisnickoIme) != null) {
			Klijent klijent = klijenti.PronadjiKlijentaPoUsernameu(korisnickoIme);
			if (klijent.getLozinka().equals(lozinka)) {
				return klijent;
			}
		}
		return null;
	}
}
