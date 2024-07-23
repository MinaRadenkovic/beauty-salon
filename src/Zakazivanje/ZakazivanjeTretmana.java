package Zakazivanje;
import korisniciSistema.Klijent;
import korisniciSistema.Kozmeticar;
import korisniciSistema.Recepcioner;
import kozmetickiSalon.KozmetickiSalon;
import uslugeTretmani.KozmetickaUslugaTretman;
import uslugeTretmani.TipKozmetickogTretmana;
import uslugeTretmani.ZakazanKozmetickiTretman;
import CRUD.KlijentiCRUD;
import CRUD.KozmetickiSalonCRUD;
import CRUD.ZakazanKozmetickiTretmanCRUD;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

public class ZakazivanjeTretmana {
	
	public boolean daLiMozeDaSeZakaze (String korisnickoImeKlijenta, List<Klijent> sviKlijenti, String prekoKogaCegaZakazuje, List<Recepcioner> recepcioneri, 
							String korisnickoImeKozmeticara, List<Kozmeticar> kozmeticari, 
							String nazivUsluge, List<KozmetickaUslugaTretman> dostupneUsluge, 
							String nazivTretmana, HashMap<Integer, TipKozmetickogTretmana> dostupniTretmani,
							LocalDate datumZakazanogTretmana, LocalTime vremeZakazanogTretmana, HashMap<Integer, ZakazanKozmetickiTretman> recnikVecZakazanih) {
		boolean zakazan = false;
		for(Klijent k: sviKlijenti) {
			if (k.getKorisnickoIme().equals(korisnickoImeKlijenta)) {
				zakazan = k.getKorisnickoIme().equals(korisnickoImeKlijenta);
			}
		}
		
		if (prekoKogaCegaZakazuje.toLowerCase().equals("online") && zakazan) {
			zakazan = prekoKogaCegaZakazuje.toLowerCase().equals("online") && zakazan;
		} else {
			for (Recepcioner r: recepcioneri) {
				if (zakazan && r.getKorisnickoIme().equals(prekoKogaCegaZakazuje)) {
					zakazan = zakazan && r.getKorisnickoIme().equals(prekoKogaCegaZakazuje);
				}
			}
		}
		if (korisnickoImeKozmeticara != null) {
			for (Kozmeticar koz: kozmeticari ) {
				if(zakazan && koz.getKorisnickoIme().equals(korisnickoImeKozmeticara)) {
					for (KozmetickaUslugaTretman kut: koz.getTretmani()) {
						if (kut.getNazivUsluge().equals(nazivUsluge)) {	//koz koji vrsi datu uslugu
							for (ZakazanKozmetickiTretman zkt: recnikVecZakazanih.values()) {
								if (koz.getKorisnickoIme().equals(zkt.getKorisnickoImeKozmeticara())) {
									if (datumZakazanogTretmana.equals(zkt.getDatumTretmana())) {
										LocalTime pocetakTretmana = zkt.getVremeTretmana(); 	
											//pocetakTretmana koji je vec zakazan
										int sati = zkt.getVremeTrajanja().getHour();
										int minuti = zkt.getVremeTrajanja().getMinute();

										int vremeTrajanjaTretmana = sati * 60 + minuti;
											
										LocalTime krajTretmana = pocetakTretmana.plusMinutes(vremeTrajanjaTretmana);
											// krajTretmana koji je vec zakazan
											
										LocalTime krajVremenaZakazanogTretmana = vremeZakazanogTretmana.plusMinutes(vremeTrajanjaTretmana);
											//kraj tretmana koji tek treba d asse zakaze

										if (krajVremenaZakazanogTretmana.isAfter(pocetakTretmana) && vremeZakazanogTretmana.isAfter(krajTretmana)) { 
											korisnickoImeKozmeticara = koz.getKorisnickoIme();
											zakazan = zakazan && koz.getKorisnickoIme().equals(korisnickoImeKozmeticara);
											return zakazan;
										} else {
											zakazan = false;
										}
									}
								}
							}
						}
					}
				}
			}
		} else {
			for (Kozmeticar koz: kozmeticari) {
				for (KozmetickaUslugaTretman kut: koz.getTretmani()) {
					if (kut.toString().equals(nazivUsluge)) {	//koz koji vrsi datu uslugu
						for (ZakazanKozmetickiTretman zkt: recnikVecZakazanih.values()) {
							if (koz.getKorisnickoIme().equals(zkt.getKorisnickoImeKozmeticara())) {
								if (datumZakazanogTretmana.equals(zkt.getDatumTretmana())) {
									LocalTime pocetakTretmana = zkt.getVremeTretmana(); 	
										//pocetakTretmana koji je vec zakazan
									int sati = zkt.getVremeTrajanja().getHour();
									int minuti = zkt.getVremeTrajanja().getMinute();

									int vremeTrajanjaTretmana = sati * 60 + minuti;
										
									LocalTime krajTretmana = pocetakTretmana.plusMinutes(vremeTrajanjaTretmana);
										// krajTretmana koji je vec zakazan
										
									LocalTime krajVremenaZakazanogTretmana = vremeZakazanogTretmana.plusMinutes(vremeTrajanjaTretmana);
										//kraj tretmana koji tek treba d asse zakaze

									if (krajVremenaZakazanogTretmana.isBefore(pocetakTretmana) && vremeZakazanogTretmana.isAfter(krajTretmana)) { 
										String kozmeticar = koz.getKorisnickoIme();
										zkt.setKorisnickoImeKozmeticara(kozmeticar);
										zakazan = true;
										break;
									} 	
								} else {
									String kozmeticar = koz.getKorisnickoIme();
									zkt.setKorisnickoImeKozmeticara(kozmeticar);
									zakazan = true;
									break;
								}
							}else {
								String kozmeticar = koz.getKorisnickoIme();
								zkt.setKorisnickoImeKozmeticara(kozmeticar);
								zakazan = true;
								break;
							}
						}
					}
				}
			}
		
		
		for (KozmetickaUslugaTretman kut: dostupneUsluge) {
			if (zakazan && kut.toString().equals(nazivUsluge)) {
				zakazan = zakazan && kut.toString().equals(nazivUsluge);
			}
		}
		
		for (TipKozmetickogTretmana tkt: dostupniTretmani.values()) {
			if (zakazan && tkt.getNazivTretmana().equals(nazivTretmana)) {
				zakazan = zakazan && tkt.getNazivTretmana().equals(nazivTretmana);
			}
		}
		
		for (ZakazanKozmetickiTretman zkt: recnikVecZakazanih.values()) {
			if (datumZakazanogTretmana.equals(zkt.getDatumTretmana())) {
				LocalTime pocetakTretmana = zkt.getVremeTretmana(); 	
					//pocetakTretmana koji je vec zakazan
				int sati = zkt.getVremeTrajanja().getHour();
				int minuti = zkt.getVremeTrajanja().getMinute();

				int vremeTrajanjaTretmana = sati * 60 + minuti;
					
				LocalTime krajTretmana = pocetakTretmana.plusMinutes(vremeTrajanjaTretmana);
					// krajTretmana koji je vec zakazan
					
				LocalTime krajVremenaZakazanogTretmana = vremeZakazanogTretmana.plusMinutes(vremeTrajanjaTretmana);
					//kraj tretmana koji tek treba d asse zakaze

				if (krajVremenaZakazanogTretmana.isBefore(pocetakTretmana) && vremeZakazanogTretmana.isAfter(krajTretmana)) { 
					zakazan = true;
				} else {
					zakazan = false;
				}
			}
		}}
		return zakazan;
	}
	
