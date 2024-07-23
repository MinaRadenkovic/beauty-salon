package CRUD;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import uslugeTretmani.TipKozmetickogTretmana;

public class TipKozmetickogTretmanaCRUD {
	
	private String tretmaniFile;
	private HashMap<Integer, TipKozmetickogTretmana> recnikTretmana;

	public TipKozmetickogTretmanaCRUD(String tretmaniFile) {
		super();
		this.tretmaniFile = tretmaniFile;
		this.recnikTretmana = new HashMap <Integer, TipKozmetickogTretmana>();
		loadData();
	}
	
	public HashMap<Integer, TipKozmetickogTretmana> getRecnikTretmana() {
		return recnikTretmana;
	}

	public TipKozmetickogTretmana pronadjiTretmanPoId(int id) {
		TipKozmetickogTretmana pronadjenTretman = null;
		for(int i: recnikTretmana.keySet()) {
			if (i == id) {
				pronadjenTretman = recnikTretmana.get(i);
				break;
			}
		}
		return pronadjenTretman;
	}
	
	public boolean loadData() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.tretmaniFile));
			String linija = null;
			while ((linija = br.readLine()) != null) {
				String[] celija = linija.split("\\|");
				int id = Integer.parseInt(celija[0]);
				String nazivUsluge = celija[1];
				LocalTime duzinaTrajanja = LocalTime.parse(celija[2]);
				String nazivTretmana = celija[3];
				float cena = Float.parseFloat(celija[4]);
				TipKozmetickogTretmana tip = new TipKozmetickogTretmana(nazivUsluge, nazivTretmana, duzinaTrajanja, cena);
				
				this.recnikTretmana.put(id, tip);
			}
			br.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public boolean saveData() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(tretmaniFile, false));
			for (int id : recnikTretmana.keySet()) {
				pw.println(id + "|" + recnikTretmana.get(id).toFileString());
			}
			pw.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public void add(String nazivUsluge, String nazivTretmana, LocalTime vremeTrajanja, float cena) {		
		TipKozmetickogTretmana tretman = new TipKozmetickogTretmana(nazivUsluge, nazivTretmana, vremeTrajanja, cena);
		int id = 0;
		for (Map.Entry<Integer, TipKozmetickogTretmana> entry : this.recnikTretmana.entrySet()) {
			if (entry.getKey() > id) {
				id = entry.getKey();
			}
		}
		id++;
		this.recnikTretmana.put(id, tretman);
		this.saveData();
		}
	
	public void edit(int id, String nazivUsluge, String nazivTretmana, LocalTime vremeTrajanja, float cena) {
		TipKozmetickogTretmana zaIzmenu = this.pronadjiTretmanPoId(id);
		zaIzmenu.setCena(cena);
		zaIzmenu.setNazivTretmana(nazivTretmana);
		zaIzmenu.setNazivUsluge(nazivUsluge);
		zaIzmenu.setVremeTrajanja(vremeTrajanja);
		this.saveData();
	}
	
	public void remove1(int id) {
		this.recnikTretmana.remove(id);
		this.saveData();
	}
	
	public void remove2(String nazivUsluge) {
		HashMap<Integer, TipKozmetickogTretmana> noviRecnik = new  HashMap<Integer, TipKozmetickogTretmana>(recnikTretmana);
		
		for (Map.Entry<Integer, TipKozmetickogTretmana> entry : noviRecnik.entrySet()) {
			if (entry.getValue().getNazivUsluge().equals(nazivUsluge)) {
				this.recnikTretmana.remove(entry.getKey());
			}
		}
		this.saveData();
	}
}
	