package Testovi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import CRUD.KozmetickiSalonCRUD;
import kozmetickiSalon.KozmetickiSalon;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class KozmetickiSalonCRUDTest {

	private static final String TEST_FILE_NAME = ".//fajlovi/testSaloni.csv";

	private KozmetickiSalonCRUD saloniCRUD;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
	
    
    @Before
    public void setUp() {
        saloniCRUD = new KozmetickiSalonCRUD(TEST_FILE_NAME);
    }
    
    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setIn(originalIn);
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
    
    @After
    public void finished() {
        File zaBrisanje = new File(TEST_FILE_NAME);
        
        try {
            FileWriter writer = new FileWriter(zaBrisanje);
            writer.write("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testAddSalon() {
        int initialSize = saloniCRUD.getSaloni().size();

        String naziv = "Salon1";
        LocalTime pocetakRadnogVremena = LocalTime.of(9, 0);
        LocalTime krajRadnogVremena = LocalTime.of(18, 0);
        String iznosLojalitiKartice = "50.00";
        String brojTretmanaZaBonus = "10";
        String ostvarenPrihodZaBonus = "1000.00";
        LocalDate datumPocetkaRacunanjaPrihodaZaBonus = LocalDate.of(2023, 1, 1);
        LocalDate datumKrajaRacunanjaPrihodaZaBonus = LocalDate.of(2023, 12, 31);

        saloniCRUD.add(naziv, pocetakRadnogVremena, krajRadnogVremena, iznosLojalitiKartice, brojTretmanaZaBonus, ostvarenPrihodZaBonus,
                datumPocetkaRacunanjaPrihodaZaBonus, datumKrajaRacunanjaPrihodaZaBonus);

        int newSize = saloniCRUD.getSaloni().size();

        assertEquals(initialSize + 1, newSize);
    }
    
    @Test
    public void testEditSalon() {
        String initialNaziv = "Salon1";
        LocalTime initialPocetakRadnogVremena = LocalTime.of(9, 0);
        LocalTime initialKrajRadnogVremena = LocalTime.of(18, 0);
        String initialIznosLojalitiKartice = "50.00";
        String initialBrojTretmanaZaBonus = "10";
        String initialOstvarenPrihodZaBonus = "1000.00";
        LocalDate initialDatumPocetkaRacunanjaPrihodaZaBonus = LocalDate.of(2023, 1, 1);
        LocalDate initialDatumKrajaRacunanjaPrihodaZaBonus = LocalDate.of(2023, 12, 31);

        saloniCRUD.add(initialNaziv, initialPocetakRadnogVremena, initialKrajRadnogVremena, initialIznosLojalitiKartice,
                initialBrojTretmanaZaBonus, initialOstvarenPrihodZaBonus, initialDatumPocetkaRacunanjaPrihodaZaBonus,
                initialDatumKrajaRacunanjaPrihodaZaBonus);

        String editedNaziv = "Salon1 - edited";
        LocalTime editedPocetakRadnogVremena = LocalTime.of(10, 0);
        LocalTime editedKrajRadnogVremena = LocalTime.of(19, 0);
        String editedIznosLojalitiKartice = "60.00";
        String editedBrojTretmanaZaBonus = "15";
        String editedOstvarenPrihodZaBonus = "1200.00";
        LocalDate editedDatumPocetkaRacunanjaPrihodaZaBonus = LocalDate.of(2023, 2, 1);
        LocalDate editedDatumKrajaRacunanjaPrihodaZaBonus = LocalDate.of(2023, 11, 30);

        saloniCRUD.edit(editedNaziv, editedPocetakRadnogVremena, editedKrajRadnogVremena, editedIznosLojalitiKartice,
                editedBrojTretmanaZaBonus, editedOstvarenPrihodZaBonus, editedDatumPocetkaRacunanjaPrihodaZaBonus,
                editedDatumKrajaRacunanjaPrihodaZaBonus);

        KozmetickiSalon editedSalon = saloniCRUD.getSaloni().get(0);
        assertEquals(editedNaziv, editedSalon.getNaziv());
        assertEquals(editedPocetakRadnogVremena, editedSalon.getPocetakRadnogVremena());
        assertEquals(editedKrajRadnogVremena, editedSalon.getKrajRadnogVremena());
        assertEquals(editedIznosLojalitiKartice, editedSalon.getIznosLojalitiKartice());
        assertEquals(editedBrojTretmanaZaBonus, editedSalon.getBrojTretmanaZaBonus());
        assertEquals(editedOstvarenPrihodZaBonus, editedSalon.getOstvarenPrihodZaBonus());
        assertEquals(editedDatumPocetkaRacunanjaPrihodaZaBonus, editedSalon.getDatumPocetkaRacunanjaPrihodaZaBonus());
        assertEquals(editedDatumKrajaRacunanjaPrihodaZaBonus, editedSalon.getDatumKrajaRacunanjaPrihodaZaBonus());
    }
    
    @Test
    public void testLoadData() {
        String testData = "Naziv|09:00|19:00|3500|1|-|2023-08-08|2023-08-31\n" +
                          "Salon|10:00|17:00|-|1|-|2023-08-01|2023-09-01\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE_NAME))) {
            writer.write(testData);
            System.out.println("Podaci su uspešno upisani u fajl.");
        } catch (IOException e) {
            e.printStackTrace();
        };


        saloniCRUD.loadData();

        List<KozmetickiSalon> kozmeticari = saloniCRUD.getSaloni();
        assertNotNull(kozmeticari);
        assertEquals(2, kozmeticari.size());
    }
    
    @Test
    public void testSaveData() {
        saloniCRUD = new KozmetickiSalonCRUD(TEST_FILE_NAME);
        String expectedContent = "Naziv|09:00|19:00|3500|1|-|2023-08-08|2023-08-31\n" +
        						 "Salon|10:00|17:00|-|1|-|2023-08-01|2023-09-01\n";

		LocalTime pocetak1 = LocalTime.of(9, 0);
		LocalTime kraj1 = LocalTime.of(19, 0);
		LocalTime pocetak2 = LocalTime.of(10, 0);
		LocalTime kraj2 = LocalTime.of(17, 0);
		LocalDate datum1 = LocalDate.of(2023, 8, 8);
		LocalDate datum2 = LocalDate.of(2023, 8, 31);
		LocalDate datum3 = LocalDate.of(2023, 8, 1);
		LocalDate datum4 = LocalDate.of(2023, 9, 1);
		
        saloniCRUD.add("Naziv", pocetak1, kraj1, "3500", "1", "-", datum1, datum2);
        saloniCRUD.add("Salon", pocetak2, kraj2, "-", "1", "-", datum3, datum4);

        saloniCRUD.saveData();

        String actualContent = readFromFile(TEST_FILE_NAME);

        assertEquals(expectedContent, actualContent);
    }
    
    private String readFromFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n"); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
    

    
}
