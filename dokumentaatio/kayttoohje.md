# Käyttöohje

Lataa tiedosto [PlanetSim.jar](https://github.com/anttkukk/otm-harjoitustyo/releases) sekä [tausta.jpg](https://github.com/anttkukk/otm-harjoitustyo/releases)

Ohjelma luo databasen suoritushakemistoon.

## Ohjelman käynnistäminen

Ohjelma käynnistetään komennolla 

`java -jar PlanetSim.jar`

### Käyttäminen

Ohjelma käynnistyy alkuruutuu:

![alku](https://github.com/anttkukk/otm-harjoitustyo/blob/master/dokumentaatio/mainmenu.png) 

Käyttäjä voi valita nyt systeemin listasta ja painamalla change system nappia. Tämän jälkeen käyttäjä voi painaa Start! nappia ja aloittaa simulaation. Käyttäjä voi myös painaa Add system nappia lisätäkseen uuden systeemin sekä poistaa luotuja systeemeitä Delete system napilla. Valmiita systeemeitä Inner planets ja Sol system ei voi poistaa.

Painaessaan Add system nappia käyttäjälle avautuu seuraava näkymä:
![add system](https://github.com/anttkukk/otm-harjoitustyo/blob/master/dokumentaatio/addsystemscreen.png)

Käyttäjä voi antaa nimen systeemillä ja painaa Create System! luodakseen systeemin. Systeemin nimi pitää olla uusi ja se ei saa olla tyhjä. Go back nappi palaa alkuruutuun.

Create screen ruudun jälkeen käyttäjä voi painaa lisätä planeetoita systeemiin ja luoda planeetoita seuraavassa ruudussa:

![add planets](https://github.com/anttkukk/otm-harjoitustyo/blob/master/dokumentaatio/addthingstosystemscreen.png)

Käyttäjä voi lisätä nyt planeetan ruudun vasemmalta puolelta antamalla tiedot ja painamalla Add Planet! nappia. Tällöin uusi planeetta ilmestyy listaan oikealle puolelle. Käyttäjä voi nyt valita planeetoita listasta ja painaa Add Object! nappia jolloin kappale lisätään systeemiin ja häipyy listasta. Delete object poistaa kappaleita mutta valmiina olevia kappaleita ei voi poistaa. Kun käyttäjä on tyytyväinen hän voi painaa Finish system! nappia, jolloin käyttäjä palaa takaisin alku valikkoon ja luotu systeemi näkyy systeemien joukossa. 

Kun käyttäjä painaa Start nappia aukeaa simulaatio:

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
