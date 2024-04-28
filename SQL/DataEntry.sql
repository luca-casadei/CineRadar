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
FROM partecipazione_cast;
DELETE
FROM membrocast;
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

-- Utente luca con username "luca"
INSERT INTO account(Username, PASSWORD, Nome, Cognome)
VALUES ("luca",
        "74307c667a965cc5d3f65a06982994f6294c39767010382659a4629fd918195a8e255c5606f1ee157f8e9a8e59b25c6b55521ea3b9955b0effd2e6aaacee8984",
        "Luca", "Casadei"); -- Password: granella --

INSERT INTO utente(Username, DataNascita)
VALUES ("luca", "2003-06-19");

-- Utente anna con username "anna"
INSERT INTO account(Username, PASSWORD, Nome, Cognome)
VALUES ("anna",
        "74307c667a965cc5d3f65a06982994f6294c39767010382659a4629fd918195a8e255c5606f1ee157f8e9a8e59b25c6b55521ea3b9955b0effd2e6aaacee8984",
        "Anna", "Rossi"); -- Password: granella --

INSERT INTO utente(Username, DataNascita)
VALUES ("anna", "1985-02-20");

-- Utente mario con username "mario"
INSERT INTO account(Username, PASSWORD, Nome, Cognome)
VALUES ("mario",
        "74307c667a965cc5d3f65a06982994f6294c39767010382659a4629fd918195a8e255c5606f1ee157f8e9a8e59b25c6b55521ea3b9955b0effd2e6aaacee8984",
        "Mario", "Rossi"); -- Password: granella --

INSERT INTO utente(Username, DataNascita)
VALUES ("mario", "1995-03-10");

-- Utente sara con username "sara"
INSERT INTO account(Username, PASSWORD, Nome, Cognome)
VALUES ("sara",
        "74307c667a965cc5d3f65a06982994f6294c39767010382659a4629fd918195a8e255c5606f1ee157f8e9a8e59b25c6b55521ea3b9955b0effd2e6aaacee8984",
        "Sara", "Bianchi"); -- Password: granella --

INSERT INTO utente(Username, DataNascita)
VALUES ("sara", "1990-07-25");
-- Utente giulia con username "giulia"
INSERT INTO account(Username, PASSWORD, Nome, Cognome)
VALUES ("giulia",
        "74307c667a965cc5d3f65a06982994f6294c39767010382659a4629fd918195a8e255c5606f1ee157f8e9a8e59b25c6b55521ea3b9955b0effd2e6aaacee8984",
        "Giulia", "Bianchi"); -- Password: granella --

INSERT INTO utente(Username, DataNascita)
VALUES ("giulia", "1990-08-12");

-- Utente marco con username "marco"
INSERT INTO account(Username, PASSWORD, Nome, Cognome)
VALUES ("marco",
        "74307c667a965cc5d3f65a06982994f6294c39767010382659a4629fd918195a8e255c5606f1ee157f8e9a8e59b25c6b55521ea3b9955b0effd2e6aaacee8984",
        "Marco", "Verdi"); -- Password: granella --

INSERT INTO utente(Username, DataNascita)
VALUES ("marco", "1988-12-03");

-- Utente paolo con username "paolo"
INSERT INTO account(Username, PASSWORD, Nome, Cognome)
VALUES ("paolo",
        "74307c667a965cc5d3f65a06982994f6294c39767010382659a4629fd918195a8e255c5606f1ee157f8e9a8e59b25c6b55521ea3b9955b0effd2e6aaacee8984",
        "Paolo", "Neri"); -- Password: granella --

INSERT INTO utente(Username, DataNascita)
VALUES ("paolo", "1987-09-15");

-- Utente francesca con username "francesca"
INSERT INTO account(Username, PASSWORD, Nome, Cognome)
VALUES ("francesca",
        "74307c667a965cc5d3f65a06982994f6294c39767010382659a4629fd918195a8e255c5606f1ee157f8e9a8e59b25c6b55521ea3b9955b0effd2e6aaacee8984",
        "Francesca", "Russo"); -- Password: granella --

INSERT INTO utente(Username, DataNascita)
VALUES ("francesca", "1992-05-20");

-- Utente giorgio con username "giorgio"
INSERT INTO account(Username, PASSWORD, Nome, Cognome)
VALUES ("giorgio",
        "74307c667a965cc5d3f65a06982994f6294c39767010382659a4629fd918195a8e255c5606f1ee157f8e9a8e59b25c6b55521ea3b9955b0effd2e6aaacee8984",
        "Giorgio", "Gialli"); -- Password: granella --

INSERT INTO utente(Username, DataNascita)
VALUES ("giorgio", "1985-11-28");

