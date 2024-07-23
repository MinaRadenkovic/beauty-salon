package izvestaji;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import CRUD.KlijentiCRUD;
import CRUD.ZakazanKozmetickiTretmanCRUD;
import Zakazivanje.StanjeZakazanogTretmana;
import uslugeTretmani.ZakazanKozmetickiTretman;
import korisniciSistema.*;

public class Izvestaji {

//	koliko je kozmetičkih tretmana svaki kozmetičar izvršio i koliko je prihodovao za izabrani opseg datuma
	public int brojIzvrsenihTretmana(String korImeKoz, LocalDate pocetak, LocalDate kraj) {
		String zakazaniFile = ".//fajlovi/zakazani.csv";
		int brojac = 0;
		HashMap<Integer, ZakazanKozmetickiTretman> zkt = new ZakazanKozmetickiTretmanCRUD(zakazaniFile).getRecnikZakazanih();
		for (ZakazanKozmetickiTretman tretman : zkt.values()) {
			if (tretman.getDatumTretmana().isBefore(kraj) && tretman.getDatumTretmana().isAfter(pocetak)) {
				if (tretman.getKorisnickoImeKozmeticara().equals(korImeKoz)) {
					brojac++;
				}
			}
		}
		return brojac;
	}
	
	public float prihodKozmeticara(String korImeKoz, LocalDate pocetak, LocalDate kraj) {
		String zakazaniFile = ".//fajlovi/zakazani.csv";
		float brojac = 0;
		HashMap<Integer, ZakazanKozmetickiTretman> zkt = new ZakazanKozmetickiTretmanCRUD(zakazaniFile).getRecnikZakazanih();
		for (ZakazanKozmetickiTretman tretman : zkt.values()) {
			if (tretman.getDatumTretmana().isBefore(kraj) && tretman.getDatumTretmana().isAfter(pocetak)) {
				if (tretman.getKorisnickoImeKozmeticara().equals(korImeKoz)) {
					brojac += tretman.getCenaTretmana();
				}
			}
		}
		return brojac;
	}
	
// koliko kozmetičkih tretmana je potvrđeno, a koliko otkazano (po razlozima) za odabrani opseg datuma
	public int brojIzvrsenihTretmana(LocalDate pocetak, LocalDate kraj) {	
		String zakazaniFile = ".//fajlovi/zakazani.csv";
		int brojac = 0;
		HashMap<Integer, ZakazanKozmetickiTretman> zkt = new ZakazanKozmetickiTretmanCRUD(zakazaniFile).getRecnikZakazanih();
		for (ZakazanKozmetickiTretman tretman : zkt.values()) {
			if (tretman.getDatumTretmana().isBefore(kraj) && tretman.getDatumTretmana().isAfter(pocetak)) {
				if (tretman.getStanje().equals(StanjeZakazanogTretmana.IZVRSEN)) {
					brojac++;
				}
			}
		}
		return brojac;
	}
	
	public int brojZakazanihTretmana(LocalDate pocetak, LocalDate kraj) {	
		String zakazaniFile = ".//fajlovi/zakazani.csv";
		int brojac = 0;
		HashMap<Integer, ZakazanKozmetickiTretman> zkt = new ZakazanKozmetickiTretmanCRUD(zakazaniFile).getRecnikZakazanih();
		for (ZakazanKozmetickiTretman tretman : zkt.values()) {
			if (tretman.getDatumTretmana().isBefore(kraj) && tretman.getDatumTretmana().isAfter(pocetak)) {
				if (tretman.getStanje().equals(StanjeZakazanogTretmana.ZAKAZAN)) {
					brojac++;
				}
			}
		}
		return brojac;
	}
	
	public int brojKlijentOtkazaoTretmana(LocalDate pocetak, LocalDate kraj) {
		String zakazaniFile = ".//fajlovi/zakazani.csv";
		int brojac = 0;
		HashMap<Integer, ZakazanKozmetickiTretman> zkt = new ZakazanKozmetickiTretmanCRUD(zakazaniFile).getRecnikZakazanih();
		for (ZakazanKozmetickiTretman tretman : zkt.values()) {
			if (tretman.getDatumTretmana().isBefore(kraj) && tretman.getDatumTretmana().isAfter(pocetak)) {
				if (tretman.getStanje().equals(StanjeZakazanogTretmana.OTKAZAO_KLIJENT)) {
					brojac++;
				}
			}
		}
		return brojac;
	}
	
