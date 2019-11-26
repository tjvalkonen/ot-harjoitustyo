# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksen avulla pidetään kirjaa projekteihin käytetystä työajasta. Tarkoituksena on seurata projektin budjetin ja alustavien arvioiden suhteen toteutunutta työmäärää. Jatkossa sovellus tuottaa parempia arvioita eri tyyppisten projektien suunnitteluun ja budjetointiin. Sovellusta on mahdollista käyttää useamman rekisteröityneen käyttäjän, joilla kaikilla on oma työajan merkintänsä.

## Käyttäjät

Sovelluksella on alussa yksi normaali käyttäjätyyppi. Tunnusten hallintaan ja ylläpitoon tehdään pääkäyttäjä.

## Käyttöliittymäluonnos

Käyttäjän kirjautumisen jälkeen, voidaa lisätä projekti tai valita jokin projekti sovelluksessa jo olevista projekteista. Valittuun projektiin voi syöttää työaikoja käyttäjäkohtaisesti. Työaikoja syötettäessä valitaan myös työn tyyppi. Työn tyyppejä voi lisätä ja muokata erikseen ylläpidon näkymästä. Työaikaa syötettäessä voidaan lisätä lyhyt tekstimuotoinen kuvaus työstä. Päivämäärän voi myös valita tehdylle työlle.

## Perusversion tarjoama toiminnallisuus

### Ennen kirjautumista

- käyttäjä voi luoda järjestelmään käyttäjätunnuksen
  - käyttäjätunnuksen täytyy olla uniikki ja pituudeltaan vähintään 3 merkkiä

- käyttäjä voi kirjautua järjestelmään
  - kirjautuminen onnistuu syötettäessä olemassaoleva käyttäjätunnus kirjautumislomakkeelle
  - jos käyttäjää ei olemassa, ilmoittaa järjestelmä tästä

### Kirjautumisen jälkeen

Tehty - käyttäjä näkee aktiiviset projektit listana.

Tehty - käyttäjä voi valita aktiivisen projektin
  - valittuun aktiiviseen projektiin voi syöttää työaikoja

Tehty - käyttäjä voi lisätä uuden projektin

- käyttäjä voi kirjautua ulos järjestelmästä

- pääkäyttäjä (ehkä muutkin) pääsee näkemään kaikkien käyttäjien työajat

- pääkäyttäjä voi poistaa käyttäjätunnuksen.

- pääkäyttäjä pääsee muokkaamaan töiden tyypittelyjä.

## Jatkokehitysideoita

Perustoiminnallisuuksien jälkeen:

- tarkein tavoite on saada seurattua projektin etukäteen suunniteltua budjetoitua ajan/rahan käyttöä suhteessa toteutuneeseen käytettyyn työaikaan.
- graafisen esitykset käytetystä työajasta, eritellen työn tyypit, tekijät ja verrata budjetoituihin aikoihin/rahaan.
