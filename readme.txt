Compilazione:
Entrare nella cartella ed eseguire:
- Windows
.\gradlew.bat build
- Linux
./gradlew build

Esecuzione:
- Windows
.\gradlew.bat run
- Linux
./gradlew run
In alternativa per eseguire dalla cartella root:
java -jar ./app/build/libs/app-all.jar

DBMS:
Come DBMS è stata usata l'alternativa open source di MySQL
MariaDB, quindi è necessario installarlo nella macchina per
poter utilizzare la connettività locale.
Bisogna quindi creare su MariaDB l'utente "cinemgr" con la password "panettone!"
ATTENZIONE, su Linux è necessario specificare "lower_case_table_names=1"
nel file di configurazione di MariaDB.

Per creare le tabelle e inserire dei dati di prova vengono 
forniti appositi file .sql e .ddl nella cartella "SQL" consegnata.

Una volta nell'app:
Qui vengono elencate le password di accesso degli utenti principali:
"admin" = "panettone!"
"luca" = "granella"

E' comunque possibile registrare altri utenti mediante le funzionalità
fornite dall'applicazione.