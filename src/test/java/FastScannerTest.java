/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import kz.java.zhassan.io.fastscanner.FastScanner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author root
 */
public class FastScannerTest {
    
    public FastScannerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void simpleIntegerTest() throws IOException{
        FastScanner fs = new FastScanner(FastScannerTest.class.getClassLoader().getResourceAsStream("test/simple_integers.txt"));
        Scanner in = new Scanner(FastScannerTest.class.getClassLoader().getResourceAsStream("test/simple_integers.txt"));
        int n = fs.nextInt();
        assertEquals(n, in.nextInt());
        for(int i = 0;i<n;i++){
            assertEquals(fs.nextInt(), in.nextInt());
        }
    }
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
