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

Käyttäjätiedot, projektit sekä työaikamerkinnät tallenetaan omiin tiedostoihinsa.

config.properties dokumenttissa on määritelty tiedostot seuraavasti:
projectFile=projects.txt
userFile=users.txt
timecardFile=timecards.txt

Projektit tallentuvat seuraavassa muodossa:

<pre>
1;Tärkeä Projekti;1740
2;Toinen Tärkeä Projekti;2700
3;Harjoitusprojekti;4530
</pre>

Ensimmäisenä on projektin tunnus eli _id_, seuraavana projektin nimi ja viimeisenä projektin arvioitu kokonaistyöaika minuutteina. Kentät ovat erotettu toisistaan puolipistein.

Käytäjät tallentuvat seuraavassa muodossa:

<pre>
Tomas;Tomas Valkonen
Jaakko;Jaakko Testaaja
Lasse;Lasse Lataaja
</pre>

Ensimmäisenä on käyttäjätunnus ja toisena käyttäjän nimi.

Työaikamerkinnät tallentuvat seuraavassa muodossa:

<pre>
1;3;140;4;Ylläpitoa;Tomas Valkonen
2;3;180;1;Suunnittelua ja muuta;Tomas Valkonen
3;3;150;3;Testailin;Jaakko Testaaja
4;3;210;2;Ohjelmoin juttuja;Tomas Valkonen
5;3;80;1;Suunnittelin jotain;Tomas Valkonen
</pre>

Ensimmäisenä on aikamerkinnän tunnus eli _id_, seuraavana on projektin tunnus eli _id_, kolmantena on merkitty työaika minuutteina, neljäntenä työntyyppi ja viidentenä työn merkinneen käyttäjän nimi.
