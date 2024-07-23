package kozmetickiSalon;

import java.time.LocalTime;
import java.time.LocalDate;

public class KozmetickiSalon {

		private String naziv;
		private LocalTime pocetakRadnogVremena, krajRadnogVremena;
		private String iznosLojalitiKartice;
		private String brojTretmanaZaBonus;
		private LocalDate datumPocetkaRacunanjaPrihodaZaBonus, datumKrajaRacunanjaPrihodaZaBonus;
		
		public String getIznosLojalitiKartice() {
			return iznosLojalitiKartice;
		}

		public void setIznosLojalitiKartice(String iznosLojalitiKartice) {
			this.iznosLojalitiKartice = iznosLojalitiKartice;
		}

		public String getBrojTretmanaZaBonus() {
			return brojTretmanaZaBonus;
		}

		public void setBrojTretmanaZaBonus(String brojTretmanaZaBonus) {
			this.brojTretmanaZaBonus = brojTretmanaZaBonus;
		}

		public String getOstvarenPrihodZaBonus() {
			return ostvarenPrihodZaBonus;
		}

		public void setOstvarenPrihodZaBonus(String ostvarenPrihodZaBonus) {
			this.ostvarenPrihodZaBonus = ostvarenPrihodZaBonus;
		}

		private String ostvarenPrihodZaBonus;
		
		public KozmetickiSalon(String naziv, LocalTime pocetakRadnogVremena, LocalTime krajRadnogVremena, 
				String iznosLojalitiKartice, String brojTretmanaZaBonus, String ostvarenPrihodZaBonus, LocalDate datumPocetkaRacunanjaPrihodaZaBonus, LocalDate datumKrajaRacunanjaPrihodaZaBonus) {
			this.naziv = naziv;
			this.pocetakRadnogVremena = pocetakRadnogVremena;
			this.krajRadnogVremena = krajRadnogVremena;
			this.brojTretmanaZaBonus = brojTretmanaZaBonus;
			this.iznosLojalitiKartice = iznosLojalitiKartice;
			this.ostvarenPrihodZaBonus = ostvarenPrihodZaBonus;
			this.datumPocetkaRacunanjaPrihodaZaBonus = datumPocetkaRacunanjaPrihodaZaBonus;
			this.datumKrajaRacunanjaPrihodaZaBonus = datumKrajaRacunanjaPrihodaZaBonus;
		}

		public String getNaziv() {
			return naziv;
		}

		public void setNaziv(String naziv) {
			this.naziv = naziv;
		}

		public LocalTime getPocetakRadnogVremena() {
			return pocetakRadnogVremena;
		}

		public void setPocetakRadnogVremena(LocalTime pocetakRadnogVremena) {
			this.pocetakRadnogVremena = pocetakRadnogVremena;
		}

		public LocalTime getKrajRadnogVremena() {
			return krajRadnogVremena;
		}

		public void setKrajRadnogVremena(LocalTime krajRadnogVremena) {
			this.krajRadnogVremena = krajRadnogVremena;
		}

		@Override
		public String toString() {
			return "Salon [naziv=" + getNaziv() + ", pocetak_radnog_vremena=" + getPocetakRadnogVremena() + 
					", kraj_radnog_vremena=" + getKrajRadnogVremena() + "]";
		}
		
		public String toFileString() {
			return getNaziv()+"|"+getPocetakRadnogVremena()+"|"+getKrajRadnogVremena()+"|"+getIznosLojalitiKartice()+"|"+getBrojTretmanaZaBonus()+"|"+
					getOstvarenPrihodZaBonus()+"|"+getDatumPocetkaRacunanjaPrihodaZaBonus()+"|"+getDatumKrajaRacunanjaPrihodaZaBonus();
		}

		public LocalDate getDatumPocetkaRacunanjaPrihodaZaBonus() {
			return datumPocetkaRacunanjaPrihodaZaBonus;
		}

		public void setDatumPocetkaRacunanjaPrihodaZaBonus(LocalDate datumPocetkaRacunanjaPrihodaZaBonus) {
			this.datumPocetkaRacunanjaPrihodaZaBonus = datumPocetkaRacunanjaPrihodaZaBonus;
		}

		public LocalDate getDatumKrajaRacunanjaPrihodaZaBonus() {
			return datumKrajaRacunanjaPrihodaZaBonus;
		}

		public void setDatumKrajaRacunanjaPrihodaZaBonus(LocalDate datumKrajaRacunanjaPrihodaZaBonus) {
			this.datumKrajaRacunanjaPrihodaZaBonus = datumKrajaRacunanjaPrihodaZaBonus;
		}	
}
