DELETE
FROM richiesta;
DELETE
FROM sezionamento_film;
DELETE
FROM sezionamento_serie;
DELETE
FROM categorizzazione_film;
DELETE
FROM categorizzazione_serie;
DELETE
FROM preferenze;
DELETE
FROM sezione;
DELETE
FROM tessera;
DELETE
FROM recfilm;
DELETE
FROM visualizzazioni_film;
DELETE
FROM recserie;
DELETE
FROM visualizzazioni_episodio;
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
FROM episodio;
DELETE
FROM stagione;
DELETE
FROM serie;
DELETE
FROM partecipazione_cast;
DELETE
FROM membrocast;
DELETE
FROM casting;
DELETE
FROM genere;
ALTER TABLE cinema
    AUTO_INCREMENT 1;
ALTER TABLE casting
    AUTO_INCREMENT 1;
ALTER TABLE film
    AUTO_INCREMENT 1;
ALTER TABLE serie
    AUTO_INCREMENT 1;
ALTER TABLE membrocast
    AUTO_INCREMENT 1;
ALTER TABLE richiesta
    AUTO_INCREMENT 1;
ALTER TABLE templatepromo
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
INSERT INTO account(Username, PASSWORD, Nome, Cognome)
VALUES ("reg2",
        "cc37ae468020f0e2a7c450ca95bddbaa9dde4406ac6e89172a7784c0ed7e148888e705b86917d038906735dc598e927f2bda3b7036b5886d78d19eca5c6bdcc1",
        "Pietrello", "Amiri"); -- Pw: romolo --
INSERT INTO registratore(Username, EmailCinema, CodiceCinema)
VALUES ("reguci1", NULL, 1);
INSERT INTO registratore(Username, EmailCinema, CodiceCinema)
VALUES ("reg", "ersca@ucicinemas.it", 1);
INSERT INTO registratore(Username, EmailCinema, CodiceCinema)
VALUES ("reg2", NULL, 2);
INSERT INTO account(Username, PASSWORD, Nome, Cognome)
VALUES ("tes",
        "a3be67122051b5362984184e412dc47ed61347e610588a9e3c639d3a99de8aa80595207f2001d6c40439f141aac5927b3dbdf254bbc4d0073393bb13d5962078",
        "Rato", "Tesse");
INSERT INTO utente(Username, DataNascita) -- Pw: tes --
VALUES ("tes", "2001-10-11");

--
-- PARTE FILM
--

-- Inserisci il primo cast
INSERT INTO casting(Nome)
VALUES ('Unbroken Cast');

-- Inserisci il primo film
INSERT INTO film (Titolo, EtaLimite, Trama, Durata, CodiceCast)
VALUES ('Unbroken', 16, 'Film sulla seconda guerra mondiale', 120, 1);

-- Inserisci membri del cast
INSERT INTO membrocast (Nome, Cognome, DataNascita, TipoAttore, TipoRegista, DataDebuttoCarriera, NomeArte)
VALUES ('Jack', 'Connell', '1990-09-01', true, false, '2005-05-13', 'Jack Connell');

-- Associa il membro del cast al cast del film
INSERT INTO partecipazione_cast (CodiceMembro, CodiceCast)
VALUES (1, 1);

-- Inserisci il cast per il secondo film
INSERT INTO casting(Nome)
VALUES ('Inception Cast');

-- Inserisci il secondo film
INSERT INTO film (Titolo, EtaLimite, Trama, Durata, CodiceCast)
VALUES ('Inception', 14, 'Film di fantascienza e azione', 148, 2);

-- Inserisci membri del cast per il secondo film
INSERT INTO membrocast (Nome, Cognome, DataNascita, TipoAttore, TipoRegista, DataDebuttoCarriera, NomeArte)
VALUES ('Leonardo', 'DiCaprio', '1974-11-11', true, false, '1989-02-08', 'Leonardo DiCaprio'),
       ('Joseph', 'Gordon-Levitt', '1981-02-17', true, false, '1988-06-19', 'Joseph Gordon-Levitt');

