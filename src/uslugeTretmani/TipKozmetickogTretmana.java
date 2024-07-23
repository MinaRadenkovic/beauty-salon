package uslugeTretmani;

import java.time.LocalTime;

public class TipKozmetickogTretmana extends KozmetickaUslugaTretman{
	
	private String nazivTretmana;
	private LocalTime vremeTrajanja;
	private float cena;
	
	public TipKozmetickogTretmana(String nazivUsluge, String nazivTretmana, LocalTime vremeTrajanja, float cena) {
		super(nazivUsluge);
		this.nazivTretmana = nazivTretmana;
		this.vremeTrajanja = vremeTrajanja;
		this.cena = cena;
	}

	public float getCena() {
		return cena;
	}

	public void setCena(float cena) {
		this.cena = cena;
	}

	public String getNazivTretmana() {
		return nazivTretmana;
	}

	public void setNazivTretmana(String nazivTretmana) {
		this.nazivTretmana = nazivTretmana;
	}

	public LocalTime getVremeTrajanja() {
		return vremeTrajanja;
	}

	public void setVremeTrajanja(LocalTime vremeTrajanja) {
		this.vremeTrajanja = vremeTrajanja;
	}
	
	@Override
	public String toString() {
		return "Tip kozmetickog tretmana [naziv usluge=" + getNazivUsluge() + ", vreme_trajanja="+
				getVremeTrajanja() + ", naziv_tretmana=" + getNazivTretmana() + ", cena=" + getCena() + "]";
	}
	
	public String toFileString() {
		return getNazivUsluge()+"|"+getVremeTrajanja()+"|"+getNazivTretmana()+"|"+getCena();
	}
}
