package Testovi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import CRUD.TipKozmetickogTretmanaCRUD;
import uslugeTretmani.TipKozmetickogTretmana;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalTime;
import java.util.HashMap;

public class TipKozmetickogTretmanaCRUDTest {

    private static final String TEST_FILE_NAME = ".//fajlovi/testTretmani.csv";

    private TipKozmetickogTretmanaCRUD tretmaniCRUD;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUp() {
        tretmaniCRUD = new TipKozmetickogTretmanaCRUD(TEST_FILE_NAME);
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
    public void testAddTretman() {
        int initialSize = tretmaniCRUD.getRecnikTretmana().size();

        String nazivUsluge = "Usluga1";
        String nazivTretmana = "Tretman1";
        LocalTime vremeTrajanja = LocalTime.of(1, 30);
        float cena = 50.00f;

        tretmaniCRUD.add(nazivUsluge, nazivTretmana, vremeTrajanja, cena);

        int newSize = tretmaniCRUD.getRecnikTretmana().size();

        assertEquals(initialSize + 1, newSize);
    }

    @Test
    public void testEditTretman() {
        String nazivUsluge = "Usluga1";
        String nazivTretmana = "Tretman1";
        LocalTime vremeTrajanja = LocalTime.of(1, 30);
        float cena = 50.00f;

        tretmaniCRUD.add(nazivUsluge, nazivTretmana, vremeTrajanja, cena);

        int id = 1; 
        String editedNazivUsluge = "Usluga1 - edited";
        String editedNazivTretmana = "Tretman1 - edited";
        LocalTime editedVremeTrajanja = LocalTime.of(2, 0);
        float editedCena = 60.00f;

        tretmaniCRUD.edit(id, editedNazivUsluge, editedNazivTretmana, editedVremeTrajanja, editedCena);

        TipKozmetickogTretmana editedTretman = tretmaniCRUD.pronadjiTretmanPoId(id);
        assertEquals(editedNazivUsluge, editedTretman.getNazivUsluge());
        assertEquals(editedNazivTretmana, editedTretman.getNazivTretmana());
        assertEquals(editedVremeTrajanja, editedTretman.getVremeTrajanja());
        assertEquals(editedCena, editedTretman.getCena(), 0.0001);
    }

    @Test
    public void testRemoveTretman1() {
        String nazivUsluge = "Usluga1";
        String nazivTretmana = "Tretman1";
        LocalTime vremeTrajanja = LocalTime.of(1, 30);
        float cena = 50.00f;

        tretmaniCRUD.add(nazivUsluge, nazivTretmana, vremeTrajanja, cena);
        int initialSize = tretmaniCRUD.getRecnikTretmana().size();

        int id = 1; 
        tretmaniCRUD.remove1(id);

        int newSize = tretmaniCRUD.getRecnikTretmana().size();

        assertEquals(initialSize - 1, newSize);
    }

    @Test
    public void testRemoveTretman2() {
        String nazivUsluge = "Usluga1";
        String nazivTretmana = "Tretman1";
        LocalTime vremeTrajanja = LocalTime.of(1, 30);
        float cena = 50.00f;

        tretmaniCRUD.add(nazivUsluge, nazivTretmana, vremeTrajanja, cena);
        int initialSize = tretmaniCRUD.getRecnikTretmana().size();

        tretmaniCRUD.remove2(nazivUsluge);

        int newSize = tretmaniCRUD.getRecnikTretmana().size();

        assertEquals(initialSize - 1, newSize);
    }
    
    @Test
    public void testLoadData() {
        String testData = "1|masaza|00:45|relaks masaza|2000.0\r\n"
        		+ "2|masaza|01:15|sportska masaza|2500.0\r\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE_NAME))) {
            writer.write(testData);
        } catch (IOException e) {
            e.printStackTrace();
        };


        tretmaniCRUD.loadData();

        HashMap<Integer, TipKozmetickogTretmana> recnik = tretmaniCRUD.getRecnikTretmana();
        assertNotNull(recnik);
        assertEquals(2, recnik.size());
    }
    
    @Test
    public void testSaveData() {
        tretmaniCRUD = new TipKozmetickogTretmanaCRUD(TEST_FILE_NAME);
        String expectedContent =  "1|masaza|00:45|relaks masaza|2000.0\n"
        		+ "2|masaza|01:15|sportska masaza|2500.0\n";

		LocalTime vreme1 = LocalTime.of(0, 45);
		LocalTime vreme2 = LocalTime.of(1, 15);

        tretmaniCRUD.add("masaza", "relaks masaza", vreme1, 2000);
        tretmaniCRUD.add("masaza", "sportska masaza", vreme2, 2500);

        tretmaniCRUD.saveData();

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