-- Associa i membri del cast al cast del secondo film
INSERT INTO partecipazione_cast (CodiceMembro, CodiceCast)
VALUES (2, 2),
       (3, 2);

-- Inserisci il cast per il terzo film
INSERT INTO casting(Nome)
VALUES ('The Matrix Cast');

-- Inserisci il terzo film
INSERT INTO film (Titolo, EtaLimite, Trama, Durata, CodiceCast)
VALUES ('The Matrix', 14, 'Film di fantascienza e azione', 136, 3);

-- Inserisci membri del cast per il terzo film
INSERT INTO membrocast (Nome, Cognome, DataNascita, TipoAttore, TipoRegista, DataDebuttoCarriera, NomeArte)
VALUES ('Keanu', 'Reeves', '1964-09-02', true, false, '1984-01-01', 'Keanu Reeves'),
       ('Carrie-Anne', 'Moss', '1967-08-21', true, false, '1988-01-01', 'Carrie-Anne Moss'),
       ('Lana', 'Wachowski', '1965-06-21', false, true, '1995-01-01', 'Lana Wachowski'),
       ('Lilly', 'Wachowski', '1967-12-29', false, true, '1995-01-01', 'Lilly Wachowski');

-- Associa i membri del cast al cast del terzo film
INSERT INTO partecipazione_cast (CodiceMembro, CodiceCast)
VALUES (4, 3),
       (5, 3),
       (6, 3),
       (7, 3);

-- Inserisci il cast per il quarto film
INSERT INTO casting(Nome)
VALUES ('Interstellar Cast');

-- Inserisci il quarto film
INSERT INTO film (Titolo, EtaLimite, Trama, Durata, CodiceCast)
VALUES ('Interstellar', 12, 'Film di fantascienza e avventura', 169, 4);

-- Inserisci membri del cast per il quarto film
INSERT INTO membrocast (Nome, Cognome, DataNascita, TipoAttore, TipoRegista, DataDebuttoCarriera, NomeArte)
VALUES ('Matthew', 'McConaughey', '1969-11-04', true, false, '1991-06-01', 'Matthew McConaughey'),
       ('Anne', 'Hathaway', '1982-11-12', true, false, '1999-09-01', 'Anne Hathaway'),
       ('Christopher', 'Nolan', '1970-07-30', false, true, '1998-01-01', 'Christopher Nolan');

-- Associa i membri del cast al cast del quarto film
INSERT INTO partecipazione_cast (CodiceMembro, CodiceCast)
VALUES (8, 4),
       (9, 4),
       (10, 4);

-- Inserisci il cast per il quinto film
INSERT INTO casting(Nome)
VALUES ('The Dark Knight Cast');

-- Inserisci il quinto film
INSERT INTO film (Titolo, EtaLimite, Trama, Durata, CodiceCast)
VALUES ('The Dark Knight', 14, 'Film di azione e thriller', 152, 5);

-- Inserisci membri del cast per il quinto film
INSERT INTO membrocast (Nome, Cognome, DataNascita, TipoAttore, TipoRegista, DataDebuttoCarriera, NomeArte)
VALUES ('Christian', 'Bale', '1974-01-30', true, false, '1986-01-01', 'Christian Bale'),
       ('Heath', 'Ledger', '1979-04-04', true, false, '1999-01-01', 'Heath Ledger');

-- Associa i membri del cast al cast del quinto film
INSERT INTO partecipazione_cast (CodiceMembro, CodiceCast)
VALUES (11, 5),
       (12, 5);

-- Visualizzazioni per il primo film
INSERT INTO visualizzazioni_film(CodiceFilm, UsernameUtente)
VALUES (1, "luca"),
       (1, "giulia"),
       (1, "marco");

