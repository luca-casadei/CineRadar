DELETE
FROM richiesta;
DELETE
FROM recfilm;
DELETE
FROM amministratore;
DELETE
FROM utente;
DELETE
FROM registratore;
DELETE
FROM account;
DELETE
FROM cinema;
DELETE
FROM film;
DELETE
FROM casting;
ALTER TABLE cinema
    AUTO_INCREMENT 1;
ALTER TABLE casting
    AUTO_INCREMENT 1;
INSERT INTO account(Username, PASSWORD, Nome, Cognome)
VALUES ("admin",
        "ea6dc907a62197d8b424b12d78b44dbd374fab1cef45b46897d9b88ebb6a8fa95453b15df2b79a26b8c25ff79995e0d2e2c952dbcb70335daec71383a435b78f",
        "Martino", "Campanaro");
INSERT INTO amministratore(Username, NumeroTelefono)
VALUES ("admin", "1234567890");
INSERT INTO account(Username, PASSWORD, Nome, Cognome)
VALUES ("luca",
        "74307c667a965cc5d3f65a06982994f6294c39767010382659a4629fd918195a8e255c5606f1ee157f8e9a8e59b25c6b55521ea3b9955b0effd2e6aaacee8984",
        "Luca", "Casadei"); -- Password: granella --
INSERT INTO utente(Username, TargaPremio, DataNascita)
VALUES ("luca", FALSE, "2003-06-19");
INSERT INTO cinema(Nome, Ind_Via, Ind_CAP, Ind_Civico, Ind_Citta)
VALUES ("UCI - Savignano", "Piazza Metropolis", "47039", 1, "Savignano sul Rubicone");
INSERT INTO cinema(Nome, Ind_Via, Ind_CAP, Ind_Civico, Ind_Citta)
VALUES ("Eliseo", "Viale Giosuè Carducci", "47521", 7, "Cesena");
INSERT INTO account(Username, PASSWORD, Nome, Cognome)
VALUES ("reguci1",
        "4b1eeaa6328550c544a4f01fbd22295bf14a5ec688b50ac8f4f368465c319ecc85a0072ca7eaf19d78a64bf1e3b0bad325bd3b986ee2f575da73e552b0adf27f",
        "Renato", "Bruni"); -- Password: bruno --
INSERT INTO account(Username, PASSWORD, Nome, Cognome)
VALUES ("reg",
        "cc37ae468020f0e2a7c450ca95bddbaa9dde4406ac6e89172a7784c0ed7e148888e705b86917d038906735dc598e927f2bda3b7036b5886d78d19eca5c6bdcc1",
        "Ernesto", "Scandoli");
INSERT INTO registratore(Username, EmailCinema, CodiceCinema)
VALUES ("reguci1", NULL, 1);
INSERT INTO registratore(Username, EmailCinema, CodiceCinema)
VALUES ("reg", "ersca@ucicinemas.it", 1);
INSERT INTO casting(Nome)
VALUES ("Unbroken Cast");
INSERT INTO film(Codice, Titolo, EtaLimite, Trama, Durata, CodiceCast)
VALUES (1, "Unbroken", 16, "Film della seconda guerra mondiale", 120, 1);
INSERT INTO recfilm(UsernameUtente, CodiceFilm, Titolo, Descrizione, VotoComplessivo)
VALUES ("luca", 1, "Bello ma poco coinvolgente",
        "Mi è piaciuto il film, lo ho trovato molto realistico e crudo, però troppo poco coinvolgente per lo spettatore",
        8);