-- Utente eleonora con username "eleonora"
INSERT INTO account(Username, PASSWORD, Nome, Cognome)
VALUES ("eleonora",
        "74307c667a965cc5d3f65a06982994f6294c39767010382659a4629fd918195a8e255c5606f1ee157f8e9a8e59b25c6b55521ea3b9955b0effd2e6aaacee8984",
        "Eleonora", "Verdi"); -- Password: granella --

INSERT INTO utente(Username, DataNascita)
VALUES ("eleonora", "1998-03-02");

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
        "Ernesto", "Scandoli"); -- Pw: romolo --
INSERT INTO registratore(Username, EmailCinema, CodiceCinema)
VALUES ("reguci1", NULL, 1);
INSERT INTO registratore(Username, EmailCinema, CodiceCinema)
VALUES ("reg", "ersca@ucicinemas.it", 1);
INSERT INTO account(Username, PASSWORD,Nome,Cognome)
VALUES ("tes","a3be67122051b5362984184e412dc47ed61347e610588a9e3c639d3a99de8aa80595207f2001d6c40439f141aac5927b3dbdf254bbc4d0073393bb13d5962078","Rato","Tesse");
INSERT INTO utente(Username, DataNascita) -- Pw: tes --
VALUES ("tes", "2001-10-11");

-- Inserisci il primo cast
INSERT INTO casting(Nome) VALUES ('Unbroken Cast');

-- Inserisci il primo film
INSERT INTO film (Codice, Titolo, EtaLimite, Trama, Durata, CodiceCast)
VALUES (1, 'Unbroken', 16, 'Film sulla seconda guerra mondiale', 120, 1);

-- Inserisci membri del cast
INSERT INTO membrocast (Codice, Nome, Cognome, DataNascita, TipoAttore, TipoRegista, DataDebuttoCarriera, NomeArte)
VALUES (1, 'Jack', 'Connell', '1990-09-01', true, false, '2005-05-13', 'Jack Connell');

-- Associa il membro del cast al cast del film
INSERT INTO partecipazione_cast (CodiceMembro, CodiceCast)
VALUES (1, 1);

-- Inserisci il cast per il secondo film
INSERT INTO casting(Nome) VALUES ('Inception Cast');

-- Inserisci il secondo film
INSERT INTO film (Codice, Titolo, EtaLimite, Trama, Durata, CodiceCast)
VALUES (2, 'Inception', 14, 'Film di fantascienza e azione', 148, 2);

-- Inserisci membri del cast per il secondo film
INSERT INTO membrocast (Codice, Nome, Cognome, DataNascita, TipoAttore, TipoRegista, DataDebuttoCarriera, NomeArte)
VALUES
    (2, 'Leonardo', 'DiCaprio', '1974-11-11', true, false, '1989-02-08', 'Leonardo DiCaprio'),
    (3, 'Joseph', 'Gordon-Levitt', '1981-02-17', true, false, '1988-06-19', 'Joseph Gordon-Levitt');

-- Associa i membri del cast al cast del secondo film
INSERT INTO partecipazione_cast (CodiceMembro, CodiceCast)
VALUES
    (2, 2),
    (3, 2);

-- Inserisci il cast per il terzo film
INSERT INTO casting(Nome) VALUES ('The Matrix Cast');

-- Inserisci il terzo film
INSERT INTO film (Codice, Titolo, EtaLimite, Trama, Durata, CodiceCast)
VALUES (3, 'The Matrix', 14, 'Film di fantascienza e azione', 136, 3);

-- Inserisci membri del cast per il terzo film
INSERT INTO membrocast (Codice, Nome, Cognome, DataNascita, TipoAttore, TipoRegista, DataDebuttoCarriera, NomeArte)
VALUES
    (4, 'Keanu', 'Reeves', '1964-09-02', true, false, '1984-01-01', 'Keanu Reeves'),
    (5, 'Carrie-Anne', 'Moss', '1967-08-21', true, false, '1988-01-01', 'Carrie-Anne Moss'),
    (6, 'Lana', 'Wachowski', '1965-06-21', false, true, '1995-01-01', 'Lana Wachowski'),
    (7, 'Lilly', 'Wachowski', '1967-12-29', false, true, '1995-01-01', 'Lilly Wachowski');

-- Associa i membri del cast al cast del terzo film
INSERT INTO partecipazione_cast (CodiceMembro, CodiceCast)
VALUES
    (4, 3),
    (5, 3),
    (6, 3),
    (7, 3);

-- Inserisci il cast per il quarto film
INSERT INTO casting(Nome) VALUES ('Interstellar Cast');

-- Inserisci il quarto film
INSERT INTO film (Codice, Titolo, EtaLimite, Trama, Durata, CodiceCast)
VALUES (4, 'Interstellar', 12, 'Film di fantascienza e avventura', 169, 4);

