# Arkkitehtuurikuvaus

## Rakenne

Pakkausrakenne:

<img src="https://github.com/tjvalkonen/ot-harjoitustyo/blob/master/dokumentointi/kuvat/pakkausrakenne01.png">

Pakkauksessa timecard.ui on JavaFX käyttöiittymä.

## Käyttöliittymä

Käyttöliittymässä on kuusi erillistä näkymää
- Kirjautuminen (Login)
- Uuden käyttäjän luominen (New User)
- Projektit lista (Projects List)
- Uuden projektin lisääminen (Add Project)
- Työaikojen lisääminen valittuun projektiin (Timecards)
- Projektin yhteenveto

Näkymät ovat toteutettu omina [Scene](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Scene.html)-oliona. Käyttöiittymä on toteutettu luokassa [timecard.ui.TimecardUi](https://x).

Näkymissä joissa on päivittyvää tietoa, kutsutaan niihin liittyviä metodeja, jotka piirtävät tarvittaessa näkymän uudelleen.

## Sovelluslogiikka

Sovelluksen datamallin muodostavat luokat [User] [Project] [Timecard]

Luokka [TimecardService] vastaa sovelluksen toiminnallisuudesta

## Tietojen pysyväistallennus

Luokat _FileUserDao_ , _FileProjectDao_ ja _FileTimecard_ hoitavat tietojen tallentamisen tiedostoihin. Luokat ovat pakkauksessa _timecard.dao_.

## Tiedostot
