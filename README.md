# Otm-harjoitustyö: N-kappaleen planeettakunta simulaattori


## Harjoitustyö

Ohjelma on N-kappaleen simulaattori, joka simuloi planeettajärjestelmiä. Kun simulaatio käynnistyy, lähtevät siinä olevat kappaleet liikkumaan. Käyttäjä voi tehdä toimintoja, kuten kiihdyttää ja hidastaa aikaa,  zoomailla ruutua, valita targetin ja subtargetin ja nähdä niiden välinen etäisyys tai jopa lisätä omia kappaleita systeemiin. 

### [Dokumentaatio](https://github.com/anttkukk/otm-harjoitustyo/tree/master/dokumentaatio)

[Vaatimusmäärittely](https://github.com/anttkukk/otm-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittelu.md)

[Tuntikirjanpito](https://github.com/anttkukk/otm-harjoitustyo/blob/master/dokumentaatio/tyoaikakirjanpito.md)

[Arkkitehtuuri](https://github.com/anttkukk/otm-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

[Käyttöohje](https://github.com/anttkukk/otm-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)

[Testausdokumentti](https://github.com/anttkukk/otm-harjoitustyo/blob/master/dokumentaatio/testaus.md)

### Releaset

[Viikko 5](https://github.com/anttkukk/otm-harjoitustyo/releases/tag/viikko5)

[Viikko 6](https://github.com/anttkukk/otm-harjoitustyo/releases/tag/viikko6)

[Loppupalautus](https://github.com/anttkukk/otm-harjoitustyo/releases/tag/Loppupalautus)

 ### Komentorivikomennot
 Testit suoritetaan komennolla 
 
 `mvn test`
 
 Testikattavuusraportti luodaan komennolla
 
 `mvn test jacoco:report`
 
 Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto target/site/jacoco/index.html
 
 Checkstyle luodaan komennolla 
 
 `mvn jxr:jxr checkstyle:checkstyle`
 
 Chechstyleä voi tarkastalla avaamalla selaimella tiedosto target/site/checkstyle.html

Javadocin voi luoda suorittamalla komennon 

`mvn javadoc:javadoc`

Generoitu JavaDoc löytyy hakemistosta `target/site/apidocs`

 
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

Katso [käyttöohje](https://github.com/anttkukk/otm-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)