-- Recensioni per il primo film
INSERT INTO recfilm(UsernameUtente, CodiceFilm, Titolo, Descrizione, VotoComplessivo)
VALUES ("luca", 1, "Bello ma poco coinvolgente",
        "Mi è piaciuto il film, lo ho trovato molto realistico e crudo, però troppo poco coinvolgente per lo spettatore",
        8),
       ("giulia", 1, "Emozionante e ben recitato",
        "Film molto toccante e ben recitato. La storia è coinvolgente e le interpretazioni degli attori sono eccellenti",
        9),
       ("marco", 1, "Da vedere assolutamente",
        "Uno dei migliori film di guerra che abbia mai visto. La regia è impeccabile e la trama tiene con il fiato sospeso fino alla fine",
        10);

-- Visualizzazioni per il secondo film
INSERT INTO visualizzazioni_film(CodiceFilm, UsernameUtente)
VALUES (2, "anna"),
       (2, "mario");

-- Recensioni per il secondo film
INSERT INTO recfilm(UsernameUtente, CodiceFilm, Titolo, Descrizione, VotoComplessivo)
VALUES ("anna", 2, "Fantastico!",
        "Incredibile film d'azione con una trama avvincente. Consiglio a tutti di vederlo almeno una volta",
        9),
       ("mario", 2, "Molto deludente",
        "Mi aspettavo di più da questo film. La trama è banale e gli effetti speciali non sono all'altezza",
        5);

-- Visualizzazioni per il terzo film
INSERT INTO visualizzazioni_film(CodiceFilm, UsernameUtente)
VALUES (3, "eleonora"),
       (3, "giorgio");

-- Recensioni per il terzo film
INSERT INTO recfilm(UsernameUtente, CodiceFilm, Titolo, Descrizione, VotoComplessivo)
VALUES ("eleonora", 3, "Capolavoro indiscusso",
        "Interstellar è un viaggio emozionante nello spazio e nel tempo. Una trama complessa ma ben sviluppata e un cast eccezionale",
        10),
       ("giorgio", 3, "Film confusionario",
        "Non sono riuscito a seguire la trama, mi è sembrato tutto molto confuso. Peccato perché aveva delle ottime premesse",
        6);

-- Visualizzazioni per il quarto film
INSERT INTO visualizzazioni_film(CodiceFilm, UsernameUtente)
VALUES (4, "francesca"),
       (4, "paolo");

-- Recensioni per il quarto film
INSERT INTO recfilm(UsernameUtente, CodiceFilm, Titolo, Descrizione, VotoComplessivo)
VALUES ("francesca", 4, "Intrigante e coinvolgente",
        "Interstellar è un film che ti cattura dall'inizio alla fine. La trama è avvincente e il cast è eccezionale",
        9),
       ("paolo", 4, "Non all'altezza delle aspettative",
        "Mi aspettavo di più da Christopher Nolan. Il film è troppo prolisso e ha alcuni buchi nella trama",
        7);

-- Visualizzazioni per il quinto film
INSERT INTO visualizzazioni_film(CodiceFilm, UsernameUtente)
VALUES (5, "giulia"),
       (5, "luca");

-- Recensioni per il quinto film
INSERT INTO recfilm(UsernameUtente, CodiceFilm, Titolo, Descrizione, VotoComplessivo)
VALUES ("giulia", 5, "Capolavoro senza tempo",
        "The Dark Knight è uno dei migliori film di supereroi mai realizzati. La performance di Heath Ledger è straordinaria",
        10),
       ("luca", 5, "Trama avvincente e attori fantastici",
        "Un film che tiene con il fiato sospeso dall'inizio alla fine. Christian Bale e Heath Ledger sono incredibili",
        9);


--
-- PARTE SERIE
--

-- Inserisci la serie Breaking Bad
INSERT INTO serie(Codice, Titolo, EtaLimite, Trama, DurataComplessiva, NumeroEpisodi)
VALUES (1, 'Breaking Bad', 14, 'Droga, polizia e Walter White', 2832, 62);

