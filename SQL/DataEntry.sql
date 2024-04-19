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

ALTER TABLE cinema AUTO_INCREMENT 1;

INSERT INTO account(Username, Password, Nome, Cognome)
VALUES ("admin",
        "ea6dc907a62197d8b424b12d78b44dbd374fab1cef45b46897d9b88ebb6a8fa95453b15df2b79a26b8c25ff79995e0d2e2c952dbcb70335daec71383a435b78f",
        "Martino", "Campanaro");
INSERT INTO amministratore(Username, NumeroTelefono)
VALUES ("admin", "1234567890");
INSERT INTO account(Username, PASSWORD, Nome, Cognome)
VALUES ("luca",
        "ee579b62aa913de2047346be9e4e5d3b92d6bb2e09f527633dfe4b1d1e22b70da40ea575320f708457fc340101890da31a774a6bdf59cefe00ac9cc3ab68a4cb",
        "Luca", "Casadei");
INSERT INTO utente(Username, TargaPremio, DataNascita)
VALUES ("luca", FALSE, "2003-06-19");
INSERT INTO cinema(Nome, Ind_Via, Ind_CAP, Ind_Civico, Ind_Citta)
VALUES ("UCI - Savignano", "Piazza Metropolis", "47039", 1, "Savignano sul Rubicone");
INSERT INTO account(Username, PASSWORD, Nome, Cognome)
VALUES ("reguci1",
        "4b1eeaa6328550c544a4f01fbd22295bf14a5ec688b50ac8f4f368465c319ecc85a0072ca7eaf19d78a64bf1e3b0bad325bd3b986ee2f575da73e552b0adf27f",
        "Renato", "Bruni"); -- pwd = bruno --
INSERT INTO registratore(Username, EmailCinema, CodiceCinema)
VALUES ("reguci1", NULL, 1);