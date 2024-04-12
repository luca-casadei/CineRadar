-- *********************************************
-- * SQL MySQL generation                      
-- *--------------------------------------------
-- * DB-MAIN version: 11.0.2              
-- * Generator date: Sep 14 2021              
-- * Generation date: Fri Apr 12 12:41:48 2024 
-- * LUN file: D:\Development\Compiled\Java\CineRadar\docs\ER\CineRadar.lun 
-- * Schema: CineRadar/1 
-- ********************************************* 


-- Database Section
-- ________________
DROP DATABASE if EXISTS CineRadar;
CREATE DATABASE CineRadar; USE CineRadar;


-- Tables Section
-- _____________
CREATE TABLE ACCOUNT (
 Username CHAR(20) NOT NULL, PASSWORD CHAR(50) NOT NULL,
 Nome VARCHAR(20) NOT NULL,
 Cognome VARCHAR(20) NOT NULL, CONSTRAINT IDACCOUNT PRIMARY KEY (Username));
CREATE TABLE AMMINISTRATORE (
 Username CHAR(20) NOT NULL,
 NumeroTelefono CHAR(10) NOT NULL, CONSTRAINT FKtipoAmm_ID PRIMARY KEY (Username));
CREATE TABLE CAST (
 Codice INT NOT NULL,
 Nome VARCHAR(50), CONSTRAINT IDCAST_ID PRIMARY KEY (Codice));
CREATE TABLE CATEGORIZZAZIONE_FILM (
 NomeGenere VARCHAR(20) NOT NULL,
 CodiceFilm INT NOT NULL, CONSTRAINT IDCATEGORIZZAZIONE_FILM PRIMARY KEY (NomeGenere, CodiceFilm));
CREATE TABLE CATEGORIZZAZIONE_SERIE (
 NomeGenere VARCHAR(20) NOT NULL,
 CodiceSerie INT NOT NULL, CONSTRAINT IDCATEGORIZZAZIONE_SERIE PRIMARY KEY (NomeGenere, CodiceSerie));
CREATE TABLE CINEMA (
 Codice INT NOT NULL,
 Nome VARCHAR(50) NOT NULL,
 Ind_Via VARCHAR(30) NOT NULL,
 Ind_CAP CHAR(10) NOT NULL,
 Ind_Civico INT NOT NULL,
 Ind_Citta VARCHAR(30) NOT NULL, CONSTRAINT IDCINEMA_ID PRIMARY KEY (Codice));
CREATE TABLE EPISODIO (
 CodiceSerie INT NOT NULL,
 NumeroStagione INT NOT NULL,
 NumeroEpisodio INT NOT NULL,
 DurataMin INT NOT NULL, CONSTRAINT IDEPISODIO PRIMARY KEY (CodiceSerie, NumeroStagione, NumeroEpisodio));
CREATE TABLE FILM (
 Codice INT NOT NULL,
 Titolo VARCHAR(50) NOT NULL,
 EtaLimite INT NOT NULL,
 Trama VARCHAR(500) NOT NULL,
 Durata INT NOT NULL,
 CodiceCast INT NOT NULL, CONSTRAINT IDFILM_ID PRIMARY KEY (Codice));
CREATE TABLE GENERE (
 Nome VARCHAR(20) NOT NULL,
 Descrizione VARCHAR(500) NOT NULL,
 NumeroVisualizzati INT NOT NULL, CONSTRAINT IDGENERE PRIMARY KEY (Nome));
CREATE TABLE MEMBROCAST (
 Codice INT NOT NULL,
 Nome VARCHAR(20) NOT NULL,
 Cognome VARCHAR(20) NOT NULL,
 DataNascita DATE NOT NULL,
 TipoAttore BOOLEAN NOT NULL,
 TipoRegista BOOLEAN NOT NULL,
 DataDebuttoCarriera DATE,
 NomeArte VARCHAR(30), CONSTRAINT IDMEMBROCAST PRIMARY KEY (Codice));
CREATE TABLE MULTIPLO (
 CodiceTemplatePromo INT NOT NULL, CONSTRAINT FKpromoMul_ID PRIMARY KEY (CodiceTemplatePromo));
CREATE TABLE PARTECIPAZIONE_CAST (
 CodiceMembro INT NOT NULL,
 CodiceCast INT NOT NULL, CONSTRAINT IDPARTECIPAZIONE_CAST PRIMARY KEY (CodiceCast, CodiceMembro));
