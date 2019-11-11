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
 * @author tjvalkon
 */
public class KassapaateTest {
    
    Kassapaate kassa;
    Maksukortti kortti;
    
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
    
    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kassa!=null);      
    }
    
    @Test
    public void konstruktoriAsettaaSaldonOikein() {
        assertEquals(100000, kassa.kassassaRahaa());
    }

    @Test
    public void konstruktoriAsettaaMyytyjenEdulllistenLounaidenMaaranOikein() {
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }

    @Test
    public void konstruktoriAsettaaMyytyjenMaukkaidenLounaidenMaaranOikein() {
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void syoEdullisestiKateisellaLisaaRahaaKassaanOikein() {
        kassa.syoEdullisesti(240);
        assertEquals(100240, kassa.kassassaRahaa());
    }
    
    @Test
    public void syoMaukkaastiKateisellaLisaaRahaaKassaanOikein() {
        kassa.syoMaukkaasti(400);
        assertEquals(100400, kassa.kassassaRahaa());
    }

    @Test
    public void syoEdullisestiKateisellaLisaaLounaidenMaaraaOikein() {
        kassa.syoEdullisesti(240);
        kassa.syoEdullisesti(240);
        kassa.syoEdullisesti(240);
        kassa.syoEdullisesti(240);
        kassa.syoEdullisesti(240);
        assertEquals(5, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void syoMaukkaastiKateisellaLisaaLounaidenMaaraaOikein() {
        kassa.syoMaukkaasti(400);
        kassa.syoMaukkaasti(400);
        kassa.syoMaukkaasti(400);
        kassa.syoMaukkaasti(400);
        kassa.syoMaukkaasti(400);
        assertEquals(5, kassa.maukkaitaLounaitaMyyty());
    }    
    
    @Test
    public void syoEdullisestiKateisellaAntaaVaihtorahanOikein() {
        assertEquals(20, kassa.syoEdullisesti(260));
    }
    
    @Test
    public void syoMaukkaastiKateisellaAntaaVaihtorahanOikein() {
        assertEquals(20, kassa.syoMaukkaasti(420));
    }

    @Test
    public void syoEdullisestiKateisellaPalauttaaKunEiRahaaOikein() {       
        assertEquals(200, kassa.syoEdullisesti(200));
    }
    
    @Test
    public void syoMaukaastiKateisellaPalauttaaKunEiRahaaOikein() {       
        assertEquals(300, kassa.syoMaukkaasti(300));
    }

    @Test
    public void syoEdullisestiKateisellaEilisaaLounaitaKunEiRahaaOikein() {       
        kassa.syoEdullisesti(200);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void syoMaukaastiKateisellaEilisaaLounaitaKunEiRahaaOikein() {       
        kassa.syoMaukkaasti(300);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }    

    
    @Test
    public void syoEdullisestiKortillaVeloittaaOikein() {
        kassa.syoEdullisesti(kortti);
        assertEquals(760, kortti.saldo());
    }
    
    @Test
    public void syoMaukkaastiKortillaVeloittaaOikein() {
        kassa.syoMaukkaasti(kortti);
        assertEquals(600, kortti.saldo());
    }

    @Test
    public void syoEdullisestiKortillaPalauttaaTrue() {
        assertEquals(true, kassa.syoEdullisesti(kortti));
    }
    
    @Test
    public void syoMaukkaastiKortillaPalauttaaTrue() {
        assertEquals(true, kassa.syoMaukkaasti(kortti));
    }

    @Test
    public void syoEdullisestiKortillaLounaidenMaaraLisaantyy() {
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        assertEquals(2, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void syoMaukkaastiKortillaLounaidenMaaraLisaantyy() {
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);
        assertEquals(2, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void syoEdullisestiKortillaKassanSaldoEiKasva() {
        kassa.syoEdullisesti(kortti);
        kassa.syoEdullisesti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void syoMaukkaastiKortillaKassanSaldoEiKasva() {
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void syoEdullisestiKortillaEiRahaaKortinsaldoEiMuutu() {
        kortti.otaRahaa(800);
        kassa.syoEdullisesti(kortti);
        assertEquals(200, kortti.saldo());
    }
    
    @Test
    public void syoMaukkaastiKortillaEiRahaaKortinsaldoEiMuutu() {
        kortti.otaRahaa(800);
        kassa.syoMaukkaasti(kortti);
        assertEquals(200, kortti.saldo());
    }
    
    @Test
    public void syoEdullisestiKortillaEiRahaaLounaidenMaaraMuuttumaton() {
        kortti.otaRahaa(800);
        kassa.syoEdullisesti(kortti);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void syoMaukkaastiKortillaEiRahaaLounaidenMaaraMuuttumaton() {
        kortti.otaRahaa(800);
        kassa.syoMaukkaasti(kortti);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }

    @Test
    public void syoEdullisestiKortillaEiRahaaPalautetaanFalse() {
        kortti.otaRahaa(800);
        assertEquals(false, kassa.syoEdullisesti(kortti));
    }
    
    @Test
    public void syoMaukkaastiKortillaEiRahaaPalautetaanFalse() {
        kortti.otaRahaa(800);        
        assertEquals(false, kassa.syoMaukkaasti(kortti));
    }
    
    @Test
    public void kortinLataaminenLisaaKortinSaldoaOikein(){
        kassa.lataaRahaaKortille(kortti, 100);
        assertEquals(1100, kortti.saldo());
    }
    
    @Test
    public void kortinLataaminenLisaaKassanSaldoaOikein(){
        kassa.lataaRahaaKortille(kortti, 100);
        assertEquals(100100, kassa.kassassaRahaa());
    }

    @Test
    public void kortinLataaminenEiOnnistuMiinusRahalla(){
        kassa.lataaRahaaKortille(kortti, -100);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
