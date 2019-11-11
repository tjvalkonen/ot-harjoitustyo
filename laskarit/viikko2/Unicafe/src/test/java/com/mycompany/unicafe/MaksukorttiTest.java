package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(1000);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void konstruktoriAsettaaSaldonOikein() {
        assertEquals("saldo: 10.0", kortti.toString());
    }
    
    @Test
    public void rahanLaittaminenKasvattaasaldoaOikein() {
        kortti.lataaRahaa(2500);
        assertEquals("saldo: 35.0", kortti.toString());
    }
    
    @Test
    public void saldoVaheneeOikein() {
        kortti.otaRahaa(250);
        assertEquals("saldo: 7.50", kortti.toString());
    }

    @Test
    public void saldoEiMuutuJosEiRahaaTarpeeksi() {
        kortti.otaRahaa(2500);
        assertEquals("saldo: 10.0", kortti.toString());
    }

    @Test
    public void palauttaaTruejosRahaaTarpeeksi() {
        assertEquals(true, kortti.otaRahaa(1000));
    }

    @Test
    public void palauttaaFalsejosRahaaEiTarpeeksi() {
        assertEquals(false, kortti.otaRahaa(2000));
    }
}
