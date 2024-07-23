package izvestaji;

import java.time.LocalDate;
import java.util.HashMap;

import CRUD.ZakazanKozmetickiTretmanCRUD;
import Zakazivanje.StanjeZakazanogTretmana;
import uslugeTretmani.ZakazanKozmetickiTretman;

public class Dijagrami {
	
	// Prikaz prihoda za prethodnih 12 meseci po tipu tretmana, kao i ukupan prihod.
	public float prihodUMesecPoTipuUsluge(String nazivUsluge, LocalDate pocMeseca, LocalDate krajMeseca) {
		String zakazaniFile = ".//fajlovi/zakazani.csv";
		float brojac = 0;
		HashMap<Integer, ZakazanKozmetickiTretman> zkt = new ZakazanKozmetickiTretmanCRUD(zakazaniFile).getRecnikZakazanih();
		for (ZakazanKozmetickiTretman tretman : zkt.values()) {
			if (tretman.getNazivUsluge().equals(nazivUsluge) && pocMeseca.isBefore(tretman.getDatumTretmana()) && krajMeseca.isAfter(tretman.getDatumTretmana())) {
				brojac += prihod(tretman.getNazivUsluge(), tretman);
			}
		}
		return brojac;
	}
	
	public float prihod (String nazivUsluge, ZakazanKozmetickiTretman zkt) {

		float novci = 0;
		if (zkt.getNazivUsluge().equals(nazivUsluge)) {
			if (zkt.getStanje() == StanjeZakazanogTretmana.IZVRSEN || zkt.getStanje() == StanjeZakazanogTretmana.ZAKAZAN || 
							zkt.getStanje() == StanjeZakazanogTretmana.NIJE_SE_POJAVIO) {
				novci += zkt.getCena();
			} else if (zkt.getStanje() == StanjeZakazanogTretmana.OTKAZAO_KLIJENT){
				novci += 0.1 * zkt.getCena();					
			} else {
				novci += 0;
			}
		}
		return novci;
	}
	
	public float prihodZaPrethodnih12MeseciUkupno(LocalDate pocMeseca, LocalDate krajMeseca) {
		String zakazaniFile = ".//fajlovi/zakazani.csv";
		float brojac = 0;
		HashMap<Integer, ZakazanKozmetickiTretman> zkt = new ZakazanKozmetickiTretmanCRUD(zakazaniFile).getRecnikZakazanih();
		for (ZakazanKozmetickiTretman tretman : zkt.values()) {
			if (pocMeseca.isBefore(tretman.getDatumTretmana()) && krajMeseca.isAfter(tretman.getDatumTretmana())) {
				brojac += prihod(tretman.getNazivUsluge(), tretman);
			}
		}
		return brojac;
	}
	
	// Prikaz angažovanja po kozmetičarima - broj realizovanih kozmetičkih tretmana u poslednjih 30 dana.
	public int brojIzvrsenihTretmanaPoKozmeticaru(String korImeKoz) {
		String zakazaniFile = ".//fajlovi/zakazani.csv";
		int brojac = 0;
		HashMap<Integer, ZakazanKozmetickiTretman> zkt = new ZakazanKozmetickiTretmanCRUD(zakazaniFile).getRecnikZakazanih();
		LocalDate danas = LocalDate.now();
		LocalDate pre30Dana = danas.minusDays(30);
		for (ZakazanKozmetickiTretman tretman : zkt.values()) {
			if (tretman.getKorisnickoImeKozmeticara().equals(korImeKoz) && pre30Dana.isBefore(tretman.getDatumTretmana()) && danas.isAfter(tretman.getDatumTretmana())) {
				brojac++;
			}
		}
		return brojac;
	}
	
	// Prikaz zastupljenosti pojedinačnih tretmana po statusu u odnosu na ukupan broj izvedenih kozmetičkih tretmana.
	public int brojIzvrsenihTretmana() {	
		String zakazaniFile = ".//fajlovi/zakazani.csv";
		int brojac = 0;
		HashMap<Integer, ZakazanKozmetickiTretman> zkt = new ZakazanKozmetickiTretmanCRUD(zakazaniFile).getRecnikZakazanih();
		for (ZakazanKozmetickiTretman tretman : zkt.values()) {
			if (tretman.getStanje().equals(StanjeZakazanogTretmana.IZVRSEN)) {
				brojac++;
			}
		}
		return brojac;
	}
	
	public int brojZakazanihTretmana() {	
		String zakazaniFile = ".//fajlovi/zakazani.csv";
		int brojac = 0;
		HashMap<Integer, ZakazanKozmetickiTretman> zkt = new ZakazanKozmetickiTretmanCRUD(zakazaniFile).getRecnikZakazanih();
		for (ZakazanKozmetickiTretman tretman : zkt.values()) {
			if (tretman.getStanje().equals(StanjeZakazanogTretmana.ZAKAZAN)) {
				brojac++;
			}
		}
		return brojac;
	}
	
	public int brojKlijentOtkazaoTretmana() {
		String zakazaniFile = ".//fajlovi/zakazani.csv";
		int brojac = 0;
		HashMap<Integer, ZakazanKozmetickiTretman> zkt = new ZakazanKozmetickiTretmanCRUD(zakazaniFile).getRecnikZakazanih();
		for (ZakazanKozmetickiTretman tretman : zkt.values()) {
			if (tretman.getStanje().equals(StanjeZakazanogTretmana.OTKAZAO_KLIJENT)) {
				brojac++;
			}
		}
		return brojac;
	}
	
	public int brojSalonOtkazaoTretmana() {
		String zakazaniFile = ".//fajlovi/zakazani.csv";
		int brojac = 0;
		HashMap<Integer, ZakazanKozmetickiTretman> zkt = new ZakazanKozmetickiTretmanCRUD(zakazaniFile).getRecnikZakazanih();
		for (ZakazanKozmetickiTretman tretman : zkt.values()) {
			if (tretman.getStanje().equals(StanjeZakazanogTretmana.OTKAZAO_SALON)) {
				brojac++;
			}
		}
		return brojac;
	}
	
	public int brojNijeSePojavioTretmana() {
		String zakazaniFile = ".//fajlovi/zakazani.csv";
		int brojac = 0;
		HashMap<Integer, ZakazanKozmetickiTretman> zkt = new ZakazanKozmetickiTretmanCRUD(zakazaniFile).getRecnikZakazanih();
		for (ZakazanKozmetickiTretman tretman : zkt.values()) {
			if (tretman.getStanje().equals(StanjeZakazanogTretmana.NIJE_SE_POJAVIO)) {
				brojac++;
			}
		}
		return brojac;
	}
}
