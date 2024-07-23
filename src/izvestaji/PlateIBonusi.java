package izvestaji;

import CRUD.*;
import Zakazivanje.StanjeZakazanogTretmana;
import kozmetickiSalon.KozmetickiSalon;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import uslugeTretmani.ZakazanKozmetickiTretman;

public class PlateIBonusi {
	
	public double plate(String korisnickoImeZaposlenog, int staz, int strucnaSprema, double osnovnaPlata) {
		double plata;
		String zakazaniFile = ".//fajlovi/zakazani.csv";
		HashMap<Integer, ZakazanKozmetickiTretman> zkt = new ZakazanKozmetickiTretmanCRUD(zakazaniFile).getRecnikZakazanih();
		String salonFile = ".//fajlovi/saloni.csv";
		List<KozmetickiSalon> ks = new KozmetickiSalonCRUD(salonFile).getSaloni();
		
		double plataBezBounusa = osnovnaPlata*strucnaSprema + 1000*staz;
		double bonusiPoBrTretmana = this.bonusiPoBrojuTretmana(korisnickoImeZaposlenog, zkt, ks.get(0).getBrojTretmanaZaBonus());
		double bonusipoPrihodu = this.bonusiPoOstvarenomPrihodu(korisnickoImeZaposlenog, zkt, ks.get(0).getOstvarenPrihodZaBonus(), ks.get(0).getDatumPocetkaRacunanjaPrihodaZaBonus(), ks.get(0).getDatumKrajaRacunanjaPrihodaZaBonus());
		double bonusi = bonusiPoBrTretmana + bonusipoPrihodu;
		plata = plataBezBounusa + bonusi;
		return plata;
	}

	public double bonusiPoBrojuTretmana(String korisnickoIme, HashMap<Integer, ZakazanKozmetickiTretman> recnikTretmana, String brTretmana) {
		double bonus = 0;
		if (!brTretmana.equals("-")) {
			int broj = Integer.parseInt(brTretmana);
			int brojac = 0;
			for (ZakazanKozmetickiTretman zkt: recnikTretmana.values()) {
				if (korisnickoIme.equals(zkt.getKorisnickoImeKozmeticara()) && zkt.getStanje().equals(StanjeZakazanogTretmana.IZVRSEN)) {
					brojac ++;
				}
			}			
			if (brojac >= broj) {
				bonus = 1000;
			}
		}
		return bonus;
	}
	public double bonusiPoOstvarenomPrihodu(String korisnickoIme, HashMap<Integer, ZakazanKozmetickiTretman> recnikTretmana, 
			String brOstvarenogPrihoda, LocalDate pocetak, LocalDate kraj) {
		double bonus = 0;
		if (!brOstvarenogPrihoda.equals("-")) {
			float broj = Float.parseFloat(brOstvarenogPrihoda);
			float brojac = 0;
			for (ZakazanKozmetickiTretman zkt: recnikTretmana.values()) {
				if (korisnickoIme.equals(zkt.getKorisnickoImeKozmeticara()) && zkt.getStanje().equals(StanjeZakazanogTretmana.IZVRSEN)) {
					brojac += zkt.getCenaTretmana();
				}
			}
			if (broj <= brojac) {
				bonus = 1000;
			}
		}
		return bonus;
	}
}