	public int brojSalonOtkazaoTretmana(LocalDate pocetak, LocalDate kraj) {
		String zakazaniFile = ".//fajlovi/zakazani.csv";
		int brojac = 0;
		HashMap<Integer, ZakazanKozmetickiTretman> zkt = new ZakazanKozmetickiTretmanCRUD(zakazaniFile).getRecnikZakazanih();
		for (ZakazanKozmetickiTretman tretman : zkt.values()) {
			if (tretman.getDatumTretmana().isBefore(kraj) && tretman.getDatumTretmana().isAfter(pocetak)) {
				if (tretman.getStanje().equals(StanjeZakazanogTretmana.OTKAZAO_SALON)) {
					brojac++;
				}
			}
		}
		return brojac;
	}
	
	public int brojNijeSePojavioTretmana(LocalDate pocetak, LocalDate kraj) {
		String zakazaniFile = ".//fajlovi/zakazani.csv";
		int brojac = 0;
		HashMap<Integer, ZakazanKozmetickiTretman> zkt = new ZakazanKozmetickiTretmanCRUD(zakazaniFile).getRecnikZakazanih();
		for (ZakazanKozmetickiTretman tretman : zkt.values()) {
			if (tretman.getDatumTretmana().isBefore(kraj) && tretman.getDatumTretmana().isAfter(pocetak)) {
				if (tretman.getStanje().equals(StanjeZakazanogTretmana.NIJE_SE_POJAVIO)) {
					brojac++;
				}
			}
		}
		return brojac;
	}
	
//za prikaz kozmetičke usluge, što podrazumeva prikaz podataka o samoj usluzi i njenom tipu,
//ukupan broj zakaznih tretmana za tu uslugu i ostvarene prihode za izabrani opseg datuma.	usluga -> relaks masaza tretman -> masaza
	public int brojZakazanihTretmanaZaOdredjenuTretman(String nazivTretmana, LocalDate pocetak, LocalDate kraj) {
		String zakazaniFile = ".//fajlovi/zakazani.csv";
		int brojac = 0;
		HashMap<Integer, ZakazanKozmetickiTretman> zkt = new ZakazanKozmetickiTretmanCRUD(zakazaniFile).getRecnikZakazanih();
		for (ZakazanKozmetickiTretman tretman : zkt.values()) {
			if (tretman.getDatumTretmana().isBefore(kraj) && tretman.getDatumTretmana().isAfter(pocetak)) {
				if (tretman.getNazivTretmana().equals(nazivTretmana)) {
					brojac++;
				}
			}
		}
		return brojac;
	}
	
	public float ostvarenPrihodZaOdredjenTretman(String nazivTretmana, LocalDate pocetak, LocalDate kraj) {
		String zakazaniFile = ".//fajlovi/zakazani.csv";
		float brojac = 0;
		HashMap<Integer, ZakazanKozmetickiTretman> zkt = new ZakazanKozmetickiTretmanCRUD(zakazaniFile).getRecnikZakazanih();
		for (ZakazanKozmetickiTretman tretman : zkt.values()) {
			if (tretman.getDatumTretmana().isBefore(kraj) && tretman.getDatumTretmana().isAfter(pocetak)) {
				if (tretman.getNazivTretmana().equals(nazivTretmana)) {
					brojac += tretman.getCenaTretmana();
				}
			}
		}
		return brojac;
	}
	

// Klijenata koji ispunjavaju uslove za karticu lojalnosti (potrošili su na tretmane više novca od iznosa koji zadaje menadžer
	public List<Klijent> klijentiKojiImajuKarticu () {
		List<Klijent> listaKlijenata = new ArrayList<>();
		String klijentiFile = ".//fajlovi/klijenti.csv";
		List<Klijent> klijenti = new KlijentiCRUD(klijentiFile).getKlijenti();
		for (Klijent k : klijenti) {
			if (k.getLojalitiKartica() == true) {
				listaKlijenata.add(k);
			}
		}
		return listaKlijenata;
	}
}