-- Inserisci il cast per Breaking Bad
INSERT INTO casting(Nome)
VALUES ('Breaking Bad Cast');

-- Inserisci membri del cast della prima stagione di Breaking Bad
INSERT INTO membrocast (Nome, Cognome, DataNascita, TipoAttore, TipoRegista, DataDebuttoCarriera, NomeArte)
VALUES ('Bryan', 'Cranston', '1956-03-07', true, false, '1980-05-06', 'Bryan Cranston'),
       ('Aaron', 'Paul', '1979-08-27', true, false, '1998-02-19', 'Aaron Paul');

-- Associa i membri del cast al cast della prima stagione di Breaking Bad
INSERT INTO partecipazione_cast (CodiceMembro, CodiceCast)
VALUES (13, 6),
       (14, 6);

-- Inserisci la prima stagione di Breaking Bad
INSERT INTO stagione(CodiceSerie, NumeroStagione, Sunto, CodiceCast)
VALUES (1, 1, 'Stagione molto bella', 6);

-- Inserisci episodi per la prima stagione di Breaking Bad
INSERT INTO episodio(CodiceSerie, NumeroStagione, NumeroEpisodio, DurataMin)
VALUES (1, 1, 1, 20),
       (1, 1, 2, 20),
       (1, 1, 3, 20),
       (1, 1, 4, 20),
       (1, 1, 5, 20);

-- Inserisci un nuovo cast per la seconda stagione di Breaking Bad
INSERT INTO casting(Nome)
VALUES ('Breaking Bad Cast - Stagione 2');
-- Inserisci membri del cast della seconda stagione di Breaking Bad
INSERT INTO membrocast (Nome, Cognome, DataNascita, TipoAttore, TipoRegista, DataDebuttoCarriera, NomeArte)
VALUES ('Anna', 'Gunn', '1968-08-11', true, false, '1992-06-19', 'Anna Gunn'),
       ('Dean', 'Norris', '1963-04-08', true, false, '1986-01-19', 'Dean Norris');

-- Associa i membri del cast al cast della seconda stagione di Breaking Bad
INSERT INTO partecipazione_cast (CodiceMembro, CodiceCast)
VALUES (15, 7),
       (16, 7);

-- Inserisci la seconda stagione di Breaking Bad
INSERT INTO stagione(CodiceSerie, NumeroStagione, Sunto, CodiceCast)
VALUES (1, 2, 'Stagione della crescita del business di Heisenberg', 7);

-- Inserisci episodi per la seconda stagione di Breaking Bad
INSERT INTO episodio(CodiceSerie, NumeroStagione, NumeroEpisodio, DurataMin)
VALUES (1, 2, 1, 45),
       (1, 2, 2, 45),
       (1, 2, 3, 45),
       (1, 2, 4, 45),
       (1, 2, 5, 45);

-- Inserisci un nuovo cast per la terza stagione di Breaking Bad
INSERT INTO casting(Nome)
VALUES ('Breaking Bad Cast - Stagione 3');
-- Inserisci membri del cast della terza stagione di Breaking Bad
INSERT INTO membrocast (Nome, Cognome, DataNascita, TipoAttore, TipoRegista, DataDebuttoCarriera, NomeArte)
VALUES ('Giancarlo', 'Esposito', '1958-04-26', true, false, '1980-03-19', 'Giancarlo Esposito'),
       ('Jonathan', 'Banks', '1947-01-31', true, false, '1972-11-15', 'Jonathan Banks');

-- Associa i membri del cast al cast della terza stagione di Breaking Bad
INSERT INTO partecipazione_cast (CodiceMembro, CodiceCast)
VALUES (17, 8),
       (18, 8);

-- Inserisci la terza stagione di Breaking Bad
INSERT INTO stagione(CodiceSerie, NumeroStagione, Sunto, CodiceCast)
VALUES (1, 3, 'La vendetta di Heisenberg', 8);

