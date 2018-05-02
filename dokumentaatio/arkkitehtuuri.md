# Arkkitehtuurikuvaus

### Rakenne

Ohjelmassa on kolmitasoinen kerrosarkkitehtuuri. Pakkaus planetsim.ui sisältää JavaFX:llä toteutetun käyttöliittymän, planetsim.domain sisältää sovelluslogiikan ja planetsim.database sisältää databasea käsittelevän pysyväistallennuksesta vastaavan koodin.

### Käyttöliittymä

Ohjelma sisältää tällähetkellä kaksi näkymää:

* Aloitusruutu, jossa voi käynnistää simulaation tai vaihtaa systeemiä valitsemalla listasta systeemin ja painamalla change system nappia.

* Itse simulaatio, jossa kappaleet kiertävät keskuskappaletta ja käyttäjällä on käytettävissä erilaisia toiminnallisuuksia näppäimmistöllä.

Näkymät on toteutettu Scene olioina, joita vaihdetaan napeista vaihtamalla sovelluksen stagen sceneä. Käyttöliittymä on rakennettu ohjelmallisesti luokassa [planetsim.ui.PlanetSystemSimulation](https://github.com/anttkukk/otm-harjoitustyo/blob/master/PlanetSim/src/main/java/planetsim/ui/PlanetSystemSimulation.java).

Käyttöliittymä on pyritty eristämään sovelluslogiikasta.



#### Alustava luokkakaavio

![luokkakaavio](https://raw.githubusercontent.com/anttkukk/otm-harjoitustyo/master/dokumentaatio/luokkakaavio.png "Luokkakaavio")


#### Toiminnallisuus

###### Planeettojen hakeminen ja update metodi

Sekvenssikaaviossa on havainnollistettu aluksi tehtävää kappaleiden hakua ja tämän jälkeen update metodin toimintaa.

![Sekvenssikaavio](https://github.com/anttkukk/otm-harjoitustyo/blob/master/dokumentaatio/sekvenssi.jpeg)


