package Testovi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import CRUD.KozmetickaUslugaCRUD;
import uslugeTretmani.KozmetickaUslugaTretman;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class KozmetickaUslugaCRUDTest {

    private static final String TEST_FILE_NAME = ".//fajlovi/testUsluge.csv";

    private KozmetickaUslugaCRUD uslugeCRUD;

    @Before
    public void setUp() {
        uslugeCRUD = new KozmetickaUslugaCRUD(TEST_FILE_NAME);
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
    public void testAddUsluga() {
        int initialSize = uslugeCRUD.getUsluge().size();
        boolean vecPostoji = false;
        if (uslugeCRUD.getUsluge() != null) {
        	for (KozmetickaUslugaTretman usluga : uslugeCRUD.getUsluge()) {
        		if (usluga.getNazivUsluge().equals("NovaUsluga")) {
        	        uslugeCRUD.add("NovaUsluga");
        	        int newSize = uslugeCRUD.getUsluge().size();
        	        vecPostoji = true;
        	        assertEquals(initialSize, newSize);
        		}
        	}
        }
        if (!vecPostoji) {
        	uslugeCRUD.add("NovaUsluga");
	        int newSize = uslugeCRUD.getUsluge().size();
	        assertEquals(initialSize + 1, newSize);
        }
        uslugeCRUD.remove("NovaUsluga");
    }

    @Test
    public void testRemoveUsluga() {
        uslugeCRUD.add("UslugaZaBrisanje");
        int initialSize = uslugeCRUD.getUsluge().size();

        KozmetickaUslugaTretman usluga = uslugeCRUD.pronadjiUsluguPoNazivu("UslugaZaBrisanje");
        assertNotNull(usluga);
        
        uslugeCRUD.remove("UslugaZaBrisanje");

        int newSize = uslugeCRUD.getUsluge().size();
        KozmetickaUslugaTretman obrisanaUsluga = uslugeCRUD.pronadjiUsluguPoNazivu("UslugaZaBrisanje");
        assertNull(obrisanaUsluga);

        assertEquals(initialSize - 1, newSize);
    }

    @Test
    public void testPronadjiUsluguPoNazivu() {
        uslugeCRUD.add("Usluga1");
        uslugeCRUD.add("Usluga2");

        KozmetickaUslugaTretman usluga = uslugeCRUD.pronadjiUsluguPoNazivu("Usluga1");

        assertNotNull(usluga);
        assertEquals("Usluga1", usluga.getNazivUsluge());
    }

    @Test
    public void testLoadData() {
        String testData = "usluga1\nusluga2\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE_NAME))) {
            writer.write(testData);
        } catch (IOException e) {
            e.printStackTrace();
        };


        uslugeCRUD.loadData();

        List<KozmetickaUslugaTretman> usluge = uslugeCRUD.getUsluge();
        assertNotNull(usluge);
        assertEquals(2, usluge.size());
    }
    
    @Test
    public void testSaveData() {
        uslugeCRUD = new KozmetickaUslugaCRUD(TEST_FILE_NAME);
        String expectedContent = "usluga1\n" +
        						 "usluga2\n";

        uslugeCRUD.add("usluga1");
        uslugeCRUD.add("usluga2");

        uslugeCRUD.saveData();

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

