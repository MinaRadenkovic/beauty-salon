package Testovi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.*;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

import CRUD.KozmeticariCRUD;
import korisniciSistema.Kozmeticar;
import uslugeTretmani.KozmetickaUslugaTretman;

public class KozmeticariCRUDTest {

	private static final String TEST_FILE_NAME = ".//fajlovi/testKozmeticari.csv";

    private KozmeticariCRUD kozmeticariCRUD;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUp() {
        kozmeticariCRUD = new KozmeticariCRUD(TEST_FILE_NAME);
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
    public void testAddKozmeticar() {
        int initialSize = kozmeticariCRUD.getKozmeticari().size();
        Kozmeticar novikoz = new Kozmeticar("noviKozmeticar", "lozinka123", "Novi", "Kozmeticar", "M", "123456789", "Adresa 1",
                3, 5, 10000.0, 35000.0, kozmeticariCRUD.tretmani("Tretman1;Tretman2"));

        boolean kozVecPostoji = false;
        for (Kozmeticar k :kozmeticariCRUD.getKozmeticari()) {
        	if (k.getKorisnickoIme().equals(novikoz.getKorisnickoIme())) {
        		kozmeticariCRUD.add("noviKozmeticar", "lozinka123", "Novi", "Kozmeticar", "M", "123456789", "Adresa 1",
                3, 5, 10000.0, "Tretman1;Tretman2");
        		kozVecPostoji = true;
        		int newsize = kozmeticariCRUD.getKozmeticari().size();
        		assertEquals(initialSize, newsize);
        	}
        }
        if (!kozVecPostoji) {
        	kozmeticariCRUD.add("noviKozmeticar", "lozinka123", "Novi", "Kozmeticar", "M", "123456789", "Adresa 1",
                3, 5, 1000.0, "Tretman1;Tretman2");
        	int newSize = kozmeticariCRUD.getKozmeticari().size();
        	assertEquals(initialSize + 1, newSize);
        }
        kozmeticariCRUD.remove("noviKozmeticar");

    }

    @Test
    public void testEditKozmeticar() {
        kozmeticariCRUD.add("editKozmeticar", "lozinka123", "Stari", "Kozmeticar", "M", "123456789", "Adresa 2",
                3, 1, 1500.0, "Tretman3;Tretman4");

        Kozmeticar kozmeticar = kozmeticariCRUD.PronadjiKozmeticaraPoUsernameu("editKozmeticar");

        assertNotNull(kozmeticar);

        kozmeticariCRUD.edit("editKozmeticar", "novaLozinka", "Novi", "Kozmeticar", "M", "987654321", "Nova Adresa",
                4, 2, 1800.0, "Tretman5;Tretman6");

        Kozmeticar editedKozmeticar = kozmeticariCRUD.PronadjiKozmeticaraPoUsernameu("editKozmeticar");
        
        List<KozmetickaUslugaTretman> tretmani = kozmeticariCRUD.tretmani("Tretman5;Tretman6");

        assertEquals("novaLozinka", editedKozmeticar.getLozinka());
        assertEquals("Novi", editedKozmeticar.getIme());
        assertEquals("Kozmeticar", editedKozmeticar.getPrezime());
        assertEquals("M", editedKozmeticar.getPol());
        assertEquals("987654321", editedKozmeticar.getTelefon());
        assertEquals("Nova Adresa", editedKozmeticar.getAdresa());
        assertEquals(4, editedKozmeticar.getStrucnaSprema());
        assertEquals(2, editedKozmeticar.getStaz());
        assertEquals(1800.0, editedKozmeticar.getOsnovnaPlata(), 0.001);
        assertEquals(tretmani.size(), editedKozmeticar.getTretmani().size());

        for (int i = 0; i < tretmani.size(); i++) {
            assertEquals(tretmani.get(i).getNazivUsluge(), editedKozmeticar.getTretmani().get(i).getNazivUsluge());
        }
        kozmeticariCRUD.remove("editKozmeticar");
    }

    @Test
    public void testRemoveKozmeticar() {
        kozmeticariCRUD.add("removeKozmeticar", "lozinka123", "Brisani", "Kozmeticar", "M", "123456789", "Adresa 3",
                2, 1, 1000.0, "Tretman7;Tretman8");
        Kozmeticar koz = kozmeticariCRUD.PronadjiKozmeticaraPoUsernameu("removeKozmeticar");
        assertNotNull(koz);

        int initialSize = kozmeticariCRUD.getKozmeticari().size();

        kozmeticariCRUD.remove("removeKozmeticar");

        int newSize = kozmeticariCRUD.getKozmeticari().size();
        Kozmeticar obrisanKoz = kozmeticariCRUD.PronadjiKozmeticaraPoUsernameu("zaBrisanje");
        assertNull(obrisanKoz);

        assertEquals(initialSize - 1, newSize);
    }

    @Test
    public void testLoadData() {
        String testData = "username1|password1|ime1|prezime1|M|123456789|adresa1|5|2|2000.0|12000.0|[Tretman1, Tretman2]\n" +
                          "username2|password2|ime2|prezime2|Z|987654321|adresa2|3|3|2000.0|9000.0|[Tretman3]\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE_NAME))) {
            writer.write(testData);
        } catch (IOException e) {
            e.printStackTrace();
        };


        kozmeticariCRUD.loadData();

        List<Kozmeticar> kozmeticari = kozmeticariCRUD.getKozmeticari();
        assertNotNull(kozmeticari);
        assertEquals(2, kozmeticari.size());
    }
    
    @Test
    public void testSaveData() {
        kozmeticariCRUD = new KozmeticariCRUD(TEST_FILE_NAME);
        String expectedContent = "username1|password1|ime1|prezime1|M|123456789|adresa1|5|2|2000.0|12000.0|[Tretman1, Tretman2]\n" +
        						 "username2|password2|ime2|prezime2|Z|987654321|adresa2|3|3|2000.0|9000.0|[Tretman3]\n";

        kozmeticariCRUD.add("username1", "password1", "ime1", "prezime1", "M", "123456789", "adresa1",
                5, 2, 2000.0, "Tretman1;Tretman2");
        kozmeticariCRUD.add("username2", "password2", "ime2", "prezime2", "Z", "987654321", "adresa2",
                3, 3, 2000.0, "Tretman3");

        kozmeticariCRUD.saveData();

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

    @Test
    public void testTretmani() {
        String tretmaniString = "Tretman9;Tretman10;Tretman11";

        List<KozmetickaUslugaTretman> tretmani = kozmeticariCRUD.tretmani(tretmaniString);

        assertEquals(3, tretmani.size());
        assertEquals("Tretman9", tretmani.get(0).getNazivUsluge());
        assertEquals("Tretman10", tretmani.get(1).getNazivUsluge());
        assertEquals("Tretman11", tretmani.get(2).getNazivUsluge());
    }
}
