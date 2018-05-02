# Käyttöohje

Lataa tiedosto [PlanetSim.jar](https://github.com/anttkukk/otm-harjoitustyo/releases) sekä [tausta.jpg](https://github.com/anttkukk/otm-harjoitustyo/releases) ja [database.db](https://github.com/anttkukk/otm-harjoitustyo/releases)

Jos database puuttuu, ohjelma luo kyseisen databasen suoritushakemistoon.

## Ohjelman käynnistäminen

Ohjelma käynnistetään komennolla 

`java -jar PlanetSim.jar`

### Käyttäminen

Ohjelma käynnistyy alkuruutuu:

![alku](https://github.com/anttkukk/otm-harjoitustyo/blob/master/dokumentaatio/planeettasimukuva.png) 

Käyttäjä voi valita nyt systeemin listasta ja pinamalla change system nappia. Tämän jälkeen käyttäjä voi painaa Start! nappia ja aloittaa simulaation


![simulaatio](https://github.com/anttkukk/otm-harjoitustyo/blob/master/dokumentaatio/simulaatio.png)

Kun simulaatio käynnistyy on käyttäjällä käytettävissä komentoja.


Kuvaa voi liikuttaa painamalla pohjassa hiiren vasenta nappia ja vetämällä tai zoomata painamalla hiiren vasenta nappia ja vetämällä. Painamalla hiirellä planeettaa valitaan planeetta kohteeksi ja kamera keskittyy siihen. 

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
