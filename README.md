# 🔧 Scrapyard Management System (Java + MySQL)

Et konsollbasert administrasjonssystem for en bilopphugger / skraphandel.  
Utviklet i Java med JDBC og MySQL som del av en **eksamensoppgave i objektorientert programmering**.

Prosjektet lar brukeren importere data om skraphandlere og kjøretøy (fossilbil, elbil og motorsykkel), lagre data i en database, og hente ut informasjon gjennom ulike SQL-spørringer.

---

## 🎯 Formål med prosjektet

Prosjektet ble levert som eksamen, og skulle demonstrere praktisk forståelse av:
- Strukturering av kode ved bruk av arv, abstrakte klasser og spesialiserte subklasser  
  (`Vehicles` → `FossilCar`, `ElectricCar`, `MotorCycle`)
- Oppkobling mellom Java og MySQL via **JDBC**
- Korrekt bruk av **PreparedStatement** for sikker og effektiv databasekommunikasjon
- Utførelse av **SELECT**, **INSERT**, **SUM** og filtrering mot databasen
- Utvikling av et konsollbasert menysystem som gir brukeren enkel tilgang til funksjonaliteten

Prosjektet representerer en eksamensbesvarelse, og fokuserer på å vise **praktisk anvendelse av objektorienterte prinsipper og databaseintegrasjon** i Java.

---

## 🧠 Funksjonalitet i programmet

Når `Main` kjøres, starter applikasjonen en meny (fra `Program.run()`):

```text
--- Meny ---
0: Importer data fra fil (advarsel: gir feil hvis data finnes fra før)
1: Vis informasjon om alle kjøretøy.
2: Vis informasjon om hvor mye drivstoff som befinner seg i fossilbilene totalt.
3: Vis informasjon om alle kjøretøy som er kjørbare.
4: Vis kjøretøy fra et valgt år (2010 eller nyere).
5: Avslutt program