-- Inserisci episodi per la terza stagione di Breaking Bad
INSERT INTO episodio(CodiceSerie, NumeroStagione, NumeroEpisodio, DurataMin)
VALUES (1, 3, 1, 50),
       (1, 3, 2, 50),
       (1, 3, 3, 50),
       (1, 3, 4, 50),
       (1, 3, 5, 50);

-- Inserisci un nuovo cast per la quarta stagione di Breaking Bad
INSERT INTO casting(Nome)
VALUES ('Breaking Bad Cast - Stagione 4');
-- Inserisci membri del cast della quarta stagione di Breaking Bad
INSERT INTO membrocast (Nome, Cognome, DataNascita, TipoAttore, TipoRegista, DataDebuttoCarriera, NomeArte)
VALUES ('Bob', 'Odenkirk', '1962-10-22', true, false, '1987-09-12', 'Bob Odenkirk'),
       ('Betsy', 'Brandt', '1973-03-14', true, false, '1998-06-28', 'Betsy Brandt');

-- Associa i membri del cast al cast della quarta stagione di Breaking Bad
INSERT INTO partecipazione_cast (CodiceMembro, CodiceCast)
VALUES (19, 9),
       (20, 9);

-- Inserisci la quarta stagione di Breaking Bad
INSERT INTO stagione(CodiceSerie, NumeroStagione, Sunto, CodiceCast)
VALUES (1, 4, 'La discesa di Heisenberg', 9);

-- Inserisci episodi per la quarta stagione di Breaking Bad
INSERT INTO episodio(CodiceSerie, NumeroStagione, NumeroEpisodio, DurataMin)
VALUES (1, 4, 1, 55),
       (1, 4, 2, 55),
       (1, 4, 3, 55),
       (1, 4, 4, 55),
       (1, 4, 5, 55);

-- Inserisci la serie Game of Thrones
INSERT INTO serie(Codice, Titolo, EtaLimite, Trama, DurataComplessiva, NumeroEpisodi)
VALUES (2, 'Game of Thrones', 18, 'Intrighi, tradimenti e lotte per il potere nei Sette Regni di Westeros', 4560, 73);

-- Inserisci il cast per Game of Thrones
INSERT INTO casting(Nome)
VALUES ('Game of Thrones Cast');

-- Inserisci membri del cast della prima stagione di Game of Thrones
INSERT INTO membrocast (Nome, Cognome, DataNascita, TipoAttore, TipoRegista, DataDebuttoCarriera, NomeArte)
VALUES ('Emilia', 'Clarke', '1986-10-23', true, false, '2009-04-08', 'Emilia Clarke'),
       ('Kit', 'Harington', '1986-12-26', true, false, '2008-09-11', 'Kit Harington');

-- Associa i membri del cast al cast della prima stagione di Game of Thrones
INSERT INTO partecipazione_cast (CodiceMembro, CodiceCast)
VALUES (21, 10),
       (22, 10);

-- Inserisci la prima stagione di Game of Thrones
INSERT INTO stagione(CodiceSerie, NumeroStagione, Sunto, CodiceCast)
VALUES (2, 1, 'La lotta per il Trono di Spade inizia', 10);

-- Inserisci episodi per la prima stagione di Game of Thrones
INSERT INTO episodio(CodiceSerie, NumeroStagione, NumeroEpisodio, DurataMin)
VALUES (2, 1, 1, 60),
       (2, 1, 2, 60),
       (2, 1, 3, 60),
       (2, 1, 4, 60),
       (2, 1, 5, 60);

-- Inserisci un nuovo cast per la seconda stagione di Game of Thrones
INSERT INTO casting(Nome)
VALUES ('Game of Thrones Cast - Stagione 2');
-- Inserisci membri del cast della seconda stagione di Game of Thrones
INSERT INTO membrocast (Nome, Cognome, DataNascita, TipoAttore, TipoRegista, DataDebuttoCarriera, NomeArte)
VALUES ('Peter', 'Dinklage', '1969-06-11', true, false, '1991-12-15', 'Peter Dinklage'),
       ('Lena', 'Headey', '1973-10-03', true, false, '1992-06-28', 'Lena Headey');

