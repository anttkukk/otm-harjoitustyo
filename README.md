# Otm-harjoitustyö: N-kappaleen planeettakunta simulaattori


## Harjoitustyö

Ohjelma on N-kappaleen simulaattori, joka simuloi planeettajärjestelmiä. Kun simulaatio käynnistyy, lähtevät siinä olevat kappaleet liikkumaan. Käyttäjä voi tehdä toimintoja, kuten kiihdyttää ja hidastaa aikaa,  zoomailla ruutua, valita targetin ja subtargetin ja nähdä niiden välinen etäisyys tai jopa lisätä omia kappaleita systeemiin. 

### [Dokumentaatio](https://github.com/anttkukk/otm-harjoitustyo/tree/master/dokumentaatio)

[Vaatimusmäärittely](https://github.com/anttkukk/otm-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittelu.md)

[Tuntikirjanpito](https://github.com/anttkukk/otm-harjoitustyo/blob/master/dokumentaatio/tyoaikakirjanpito.md)

[Arkkitehtuuri](https://github.com/anttkukk/otm-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

### Releaset

[Viikko 5](https://github.com/anttkukk/otm-harjoitustyo/releases/tag/viikko5)


 ### Komentorivikomennot
 Testit suoritetaan komennolla 
 
 `mvn test`
 
 Testikattavuusraportti luodaan komennolla
 
 `mvn test jacoco:report`
 
 Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto target/site/jacoco/index.html
 
 Checkstyle luodaan komennolla 
 
 `mvn jxr:jxr checkstyle:checkstyle`
 
 Chechstyleä voi tarkastalla avaamalla selaimella tiedosto target/site/checkstyle.html
 
 Suoritettavan jar-tiedoston voi luoda komennolla 
 
 `mvn package`
 
 Tällöin target kansioon luodaan PlanetSim-1.0-SNAPSHOT.jar. Jaria voi ajaa komennolla
 
 `java -jar PlanetSim-1.0-SNAPSHOT.jar`
 
 Kunhan ohjelma on siirretty päähakemistoon tai lisätty tiedostonimen eteen target/tiedostonnimi.jar

#### Käyttäminen
Ohjelman voi käynnistää joko painamalla netbeansin projektin ajo nappia tai komentorivi komennolla

`mvn compile exec:java -Dexec.mainClass=planetsim.PlanetSystemSimulation`

Jos jar-tiedosto on luotu, voi sitä ajaa komennolla 

`java -jar PlanetSim-1.0-SNAPSHOT.jar`

Ohjelmassa käynnistyy heti simulaatio. Kuvaa voi liikuttaa painamalla pohjassa hiiren vasenta nappia ja vetämällä tai zoomata painamalla hiiren vasenta nappia ja vetämällä. Painamalla hiirellä planeettaa valitaan planeetta kohteeksi ja kamera keskittyy siihen. 

Ohjelmassa on myös näppäimiä:



| Näppäin | Toiminto |
|---------|---------|
| R | Palauttaa alkuperäisen kuvakulman |
| , | Jakaa timestepin kymmenellä |
| . | Kertoo timestepin kymmenellä |
| Space | Palauttaa alkuperäisen timestepin |
| P | Pausettaa/jatkaa simulaation |
| B | Kääntää timestepin käänteiseksi |
| 1 | Keskittää ruudun viimeisimpään kohteeseen |
| vasen nuoli | vaihtaa targettia edelliseen |
| oikea nuoli | vaihtaa targettia seuraavaan |
| ctrl + vasen nuoli | vaihtaa subtargettia edelliseen |
| ctrl + oikea nuoli | vaihtaa subtargettia seuraavaan |


Painamalla C ohjelma siirtyy luomismoodiin, jossa hiiren painaminen luo uusia pieni massaisia pienkappaleita. Kappaleen nopeus määrätään hiiren painalluksen ja päästön välinen pikselimäärä kerrottuna -200.

Klikkaamalla planeettaa tulee planeetasta target ja kamera keskittyy siihen. Klikkaamalla hiiren oikealla painikkeella toista kappaletta tulee toisesta kappaleesta subtarget ja kappaleiden välinen etäisyys ilmoitetaan.
