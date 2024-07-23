package Testovi;
import org.junit.Test;

import CRUD.KlijentiCRUD;
import korisniciSistema.Klijent;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;

import java.util.List;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.BufferedReader;

public class KlijentiCRUDTest {
	
	private KlijentiCRUD klijentiCRUD;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

	@Before
	public void setUp() {
		klijentiCRUD = new KlijentiCRUD(".//fajlovi/testKlijenti.csv");
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
	public void testAddKlijent() {
		int initialSize = klijentiCRUD.getKlijenti().size();
		Klijent noviKlijent = new Klijent("noviKlijent", "password", "Ime", "Prezime", "M", "123456789", "Adresa", false);

		boolean klijentVecPosoji = false;
		for (Klijent k : klijentiCRUD.getKlijenti()) {
			if (k.getKorisnickoIme().equals(noviKlijent.getKorisnickoIme())) {
				klijentiCRUD.add("noviKlijent", "password", "Ime", "Prezime", "M", "123456789", "Adresa", false);
				int newSize = klijentiCRUD.getKlijenti().size();
				klijentVecPosoji = true;
				assertEquals(initialSize, newSize);
			}
		}
		if (!klijentVecPosoji) {
	        klijentiCRUD.add("noviKlijent", "password", "Ime", "Prezime", "M", "123456789", "Adresa", false);

	        int newSize = klijentiCRUD.getKlijenti().size();
	        assertEquals(initialSize + 1, newSize);
		}
    }

    @Test
    public void testEditKlijent() {
        klijentiCRUD.add("zaEdit", "password", "Ime", "Prezime", "M", "123456789", "Adresa", false);

        Klijent klijent = klijentiCRUD.PronadjiKlijentaPoUsernameu("zaEdit");
        assertNotNull(klijent);

        klijentiCRUD.edit("zaEdit", "newPassword", "NovoIme", "NovoPrezime", "F", "987654321", "NovaAdresa", true);

        Klijent izmenjenKlijent = klijentiCRUD.PronadjiKlijentaPoUsernameu("zaEdit");
        assertNotNull(izmenjenKlijent);
        assertEquals("newPassword", izmenjenKlijent.getLozinka());
        assertEquals("NovoIme", izmenjenKlijent.getIme());
        assertEquals("NovoPrezime", izmenjenKlijent.getPrezime());
        assertEquals("F", izmenjenKlijent.getPol());
        assertEquals("987654321", izmenjenKlijent.getTelefon());
        assertEquals("NovaAdresa", izmenjenKlijent.getAdresa());
        assertTrue(izmenjenKlijent.getLojalitiKartica());
        klijentiCRUD.remove("zaEdit");
    }

    @Test
    public void testRemoveKlijent() {
        klijentiCRUD.add("zaBrisanje", "password", "Ime", "Prezime", "M", "123456789", "Adresa", false);

        Klijent klijent = klijentiCRUD.PronadjiKlijentaPoUsernameu("zaBrisanje");
        assertNotNull(klijent);

        klijentiCRUD.remove("zaBrisanje");

        Klijent obrisanKlijent = klijentiCRUD.PronadjiKlijentaPoUsernameu("zaBrisanje");
        assertNull(obrisanKlijent);
    }
    
    @Test
    public void testLoadData() {
        String testData = "username1|password1|ime1|prezime1|M|123456789|adresa1|true\n" +
                          "username2|password2|ime2|prezime2|F|987654321|adresa2|false";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(testData.getBytes());
        System.setIn(inputStream);

        klijentiCRUD.loadData();

        List<Klijent> klijenti = klijentiCRUD.getKlijenti();
        assertNotNull(klijenti);
        assertEquals(2, klijenti.size());
    }
    
    @Test
    public void testSaveData() {
        String testFilePath = "testKlijenti.csv";
        klijentiCRUD = new KlijentiCRUD(testFilePath);
        String expectedContent = "username1|password1|ime1|prezime1|M|123456789|adresa1|true\n" +
        						 "username2|password2|ime2|prezime2|F|987654321|adresa2|false\n";


        klijentiCRUD.add("username1", "password1", "ime1", "prezime1", "M", "123456789", "adresa1", true);
        klijentiCRUD.add("username2", "password2", "ime2", "prezime2", "F", "987654321", "adresa2", false);

        klijentiCRUD.saveData();

        String actualContent = readFromFile(testFilePath);

        assertEquals(expectedContent, actualContent);
    }
    
    // Metoda za čitanje sadržaja iz datoteke
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