-- Associa i membri del cast al cast della seconda stagione di Game of Thrones
INSERT INTO partecipazione_cast (CodiceMembro, CodiceCast)
VALUES (23, 11),
       (24, 11);

-- Inserisci la seconda stagione di Game of Thrones
INSERT INTO stagione(CodiceSerie, NumeroStagione, Sunto, CodiceCast)
VALUES (2, 2, 'La guerra dei Cinque Re infuria', 11);

-- Inserisci episodi per la seconda stagione di Game of Thrones
INSERT INTO episodio(CodiceSerie, NumeroStagione, NumeroEpisodio, DurataMin)
VALUES (2, 2, 1, 60),
       (2, 2, 2, 60),
       (2, 2, 3, 60),
       (2, 2, 4, 60),
       (2, 2, 5, 60);

INSERT INTO visualizzazioni_episodio(UsernameUtente, CodiceSerie, NumeroStagione, NumeroEpisodio, DataVisualizzazione)
VALUES ("luca", 1, 1, 1, "2024-05-09"),
       ("luca", 1, 1, 2, "2024-05-09"),
       ("luca", 1, 1, 3, "2024-05-09"),
       ("luca", 1, 1, 4, "2024-05-09"),
       ("luca", 1, 1, 5, "2024-05-09"),
       ("luca", 1, 2, 1, "2024-05-09"),
       ("luca", 1, 2, 2, "2024-05-09"),
       ("luca", 1, 2, 3, "2024-05-09"),
       ("luca", 1, 2, 4, "2024-05-09"),
       ("luca", 1, 2, 5, "2024-05-09"),
       ("luca", 1, 3, 1, "2024-05-09"),
       ("luca", 1, 3, 2, "2024-05-09"),
       ("luca", 1, 3, 3, "2024-05-09"),
       ("luca", 1, 3, 4, "2024-05-09"),
       ("luca", 1, 3, 5, "2024-05-09"),
       ("luca", 1, 4, 1, "2024-05-09"),
       ("luca", 1, 4, 2, "2024-05-09"),
       ("luca", 1, 4, 3, "2024-05-09"),
       ("luca", 1, 4, 4, "2024-05-09"),
       ("luca", 1, 4, 5, "2024-05-09");


-- Tesserine!
INSERT INTO tessera(CodiceCinema, UsernameUtente, NumeroTessera, DataRinnovo)
VALUES (1, "luca", 1, "2024-05-09"),
       (1, "mario", 2, "2024-05-09");

-- Recensioni per Breaking Bad
INSERT INTO recserie(UsernameUtente, CodiceSerie, Titolo, Descrizione, VotoComplessivo)
VALUES ("luca", 1, "Capolavoro assoluto",
        "Breaking Bad è una delle migliori serie TV mai realizzate. La trama è avvincente, i personaggi sono ben sviluppati e le performance degli attori sono straordinarie.",
        10);
-- ("giulia", 1, "Indimenticabile", "Questa serie mi ha tenuto incollata allo schermo dall'inizio alla fine. Una storia coinvolgente e piena di suspense.", 9),
-- ("mario", 1, "Pura eccellenza", "Breaking Bad è un capolavoro di storytelling. Bryan Cranston e Aaron Paul sono semplicemente fantastici.", 10);

-- Recensioni per Game of Thrones
-- INSERT INTO recserie(UsernameUtente, CodiceSerie, Titolo, Descrizione, VotoComplessivo)
-- VALUES
-- ("eleonora", 2, "Fantasy epico", "Game of Thrones ha una trama complessa e personaggi ben definiti. Le location e gli effetti speciali sono incredibili.", 9),
-- ("giorgio", 2, "Deludente finale", "La serie ha avuto dei momenti incredibili, ma il finale è stato una delusione. Troppe trame non risolte e decisioni discutibili.", 7),
-- ("francesca", 2, "Un viaggio emozionante", "Game of Thrones è stata un'esperienza incredibile. Nonostante qualche svista nelle ultime stagioni, rimarrà sempre una delle mie serie preferite.", 9);

