package CRUD;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import uslugeTretmani.ZakazanKozmetickiTretman;
import Zakazivanje.StanjeZakazanogTretmana;

public class ZakazanKozmetickiTretmanCRUD {
	
	private String zakazaniFile;
	private HashMap<Integer, ZakazanKozmetickiTretman> recnikZakazanih;

	public ZakazanKozmetickiTretmanCRUD(String zakazaniFile) {

		super();
		this.zakazaniFile = ".//fajlovi/zakazani.csv";
		this.recnikZakazanih = new HashMap <Integer, ZakazanKozmetickiTretman>();
		loadData();
	}
	
	public HashMap<Integer, ZakazanKozmetickiTretman> getRecnikZakazanih() {
		return recnikZakazanih;
	}

	public ZakazanKozmetickiTretman pronadjiTretmanPoId(int id) {
		ZakazanKozmetickiTretman pronadjen = null;
		for(int i: recnikZakazanih.keySet()) {
			if (i == id) {
				pronadjen = recnikZakazanih.get(i);
			}
		}
		return pronadjen;
	}
	
	public HashMap <Integer, ZakazanKozmetickiTretman> loadData() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.zakazaniFile));
			String linija = null;
			while ((linija = br.readLine()) != null) {
				String[] celija = linija.split("\\|");
				if (celija.length > 1) {
					String nazivUsluge = celija[1];
					String nazivTretmana = celija[2];
					LocalTime vremeTrajanja = LocalTime.parse(celija[8]);
					String korisnickoImeKlijenta = celija[3];
					String korisnickoImeKozmeticara = celija[4];
					String nacinZakazivanja = celija[9];
					float cenaTretmana = Float.parseFloat(celija[5]);
					LocalDate datumTretmana = LocalDate.parse(celija[6]);
					LocalTime vremeTretmana = LocalTime.parse(celija[7]);
					StanjeZakazanogTretmana stanje = StanjeZakazanogTretmana.valueOf(celija[10]);
					int id = Integer.parseInt(celija[0]);
					
			        ZakazanKozmetickiTretman tretman = new ZakazanKozmetickiTretman(
			                nazivUsluge, nazivTretmana, vremeTrajanja,
			                korisnickoImeKlijenta, korisnickoImeKozmeticara, nacinZakazivanja,
			                cenaTretmana, datumTretmana, vremeTretmana,
			                stanje, id
			            );
					this.recnikZakazanih.put(id, tretman);
				}
			}
			br.close();
		} catch (IOException e) {
			return null;
		}
		return getRecnikZakazanih();
	}
	
	public void readData() {
		for(Map.Entry<Integer, ZakazanKozmetickiTretman> zkt: recnikZakazanih.entrySet()) {
			ZakazanKozmetickiTretman tretman = zkt.getValue();
			System.out.println(tretman.toString());
		}
	}
	
	public boolean saveData() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(zakazaniFile, false));
			for (int id : recnikZakazanih.keySet()) {
				pw.println(recnikZakazanih.get(id).toFileString());
			}
			pw.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public void add(String korisnickoImeKlijenta, String nacinZakazivanja, String korisnickoImeKozmeticara, String nazivUsluge, 
			String nazivTretmana, LocalDate datumTretmana, LocalTime vremeTretmana, LocalTime vremeTrajanja, float cenaTretmana, 
			StanjeZakazanogTretmana stanjeZakazanogTretmana, int zakazaniId, HashMap<Integer, ZakazanKozmetickiTretman> recnikVecZakazanih) {
		
		recnikVecZakazanih.put(zakazaniId, new ZakazanKozmetickiTretman(nazivUsluge, nazivTretmana, vremeTrajanja, 
				korisnickoImeKlijenta, korisnickoImeKozmeticara, nacinZakazivanja, cenaTretmana, datumTretmana, vremeTretmana, stanjeZakazanogTretmana, zakazaniId));
		this.recnikZakazanih = recnikVecZakazanih;
		this.saveData();
	}
	
	public void edit(int zakazaniId, String nazivUsluge, String nazivTretmana, LocalTime vremeTrajanja, StanjeZakazanogTretmana stanje,
			String korisnickoImeKlijenta, String korisnickoImeKozmeticara, String nacinZakazivanja, float cenaTretmana, LocalDate datumTretmana, LocalTime vremeTretmana) {
		ZakazanKozmetickiTretman zaIzmenu = this.pronadjiTretmanPoId(zakazaniId);
		zaIzmenu.setNazivTretmana(nazivTretmana);
		zaIzmenu.setNazivUsluge(nazivUsluge);
		zaIzmenu.setVremeTrajanja(vremeTrajanja);
		zaIzmenu.setDatumTretmana(datumTretmana);
		zaIzmenu.setKorisnickoImeKlijenta(korisnickoImeKlijenta);
		zaIzmenu.setKorisnickoImeKozmeticara(korisnickoImeKozmeticara);
		zaIzmenu.setVremeTretmana(vremeTretmana);
		zaIzmenu.setNacinZakazivanja(nacinZakazivanja);
		zaIzmenu.setStanje(stanje);
		this.saveData();		
	}
	
	public void remove(int id) {
		this.recnikZakazanih.remove(id);
		this.saveData();
	}
}
