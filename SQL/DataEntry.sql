DELETE FROM amministratore;
DELETE FROM utente;
DELETE FROM account;

INSERT INTO account(Username, Password, Nome, Cognome) VALUES("admin","ea6dc907a62197d8b424b12d78b44dbd374fab1cef45b46897d9b88ebb6a8fa95453b15df2b79a26b8c25ff79995e0d2e2c952dbcb70335daec71383a435b78f","Martino","Campanaro");
INSERT INTO amministratore(Username, NumeroTelefono) VALUES("admin","3496093038");
INSERT INTO account(Username,PASSWORD,Nome,Cognome) VALUES("luca","ee579b62aa913de2047346be9e4e5d3b92d6bb2e09f527633dfe4b1d1e22b70da40ea575320f708457fc340101890da31a774a6bdf59cefe00ac9cc3ab68a4cb","Luca","Casadei");
INSERT INTO utente(Username,TargaPremio,DataNascita) VALUES("luca",FALSE,"2003-06-19");