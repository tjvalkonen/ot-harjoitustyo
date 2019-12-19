# Käyttöohje

Lataa tiedosto [timecard.jar](x)

## Konfigurointi

Ohjelma olettaa, että sen käynnistyshakemistossa on konfiguraatiotiedosto _config.properties_, joka määrittelee käyttäjät ja todot tallettavien tiedostojen nimet. Tiedoston muoto on seuraava

```
projectFile=projects.txt
userFile=users.txt
timecardFile=timecards.txt
```

## Ohjelman käynnistäminen

Ohjelma käynnistetään komennolla 

```
java -jar timecard.jar
```

## Kirjautuminen

Sovellus käynnistyy kirjautumisnäkymään (Login):

<img src="https://github.com/tjvalkonen/ot-harjoitustyo/blob/master/dokumentointi/kuvat/login.png">

Sovellukseen voi kirjautua olemassa olevalla tunnuksella, kirjoittamalla se tekstikenttään ja painamalla _Login_.

## Uuden käyttäjän luominen

Kirjautumisnäkymästä pääsee luomaan uuden tunnuksen painamalla _Create New User_ nappia.

Uuden käyttäjätunnuksen luontinäkymässä (New User) uuden käyttäjän tiedot syöteään kenttin ja painetaan _Create_

<img src="https://github.com/tjvalkonen/ot-harjoitustyo/blob/master/dokumentointi/kuvat/newuser.png">

Jos käyttäjän luominen onnistuu, palataan kirjautumisnäkymään.
Kirjautumisnäkymään voidaan palata luomatta uutta tunnusta painamalla _Back To Login_.

## Projektit lista

Kirjautumisen jälkeen ensimmäinen näkymä on lista projekteista (Projects List).

Sovelluksessa olevat projektit näkyvät listana ja jokaisen projektin kohdalla oikealla on _Select_ nappi, jota painamalla pääsee projektin työaikojen tarkasteluun (Timecards).

Uuden projektin lisäämisnäkymään (Add Project) pääsee painamalla _Add Project_ nappia.

<img src="https://github.com/tjvalkonen/ot-harjoitustyo/blob/master/dokumentointi/kuvat/projectslist.png">

## Uuden projektin lisääminen

Uuden projektin lisäämisnäkymässä (Add Project) nimi syötetään nimikenttään ja projektin arvioitu työaika (Estimated Time To Complete) aika kenttiin. Arvioidun ajan syötäminen ei ole pakollista. uusi projekti lisätään painamalla _Add Project_ nappia. Lisäämisen jälkeen näkymä siiryy projektit listaan (Projects List). Projektit listaan pääsee myös lisäämättä uutta projektia painamalla _Projects List_ nappia.

<img src="https://github.com/tjvalkonen/ot-harjoitustyo/blob/master/dokumentointi/kuvat/addproject.png">

## Työaikojen lisääminen valittuun projektiin

Projektin työaikojen tarkistelunäkymässä (Timecards) voidaan lisätä työaikamerkintöjä valitulle projektille. Työaika syötetään tunti- ja minuuttikenttiin, työntyyppi valitaan alasvetovalikosta ja työlle voi kirjoittaa lyhyen kuvauksen. _Add Time_ nappi lisää syötetyt tiedot projektin työaikoihin. _Project Summary_ napilla pääsee projektin yhteenvetonäkymään (Project Summary). _Projects List_ napilla pääsee projektit lista -näkymään.

<img src="https://github.com/tjvalkonen/ot-harjoitustyo/blob/master/dokumentointi/kuvat/timecards.png">

## Projektin yhteenveto

Projektin yhteenvetonäkymässä (Project Summary) on listattuna projektiin merkityt työajat työntyypeittäin, käytetty kokonaisaika sekä arvioitu projektin kokonaistyäaika. _Projects List_ napilla pääsee projektit lista -näkymään. _Timecards_ napilla pääsee  Projektin työaikojen tarkistelunäkymään (Timecards).

<img src="https://github.com/tjvalkonen/ot-harjoitustyo/blob/master/dokumentointi/kuvat/projectsummary.png">

Oikeassa ylänurjassa on _Logout_ nappi, jota painamalla käyttäjä kirjautuu ulos sovelluksesta ja sovellus palaa takaisin kirjaantumisnäkymään.

