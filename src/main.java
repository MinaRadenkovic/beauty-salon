import java.awt.EventQueue;
import gui.logIN;

public class main {
	
	public static void main (String[] args) {	
		System.out.println("---------------------------------------");
		System.out.println("SV76-2022");
		System.out.println("Mina Radenković");
		System.out.println("---------------------------------------");
		System.out.println("Projektni zadatak");
		System.out.println("Objektno orijentisano programiranje 1");
		System.out.println("---------------------------------------");

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					logIN frame = new logIN();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
//		String klijentiFile = ".//fajlovi/klijenti.csv";
//		String kozmeticariFile = ".//fajlovi/kozmeticari.csv";
//		String menadzeriFile = ".//fajlovi/menadzeri.csv";
//		String salonFile = ".//fajlovi/saloni.csv";
//		String recepcineriFile = ".//fajlovi/recepcioneri.csv";
//		
//		String uslugeFile = ".//fajlovi/usluge.csv";
//		String tretmaniFile = ".//fajlovi/tretmani.csv";
//		String zakazaniFile = ".//fajlovi/zakazani.csv";
//		
//		KlijentiCRUD k = new KlijentiCRUD(klijentiFile);
//		KozmeticariCRUD koz = new KozmeticariCRUD(kozmeticariFile);
//		MenadzeriCRUD m = new MenadzeriCRUD(menadzeriFile);
//		RecepcioneriCRUD r = new RecepcioneriCRUD(recepcineriFile);
//		KozmetickiSalonCRUD ks = new KozmetickiSalonCRUD(salonFile);
//		
//		KozmetickaUslugaCRUD kut = new KozmetickaUslugaCRUD(uslugeFile);
//		TipKozmetickogTretmanaCRUD tkt = new TipKozmetickogTretmanaCRUD(tretmaniFile);
//		ZakazanKozmetickiTretmanCRUD zkt = new ZakazanKozmetickiTretmanCRUD(zakazaniFile);
//		
//		ZakazivanjeTretmana zakazivanjeTretmana = new ZakazivanjeTretmana();	
//		zkt.loadData();		
//		LocalTime pocetak = LocalTime.of(8, 0);
//		LocalTime kraj = LocalTime.of(20, 0);
//		ks.add("Moj salon", pocetak, kraj);
//		ks.readData();
//		
//        m.add("nikolanikolic", "menadzer", "nikola", "nikolic", "musko", "123456789", "adresa", 3, 15, 1000);	//minimalne(pocetne) plate postavljene 
//        r.add("peraperic", "recepcioner", "pera", "peric", "musko", "123456789", "adresa", 2, 8, 400);
//        
//        koz.add("simasimic", "kozmeticar", "sima", "simic", "musko", "1", "adresa1", 2, 3, 500, null);
//        koz.add("zikazikic", "kozmeticar", "zika", "zikic", "musko", "2", "adresa2", 2, 4, 500, null);
//        koz.add("jadrankajovanovic", "kozmeticar", "jadranka", "jovanovic", "zensko", "3", "adresa3", 1, 3, 500, null);
//
////        k.add("milicamilic", "klijent", "milica", "milic", "zensko", "1010", "adresa klijenta", false);
////        k.add("mikamikic", "klijent", "mika", "mikic", "zensko", "1010", "adresa klijenta", false);
//
//        System.out.println();
//        m.readData();
//        r.readData();
//        koz.readData();
////        k.readData();
//
//        koz.edit("jadrankajovanovic", "kozmeticar", "jovana", "jovanovic", "zensko", "2", "adresa3", 1, 3, 200, null);
//	    m.readData();
//	    r.readData();
//	    koz.readData();
////	    k.readData();
//	    System.out.println();
//	    koz.remove("zikazikic");
//	    m.readData();
//	    r.readData();
//	    koz.readData();
////	    k.readData();
//	    System.out.println();
//	    
//	    kut.add("masaza");
//	    kut.add("manikir");
//	    kut.add("pedikir");
//	    	    
//	    tkt.add("masaza", "relaks masaza", LocalTime.of(0, 45), 2000);
//	    tkt.add("masaza", "sportska masaza", LocalTime.of(1, 15), 2500);
//	    tkt.add("manikir", "francuski manikir", LocalTime.of(0, 55), 1500);
//	    tkt.add("manikir", "gel lak", LocalTime.of(1, 20), 1600);
//	    tkt.add("manikir", "spa manikir", LocalTime.of(1, 30), 2000);
//	    tkt.add("masaza", "spa pedikir", LocalTime.of(0, 45), 1600);
//	    
//	    kut.readData();
//	    tkt.readData();
//	    System.out.println();
//	    
//	    tkt.edit(2, "manikir", "francuski manikir", LocalTime.of(0, 50), 1500);
//	    tkt.edit(5, "pedikir", "spa pedikir", LocalTime.of(0, 45), 1600);
//	    
//	    kut.readData();
//	    tkt.readData();
//	    System.out.println();
//	    
////	    kut.remove("pedikir");
////	    tkt.remove2("pedikir");
//	    
//	    kut.readData();
//	    tkt.readData();
//	    System.out.println();
//	    
//	    koz.edit("simasimic", "kozmeticar", "sima", "simic", "musko", "1", "adresa1", 2, 3, 200, "masaza;manikir");
//	    koz.edit("jadrankajovanovic", "kozmeticar", "jovana", "jovanovic", "zensko", "2", "adresa3", 1, 3, 200, "manikir");
//	    koz.edit("zikazikic", "kozmeticar", "zika", "zikic", "musko", "2", "adresa2", 2, 4, 200, "masaza;manikir;pedikir");
//	    
//	    koz.readData();
//	    System.out.println();
//	    
////	    zkt.add("masaza", "relaks masaza", LocalTime.of(0, 45), 1, "milicamilic", "simasimic", 2000, LocalDate.of(2023, 10, 10));
////	    zkt.add("manikir", "gel lak", LocalTime.of(1, 20), 4, "mikamikic", "simasimic", 1600, LocalDate.of(2023, 7, 5));
////	    zkt.add("manikir", "spa manikir", LocalTime.of(1, 30), 5, "mikamikic", "jadrankajovanovic", 2000, LocalDate.of(2023, 7, 5));
//	    
//	    zkt.readData();
//	    System.out.println();
//	    
////	    zkt.edit(1001, "manikir", "francuski manikir", LocalTime.of(1, 20), 4, "mikamikic", "simasimic", 1600, LocalDate.of(2023, 7, 5));
//	    zkt.readData();
//	    System.out.println();
//	    
//	    tkt.readData();
//	    System.out.println();
//	    
////	    tkt.edit(2, "masaza", "relaks masaza", LocalTime.of(0, 45), 1700);
//	    
//	    tkt.readData();
//	    zkt.readData();	 
//	    
//	    m.readData();
//	    r.readData();
//	    koz.readData();
////	    k.readData();
//	    kut.readData();
//	    tkt.readData();
//	    
//	    zakazivanjeTretmana.zakazivanje("milicamilic", k.getKlijenti(), "online", r.getRecepcioneri(), "jadrankajovanovic", koz.getKozmeticari(), "manikir", 
//	    		kut.getUsluge(), "francuski manikir", tkt.getRecnikTretmana(), LocalDate.of(2023, 6, 10), LocalTime.of(18, 0), zkt.getRecnikZakazanih());
//	    
//	    zakazivanjeTretmana.zakazivanje("milicamilic", k.getKlijenti(), "peraperic", r.getRecepcioneri(), "zikazikic", koz.getKozmeticari(), "pedikir", 
//	    		kut.getUsluge(), "spa pedikir", tkt.getRecnikTretmana(), LocalDate.of(2023, 6, 11), LocalTime.of(9, 0), zkt.getRecnikZakazanih());
//	    
//	    zakazivanjeTretmana.zakazivanje("mikamikic", k.getKlijenti(), "online", r.getRecepcioneri(), "simasimic", koz.getKozmeticari(), "masaza", 
//	    		kut.getUsluge(), "sportska masaza", tkt.getRecnikTretmana(), LocalDate.of(2023, 6, 12), LocalTime.of(8, 0), zkt.getRecnikZakazanih());
//	
//	    zakazivanjeTretmana.zakazivanje("mikamikic", k.getKlijenti(), "peraperic", r.getRecepcioneri(), "zikazikic", koz.getKozmeticari(), "masaza", 
//	    		kut.getUsluge(), "relaks masaza", tkt.getRecnikTretmana(), LocalDate.of(2023, 6, 13), LocalTime.of(19, 0), zkt.getRecnikZakazanih());
//	
//	    zakazivanjeTretmana.zakazivanje("mikamikic", k.getKlijenti(), "peraperic", r.getRecepcioneri(), "jadrankajovanovic", koz.getKozmeticari(), "manikir", 
//	    		kut.getUsluge(), "francuski manikir", tkt.getRecnikTretmana(), LocalDate.of(2023, 6, 10), LocalTime.of(18, 0), zkt.getRecnikZakazanih());
//	
//		PrikazTretmanaPoKozmeticaru prikazTretmanaPoKozmeticaru = new PrikazTretmanaPoKozmeticaru("zikazikic", zkt.getRecnikZakazanih());
//
//		m.setLojalitiKartica(3500);
//		
//		Izvrsen izvrsentretman = new Izvrsen();
//		izvrsentretman.izvrsenTretman(1000, zkt);
//		zkt.readData();
////		System.out.println("Stanje: " + Integer.toString(k.stanjeNaKartici("milicamilic", zkt.getRecnikZakazanih())));
//		System.out.println("Bilans novca salona: " + Integer.toString(ks.bilansNovcaSalona(zkt.getRecnikZakazanih())));
//
//		KlijentOtkazao klijentOtkazaoTretman = new KlijentOtkazao();
//		klijentOtkazaoTretman.klijentOtkazao(1001, zkt);		
//		zkt.readData();
////		System.out.println("Stanje: " + Integer.toString(k.stanjeNaKartici("milicamilic", zkt.getRecnikZakazanih())));
//		System.out.println("Bilans novca salona: " + Integer.toString(ks.bilansNovcaSalona(zkt.getRecnikZakazanih())));
//
//		SalonOtkazao salonOtkazaoTretman = new SalonOtkazao();
//		salonOtkazaoTretman.salonOtkazao(1002, zkt);
//		zkt.readData();
////		System.out.println("Stanje: " + Integer.toString(k.stanjeNaKartici("mikamikic", zkt.getRecnikZakazanih())));
//		System.out.println("Bilans novca salona: " + Integer.toString(ks.bilansNovcaSalona(zkt.getRecnikZakazanih())));
//
//		NijeSePojavio nijeSePojavioTretman = new NijeSePojavio();
//		nijeSePojavioTretman.nijeSePojavio(1003, zkt);
//		zkt.readData();
////		System.out.println("Stanje: " + Integer.toString(k.stanjeNaKartici("mikamikic", zkt.getRecnikZakazanih())));
//		System.out.println("Bilans novca salona: " + Integer.toString(ks.bilansNovcaSalona(zkt.getRecnikZakazanih())));
//
//	    zakazivanjeTretmana.zakazivanje("milicamilic", k.getKlijenti(), "online", r.getRecepcioneri(), "simasimic", koz.getKozmeticari(), "manikir", 
//	    		kut.getUsluge(), "gel lak", tkt.getRecnikTretmana(), LocalDate.of(2023, 6, 14), LocalTime.of(9, 0), zkt.getRecnikZakazanih());
//	    
//	    zakazivanjeTretmana.zakazivanje("mikamikic", k.getKlijenti(), "online", r.getRecepcioneri(), null, koz.getKozmeticari(), "manikir", 
//	    		kut.getUsluge(), "spa manikir", tkt.getRecnikTretmana(), LocalDate.of(2023, 6, 14), LocalTime.of(9, 0), zkt.getRecnikZakazanih());
//	    
//	    zkt.readData();
////		System.out.println("Stanje: " + Integer.toString(k.stanjeNaKartici("mikamikic", zkt.getRecnikZakazanih())));
//		System.out.println("Bilans novca salona: " + Integer.toString(ks.bilansNovcaSalona(zkt.getRecnikZakazanih())));
//	    
//	    IzvestajOKlijentu izvestaj = new IzvestajOKlijentu();
//	    izvestaj.klijentIzvestaj("mikamikic", zkt.getRecnikZakazanih(), k.getKlijenti());
//	    
//		System.out.println("Prihod: " + Integer.toString(ks.bilansNovcaSalona(zkt.getRecnikZakazanih())));
//		PrihodiIRashodi rashod = new PrihodiIRashodi();
//		System.out.println("Rashod: " + Double.toString(rashod.rashodi(m.getMenadzeri(), koz.getKozmeticari(), r.getRecepcioneri(), zkt.getRecnikZakazanih())));    
	}
}
