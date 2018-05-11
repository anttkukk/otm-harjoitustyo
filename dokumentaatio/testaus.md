# Testausdokumentti

Ohjelmaa on testattu automatisoiduin yksikkötestein JUnitilla sekä manuaalisesti järjestelmätason testein.

## Yksikkötestaus

### Sovelluslogiikka

Automatisoitujen testien ytimen mudoostavat sovelluslogiikkaa, eli pakkauksen [planetsim.domain](https://github.com/anttkukk/otm-harjoitustyo/tree/master/PlanetSim/src/main/java/planetsim/domain) luokkia testaavat testit [PlanetSystemTest](https://github.com/anttkukk/otm-harjoitustyo/blob/master/PlanetSim/src/test/java/planetsim/domain/PlanetSystemTest.java), [PlanetTest](https://github.com/anttkukk/otm-harjoitustyo/blob/master/PlanetSim/src/test/java/planetsim/domain/PlanetTest.java) sekä [VectorTest](https://github.com/anttkukk/otm-harjoitustyo/blob/master/PlanetSim/src/test/java/planetsim/domain/VectorTest.java), joiden määrittelemät testitapaukset simuloivat ohjelman toiminnallisuuksia.

### DAO-luokat

DAO-luokat testataan [PlanetDaoTest](https://github.com/anttkukk/otm-harjoitustyo/blob/master/PlanetSim/src/test/java/planetsim/database/PlanetDaoTest.java)-luokan avulla. Testiluokassa luodaan ainoastaan testaamisene käytetty testDatabase. Testit poistavat testien lopuksi aina lisäykset testDatabaseen, jolloin testit voi ajaa uudestaan huoletta.

### Testauskattavuus

Käyttöliittymää lukuunottamatta sovelluksen testauksen rivikattavuus on 98% ja haarautumakattavuus 100%

![testauskattavuus](https://github.com/anttkukk/otm-harjoitustyo/blob/master/dokumentaatio/jacocoreport.png)

Testaamatta jäivät vain Planet-luokan jotkin getterit ja setterit sekä toString.

## Järjestelmätestaus

Sovelluksen järjestelmätestaus on suoritettu manuaalisesti

### Asennus

Sovellus on haettu ja sitä on testattu [käyttöohjeen](https://github.com/anttkukk/otm-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md) kuvaamalla tavalla Linux-ympäristöön siten, että sovelluksen käynnistyshakemistossa on ollut käyttöohjeen kuvauksen mukainen tausta.jpg

Sovellsuta on testattu sekä tilanteissa, joissa käyttäjät database on ollut olemassa sekä sellaisessa, jossa databasea ei ole ollut ja ohjelma on luonut sen itse.

### Toiminnallisuudet

Kaikki [määrittelydokumentin](https://github.com/anttkukk/otm-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittelu.md) ja käyttöohjeen listaamat toiminnallisuudet on käyty läpi. Kaikkiin tekstikenttiin on myös yritetty täyttää virheellisiä arvoja kuten tyhjiä ja kirjaimia vain double syötekenttissä.

### Sovellukseen jääneet laatuongelmat

Ohjelman käyttöjärjestelmän koodi jäi hieman epäselväksi ja sitä olisi voinut parantaa jakamalla asioita metodeihin ja metodeita pienempiin metodeihin.

Tämän lisäksi planetDao-luokan metodit voisivat olla nimetty kenties yhtenäisemmin.