-- Inserisci membri del cast per il quarto film
INSERT INTO membrocast (Codice, Nome, Cognome, DataNascita, TipoAttore, TipoRegista, DataDebuttoCarriera, NomeArte)
VALUES
    (8, 'Matthew', 'McConaughey', '1969-11-04', true, false, '1991-06-01', 'Matthew McConaughey'),
    (9, 'Anne', 'Hathaway', '1982-11-12', true, false, '1999-09-01', 'Anne Hathaway'),
    (10, 'Christopher', 'Nolan', '1970-07-30', false, true, '1998-01-01', 'Christopher Nolan');

-- Associa i membri del cast al cast del quarto film
INSERT INTO partecipazione_cast (CodiceMembro, CodiceCast)
VALUES
    (8, 4),
    (9, 4),
    (10, 4);

-- Inserisci il cast per il quinto film
INSERT INTO casting(Nome) VALUES ('The Dark Knight Cast');

-- Inserisci il quinto film
INSERT INTO film (Codice, Titolo, EtaLimite, Trama, Durata, CodiceCast)
VALUES (5, 'The Dark Knight', 14, 'Film di azione e thriller', 152, 5);

-- Inserisci membri del cast per il quinto film
INSERT INTO membrocast (Codice, Nome, Cognome, DataNascita, TipoAttore, TipoRegista, DataDebuttoCarriera, NomeArte)
VALUES
    (11, 'Christian', 'Bale', '1974-01-30', true, false, '1986-01-01', 'Christian Bale'),
    (12, 'Heath', 'Ledger', '1979-04-04', true, false, '1999-01-01', 'Heath Ledger');

-- Associa i membri del cast al cast del quinto film
INSERT INTO partecipazione_cast (CodiceMembro, CodiceCast)
VALUES
    (11, 5),
    (12, 5);

-- Recensioni per il primo film
INSERT INTO recfilm(UsernameUtente, CodiceFilm, Titolo, Descrizione, VotoComplessivo)
VALUES
    ("luca", 1, "Bello ma poco coinvolgente",
     "Mi è piaciuto il film, lo ho trovato molto realistico e crudo, però troppo poco coinvolgente per lo spettatore",
     8),
    ("giulia", 1, "Emozionante e ben recitato",
     "Film molto toccante e ben recitato. La storia è coinvolgente e le interpretazioni degli attori sono eccellenti",
     9),
    ("marco", 1, "Da vedere assolutamente",
     "Uno dei migliori film di guerra che abbia mai visto. La regia è impeccabile e la trama tiene con il fiato sospeso fino alla fine",
     10);

-- Recensioni per il secondo film
INSERT INTO recfilm(UsernameUtente, CodiceFilm, Titolo, Descrizione, VotoComplessivo)
VALUES
    ("anna", 2, "Fantastico!",
     "Incredibile film d'azione con una trama avvincente. Consiglio a tutti di vederlo almeno una volta",
     9),
    ("mario", 2, "Molto deludente",
     "Mi aspettavo di più da questo film. La trama è banale e gli effetti speciali non sono all'altezza",
     5);

-- Recensioni per il terzo film
INSERT INTO recfilm(UsernameUtente, CodiceFilm, Titolo, Descrizione, VotoComplessivo)
VALUES
    ("eleonora", 3, "Capolavoro indiscusso",
     "Interstellar è un viaggio emozionante nello spazio e nel tempo. Una trama complessa ma ben sviluppata e un cast eccezionale",
     10),
    ("giorgio", 3, "Film confusionario",
     "Non sono riuscito a seguire la trama, mi è sembrato tutto molto confuso. Peccato perché aveva delle ottime premesse",
     6);

-- Recensioni per il quarto film
INSERT INTO recfilm(UsernameUtente, CodiceFilm, Titolo, Descrizione, VotoComplessivo)
VALUES
    ("francesca", 4, "Intrigante e coinvolgente",
     "Interstellar è un film che ti cattura dall'inizio alla fine. La trama è avvincente e il cast è eccezionale",
     9),
    ("paolo", 4, "Non all'altezza delle aspettative",
     "Mi aspettavo di più da Christopher Nolan. Il film è troppo prolisso e ha alcuni buchi nella trama",
     7);

-- Recensioni per il quinto film
INSERT INTO recfilm(UsernameUtente, CodiceFilm, Titolo, Descrizione, VotoComplessivo)
VALUES
    ("giulia", 5, "Capolavoro senza tempo",
     "The Dark Knight è uno dei migliori film di supereroi mai realizzati. La performance di Heath Ledger è straordinaria",
     10),
    ("luca", 5, "Trama avvincente e attori fantastici",
     "Un film che tiene con il fiato sospeso dall'inizio alla fine. Christian Bale e Heath Ledger sono incredibili",
     9);
