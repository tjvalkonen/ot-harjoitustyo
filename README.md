# Työajanseuranta

## Dokumentaatio

[Käyttöohje](https://github.com/tjvalkonen/ot-harjoitustyo/blob/master/dokumentointi/kayttoohje.md)

[Vaatimusmaarittely](https://github.com/tjvalkonen/ot-harjoitustyo/blob/master/dokumentointi/vaatimusmaarittely.md)

[Arkkitehtuuri](https://github.com/tjvalkonen/ot-harjoitustyo/blob/master/dokumentointi/arkkitehtuuri.md)

[Testausdokumentti](https://github.com/tjvalkonen/ot-harjoitustyo/blob/master/dokumentointi/testausdokumentti.md)

[Työaikakirjanpito](https://github.com/tjvalkonen/ot-harjoitustyo/blob/master/dokumentointi/tyoaikakirjanpito.md)

## Releaset

[Release v0.1](https://github.com/tjvalkonen/ot-harjoitustyo/releases/tag/v0.1)

[Release v0.2](https://github.com/tjvalkonen/ot-harjoitustyo/releases/tag/v0.2)

[Loppupalautus - Release v1.0](https://github.com/tjvalkonen/ot-harjoitustyo/releases/tag/v1.0)

## Komentorivitoiminnot

### Testaus

Testaukset voidaan suorittaa seuraavilla komennoilla:

```
mvn test
```

Testikattavuusraportti saadaan seuraavalla komennolla:

```
mvn jacoco:report
```

Testikattavuusraportti löytyy kansiosta ja voidaan tarkistella selaimella:
_target/site/jacoco/index.html_

### Suoritettavan paketin generointi

Sovelluksesta saa tehtyä suoritettavan jar tiedoston komennolla:

```
mvn package
```

Luotu tiedosto löytyy hakemistosta _target_ nimellä _Timecard-1.0-SNAPSHOT.jar_

Sovellus olettaa että samassa kansiossa on tiedosto: config.properties
Tiedosto on tekstitiedosto, jonka sisältö on:
<pre>
projectFile=projects.txt
userFile=users.txt
timecardFile=timecards.txt
</pre>

Sovelluksen voi käynnistää komennolla:

```
java -jar Timecard-1.0-SNAPSHOT.jar
```

### JavaDoc

Soveluksen JavaDoc dokumentaation saa luotua komennolla:

```
mvn javadoc:javadoc
```

JavaDoc löytyy kansiosta ja voidaan tarkistella selaimella:
_target/site/apidocs/index.html_

### Checkstyle

[checkstyle.xml] dokumentin määrittelemät tarkistukset voidaan ajaa komennolla:

```
mvn jxr:jxr checkstyle:checkstyle
```

Checkstyle raportti löytyy kansiosta ja voidaan tarkistella selaimella:
 _target/site/checkstyle.html_
