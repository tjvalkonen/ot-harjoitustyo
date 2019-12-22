# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksen avulla pidetään kirjaa projekteihin käytetystä työajasta. Tarkoituksena on seurata projektin budjetin ja alustavien arvioiden suhteen toteutunutta työmäärää. Jatkossa sovellus tuottaa parempia arvioita eri tyyppisten projektien suunnitteluun ja budjetointiin. Sovellusta on mahdollista käyttää useamman rekisteröityneen käyttäjän.

## Käyttäjät

Sovelluksella on yksi käyttäjätyyppi.

## Käyttöliittymäluonnos

Käyttäjän kirjautumisen jälkeen, voidaa lisätä projekti tai valita jokin projekti sovelluksessa jo olevista projekteista. Valittuun projektiin voi syöttää työaikoja käyttäjäkohtaisesti. Työaikoja syötettäessä valitaan myös työn tyyppi. Työn tyyppejä voi lisätä ja muokata erikseen ylläpidon näkymästä. Työaikaa syötettäessä voidaan lisätä lyhyt tekstimuotoinen kuvaus työstä. 

## Perusversion tarjoama toiminnallisuus

### Ennen kirjautumista

- käyttäjä voi luoda järjestelmään käyttäjätunnuksen
- käyttäjätunnuksen täytyy olla uniikki ja pituudeltaan vähintään 3 merkkiä
- käyttäjä voi kirjautua järjestelmään
- kirjautuminen onnistuu syötettäessä olemassaoleva käyttäjätunnus kirjautumislomakkeelle
- jos käyttäjää ei olemassa, tulee tästä ilmoitus

### Kirjautumisen jälkeen

- käyttäjä näkee projektit listana.
- käyttäjä voi valita projektin
- valittuun projektiin voi syöttää työaikoja
- käyttäjä voi lisätä uuden projektin
- käyttäjä voi kirjautua ulos järjestelmästä
- käyttäjä voi nähdä projektin yhteenvedon, jossa myös graafi työntyyppien ajan jakautumisesta

## Jatkokehitysideoita

- Päivämäärän voi myös valita tehdylle työlle
- Lisättyjä työaikoja voisi muokata ja poistaa
- Lisättyjä projektieja voisi muokata ja poistaa
- Tehdään sovellukseen toinen käyttäjätyyppi ylläpidolle
- ylläpitäjä (ehkä muutkin) pääsee näkemään kaikkien käyttäjien työajat
- ylläpitäjä voi poistaa käyttäjätunnuksen.
- ylläpitäjä pääsee muokkaamaan töiden tyypittelyjä.

Perustoiminnallisuuksien jälkeen:

- tarkein tavoite on saada seurattua projektin etukäteen suunniteltua budjetoitua ajan/rahan käyttöä suhteessa toteutuneeseen käytettyyn työaikaan.
- graafisen esitykset käytetystä työajasta, eritellen työn tyypit, tekijät ja verrata budjetoituihin aikoihin/rahaan.
