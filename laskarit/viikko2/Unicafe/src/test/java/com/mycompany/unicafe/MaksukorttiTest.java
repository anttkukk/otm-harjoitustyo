package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void saldoAluksiOikein() {
        assertEquals("saldo: 0.10", kortti.toString());
    }
    @Test
    public void rahanLataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(10);
        assertEquals("saldo: 0.20", kortti.toString());
        
    }
    @Test
    public void saldoVaheneeOikeinKunTarpeeksiRahaa() {
        kortti = new Maksukortti(1000);
        kortti.otaRahaa(100);
        assertEquals("saldo: 9.0", kortti.toString());
        
    }
    @Test
    public void saldoEiMuutuJosLiianVahanRahaa() {
        kortti.otaRahaa(100);
        assertEquals("saldo: 0.10", kortti.toString());
        
    }
    @Test
    public void trueJosRahatRiittivat() {
        assertTrue(kortti.otaRahaa(5));
        
    }
    @Test
    public void falseJosRahatEivatRiita() {
        assertTrue(!kortti.otaRahaa(1000));
        
    }
    @Test
    public void saldoToimii() {
        assertTrue(kortti.saldo() == 10);
    }
    
}
