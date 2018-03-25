/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unicafe;

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
public class KassapaateTest {
    Kassapaate kassa;
    Maksukortti kortti;
    public KassapaateTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        kassa = new Kassapaate();
        kortti = new Maksukortti(1000);
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void kassapaatteenRahamaaraAlussaOikein() {
        assertTrue(kassa.kassassaRahaa() == 100000);
    }
    @Test
    public void edullistenLounaidenMaaraAlussaNolla() {
        assertTrue(kassa.edullisiaLounaitaMyyty() == 0);
        
    }
    @Test
    public void maukkaidenLounaidenMaaraAlussaNolla() {
        assertTrue(kassa.maukkaitaLounaitaMyyty() == 0);
        
    }
    @Test
    public void kassanRahaKasvaaEdullisenVerran() {
        int rahaa = kassa.kassassaRahaa();
        kassa.syoEdullisesti(1000);
        assertTrue(kassa.kassassaRahaa() == rahaa + 240);
    }
    @Test
    public void kassanRahaKasvaaMaukkaanVerran() {
        int rahaa = kassa.kassassaRahaa();
        kassa.syoMaukkaasti(1000);
        assertTrue(kassa.kassassaRahaa() == rahaa + 400);
    }
    @Test
    public void vaihtorahanMaaraOikeaEdullisessa() {
        assertTrue(kassa.syoEdullisesti(1000) == 760);
        
    }
    @Test
    public void vaihtorahanMaaraOikeaMaukkaassa() {
        assertTrue(kassa.syoMaukkaasti(1000) == 600);
    }
    @Test
    public void myytyjenEdullistenLounaidenMaaraNousee() {
        kassa.syoEdullisesti(1000);
        assertTrue(kassa.edullisiaLounaitaMyyty() == 1);
    }
    @Test
    public void myytyjenMaukkaidenLounaidenMaaraNousee() {
        kassa.syoMaukkaasti(1000);
        assertTrue(kassa.maukkaitaLounaitaMyyty() == 1);
    }
    @Test
    public void josEdullinenMaksuEiRiitaKassanRahamaaraEiMuutu() {
        int rahaa = kassa.kassassaRahaa();
        kassa.syoEdullisesti(1);
        assertTrue(rahaa == kassa.kassassaRahaa());
    }
    @Test
    public void josMaukasMaksuEiRiitaKassanRahamaaraEiMuutu() {
        int rahaa = kassa.kassassaRahaa();
        kassa.syoMaukkaasti(1);
        assertTrue(rahaa == kassa.kassassaRahaa());
    }
    @Test
    public void josEdullinenMaksuEiRiitaRahatPalautetaanVaihtorahana() {
        assertTrue(kassa.syoEdullisesti(100) == 100);
    }
    @Test
    public void josMaukasMaksuEiRiitaRahatPalautetaanVaihtorahana() {
        assertTrue(kassa.syoMaukkaasti(100) == 100);
    }
    @Test
    public void josRahatEivatRiitaEdullistenLounaidenMaaraEiKasva() {
        kassa.syoEdullisesti(1);
        assertTrue(kassa.edullisiaLounaitaMyyty() == 0);
    }
    @Test
    public void josRahatEivatRiitaMaukkaittenLounaidenMaaraEiKasva() {
        kassa.syoMaukkaasti(1);
        assertTrue(kassa.maukkaitaLounaitaMyyty() == 0);
    }
    @Test
    public void kortiltaVeloitetaanSummaEdullisessaLounaassa() {
        kassa.syoEdullisesti(kortti);
        assertTrue(kortti.saldo() == 760);
    }
    @Test
    public void kortiltaVeloitetaanSummaMaukkaassaLounaassa() {
        kassa.syoMaukkaasti(kortti);
        assertTrue(kortti.saldo() == 600);
    }
    @Test
    public void palautetaanTrueKunKortillaVaraaEdulliseen() {
        assertTrue(kassa.syoEdullisesti(kortti));
    }
    @Test
    public void palautetaanTrueKunKortillaVaraaMaukkaaseen() {
        assertTrue(kassa.syoMaukkaasti(kortti));
    }
    @Test
    public void edullistenLounaidenMaaraKasvaaKorttimaksulla() {
        kassa.syoEdullisesti(kortti);
        assertTrue(kassa.edullisiaLounaitaMyyty() == 1);
    }
    @Test
    public void maukkaiednLounaidenMaaraKasvaaKorttimaksulla() {
        kassa.syoMaukkaasti(kortti);
        assertTrue(kassa.maukkaitaLounaitaMyyty() == 1);
    }
    @Test
    public void josKortillaEiEdulliseenRahaaEiKortinRahamaaraMuutu() {
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);
        kassa.syoEdullisesti(kortti);
        assertTrue(kortti.saldo() == 200);
    }
    @Test
    public void josKortillaEiMaukkaaseenRahaaEiKortinRahamaaraMuutu() {
        kassa.syoMaukkaasti(kortti);
        kassa.syoEdullisesti(kortti);
        kassa.syoMaukkaasti(kortti);
        assertTrue(kortti.saldo() == 360);
    }
    @Test
    public void josKortillaEiRahaaEdullistenMaaraEiKasva() {
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);
        kassa.syoEdullisesti(kortti);
        assertTrue(kassa.edullisiaLounaitaMyyty() == 0);
    }
    @Test
    public void josKortillaEiRahaaMaukkaidenMaaraEiKasva() {
        Maksukortti visa = new Maksukortti(100);
        kassa.syoMaukkaasti(visa);
        assertTrue(kassa.maukkaitaLounaitaMyyty() == 0);
    }
    @Test
    public void josKortillaEiVaraaPalautetaanFalseSyodessaEdullisesti() {
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);
        assertTrue(!kassa.syoEdullisesti(kortti));
    }
    @Test
    public void josKortillaEiVaraaPalautetaanFalseSyodessaMaukkaasti() {
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);
        assertTrue(!kassa.syoMaukkaasti(kortti));
    }
    @Test
    public void kassanRahamaaraEiMuutuEdullisessaKorttiOstoksessa() {
        kassa.syoEdullisesti(kortti);
        assertTrue(kassa.kassassaRahaa() == 100000);
    }
    @Test
    public void kassanRahamaaraEiMuutuMaukkaassaKorttiOstoksessa() {
        kassa.syoMaukkaasti(kortti);
        assertTrue(kassa.kassassaRahaa() == 100000);
    }
    @Test
    public void rahaaLadattaessaKortinSaldoMuuttuu() {
        kassa.lataaRahaaKortille(kortti, 1000);
        assertTrue(kortti.saldo() == 2000);
    }
    @Test
    public void rahaaLadattaessaKortilleKassanRahamaaraKasvaa() {
        kassa.lataaRahaaKortille(kortti, 1000);
        assertTrue(kassa.kassassaRahaa() == 101000);
    }
    @Test
    public void rahaaEiLadataKortilleNegatiivistaMaaraa() {
        kassa.lataaRahaaKortille(kortti, -1000);
        assertTrue(kortti.saldo() == 1000);
    }
    @Test
    public void kortilleLadattavaNegatiivinenMaaraEiVaikutaKassanRahamaaraan() {
        kassa.lataaRahaaKortille(kortti, -1000);
        assertTrue(kassa.kassassaRahaa() == 100000);
    }
}