CREATE TABLE PREFERENZE (
 NomeGenere VARCHAR(20) NOT NULL,
 UsernameUtente CHAR(20) NOT NULL, CONSTRAINT IDPREFERENZE PRIMARY KEY (NomeGenere, UsernameUtente));
CREATE TABLE PREMI_TESSERA (
 CodicePromoPromo INT NOT NULL,
 Scadenza DATE NOT NULL,
 CodiceCinema INT NOT NULL,
 UsernameUtente CHAR(20) NOT NULL, CONSTRAINT IDPREMI_TESSERA PRIMARY KEY (CodicePromoPromo, Scadenza, CodiceCinema, UsernameUtente));
CREATE TABLE PROMO (
 CodiceTemplatePromo INT NOT NULL,
 Scadenza DATE NOT NULL, CONSTRAINT IDPROMO PRIMARY KEY (CodiceTemplatePromo, Scadenza));
CREATE TABLE PROMO_GENERE (
 NomeGenere VARCHAR(20) NOT NULL,
 CodiceTemplateMultiplo INT NOT NULL, CONSTRAINT IDPROMO_GENERE PRIMARY KEY (NomeGenere, CodiceTemplateMultiplo));
CREATE TABLE RECFILM (
 UsernameUtente CHAR(20) NOT NULL,
 CodiceFilm INT NOT NULL,
 Titolo VARCHAR(50) NOT NULL,
 Descrizione VARCHAR(500) NOT NULL,
 VotoComplessivo INT NOT NULL, CONSTRAINT IDRECFILM_ID PRIMARY KEY (UsernameUtente, CodiceFilm));
CREATE TABLE RECSERIE (
 UsernameUtente CHAR(20) NOT NULL,
 CodiceSerie INT NOT NULL,
 Titolo VARCHAR(50) NOT NULL,
 Descrizione VARCHAR(500) NOT NULL, CONSTRAINT IDRECSERIE_ID PRIMARY KEY (UsernameUtente, CodiceSerie));
CREATE TABLE REGISTRATORE (
 Username CHAR(20) NOT NULL,
 EmailCinema VARCHAR(200),
 CodiceCinema INT NOT NULL, CONSTRAINT FKtipoReg_ID PRIMARY KEY (Username));
CREATE TABLE RICHIESTA (
 Numero INT NOT NULL,
 Tipo BOOLEAN NOT NULL,
 Titolo VARCHAR(100) NOT NULL,
 AnnoUscita DATE NOT NULL,
 Descrizione VARCHAR(100) NOT NULL,
 Chiusa BOOLEAN NOT NULL,
 UsernameUtente CHAR(20) NOT NULL, CONSTRAINT IDRICHIESTA PRIMARY KEY (Numero));
CREATE TABLE SERIE (
 Codice INT NOT NULL,
 Titolo VARCHAR(50) NOT NULL,
 EtaLimite INT NOT NULL,
 Trama VARCHAR(500) NOT NULL,
 DurataComplessiva INT NOT NULL,
 NumeroEpisodi INT NOT NULL, CONSTRAINT IDSERIE_ID PRIMARY KEY (Codice));
CREATE TABLE SEZIONAMENTO_FILM (
 UsernameUtente CHAR(20) NOT NULL,
 CodiceRecFilm INT NOT NULL,
 NomeSezione CHAR(10) NOT NULL,
 Voto INT NOT NULL, CONSTRAINT IDSEZIONAMENTO_FILM PRIMARY KEY (NomeSezione, UsernameUtente, CodiceRecFilm));
CREATE TABLE SEZIONAMENTO_SERIE (
 NomeSezione CHAR(10) NOT NULL,
 UsernameUtente CHAR(20) NOT NULL,
 CodiceRecSerie INT NOT NULL,
 Voto INT NOT NULL, CONSTRAINT IDSEZIONAMENTO_SERIE PRIMARY KEY (NomeSezione, UsernameUtente, CodiceRecSerie));
CREATE TABLE SEZIONE (
 Nome CHAR(10) NOT NULL,
 Dettaglio VARCHAR(100) NOT NULL, CONSTRAINT IDSEZIONI PRIMARY KEY (Nome));
CREATE TABLE SINGOLO (
 CodiceTemplatePromo INT NOT NULL,
 CodiceSerie INT NOT NULL,
 CodiceFilm INT NOT NULL, CONSTRAINT FKpromoSin_ID PRIMARY KEY (CodiceTemplatePromo));
CREATE TABLE STAGIONE (
 CodiceSerie INT NOT NULL,
 NumeroStagione INT NOT NULL,
 Sunto VARCHAR(500) NOT NULL,
 CodiceCast INT NOT NULL, CONSTRAINT IDSTAGIONE_ID PRIMARY KEY (CodiceSerie, NumeroStagione));