	public boolean zakazivanje(String korisnickoImeKlijenta, List<Klijent> sviKlijenti, String prekoKogaCegaZakazuje, List<Recepcioner> recepcioneri, 
							String korisnickoImeKozmeticara, List<Kozmeticar> kozmeticari, 
							String nazivUsluge, List<KozmetickaUslugaTretman> dostupneUsluge, 
							String nazivTretmana, HashMap<Integer, TipKozmetickogTretmana> dostupniTretmani,
							LocalDate datumZakazanogTretmana, LocalTime vremeZakazanogTretmana, HashMap<Integer, ZakazanKozmetickiTretman> recnikVecZakazanih) {
		
		if (daLiMozeDaSeZakaze(korisnickoImeKlijenta, sviKlijenti, prekoKogaCegaZakazuje, recepcioneri, korisnickoImeKozmeticara, kozmeticari, 
				nazivUsluge, dostupneUsluge, nazivTretmana, dostupniTretmani, datumZakazanogTretmana, vremeZakazanogTretmana, recnikVecZakazanih) == true) {
			StanjeZakazanogTretmana stanje = StanjeZakazanogTretmana.ZAKAZAN;
			String zakazaniFile = ".//fajlovi/zakazani.csv";
			ZakazanKozmetickiTretmanCRUD tretman = new ZakazanKozmetickiTretmanCRUD(zakazaniFile);
			
			for (TipKozmetickogTretmana tkt: dostupniTretmani.values()) {
				if (tkt.getNazivTretmana().equals(nazivTretmana)) {
					LocalTime vremeTrajanja = tkt.getVremeTrajanja();
					float cenaTretmana = tkt.getCena();
					for (Klijent klijent: sviKlijenti) {
						if (klijent.getLojalitiKartica() == true) {
							cenaTretmana *= 0.9;
						}
					}
					int id = 1000;
					for (int zkt: recnikVecZakazanih.keySet()) {
						if (id <= zkt) {
							id = zkt+ 1;
						}
					}

					if (korisnickoImeKozmeticara == null) {
						for (Kozmeticar koz: kozmeticari) {
							for (KozmetickaUslugaTretman kut: koz.getTretmani()) {
								if (kut.getNazivUsluge().equals(nazivUsluge)) {	//koz koji vrsi datu uslugu
									for (ZakazanKozmetickiTretman zkt: recnikVecZakazanih.values()) {
										if (koz.getKorisnickoIme().equals(zkt.getKorisnickoImeKozmeticara())) {
											if (datumZakazanogTretmana.equals(zkt.getDatumTretmana())) {
												LocalTime pocetakTretmana = zkt.getVremeTretmana(); 	
													//pocetakTretmana koji je vec zakazan
												int sati = zkt.getVremeTrajanja().getHour();
												int minuti = zkt.getVremeTrajanja().getMinute();

												int vremeTrajanjaTretmana = sati * 60 + minuti;
													
												LocalTime krajTretmana = pocetakTretmana.plusMinutes(vremeTrajanjaTretmana);
													// krajTretmana koji je vec zakazan
													
												LocalTime krajVremenaZakazanogTretmana = vremeZakazanogTretmana.plusMinutes(vremeTrajanjaTretmana);
													//kraj tretmana koji tek treba da se zakaze

												if (krajVremenaZakazanogTretmana.isBefore(pocetakTretmana) && vremeZakazanogTretmana.isAfter(krajTretmana)) { 
													korisnickoImeKozmeticara = koz.getKorisnickoIme();
													zkt.setKorisnickoImeKozmeticara(korisnickoImeKozmeticara);
													break;
												} 	
											} else {
												korisnickoImeKozmeticara = koz.getKorisnickoIme();
												zkt.setKorisnickoImeKozmeticara(korisnickoImeKozmeticara);
												break;
											}
										} else {
											korisnickoImeKozmeticara = koz.getKorisnickoIme();
											zkt.setKorisnickoImeKozmeticara(korisnickoImeKozmeticara);
											break;
										}
									}
								}
							}
						}
					}
					tretman.add(korisnickoImeKlijenta, prekoKogaCegaZakazuje, korisnickoImeKozmeticara, nazivUsluge, 
							nazivTretmana, datumZakazanogTretmana, vremeZakazanogTretmana, vremeTrajanja, cenaTretmana, stanje, id, recnikVecZakazanih);
					
					String klijentiFile = ".//fajlovi/klijenti.csv";
					KlijentiCRUD klijent = new KlijentiCRUD(klijentiFile);
					String salonFile = ".//fajlovi/saloni.csv";
					List<KozmetickiSalon> ks = new KozmetickiSalonCRUD(salonFile).getSaloni();
					String potrosnja = ks.get(0).getIznosLojalitiKartice();
					if (!potrosnja.equals("-")) {
						if (klijent.lojalitiKartica(korisnickoImeKlijenta, recnikVecZakazanih)) {
							for (Klijent k: sviKlijenti) {
								if (k.getKorisnickoIme().equals(korisnickoImeKlijenta)) {
									k.setLojalitiKartica(true);
									break;
								}
							}	
						}
					}
				}
			} 
			return true; 	
		} else {
			return false;
		}
	}
}
