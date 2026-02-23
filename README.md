# Android Sääsovellus

Tämä on Android-projekti Kotlinilla, joka hakee reaaliaikaiset säätiedot halutulle kaupungille ja näyttää käyttäjän hakuhistorian. Säätiedot haetaan livenä OpenWeather API -rajapinnasta (https://openweathermap.org/). Käyttäjä syöttää halutun kaupungin hakuun ja sovellus tekee pyynnön rajapinnalle ja saa vastauksena kaupungin lämpötilan ja pienen kuvauksen säästä (esim. Snow / clear sky).

## Projektin arkkitehtuuri ja elementit

Projekti on tehty MVVM-arkkitehtuurin mukaisesti. 

Käytetyt elementit ja teknologiat:
* **Jetpack Compose:** Sovelluksen UI on rakennettu reaktiivisesti ja päivittyy automaattisesti tilan muuttuessa.
* **Retrofit:** Tekee API-pyynnöt ja vastaa JSON-mallisten vastausten muuttamisesta dataolioiksi, joita Kotlin ymmärtää.
* **Room database:** Googlen luoma kirjasto, joka luo paikallisen SQLite-tietokannan. Tämä tallentaa käyttäjän tekemät haut ja saadut tulokset puhelimen muistiin.
* **Kotlin Coroutines ja Flow:** Hoitavat verkkohaut ja tietokantakyselyt niin, että käyttöliittymä ei jäädy.

## Projektin välimuisti

Projektissa käytetään Room-tietokantaa välimuistina:

* Kun kaupunkia haetaan, sovellus tarkistaa ensin paikallisen tietokannan. Jos kaupungin tietoa ei löydy tietokannasta, niin sovellus hakee nykyisen sään OpenWeather API:sta, tallentaa sen Roomiin ja päivittää näkymän. Samalla tallennus saa aikaleiman (timestamp), josta selviää milloin haku on tehty.
* Jos haku löytyy jo tietokannasta ja tämä haku on tehty alle 30 minuuttia sitten, niin sovellus näyttää tämän tiedon eli tällöin ei haeta nykyistä säätä rajapinnasta. Jos on kuitenkin kulunut yli 30 minuuttia, niin tällöin tehdään uusi haku rajapintaan ja haetaan tuorein sää OpenWeatherista.

Tämä projekti on toteutettu osana Android-ohjelmoinnin kurssin viikkotehtäviä
