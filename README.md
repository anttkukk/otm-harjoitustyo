# Otm-harjoitustyö: N-kappaleen planeettakunta simulaattori


## Harjoitustyö

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

#### Käyttäminen
Ohjelman voi käynnistää joko painamalla netbeansin projektin ajo nappia tai komentorivi komennolla

`mvn compile exec:java -Dexec.mainClass=planetsim.PlanetSystemSimulation`

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


Painamalla C ohjelma siirtyy luomismoodiin, jossa hiiren painaminen luo uusia pieni massaisia pienkappaleita. Kappaleen nopeus määrätään hiiren painalluksen ja päästön välinen pikselimäärä kerrottuna -200.
