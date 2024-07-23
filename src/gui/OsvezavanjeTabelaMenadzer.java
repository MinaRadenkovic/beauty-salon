package gui;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import CRUD.KlijentiCRUD;
import CRUD.KozmeticariCRUD;
import CRUD.KozmetickaUslugaCRUD;
import CRUD.KozmetickiSalonCRUD;
import CRUD.MenadzeriCRUD;
import CRUD.RecepcioneriCRUD;
import CRUD.TipKozmetickogTretmanaCRUD;
import CRUD.ZakazanKozmetickiTretmanCRUD;
import korisniciSistema.Klijent;
import korisniciSistema.Kozmeticar;
import korisniciSistema.Menadzer;
import korisniciSistema.Recepcioner;
import kozmetickiSalon.KozmetickiSalon;
import uslugeTretmani.KozmetickaUslugaTretman;
import uslugeTretmani.TipKozmetickogTretmana;
import uslugeTretmani.ZakazanKozmetickiTretman;

public class OsvezavanjeTabelaMenadzer {
	
	String klijentiFile = ".//fajlovi/klijenti.csv";
	String kozmeticariFile = ".//fajlovi/kozmeticari.csv";
	String menadzeriFile = ".//fajlovi/menadzeri.csv";
	String salonFile = ".//fajlovi/saloni.csv";
	String recepcineriFile = ".//fajlovi/recepcioneri.csv";
	
	String uslugeFile = ".//fajlovi/usluge.csv";
	String tretmaniFile = ".//fajlovi/tretmani.csv";
	String zakazaniFile = ".//fajlovi/zakazani.csv";
	
	List<KozmetickiSalon> ks = new KozmetickiSalonCRUD(salonFile).getSaloni();
	
	HashMap<Integer, ZakazanKozmetickiTretman> zkt = new ZakazanKozmetickiTretmanCRUD(zakazaniFile).getRecnikZakazanih();

	public void osveziTabeluTretmana(JTable tabela) {
		HashMap<Integer, TipKozmetickogTretmana> tkt = new TipKozmetickogTretmanaCRUD(tretmaniFile).getRecnikTretmana();

        ((DefaultTableModel) tabela.getModel()).setRowCount(0);
		for (TipKozmetickogTretmana tretman: tkt.values()) {
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
			String vremeTrajanja = tretman.getVremeTrajanja().format(formatter);
			String[] red = {
				tretman.getNazivUsluge(),
				tretman.getNazivTretmana(),
				vremeTrajanja,
				Float.toString(tretman.getCena())};
			((DefaultTableModel) tabela.getModel()).addRow(red);
		}
	}
	
	public void osveziTabeluUsluga(JTable tabela) {
		List<KozmetickaUslugaTretman> kut = new KozmetickaUslugaCRUD(uslugeFile).getUsluge();

        ((DefaultTableModel) tabela.getModel()).setRowCount(0);
		for (KozmetickaUslugaTretman usluga: kut) {
			String[] red = {
				usluga.getNazivUsluge()};
			((DefaultTableModel) tabela.getModel()).addRow(red);
		}
	}

	public void osveziTabeluKlijenata(JTable tabela) {
		List<Klijent> listaKlijenata = new KlijentiCRUD(klijentiFile).getKlijenti();

        ((DefaultTableModel) tabela.getModel()).setRowCount(0);
		for (Klijent k : listaKlijenata) {
			Object [] red = {
					k.getKorisnickoIme(),
					k.getLozinka(),
					k.getIme(),
					k.getPrezime(),
					k.getPol(),
					k.getTelefon(),
					k.getAdresa(),
					k.getLojalitiKartica()};
			((DefaultTableModel) tabela.getModel()).addRow(red);
		}
	}

	public void osveziTabeluRecepcionera(JTable tabela) {
		List<Recepcioner> listaRecepcionera = new RecepcioneriCRUD(recepcineriFile).getRecepcioneri();

		((DefaultTableModel) tabela.getModel()).setRowCount(0);
		for (Recepcioner recepcioner : listaRecepcionera) {
			String[] red = {
					recepcioner.getKorisnickoIme(),
					recepcioner.getLozinka(),
					recepcioner.getIme(),
					recepcioner.getPrezime(),
					recepcioner.getPol(),
					recepcioner.getTelefon(),
					recepcioner.getAdresa(),
					Integer.toString(recepcioner.getStrucnaSprema()),
					Integer.toString(recepcioner.getStaz()),
					Double.toString(recepcioner.getOsnovnaPlata()),
					Double.toString(recepcioner.getPlata())};
			((DefaultTableModel) tabela.getModel()).addRow(red);
		}
	}

	public void osveziTabeluMenadzera(JTable tabela) {
		List<Menadzer> listaMenadzera = new MenadzeriCRUD(menadzeriFile).getMenadzeri();

		((DefaultTableModel) tabela.getModel()).setRowCount(0);
		for (Menadzer m : listaMenadzera) {
			String[] red = {
					m.getKorisnickoIme(),
					m.getLozinka(),
					m.getIme(),
					m.getPrezime(),
					m.getPol(),
					m.getTelefon(),
					m.getAdresa(),
					Integer.toString(m.getStrucnaSprema()),
					Integer.toString(m.getStaz()),
					Double.toString(m.getOsnovnaPlata()),
					Double.toString(m.getPlata())};
			((DefaultTableModel) tabela.getModel()).addRow(red);
		}
	}
	
	public void osveziTabeluKozmeticara(JTable tabela) {
		List<Kozmeticar> listaKozmeticara = new KozmeticariCRUD(kozmeticariFile).getKozmeticari();

		((DefaultTableModel) tabela.getModel()).setRowCount(0);
		for (Kozmeticar kozmeticar : listaKozmeticara) {
			List<KozmetickaUslugaTretman> tretmani = kozmeticar.getTretmani();
			String[] red = {
					kozmeticar.getKorisnickoIme(),
					kozmeticar.getLozinka(),
					kozmeticar.getIme(),
					kozmeticar.getPrezime(),
					kozmeticar.getPol(),
					kozmeticar.getTelefon(),
					kozmeticar.getAdresa(),
					Integer.toString(kozmeticar.getStrucnaSprema()),
					Integer.toString(kozmeticar.getStaz()),
					Double.toString(kozmeticar.getOsnovnaPlata()),
					Double.toString(kozmeticar.getPlata()),
					kozmeticar.tretmaniZaTabelu(tretmani)};
			((DefaultTableModel) tabela.getModel()).addRow(red);
		}
	}
}