CREATE TABLE TEMPLATEPROMO (
 CodicePromo INT NOT NULL,
 PercentualeSconto INT NOT NULL, CONSTRAINT IDCOUPON PRIMARY KEY (CodicePromo));
CREATE TABLE TESSERA (
 CodiceCinema INT NOT NULL,
 UsernameUtente CHAR(20) NOT NULL,
 NumeroTessera INT NOT NULL,
 DataRinnovo DATE NOT NULL, CONSTRAINT IDTESSERA PRIMARY KEY (CodiceCinema, UsernameUtente));
CREATE TABLE UTENTE (
 Username CHAR(20) NOT NULL,
 TargaPremio BOOLEAN,
 DataNascita DATE NOT NULL, CONSTRAINT FKtipoUsr_ID PRIMARY KEY (Username));
CREATE TABLE VALUTAZIONE_FILM (
 UsernameUtenteValutato CHAR(20) NOT NULL,
 CodiceRecFilm INT NOT NULL,
 UsernameUtente CHAR(20) NOT NULL,
 positiva BOOLEAN NOT NULL, CONSTRAINT IDVALUTAZIONE_FILM PRIMARY KEY (UsernameUtenteValutato, CodiceRecFilm, UsernameUtente));
CREATE TABLE VALUTAZIONE_SERIE (
 UsernameUtenteValutato CHAR(20) NOT NULL,
 CodiceRecSerie INT NOT NULL,
 UsernameUtente CHAR(20) NOT NULL,
 positiva BOOLEAN NOT NULL, CONSTRAINT IDVALUTAZIONE_SERIE PRIMARY KEY (UsernameUtente, UsernameUtenteValutato, CodiceRecSerie));
CREATE TABLE VISUALIZZAZIONI_EPISODIO (
 UsernameUtente CHAR(20) NOT NULL,
 CodiceSerie INT NOT NULL,
 NumeroEpisodio INT NOT NULL,
 NumeroStagione INT NOT NULL, DATA DATE NOT NULL, CONSTRAINT IDVISUALIZZAZIONI_EPISODIO PRIMARY KEY (UsernameUtente, CodiceSerie, NumeroEpisodio, NumeroStagione));
CREATE TABLE VISUALIZZAZIONI_FILM (
 CodiceFilm INT NOT NULL,
 UsernameUtente CHAR(20) NOT NULL, CONSTRAINT IDVISUALIZZAZIONI_FILM PRIMARY KEY (UsernameUtente, CodiceFilm));


-- Constraints Section
-- ___________________
ALTER TABLE AMMINISTRATORE ADD CONSTRAINT FKtipoAmm_FK FOREIGN KEY (Username) REFERENCES ACCOUNT (Username);

-- Not implemented
-- alter table CAST add constraint IDCAST_CHK
--     check(exists(select * from PARTECIPAZIONE_CAST
--                  where PARTECIPAZIONE_CAST.CodiceCast = Codice));
ALTER TABLE CATEGORIZZAZIONE_FILM ADD CONSTRAINT FKcat_FILF FOREIGN KEY (CodiceFilm) REFERENCES FILM (Codice); 
ALTER TABLE CATEGORIZZAZIONE_FILM ADD CONSTRAINT FKcat_GENF FOREIGN KEY (NomeGenere) REFERENCES GENERE (Nome); 
ALTER TABLE CATEGORIZZAZIONE_SERIE ADD CONSTRAINT FKcat_SERS FOREIGN KEY (CodiceSerie) REFERENCES SERIE (Codice); 
ALTER TABLE CATEGORIZZAZIONE_SERIE ADD CONSTRAINT FKcat_GENS FOREIGN KEY (NomeGenere) REFERENCES GENERE (Nome);

-- Not implemented
-- alter table CINEMA add constraint IDCINEMA_CHK
--     check(exists(select * from REGISTRATORE
--                  where REGISTRATORE.CodiceCinema = Codice));
ALTER TABLE EPISODIO ADD CONSTRAINT FKcompStagione FOREIGN KEY (CodiceSerie, NumeroStagione) REFERENCES STAGIONE (CodiceSerie, NumeroStagione);

