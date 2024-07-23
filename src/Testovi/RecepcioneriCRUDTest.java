package Testovi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.*;

import java.util.List;

import CRUD.RecepcioneriCRUD;
import korisniciSistema.Recepcioner;

public class RecepcioneriCRUDTest {

	private static final String TEST_FILE_NAME = ".//fajlovi/testRecepcioneri.csv";

	private RecepcioneriCRUD recepcioneriCRUD;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
	
    @Before
    public void setUp() {
        recepcioneriCRUD = new RecepcioneriCRUD(TEST_FILE_NAME);
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
    public void testAddRecepcioner() {
        int initialSize = recepcioneriCRUD.getRecepcioneri().size();
        boolean kozVecPostoji = false;
        if (recepcioneriCRUD.getRecepcioneri() != null) {
        	for (Recepcioner k : recepcioneriCRUD.getRecepcioneri()) {
	        	if (k.getKorisnickoIme().equals("NoviRecepcioner")) {
	        		recepcioneriCRUD.add("NoviRecepcioner", "lozinka123", "Novi", "Recepcioner", "M", "123456789", "Adresa 1",
	                        5, 2, 2000.0);
	                int newSize = recepcioneriCRUD.getRecepcioneri().size();
	                kozVecPostoji = true;
	                assertEquals(initialSize, newSize); 
	        	}
	        }
        }
        if (!kozVecPostoji) {
        	recepcioneriCRUD.add("NoviRecepcioner", "lozinka123", "Novi", "Recepcioner", "M", "123456789", "Adresa 1",
                5, 2, 2000.0);
        	int newSize = recepcioneriCRUD.getRecepcioneri().size();
        	assertEquals(initialSize + 1, newSize);
        }
        recepcioneriCRUD.remove("NoviRecepcioner");
    }
    
    @Test
    public void testEditRecepcioner() {
        recepcioneriCRUD.add("editRecepcioner", "lozinka123", "Stari", "Kozmeticar", "M", "123456789", "Adresa 2",
                3, 1, 1500.0);

        Recepcioner recepcioner = recepcioneriCRUD.PronadjiRecepcioneraPoUsernameu("editRecepcioner");

        assertNotNull(recepcioner);

        recepcioneriCRUD.edit("editRecepcioner", "novaLozinka", "Novi", "Kozmeticar", "M", "987654321", "Nova Adresa",
                4, 2, 1800.0);

        Recepcioner editedKozmeticar = recepcioneriCRUD.PronadjiRecepcioneraPoUsernameu("editRecepcioner");
        
        assertEquals("novaLozinka", editedKozmeticar.getLozinka());
        assertEquals("Novi", editedKozmeticar.getIme());
        assertEquals("Kozmeticar", editedKozmeticar.getPrezime());
        assertEquals("M", editedKozmeticar.getPol());
        assertEquals("987654321", editedKozmeticar.getTelefon());
        assertEquals("Nova Adresa", editedKozmeticar.getAdresa());
        assertEquals(4, editedKozmeticar.getStrucnaSprema());
        assertEquals(2, editedKozmeticar.getStaz());
        assertEquals(1800.0, editedKozmeticar.getOsnovnaPlata(), 0.001);

        recepcioneriCRUD.remove("editRecepcioner");
    }
    
    @Test
    public void testRemoveKozmeticar() {
        recepcioneriCRUD.add("removeRecepcioner", "lozinka123", "Brisani", "Kozmeticar", "M", "123456789", "Adresa 3",
                2, 1, 1000.0);
        Recepcioner koz = recepcioneriCRUD.PronadjiRecepcioneraPoUsernameu("removeRecepcioner");
        assertNotNull(koz);

        int initialSize = recepcioneriCRUD.getRecepcioneri().size();

        recepcioneriCRUD.remove("removeRecepcioner");

        int newSize = recepcioneriCRUD.getRecepcioneri().size();
        Recepcioner obrisanKoz = recepcioneriCRUD.PronadjiRecepcioneraPoUsernameu("zaBrisanje");
        assertNull(obrisanKoz);

        assertEquals(initialSize - 1, newSize);
    }
    
    @Test
    public void testLoadData() {
        String testData = "username1|password1|ime1|prezime1|M|123456789|adresa1|5|2|2000.0|12000.0|\n" +
                          "username2|password2|ime2|prezime2|Z|987654321|adresa2|3|3|2000.0|9000.0|\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE_NAME))) {
            writer.write(testData);
        } catch (IOException e) {
            e.printStackTrace();
        };
        recepcioneriCRUD.loadData();

        List<Recepcioner> rec = recepcioneriCRUD.getRecepcioneri();
        assertNotNull(rec);
        assertEquals(2, rec.size());
        recepcioneriCRUD.remove("username1");
        recepcioneriCRUD.remove("username2");
        assertEquals(0, rec.size());
    }
    
    @Test
    public void testSaveData() {
        recepcioneriCRUD = new RecepcioneriCRUD(TEST_FILE_NAME);
        String expectedContent = "username1|password1|ime1|prezime1|M|123456789|adresa1|5|2|2000.0|12000.0\n" +
        						 "username2|password2|ime2|prezime2|Z|987654321|adresa2|3|3|2000.0|9000.0\n";

        recepcioneriCRUD.add("username1", "password1", "ime1", "prezime1", "M", "123456789", "adresa1",
                5, 2, 2000.0);
        recepcioneriCRUD.add("username2", "password2", "ime2", "prezime2", "Z", "987654321", "adresa2",
                3, 3, 2000.0);

        recepcioneriCRUD.saveData();

        String actualContent = readFromFile(TEST_FILE_NAME);

        assertEquals(expectedContent, actualContent);
        recepcioneriCRUD.remove("username1");
        recepcioneriCRUD.remove("username2");
        int size = recepcioneriCRUD.getRecepcioneri().size();
        assertEquals(0, size);

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