INSERT INTO genere(Nome, Descrizione)
VALUES ("Horror", "Storie che fanno paura con cose soprannaturali"),
       ("Thriller", "Storie che fanno paura ma più realistiche"),
       ("Commedia", "Storie che fanno ridere"),
       ("Giallo", "Storie con spie e poliziotti"),
       ("Rosa", "Storie d'amore"),
       ("Storico", "Documentari o elementi storici"),
       ("Azione", "Boom, bang, pow!");

INSERT INTO sezione(Nome, Dettaglio)
VALUES ("Trama", "La storia e il proseguio degli eventi."),
       ("Sonoro", "Una buona composizione musicale è fondamentale per mantenere attiva l'attenzione."),
       ("Effetti",
        "Se un elemento è animato, si riferisce alla qualità dell'animazione, senno' agli effetti cinematici."),
       ("Casting", "Come si sono comportati gli attori.");

INSERT INTO sezionamento_film (UsernameUtente, CodiceRecFilm, NomeSezione, Voto)
VALUES ("luca", 1, "trama", 6),
       ("luca", 1, "Sonoro", 10),
       ("luca", 1, "Effetti", 7),
       ("luca", 1, "Casting", 9);

INSERT INTO sezionamento_serie (UsernameUtente, CodiceRecSerie, NomeSezione, Voto)
VALUES ("luca", 1, "trama", 8),
       ("luca", 1, "Sonoro", 1),
       ("luca", 1, "Effetti", 2),
       ("luca", 1, "Casting", 10);

INSERT INTO categorizzazione_film (NomeGenere, CodiceFilm)
VALUES ("Storico", 1),
       ("Azione", 1),
       ("Thriller", 2),
       ("Azione", 2),
       ("Azione", 3),
       ("Thriller", 3),
       ("Storico", 4),
       ("Thriller", 4),
       ("Azione", 5),
       ("Giallo", 5);

INSERT INTO categorizzazione_serie (NomeGenere, CodiceSerie)
VALUES ("Giallo", 1),
       ("Thriller", 1),
       ("Storico", 2),
       ("Azione", 2);

UPDATE genere
SET genere.NumeroVisualizzati = genere.NumeroVisualizzati + 2
WHERE genere.Nome IN (SELECT categorizzazione_film.NomeGenere
                      FROM categorizzazione_film
                      WHERE CodiceFilm = 5);

UPDATE genere
SET genere.NumeroVisualizzati = genere.NumeroVisualizzati + 2
WHERE genere.Nome IN (SELECT categorizzazione_film.NomeGenere
                      FROM categorizzazione_film
                      WHERE CodiceFilm = 4);

UPDATE genere
SET genere.NumeroVisualizzati = genere.NumeroVisualizzati + 2
WHERE genere.Nome IN (SELECT categorizzazione_film.NomeGenere
                      FROM categorizzazione_film
                      WHERE CodiceFilm = 3);

UPDATE genere
SET genere.NumeroVisualizzati = genere.NumeroVisualizzati + 2
WHERE genere.Nome IN (SELECT categorizzazione_film.NomeGenere
                      FROM categorizzazione_film
                      WHERE CodiceFilm = 2);

UPDATE genere
SET genere.NumeroVisualizzati = genere.NumeroVisualizzati + 3
WHERE genere.Nome IN (SELECT categorizzazione_film.NomeGenere
                      FROM categorizzazione_film
                      WHERE CodiceFilm = 1);

UPDATE genere
SET genere.NumeroVisualizzati = genere.NumeroVisualizzati + 20
WHERE genere.Nome IN (SELECT categorizzazione_serie.NomeGenere
                      FROM categorizzazione_serie
                      WHERE CodiceSerie = 1);