-- Not implemented
-- alter table FILM add constraint IDFILM_CHK
--     check(exists(select * from CATEGORIZZAZIONE_FILM
--                  where CATEGORIZZAZIONE_FILM.CodiceFilm = Codice));
ALTER TABLE FILM ADD CONSTRAINT FKcastfilm FOREIGN KEY (CodiceCast) REFERENCES CAST (Codice); 
ALTER TABLE MULTIPLO ADD CONSTRAINT FKpromoMul_FK FOREIGN KEY (CodiceTemplatePromo) REFERENCES TEMPLATEPROMO (CodicePromo);

-- Not implemented
-- alter table MULTIPLO add constraint FKpromoMul_CHK
--     check(exists(select * from PROMO_GENERE
--                  where PROMO_GENERE.CodiceTemplateMultiplo = CodiceTemplatePromo));
ALTER TABLE PARTECIPAZIONE_CAST ADD CONSTRAINT FKmem_CAS FOREIGN KEY (CodiceCast) REFERENCES CAST (Codice); 
ALTER TABLE PARTECIPAZIONE_CAST ADD CONSTRAINT FKmem_MEM FOREIGN KEY (CodiceMembro) REFERENCES MEMBROCAST (Codice); 
ALTER TABLE PREFERENZE ADD CONSTRAINT FKpre_UTE FOREIGN KEY (UsernameUtente) REFERENCES UTENTE (Username); 
ALTER TABLE PREFERENZE ADD CONSTRAINT FKpre_GEN FOREIGN KEY (NomeGenere) REFERENCES GENERE (Nome); 
ALTER TABLE PREMI_TESSERA ADD CONSTRAINT FKpre_TES FOREIGN KEY (CodiceCinema, UsernameUtente) REFERENCES TESSERA (CodiceCinema, UsernameUtente); 
ALTER TABLE PREMI_TESSERA ADD CONSTRAINT FKpre_PRO FOREIGN KEY (CodicePromoPromo, Scadenza) REFERENCES PROMO (CodiceTemplatePromo, Scadenza); 
ALTER TABLE PROMO ADD CONSTRAINT FKvalidità FOREIGN KEY (CodiceTemplatePromo) REFERENCES TEMPLATEPROMO (CodicePromo); 
ALTER TABLE PROMO_GENERE ADD CONSTRAINT FKpro_MUL FOREIGN KEY (CodiceTemplateMultiplo) REFERENCES MULTIPLO (CodiceTemplatePromo); 
ALTER TABLE PROMO_GENERE ADD CONSTRAINT FKpro_GEN FOREIGN KEY (NomeGenere) REFERENCES GENERE (Nome);

-- Not implemented
-- alter table RECFILM add constraint IDRECFILM_CHK
--     check(exists(select * from SEZIONAMENTO_FILM
--                  where SEZIONAMENTO_FILM.UsernameUtente = UsernameUtente and SEZIONAMENTO_FILM.CodiceRecFilm = CodiceFilm));
ALTER TABLE RECFILM ADD CONSTRAINT FKrecensionefilm FOREIGN KEY (CodiceFilm) REFERENCES FILM (Codice); 
ALTER TABLE RECFILM ADD CONSTRAINT FKscrittRecFilm FOREIGN KEY (UsernameUtente) REFERENCES UTENTE (Username);

-- Not implemented
-- alter table RECSERIE add constraint IDRECSERIE_CHK
--     check(exists(select * from SEZIONAMENTO_SERIE
--                  where SEZIONAMENTO_SERIE.UsernameUtente = UsernameUtente and SEZIONAMENTO_SERIE.CodiceRecSerie = CodiceSerie));
ALTER TABLE RECSERIE ADD CONSTRAINT FKrecensioneserie FOREIGN KEY (CodiceSerie) REFERENCES SERIE (Codice); 
ALTER TABLE RECSERIE ADD CONSTRAINT FKscrittRecSerie FOREIGN KEY (UsernameUtente) REFERENCES UTENTE (Username); 
ALTER TABLE REGISTRATORE ADD CONSTRAINT FKtipoReg_FK FOREIGN KEY (Username) REFERENCES ACCOUNT (Username); 
ALTER TABLE REGISTRATORE ADD CONSTRAINT FKafferenza FOREIGN KEY (CodiceCinema) REFERENCES CINEMA (Codice); 
ALTER TABLE RICHIESTA ADD CONSTRAINT FKeffetuazione FOREIGN KEY (UsernameUtente) REFERENCES UTENTE (Username);

-- Not implemented
-- alter table SERIE add constraint IDSERIE_CHK
--     check(exists(select * from CATEGORIZZAZIONE_SERIE
--                  where CATEGORIZZAZIONE_SERIE.CodiceSerie = Codice)); 

