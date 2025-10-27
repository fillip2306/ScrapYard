# 🔧 Scrapyard Management System (Java + MySQL)

Et konsollbasert administrasjonssystem for en bilopphugger / skraphandel.  
Laget i Java med JDBC og MySQL som del av en eksamensoppgave / innlevering i programmering.

Prosjektet lar deg importere data om skraphandlere og kjøretøy (fossilbil, elbil og motorsykkel), lagre dette i database, og hente ut informasjon gjennom ulike spørringer.

---

## 🎯 Hva var målet med prosjektet?

Målet var å vise at jeg kan:
- Strukturere kode med arv, abstrakte klasser og spesialiserte subklasser (`Vehicles` → `FossilCar`, `ElectricCar`, `MotorCycle`)
- Koble Java til en ekte database med `JDBC`
- Bruke `PreparedStatement` riktig (for å unngå SQL injection og for å sende inn parametere trygt)
- Kjøre SELECT, INSERT, SUM og filtrering mot databasen
- Lage et lite "system" med meny i konsollen som lar brukeren kjøre ulike operasjoner

Dette er ikke et ferdig produkt, men en faglig øving i objektorientert Java + database.

---

## 🧠 Funksjonalitet i programmet

Når du kjører `Main`, starter programmet en meny (i `Program.run()`):

```text
--- Meny ---
0: Importer data fra fil (advarsel: gir feil hvis data finnes fra før)
1: Vis informasjon om alle kjøretøy.
2: Vis informasjon om hvor mye drivstoff som befinner seg i fossilbilene totalt.
3: Vis informasjon om alle kjøretøy som er kjørbare.
4: Vis kjøretøy fra et valgt år (2010 eller nyere).
5: Avslutt program
