package CRUD;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import uslugeTretmani.KozmetickaUslugaTretman;

public class KozmetickaUslugaCRUD {

	private List<KozmetickaUslugaTretman> usluge;
	private String uslugeFile;
	
	public KozmetickaUslugaCRUD(String uslugeFile) {
		super();
		this.uslugeFile = uslugeFile;
		this.usluge = new ArrayList<KozmetickaUslugaTretman>();
		loadData();
	}
	
	public List<KozmetickaUslugaTretman> getUsluge() {
		return usluge;
	}

	public KozmetickaUslugaTretman pronadjiUsluguPoNazivu(String naziv) {
		KozmetickaUslugaTretman pronadjenaUsluga = null;
		for (int i = 0; i<usluge.size(); i++) {
			KozmetickaUslugaTretman kut = usluge.get(i);
			if (kut.getNazivUsluge().equals(naziv)) {
				pronadjenaUsluga = kut;
				break;
				}
			}
		return pronadjenaUsluga;
		}
	
	public boolean loadData() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.uslugeFile));
			String linija = null;
			while ((linija = br.readLine()) != null) {
				this.usluge.add(new KozmetickaUslugaTretman(linija));
			}
			br.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public void readData() {
		for(KozmetickaUslugaTretman kut: usluge) {
			System.out.println(kut.toString());
		}
	}
	
	public boolean saveData() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(this.uslugeFile, false));
			for (KozmetickaUslugaTretman kut : usluge) {
				pw.println(kut.toFileString());
			}
			pw.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public void add(String nazivUsluge) {
		boolean vecPostoji = false;
		for (KozmetickaUslugaTretman usluga : this.usluge) {
			if (usluga.getNazivUsluge().equals(nazivUsluge)) {
				vecPostoji = true;
			}
		}
		if (!vecPostoji) {
			this.usluge.add(new KozmetickaUslugaTretman(nazivUsluge));
			this.saveData();		
		}
	}
	
	public void edit(String nazivUsluge) {
//		edit ne vredi jer usluga tretmana sadrzi samo naziv?
	}

	public void remove(String nazivUsluge) {
		KozmetickaUslugaTretman kut = pronadjiUsluguPoNazivu(nazivUsluge);
		this.usluge.remove(kut);
		this.saveData();
	}
}
