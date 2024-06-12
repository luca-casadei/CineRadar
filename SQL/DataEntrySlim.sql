DELETE
FROM amministratore;
DELETE
FROM account;
DELETE
FROM genere;
DELETE
FROM sezione;

INSERT INTO account(Username, PASSWORD, Nome, Cognome)
VALUES ("admin",
        "ea6dc907a62197d8b424b12d78b44dbd374fab1cef45b46897d9b88ebb6a8fa95453b15df2b79a26b8c25ff79995e0d2e2c952dbcb70335daec71383a435b78f",
        "Martino", "Campanaro");
INSERT INTO amministratore(Username, NumeroTelefono)
VALUES ("admin", "1234567890");

INSERT INTO genere(Nome, Descrizione)
VALUES ("Horror", "Che paura");
INSERT INTO genere(Nome, Descrizione)
VALUES ("Rosa", "Che romantico");
INSERT INTO genere(Nome, Descrizione)
VALUES ("Giallo", "Assassinio");

INSERT INTO sezione(Nome, Dettaglio)
VALUES ("Trama", "La storia");
INSERT INTO sezione(Nome, Dettaglio)
VALUES ("Effetti", "Le particelle e scenografia");
INSERT INTO sezione(Nome, Dettaglio)
VALUES ("Cast", "Bravura degli attori");