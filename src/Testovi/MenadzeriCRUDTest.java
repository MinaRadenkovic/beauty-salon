package Testovi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.*;

import java.util.List;

import CRUD.MenadzeriCRUD;
import korisniciSistema.Menadzer;

public class MenadzeriCRUDTest {

	private static final String TEST_FILE_NAME = ".//fajlovi/testMenadzeri.csv";

	private MenadzeriCRUD menadzeriCRUD;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
	
    @Before
    public void setUp() {
        menadzeriCRUD = new MenadzeriCRUD(TEST_FILE_NAME);
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
    public void testAddMenadzer() {
        int initialSize = menadzeriCRUD.getMenadzeri().size();
        boolean kozVecPostoji = false;
        if (menadzeriCRUD.getMenadzeri() != null) {
        	for (Menadzer k : menadzeriCRUD.getMenadzeri()) {
	        	if (k.getKorisnickoIme().equals("NoviMenadzer")) {
	        		menadzeriCRUD.add("NoviMenadzer", "lozinka123", "Novi", "Menadzer", "M", "123456789", "Adresa 1",
	                        5, 2, 2000.0);
	                int newSize = menadzeriCRUD.getMenadzeri().size();
	                kozVecPostoji = true;
	                assertEquals(initialSize, newSize); 
	        	}
	        }
        }
        if (!kozVecPostoji) {
        	menadzeriCRUD.add("NoviMenadzer", "lozinka123", "Novi", "Menadzer", "M", "123456789", "Adresa 1",
                5, 2, 2000.0);
        	int newSize = menadzeriCRUD.getMenadzeri().size();
        	assertEquals(initialSize + 1, newSize);
        }
        menadzeriCRUD.remove("NoviMenadzer");
    }
    
    @Test
    public void testEditMenadzer() {
        menadzeriCRUD.add("editMenadzer", "lozinka123", "Stari", "Kozmeticar", "M", "123456789", "Adresa 2",
                3, 1, 1500.0);

        Menadzer menadzer = menadzeriCRUD.PronadjiMenadzeraPoUsernameu("editMenadzer");

        assertNotNull(menadzer);

        menadzeriCRUD.edit("editMenadzer", "novaLozinka", "Novi", "Kozmeticar", "M", "987654321", "Nova Adresa",
                4, 2, 1800.0);

        Menadzer editedKozmeticar = menadzeriCRUD.PronadjiMenadzeraPoUsernameu("editMenadzer");
        
        assertEquals("novaLozinka", editedKozmeticar.getLozinka());
        assertEquals("Novi", editedKozmeticar.getIme());
        assertEquals("Kozmeticar", editedKozmeticar.getPrezime());
        assertEquals("M", editedKozmeticar.getPol());
        assertEquals("987654321", editedKozmeticar.getTelefon());
        assertEquals("Nova Adresa", editedKozmeticar.getAdresa());
        assertEquals(4, editedKozmeticar.getStrucnaSprema());
        assertEquals(2, editedKozmeticar.getStaz());
        assertEquals(1800.0, editedKozmeticar.getOsnovnaPlata(), 0.001);

        menadzeriCRUD.remove("editMenadzer");
    }
    
    @Test
    public void testRemoveMenadzer() {
        menadzeriCRUD.add("removeMenadzer", "lozinka123", "Brisani", "Kozmeticar", "M", "123456789", "Adresa 3",
                2, 1, 1000.0);
        Menadzer koz = menadzeriCRUD.PronadjiMenadzeraPoUsernameu("removeMenadzer");
        assertNotNull(koz);

        int initialSize = menadzeriCRUD.getMenadzeri().size();

        menadzeriCRUD.remove("removeMenadzer");

        int newSize = menadzeriCRUD.getMenadzeri().size();
        Menadzer obrisanKoz = menadzeriCRUD.PronadjiMenadzeraPoUsernameu("zaBrisanje");
        assertNull(obrisanKoz);

        assertEquals(initialSize - 1, newSize);
    }
    
    @Test
    public void testLoadData() {
        String testData = "username1|password1|ime1|prezime1|M|123456789|adresa1|5|2|2000.0|12000.0\n" +
                          "username2|password2|ime2|prezime2|Z|987654321|adresa2|3|3|2000.0|9000.0\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE_NAME))) {
            writer.write(testData);
        } catch (IOException e) {
            e.printStackTrace();
        };
        menadzeriCRUD.loadData();

        List<Menadzer> menadzeri = menadzeriCRUD.getMenadzeri();
        assertNotNull(menadzeri);
        assertEquals(2, menadzeri.size());
        menadzeriCRUD.remove("username1");
        menadzeriCRUD.remove("username2");
        assertEquals(0, menadzeri.size());

    }
    
    @Test
    public void testSaveData() {
        menadzeriCRUD = new MenadzeriCRUD(TEST_FILE_NAME);
        String expectedContent = "username1|password1|ime1|prezime1|M|123456789|adresa1|5|2|2000.0|12000.0\n" +
        						 "username2|password2|ime2|prezime2|Z|987654321|adresa2|3|3|2000.0|9000.0\n";

        menadzeriCRUD.add("username1", "password1", "ime1", "prezime1", "M", "123456789", "adresa1",
                5, 2, 2000.0);
        menadzeriCRUD.add("username2", "password2", "ime2", "prezime2", "Z", "987654321", "adresa2",
                3, 3, 2000.0);

        menadzeriCRUD.saveData();

        String actualContent = readFromFile(TEST_FILE_NAME);

        assertEquals(expectedContent, actualContent);
        List<Menadzer> menadzeri = menadzeriCRUD.getMenadzeri();

        menadzeriCRUD.remove("username1");
        menadzeriCRUD.remove("username2");
        assertEquals(0, menadzeri.size());
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