-- Not implemented
-- alter table SERIE add constraint IDSERIE_CHK
--     check(exists(select * from STAGIONE
--                  where STAGIONE.CodiceSerie = Codice));
ALTER TABLE SEZIONAMENTO_FILM ADD CONSTRAINT FKrec_SEZF FOREIGN KEY (NomeSezione) REFERENCES SEZIONE (Nome); 
ALTER TABLE SEZIONAMENTO_FILM ADD CONSTRAINT FKrec_RECF FOREIGN KEY (UsernameUtente, CodiceRecFilm) REFERENCES RECFILM (UsernameUtente, CodiceFilm); 
ALTER TABLE SEZIONAMENTO_SERIE ADD CONSTRAINT FKrec_RECS FOREIGN KEY (UsernameUtente, CodiceRecSerie) REFERENCES RECSERIE (UsernameUtente, CodiceSerie); 
ALTER TABLE SEZIONAMENTO_SERIE ADD CONSTRAINT FKrec_SEZS FOREIGN KEY (NomeSezione) REFERENCES SEZIONE (Nome); 
ALTER TABLE SINGOLO ADD CONSTRAINT FKpromoSin_FK FOREIGN KEY (CodiceTemplatePromo) REFERENCES TEMPLATEPROMO (CodicePromo); 
ALTER TABLE SINGOLO ADD CONSTRAINT FKsinSerie FOREIGN KEY (CodiceSerie) REFERENCES SERIE (Codice); 
ALTER TABLE SINGOLO ADD CONSTRAINT FKsinFilm FOREIGN KEY (CodiceFilm) REFERENCES FILM (Codice);

-- Not implemented
-- alter table STAGIONE add constraint IDSTAGIONE_CHK
--     check(exists(select * from EPISODIO
--                  where EPISODIO.CodiceSerie = CodiceSerie and EPISODIO.NumeroStagione = NumeroStagione));
ALTER TABLE STAGIONE ADD CONSTRAINT FKcompSerie FOREIGN KEY (CodiceSerie) REFERENCES SERIE (Codice); 
ALTER TABLE STAGIONE ADD CONSTRAINT FKcaststagione FOREIGN KEY (CodiceCast) REFERENCES CAST (Codice); 
ALTER TABLE TESSERA ADD CONSTRAINT FKtesseramento FOREIGN KEY (UsernameUtente) REFERENCES UTENTE (Username); 
ALTER TABLE TESSERA ADD CONSTRAINT FKappartenenza FOREIGN KEY (CodiceCinema) REFERENCES CINEMA (Codice); 
ALTER TABLE UTENTE ADD CONSTRAINT FKtipoUsr_FK FOREIGN KEY (Username) REFERENCES ACCOUNT (Username); 
ALTER TABLE VALUTAZIONE_FILM ADD CONSTRAINT FKval_UTEF FOREIGN KEY (UsernameUtente) REFERENCES UTENTE (Username); 
ALTER TABLE VALUTAZIONE_FILM ADD CONSTRAINT FKval_RECF FOREIGN KEY (UsernameUtenteValutato, CodiceRecFilm) REFERENCES RECFILM (UsernameUtente, CodiceFilm); 
ALTER TABLE VALUTAZIONE_SERIE ADD CONSTRAINT FKval_UTES FOREIGN KEY (UsernameUtente) REFERENCES UTENTE (Username); 
ALTER TABLE VALUTAZIONE_SERIE ADD CONSTRAINT FKval_RECS FOREIGN KEY (UsernameUtenteValutato, CodiceRecSerie) REFERENCES RECSERIE (UsernameUtente, CodiceSerie); 
ALTER TABLE VISUALIZZAZIONI_EPISODIO ADD CONSTRAINT FKvis_EPIS FOREIGN KEY (CodiceSerie, NumeroEpisodio, NumeroStagione) REFERENCES EPISODIO (CodiceSerie, NumeroStagione, NumeroEpisodio); 
ALTER TABLE VISUALIZZAZIONI_EPISODIO ADD CONSTRAINT FKvis_UTES FOREIGN KEY (UsernameUtente) REFERENCES UTENTE (Username); 
ALTER TABLE VISUALIZZAZIONI_FILM ADD CONSTRAINT FKvis_UTEF FOREIGN KEY (UsernameUtente) REFERENCES UTENTE (Username); 
ALTER TABLE VISUALIZZAZIONI_FILM ADD CONSTRAINT FKvis_FILF FOREIGN KEY (CodiceFilm) REFERENCES FILM (Codice);


-- Index Section
-- _____________ 
