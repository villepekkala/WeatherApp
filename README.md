# Viikko 6

# Mitä Room tekee ja arkkitehtuuri sovelluksessa

Room on Googlen kirjasto, joka tekee SQLite-tietokannan käytöstä helppoa. Tämän avulla ei tarvitse käyttää pitkiä pätkiä SQL koodia.

**Entity** antaa muotin tietokannan tablelle. Esim kaupungin, kuvauksen ja lämpötilan. 

**DAO** Lyhennetty Data Access Objectista. Sisältää niin sanotusti komennot tietokannan lukemiseen ja kirjoittamiseen.

**Database** On varsinainen tietokantaluokka, mikä sitoo DAOn ja Entityn yhteen.

**repository** Päättää, että haetaanko data paikallisesti Roomista vai verkon yli APIsta. Esim hakuhistoria haetaan paikallisesti mutta sitten kaupungin sää haetaan verkon yli APIn avulla.

**ViewModel** Toimii datan ja käyttöliittymän välissä. Se esim muuttaa tietokannan virran (flow) käyttöliittymälle sopivaksi tilaksi StateFlowksi.

**UI** Toimii reaktiivisesti ja kuuntelee vain ViewModelin tilaa collectAsState funktiolla ja piirtää itsensä uusiksi kun tila muuttuu.

# Projektin rakenne
**Data** /local, sisältää databasen, Entityn ja DAOn. /remote, sisältää tarvittavat verkkohaulle kuten APIn ja rajapinnan. /repository, hallitsee datan ja välimuistilogiikan.

**ViewModel** Sisältää WeatherViewModelin ja ui-tilojen määrittelyn.

**View** Sisältää Jetpack compose käyttöliittymän kuten WeatherScreenin.


# Datavirta
Silloin kun tietokantaan tallennetaan uusi haku, niin tällöin Room puskee päivitetyn listan jatkuvaan Flow-virtaan. ViewModeli ottaa tämän virran vastaan ja muuntaa sen StateIn funktiota käyttäen StateFlowiksi, jotta käyttöliittymä pystyy tätä kuuntelemaan. Käyttöliittymä kuuntelee tilaa jatkuvasti CollectAsStatella ja kun tila päivittyy niin käyttöliittymä piirtää itsensä uusiksi Compose funktion avulla, jolloin hakuhistoria päivittyy. 

# Välimuisti logiikka
Käyttäjän etsiessä kaupunkia, niin repository tarkistaa ensin Room tietokannasta, löytyykö kyseinen sää jo sieltä. Jos data löytyy ja sen aikaleimasta on alle 30 minuuttia niin se näyttää sen datan, jolloin API hakua ei tehdä. Jos dataa ei löydy tai se on yli 30 minuuttia vanhaa dataa niin tällöin repository hakee uusimman sään OpenWeatherista. Haettu uusi sää tallennetaan ensin tietokantaan, minkä jälkeen se näytetään käyttäjälle ja myöskin hakuhistoria päivittyy automaattisesti. Haku on tosin "kirjain sensitiivinen" eli jos hakee isolla kirjaimella ja entinen haku on pienellä niin se hakee uuden tiedon.

# Youtube
https://youtu.be/fRU6LOuzGVI