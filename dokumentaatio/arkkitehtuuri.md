# Arkkitehtuurikuvaus

### Rakenne

Ohjelmassa on kolmitasoinen kerrosarkkitehtuuri. Pakkaus planetsim.ui sisältää JavaFX:llä toteutetun käyttöliittymän, planetsim.domain sisältää sovelluslogiikan ja planetsim.database sisältää databasea käsittelevän pysyväistallennuksesta vastaavan koodin.

### Käyttöliittymä

Ohjelma sisältää tällähetkellä kaksi näkymää:

* Aloitusruutu, jossa voi käynnistää simulaation tai vaihtaa systeemiä valitsemalla listasta systeemin ja painamalla change system nappia.

* Itse simulaatio, jossa kappaleet kiertävät keskuskappaletta ja käyttäjällä on käytettävissä erilaisia toiminnallisuuksia näppäimmistöllä.

Näkymät on toteutettu Scene olioina, joita vaihdetaan napeista vaihtamalla sovelluksen stagen sceneä. Käyttöliittymä on rakennettu ohjelmallisesti luokassa [planetsim.ui.PlanetSystemSimulation](https://github.com/anttkukk/otm-harjoitustyo/blob/master/PlanetSim/src/main/java/planetsim/ui/PlanetSystemSimulation.java).

Käyttöliittymä on pyritty eristämään sovelluslogiikasta.

### Sovelluslogiikka

Sovelluksen toiminnallisuudesta vastaavat luokat [Planet](https://github.com/anttkukk/otm-harjoitustyo/blob/master/PlanetSim/src/main/java/planetsim/domain/Planet.java) ja [PlanetSystem](https://github.com/anttkukk/otm-harjoitustyo/blob/master/PlanetSim/src/main/java/planetsim/domain/PlanetSystem.java)
Planet luokka käyttää hyväkseen luokkaa [Vector](https://github.com/anttkukk/otm-harjoitustyo/blob/master/PlanetSim/src/main/java/planetsim/domain/Vector.java). Planet luokan paikka, nopeus ja kiihtyvyys ovat vektoreita. 
Planet luokassa on metodit numeerisen integroinnin suoritukseen:
* Vector newPos(int t) – laskee uuden paikan vektorin aika-askeleen t päähän
* Vector newAcc(ArrayList<Planet> planets) – laskee uuden kiihtyvyyden muiden kappaleiden voimien summana
* Vector newVelo(int t) – laskee uuden nopeuden vektorin aika-askeleen t päähän
PlanetSystem luokka pitää sisällään listan planeetoista ja metodin update(int timestep), jonka avulla numeerinen integrointi voidaan suorittaa. Tätä metodia kutsutaan käyttöliittymässä animaatiossa, jolloin systeemi päivittyy animaation mukana.
Planeetat PlanetSystem saa databasesta PlanetDao luokan avulla.


Ohjelman luokka/pakkauskaavio on seuraavanlainen:
![luokkakaavio](https://github.com/anttkukk/otm-harjoitustyo/blob/master/dokumentaatio/Luokkakaavio2.png)

### Tietojen pysyväistallennus

Pakkauksen planetsim.database luokat huolehtivat tietojen tallennuksesta databaseen. PlanetDao ja Database luokka hoitavat databaseen tallennuksen ja tietojen hakemisen


### Päätoiminnallisuus

##### Planeettojen hakeminen ja update metodi

Sekvenssikaaviossa on havainnollistettu aluksi tehtävää kappaleiden hakua ja tämän jälkeen update metodin toimintaa. Update metodi on numeerisen integroinnin perusta ja sitä kutsutaan aina simulaation ollessa päällä jokaisella ruudunpäivityksellä

![Sekvenssikaavio](https://github.com/anttkukk/otm-harjoitustyo/blob/master/dokumentaatio/sekvenssi.jpeg)

### Ohjelman rakenteeseen jääneet heikkoudet

#### Käyttöliittymä
Ohjelmassa ei tällä hetkellä ole mahdollisuutta lisätä uusia systeemeitä ja Se pitäisi lisätä hetimiten

