package Testovi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import CRUD.ZakazanKozmetickiTretmanCRUD;
import Zakazivanje.StanjeZakazanogTretmana;
import uslugeTretmani.ZakazanKozmetickiTretman;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.io.*;

public class ZakazanKozmetickiTretmanCRUDTest {

    private static final String TEST_FILE_NAME = ".//fajlovi/testZakazani.csv";

    private ZakazanKozmetickiTretmanCRUD zakazaniCRUD;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUp() {
        zakazaniCRUD = new ZakazanKozmetickiTretmanCRUD(TEST_FILE_NAME);
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

    @Test
    public void testAddZakazanTretman() {
    	zakazaniCRUD.getRecnikZakazanih().clear();
        int initialSize = zakazaniCRUD.getRecnikZakazanih().size();

        String korisnickoImeKlijenta = "klijent1";
        String nacinZakazivanja = "online";
        String korisnickoImeKozmeticara = "kozmeticar1";
        String nazivUsluge = "usluga1";
        String nazivTretmana = "tretman1";
        LocalDate datumTretmana = LocalDate.now();
        LocalTime vremeTretmana = LocalTime.now();
        LocalTime vremeTrajanja = LocalTime.of(1, 0);
        float cenaTretmana = 50.00f;
        StanjeZakazanogTretmana stanje = StanjeZakazanogTretmana.ZAKAZAN;

        zakazaniCRUD.add(korisnickoImeKlijenta, nacinZakazivanja, korisnickoImeKozmeticara, nazivUsluge, nazivTretmana,
            datumTretmana, vremeTretmana, vremeTrajanja, cenaTretmana, stanje, 1, new HashMap<>());

        int newSize = zakazaniCRUD.getRecnikZakazanih().size();

        assertEquals(initialSize + 1, newSize);
    }

    @Test
    public void testEditZakazanTretman() {
        String korisnickoImeKlijenta = "klijent1";
        String nacinZakazivanja = "online";
        String korisnickoImeKozmeticara = "kozmeticar1";
        String nazivUsluge = "usluga1";
        String nazivTretmana = "tretman1";
        LocalDate datumTretmana = LocalDate.now();
        LocalTime vremeTretmana = LocalTime.now();
        LocalTime vremeTrajanja = LocalTime.of(1, 0);
        float cenaTretmana = 50.00f;
        StanjeZakazanogTretmana stanje = StanjeZakazanogTretmana.ZAKAZAN;

        zakazaniCRUD.add(korisnickoImeKlijenta, nacinZakazivanja, korisnickoImeKozmeticara, nazivUsluge, nazivTretmana,
            datumTretmana, vremeTretmana, vremeTrajanja, cenaTretmana, stanje, 1, new HashMap<>());

        int id = 1; 
        String editedNazivUsluge = "usluga1 - edited";
        String editedNazivTretmana = "tretman1 - edited";
        LocalTime editedVremeTrajanja = LocalTime.of(1, 30);
        StanjeZakazanogTretmana editedStanje = StanjeZakazanogTretmana.IZVRSEN;

        zakazaniCRUD.edit(id, editedNazivUsluge, editedNazivTretmana, editedVremeTrajanja, editedStanje,
            korisnickoImeKlijenta, korisnickoImeKozmeticara, nacinZakazivanja, cenaTretmana, datumTretmana, vremeTretmana);

        ZakazanKozmetickiTretman editedTretman = zakazaniCRUD.pronadjiTretmanPoId(id);
        assertEquals(editedNazivUsluge, editedTretman.getNazivUsluge());
        assertEquals(editedNazivTretmana, editedTretman.getNazivTretmana());
        assertEquals(editedVremeTrajanja, editedTretman.getVremeTrajanja());
        assertEquals(editedStanje, editedTretman.getStanje());
    }

    @Test
    public void testRemoveZakazanTretman() {
        String korisnickoImeKlijenta = "klijent1";
        String nacinZakazivanja = "online";
        String korisnickoImeKozmeticara = "kozmeticar1";
        String nazivUsluge = "usluga1";
        String nazivTretmana = "tretman1";
        LocalDate datumTretmana = LocalDate.now();
        LocalTime vremeTretmana = LocalTime.now();
        LocalTime vremeTrajanja = LocalTime.of(1, 0);
        float cenaTretmana = 50.00f;
        StanjeZakazanogTretmana stanje = StanjeZakazanogTretmana.ZAKAZAN;

        zakazaniCRUD.add(korisnickoImeKlijenta, nacinZakazivanja, korisnickoImeKozmeticara, nazivUsluge, nazivTretmana,
            datumTretmana, vremeTretmana, vremeTrajanja, cenaTretmana, stanje, 1, new HashMap<>());
        int initialSize = zakazaniCRUD.getRecnikZakazanih().size();

        zakazaniCRUD.remove(1);
        assertEquals(zakazaniCRUD.getRecnikZakazanih().size(), initialSize - 1);
    }
    
    @Test
    public void testLoadData() {

        String testData = "1000|manikir|francuski manikir|milicamilic|jovanajovanovic|1500.0|2023-09-10|18:00|00:50|online|ZAKAZAN\n"+
        		"1001|pedikir|spa pedikir|milicamilic|zikazikic|1600.0|2023-09-11|09:00|00:45|peraperic|ZAKAZAN\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE_NAME))) {
            writer.write(testData);
        } catch (IOException e) {
            e.printStackTrace();
        };


        zakazaniCRUD.loadData();

        HashMap<Integer, ZakazanKozmetickiTretman> recnik = zakazaniCRUD.getRecnikZakazanih();
        assertNotNull(recnik);
    }
    
    @Test
    public void testSaveData() {
        zakazaniCRUD = new ZakazanKozmetickiTretmanCRUD(TEST_FILE_NAME);

        String expectedContent =  "1000|manikir|francuski manikir|milicamilic|jovanajovanovic|1500.0|2023-09-10|18:00|00:50|online|ZAKAZAN\n"+
        		"1001|pedikir|spa pedikir|milicamilic|zikazikic|1600.0|2023-09-11|09:00|00:45|peraperic|ZAKAZAN\n";

        HashMap<Integer, ZakazanKozmetickiTretman> hashmapa = zakazaniCRUD.getRecnikZakazanih();
		LocalTime vreme1 = LocalTime.of(0, 45);
		LocalTime vreme2 = LocalTime.of(1, 15);
		LocalTime vreme3 = LocalTime.of(9, 00);
		LocalTime vreme4 = LocalTime.of(0, 45);
		LocalDate datum1 = LocalDate.of(2023, 9, 10);
		LocalDate datum2 = LocalDate.of(2023, 9, 11);

        zakazaniCRUD.add("milicamilic", "online", "jovanajovanovic", "manikir", "francuski manikir", datum1, vreme1, vreme2, 1500, StanjeZakazanogTretmana.ZAKAZAN, 1000, hashmapa);
        zakazaniCRUD.add("milicamilic", "peraperic", "zikazikic", "panikir", "spa pedikir", datum2, vreme3, vreme4, 1600, StanjeZakazanogTretmana.ZAKAZAN, 1001, hashmapa);

        zakazaniCRUD.saveData();

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
