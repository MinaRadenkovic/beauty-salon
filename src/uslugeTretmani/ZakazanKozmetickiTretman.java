package uslugeTretmani;

import java.time.LocalTime;
import java.time.LocalDate;
import Zakazivanje.StanjeZakazanogTretmana;

public class ZakazanKozmetickiTretman extends TipKozmetickogTretmana{
	
	private String korisnickoImeKlijenta;
	private String korisnickoImeKozmeticara;
	private float cenaTretmana;
	private LocalDate datumTretmana;
	private LocalTime vremeTretmana;
	private String nacinZakazivanja;
	private int idZakazanogTretmana;
	private StanjeZakazanogTretmana stanje;

	public ZakazanKozmetickiTretman(String nazivUsluge, String nazivTretmana, LocalTime vremeTrajanja,
			String korisnickoImeKlijenta, String korisnickoImeKozmeticara, String nacinZakazivanja, float cenaTretmana, 
			LocalDate datumTretmana, LocalTime vremeTretmana, StanjeZakazanogTretmana stanje, int id) {
		super(nazivUsluge, nazivTretmana, vremeTrajanja, cenaTretmana);
		this.cenaTretmana = cenaTretmana;
		this.datumTretmana = datumTretmana;
		this.korisnickoImeKlijenta = korisnickoImeKlijenta;
		this.korisnickoImeKozmeticara = korisnickoImeKozmeticara;
		this.vremeTretmana = vremeTretmana;
		this.nacinZakazivanja = nacinZakazivanja;
		this.stanje = stanje;
		this.idZakazanogTretmana = id;
	}
	
	public int getIdZakazanogTretmana() {
		return idZakazanogTretmana;
	}

	public void setIdZakazanogTretmana(int idZakazanogTretmana) {
		this.idZakazanogTretmana = idZakazanogTretmana;
	}

	public StanjeZakazanogTretmana getStanje() {
		return stanje;
	}

	public void setStanje(StanjeZakazanogTretmana stanje) {
		this.stanje = stanje;
	}

	public String getNacinZakazivanja() {
		return nacinZakazivanja;
	}

	public void setNacinZakazivanja(String nacinZakazivanja) {
		this.nacinZakazivanja = nacinZakazivanja;
	}

	public LocalTime getVremeTretmana() {
		return vremeTretmana;
	}

	public void setVremeTretmana(LocalTime vremeTretmana) {
		this.vremeTretmana = vremeTretmana;
	}

	public String getKorisnickoImeKlijenta() {
		return korisnickoImeKlijenta;
	}

	public void setKorisnickoImeKlijenta(String korisnickoImeKlijenta) {
		this.korisnickoImeKlijenta = korisnickoImeKlijenta;
	}

	public String getKorisnickoImeKozmeticara() {
		return korisnickoImeKozmeticara;
	}

	public void setKorisnickoImeKozmeticara(String korisnickoImeKozmeticara) {
		this.korisnickoImeKozmeticara = korisnickoImeKozmeticara;
	}

	public float getCenaTretmana() {
		return cenaTretmana;
	}

	public void setCenaTretmana(int cenaTretmana) {
		this.cenaTretmana = cenaTretmana;
	}

	public LocalDate getDatumTretmana() {
		return datumTretmana;
	}

	public void setDatumTretmana(LocalDate datumTretmana) {
		this.datumTretmana = datumTretmana;
	}
	
	@Override
	public String toString() {
		return "Zakazan kozmeticki tretman [naziv_usluge=" + getNazivUsluge() + ", naziv_tretmana=" + getNazivTretmana() 
				+ ", vreme_trajanja=" + getVremeTrajanja() + ", cena=" + getCenaTretmana() +
				", datum=" + getDatumTretmana() + ", vreme=" + getVremeTretmana() + ", klijent=" + getKorisnickoImeKlijenta() + ", kozmeticar="
				+ getKorisnickoImeKozmeticara() + ", nacin_zakazivanja=" + getNacinZakazivanja() + ", stanje=" + getStanje()+ "]";
	}
	
	public String toFileString() {
		return getIdZakazanogTretmana()+"|"+getNazivUsluge()+"|"+getNazivTretmana()+"|"+getKorisnickoImeKlijenta()+"|"+getKorisnickoImeKozmeticara()
				+"|"+getCenaTretmana()+"|"+getDatumTretmana()+"|"+getVremeTretmana()+"|"+getVremeTrajanja()+"|"+getNacinZakazivanja()+"|"+getStanje();
	}
}
