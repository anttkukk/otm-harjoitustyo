/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author anttkukk
 */
public class MaksukorttiTest {
    
    Maksukortti kortti;
    
    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    public MaksukorttiTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }


    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void hello() {
    }
    
    @Test
    public void kostruktoriAsettaaSaldonOikein() {

        
        assertEquals("Kortilla on rahaa 10.0 euroa", kortti.toString());
    }
    @Test
    public void syoEdullisestiVahentaaSaldoaOikein() {
       
        kortti.syoEdullisesti();
        assertEquals("Kortilla on rahaa 7.5 euroa", kortti.toString());
        
    }
    @Test
    public void syoMaukkaastiVahentaaSaldoaOikein() {
        
        kortti.syoMaukkaasti();
        assertEquals("Kortilla on rahaa 6.0 euroa", kortti.toString());
        
    }
    @Test
    public void syoEdullisestiEiVieSaldoaNegatiiviseksi() {
        
        kortti.syoMaukkaasti();
        kortti.syoMaukkaasti();
        // nyt kortin saldo on 2
        kortti.syoEdullisesti();
        
        assertEquals("Kortilla on rahaa 2.0 euroa", kortti.toString());
        
    }
    
    @Test
    public void kortilleVoiLadataRahaa() {
        kortti.lataaRahaa(25);
        assertEquals("Kortilla on rahaa 35.0 euroa", kortti.toString());
        
    }
    @Test
    public void kortinSaldoEiYlitaMaksimiarvoa() {
        kortti.lataaRahaa(200);
        assertEquals("Kortilla on rahaa 150.0 euroa", kortti.toString());
        
    }
    @Test
    public void syoMaukkaastiEiVieSaldoaNegatiiviseksi() {
        kortti.syoMaukkaasti();
        kortti.syoMaukkaasti();
        kortti.syoMaukkaasti();
        assertEquals("Kortilla on rahaa 2.0 euroa", kortti.toString());
    }
    @Test
    public void negatiivisenSummanLataaminenEiMuutaSaldoa() {
        kortti.lataaRahaa(-2.0);
        assertEquals("Kortilla on rahaa 10.0 euroa", kortti.toString());
        
    }
    @Test
    public void pystyyOstamaanEdullisenLounaanKunKortillaEdullisenLounaanVerranRahaa() {
        Maksukortti uusi = new Maksukortti(2.5);
        uusi.syoEdullisesti();
        assertEquals("Kortilla on rahaa 0.0 euroa", uusi.toString());
    }
    @Test
    public void pystyyOstamaanMaukkaanLounaanKunKortillaMaukkaanLounaanVerranRahaa() {
        Maksukortti uusi = new Maksukortti(4.0);
        uusi.syoMaukkaasti();
        assertEquals("Kortilla on rahaa 0.0 euroa", uusi.toString());
        
    }
}
