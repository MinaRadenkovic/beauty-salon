package izvestaji;

import java.util.HashMap;
import java.util.List;

import korisniciSistema.Kozmeticar;
import korisniciSistema.Menadzer;
import korisniciSistema.Recepcioner;
import uslugeTretmani.ZakazanKozmetickiTretman;

public class PrihodiIRashodi {

		public void prihodi() {
			//prihod je zapravo bilans novca salona iz KozmetickiSalonCRUD
		}
		
		public double rashodi (List<Menadzer> menadzeri, List<Kozmeticar> kozmeticari, List<Recepcioner> recepcioneri, HashMap<Integer, ZakazanKozmetickiTretman> recnikTretmana) {
			double rashod = 0;
//			PlateIBonusi plata = new PlateIBonusi();
//
//			for (Menadzer m: menadzeri) {
//				double p = plata.plate(m.getKorisnickoIme(), menadzeri, kozmeticari, recepcioneri, recnikTretmana);
//				m.setPlata(p);
//				rashod += m.getPlata();
//			}
//			for (Kozmeticar koz: kozmeticari) {
//				double p = plata.plate(koz.getKorisnickoIme(), menadzeri, kozmeticari, recepcioneri, recnikTretmana);
//				koz.setPlata(p);
//				rashod += koz.getPlata();
//			}
//			for (Recepcioner r: recepcioneri) {
//				double p = plata.plate(r.getKorisnickoIme(), menadzeri, kozmeticari, recepcioneri, recnikTretmana);
//				r.setPlata(p);
//				rashod += r.getPlata();
//			}
			return rashod;
		}
}
