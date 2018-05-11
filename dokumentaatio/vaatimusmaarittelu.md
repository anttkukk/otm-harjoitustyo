# Vaatimusmäärittely
## Sovelluksen tarkoitus

Sovellus on n-kappaleen simulaatio, joka simuloi n-kappaleen tähtijärjestelmää. Sovelluksessa on oletuksena kaksi järjestelmää: Sisäplaneetat ja meidän aurinkokuntamme. Sisäplaneetta järjestelmässä on myös kuu sekä muutama komeetta mukaan lukien Churyumov-Gerasimenko, jonne Rosetta luotain taannoin laskeutui. Käyttäjällä on mahdollisuus lisätä systeemeitä valitsemalla luotujen planeettojen listasta. Tämän lisäksi käyttäjä voi lisätä uusia planeettoja. Käyttäjä voi myös poistaa lisättyjä systeemeitä ja planeettoja mutta ei oletuksena olleita, jotta ohjelmasta ei häviä kaikki kappaleet. 

## Perusversion toiminnalisuus

Ohjelma avautuu aloitusruutuun. Käyttäjä voi nyt valittaa aloittaa simulaation tai vaihtaa systeemiä systeemien listasta. Käyttäjä voi myös lisätä uuden systeemin alkuvalikosta. Painaessa Add system-nappia käyttäjältä kysytään uudessa ruudussa systeemin nimeä. Annettua nimen ja painamalla create system siirrytään seuraavaan ruutuun. On myös Go back nappi, jos käyttäjä haluaakin palata takaisin tekemättä systeemiä. Seuraavassa ruudussa vasemmalla käyttäjä voi tehdä uuden planeetan asettamalla tiedot ja painamalla add planet nappia. Oikealla taas on lista planeetoista, jonne luotu planeettakin sitten ilmestyy. Käyttäjä voi valita listalta kappaleen ja lisätä sen systeemiin, jolloin se häviää listalta. Lopuksi käyttäjä voi painaa Finish System! -nappia, jolloin käyttäjä palaa aloitusruutuun. Painamalla start nappia itse simulaatio alkaa.

Simulaation käynnissä ollessa ohjelma tulostaa kappaleiden paikkoja ylhäältä päin katsottuna. Vasemmalla yläkulmassa on tulostettuna tietoja, kuten kuinka pitkää aikaa on simuloitu ja aika-askeleen pituus. Käyttäjällä on mahdollisuus painaa nappeja kuten aika-askeleen hidastus ja nopeutus sekä pause. Tämän lisäks hiiren avulla käyttäjä voi liikuttaa ja zoomata ruutua. Painaessa planeettaa simulaatio keskittyy siihen. Hiiren oikeaa painiketta painamalla käyttäjä voi valita toisen kappaleen subtargetiksi, jolloin target kappaleen ja subtarget kappaleen välille piirretään viiva ja niiden etäisyys kerrotaan. Käyttäjä voi myös siirtyä Create modeen, jossa käyttäjällä on mahdollisuus luoda uusia pienkappaleita systeemiin. Pienkappaleet syntyvät, kun käyttäjä painaa hiiren pohjaan ja niiden ratanopeus määräytyy siitä minne käyttäjä vetää hiirensä napin ollessa painettuna. Kun käyttäjä päästää irti, luodaan uusi pienkappale systeemiin. Pienkappaleita ei tallenneta databaseen. 

## Käyttöjärjestelmä luonnos

Kuvassa käyttöjärjestelmän luonnos

![Luonnos](https://github.com/anttkukk/otm-harjoitustyo/blob/master/dokumentaatio/kayttis2.jpeg)
## Jatkokehitys

Sovellus varoittaa, jos luotu järjestelmä on hyvin selkeästi epästabiili. Esim jos käyttäjä asettaa kaksi kappaletta päällekkäin/hyvin lähelle toisiaan. Esim aurinko ja toinen keskuskappale.

Kappaleiden värin voisi asettaa kappaletta luodessa

Cancel-nappi kun systeemi on luotu ja käyttäjä on valitsemassa planeettoja systeemiin
