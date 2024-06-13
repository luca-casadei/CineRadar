-- *********************************************
-- * SQL MySQL generation                      
-- *--------------------------------------------
-- * DB-MAIN version: 11.0.2              
-- * Generator date: Sep 14 2021              
-- * Generation date: Sat Apr 13 16:13:07 2024 
-- * LUN file: D:\Development\Compiled\Java\CineRadar\docs\ER\CineRadar.lun 
-- * Schema: CineRadar/1 
-- ********************************************* 


-- Database Section
-- ________________
DROP DATABASE if EXISTS CineRadar;
CREATE DATABASE CineRadar;
USE
    CineRadar;

-- Tables Section
CREATE TABLE ACCOUNT
(
    Username CHAR(20)    NOT NULL,
    PASSWORD CHAR(128)   NOT NULL,
    Nome     VARCHAR(20) NOT NULL,
    Cognome  VARCHAR(20) NOT NULL,
    CONSTRAINT IDACCOUNT PRIMARY KEY (Username)
);
CREATE TABLE AMMINISTRATORE
(
    Username       CHAR(20) NOT NULL,
    NumeroTelefono CHAR(10) NOT NULL,
    CONSTRAINT FKtipoAmm_ID PRIMARY KEY (Username)
);
CREATE TABLE CASTING
(
    Codice INT NOT NULL AUTO_INCREMENT,
    Nome   VARCHAR(50),
    CONSTRAINT IDCAST_ID PRIMARY KEY (Codice)
);
CREATE TABLE CATEGORIZZAZIONE_FILM
(
    NomeGenere VARCHAR(20) NOT NULL,
    CodiceFilm INT         NOT NULL,
    CONSTRAINT IDCATEGORIZZAZIONE_FILM PRIMARY KEY (NomeGenere, CodiceFilm)
);
CREATE TABLE CATEGORIZZAZIONE_SERIE
(
    NomeGenere  VARCHAR(20) NOT NULL,
    CodiceSerie INT         NOT NULL,
    CONSTRAINT IDCATEGORIZZAZIONE_SERIE PRIMARY KEY (NomeGenere, CodiceSerie)
);
CREATE TABLE CINEMA
(
    Codice     INT         NOT NULL AUTO_INCREMENT,
    Nome       VARCHAR(50) NOT NULL,
    Ind_Via    VARCHAR(30) NOT NULL,
    Ind_CAP    CHAR(10)    NOT NULL,
    Ind_Civico INT         NOT NULL CHECK (Ind_Civico >= 0),
    Ind_Citta  VARCHAR(30) NOT NULL,
    CONSTRAINT IDCINEMA_ID PRIMARY KEY (Codice)
);
CREATE TABLE EPISODIO
(
    CodiceSerie    INT NOT NULL,
    NumeroStagione INT NOT NULL,
    NumeroEpisodio INT NOT NULL,
    DurataMin      INT NOT NULL,
    CONSTRAINT IDEPISODIO PRIMARY KEY (CodiceSerie, NumeroStagione, NumeroEpisodio)
);
CREATE TABLE FILM
(
    Codice     INT          NOT NULL AUTO_INCREMENT,
    Titolo     VARCHAR(50)  NOT NULL,
    EtaLimite  INT          NOT NULL CHECK (EtaLimite >= 0),
    Trama      VARCHAR(500) NOT NULL,
    Durata     INT          NOT NULL CHECK (Durata > 0),
    CodiceCast INT          NOT NULL,
    CONSTRAINT IDFILM_ID PRIMARY KEY (Codice)
);
CREATE TABLE GENERE
(
    Nome               VARCHAR(20)  NOT NULL,
    Descrizione        VARCHAR(500) NOT NULL,
    NumeroVisualizzati INT          NOT NULL DEFAULT 0 CHECK (NumeroVisualizzati >= 0),
    CONSTRAINT IDGENERE PRIMARY KEY (Nome)
);
CREATE TABLE MEMBROCAST
(
    Codice              INT         NOT NULL AUTO_INCREMENT,
    Nome                VARCHAR(20) NOT NULL,
    Cognome             VARCHAR(20) NOT NULL,
    DataNascita         DATE        NOT NULL,
    TipoAttore          BOOLEAN     NOT NULL,
    TipoRegista         BOOLEAN     NOT NULL,
    DataDebuttoCarriera DATE CHECK (DataDebuttoCarriera > MEMBROCAST.DataNascita),
    NomeArte            VARCHAR(30),
    CONSTRAINT IDMEMBROCAST PRIMARY KEY (Codice)
);
CREATE TABLE MULTIPLO
(
    CodiceTemplatePromo INT NOT NULL,
    CONSTRAINT FKpromoMul_ID PRIMARY KEY (CodiceTemplatePromo)
);
CREATE TABLE PARTECIPAZIONE_CAST
(
    CodiceMembro INT NOT NULL,
    CodiceCast   INT NOT NULL,
    CONSTRAINT IDPARTECIPAZIONE_CAST PRIMARY KEY (CodiceCast, CodiceMembro)
);
CREATE TABLE PREFERENZE
(
    NomeGenere     VARCHAR(20) NOT NULL,
    UsernameUtente CHAR(20)    NOT NULL,
    CONSTRAINT IDPREFERENZE PRIMARY KEY (NomeGenere, UsernameUtente)
);
CREATE TABLE PREMI_TESSERA
(
    CodicePromoPromo INT      NOT NULL,
    Scadenza         DATE     NOT NULL,
    CodiceCinema     INT      NOT NULL,
    UsernameUtente   CHAR(20) NOT NULL,
    CONSTRAINT IDPREMI_TESSERA PRIMARY KEY (CodicePromoPromo, Scadenza, CodiceCinema, UsernameUtente)
);
CREATE TABLE PROMO
(
    CodiceTemplatePromo INT  NOT NULL,
    Scadenza            DATE NOT NULL,
    CONSTRAINT IDPROMO PRIMARY KEY (CodiceTemplatePromo, Scadenza)
);
CREATE TABLE PROMO_GENERE
(
    NomeGenere             VARCHAR(20) NOT NULL,
    CodiceTemplateMultiplo INT         NOT NULL,
    CONSTRAINT IDPROMO_GENERE PRIMARY KEY (NomeGenere, CodiceTemplateMultiplo)
);
CREATE TABLE RECFILM
(
    UsernameUtente  CHAR(20)     NOT NULL,
    CodiceFilm      INT          NOT NULL,
    Titolo          VARCHAR(50)  NOT NULL,
    Descrizione     VARCHAR(500) NOT NULL,
    VotoComplessivo DOUBLE       DEFAULT 0 NOT NULL CHECK (VotoComplessivo >= 0),
    CONSTRAINT IDRECFILM_ID PRIMARY KEY (UsernameUtente, CodiceFilm)
);
CREATE TABLE RECSERIE
(
    UsernameUtente  CHAR(20)     NOT NULL,
    CodiceSerie     INT          NOT NULL,
    Titolo          VARCHAR(50)  NOT NULL,
    Descrizione     VARCHAR(500) NOT NULL,
    VotoComplessivo DOUBLE       DEFAULT 0 NOT NULL CHECK (VotoComplessivo >= 0),
    CONSTRAINT IDRECSERIE_ID PRIMARY KEY (UsernameUtente, CodiceSerie)
);
CREATE TABLE REGISTRATORE
(
    Username     CHAR(20) NOT NULL,
    EmailCinema  VARCHAR(200),
    CodiceCinema INT      NOT NULL,
    CONSTRAINT FKtipoReg_ID PRIMARY KEY (Username)
);
CREATE TABLE RICHIESTA
(
    Numero         INT          NOT NULL AUTO_INCREMENT,
    Tipo           BOOLEAN      NOT NULL,
    Titolo         VARCHAR(100) NOT NULL,
    AnnoUscita     DATE         NOT NULL,
    Descrizione    VARCHAR(100) NOT NULL,
    Chiusa         BOOLEAN      NOT NULL DEFAULT FALSE,
    UsernameUtente CHAR(20)     NOT NULL,
    CONSTRAINT IDRICHIESTA PRIMARY KEY (Numero)
);
CREATE TABLE SERIE
(
    Codice            INT          NOT NULL AUTO_INCREMENT,
    Titolo            VARCHAR(50)  NOT NULL,
    EtaLimite         INT          NOT NULL,
    Trama             VARCHAR(500) NOT NULL,
    DurataComplessiva INT          NOT NULL,
    NumeroEpisodi     INT          NOT NULL,
    CONSTRAINT IDSERIE_ID PRIMARY KEY (Codice)
);
CREATE TABLE SEZIONAMENTO_FILM
(
    UsernameUtente CHAR(20) NOT NULL,
    CodiceRecFilm  INT      NOT NULL,
    NomeSezione    CHAR(10) NOT NULL,
    Voto           INT      NOT NULL,
    CONSTRAINT IDSEZIONAMENTO_FILM PRIMARY KEY (NomeSezione, UsernameUtente, CodiceRecFilm)
);
CREATE TABLE SEZIONAMENTO_SERIE
(
    NomeSezione    CHAR(10) NOT NULL,
    UsernameUtente CHAR(20) NOT NULL,
    CodiceRecSerie INT      NOT NULL,
    Voto           INT      NOT NULL,
    CONSTRAINT IDSEZIONAMENTO_SERIE PRIMARY KEY (NomeSezione, UsernameUtente, CodiceRecSerie)
);
CREATE TABLE SEZIONE
(
    Nome      CHAR(10)     NOT NULL,
    Dettaglio VARCHAR(100) NOT NULL,
    CONSTRAINT IDSEZIONI PRIMARY KEY (Nome)
);
CREATE TABLE SINGOLO
(
    CodiceTemplatePromo INT NOT NULL,
    CodiceSerie         INT NULL,
    CodiceFilm          INT NULL,
    CONSTRAINT FKpromoSin_ID PRIMARY KEY (CodiceTemplatePromo)
);
CREATE TABLE STAGIONE
(
    CodiceSerie    INT          NOT NULL,
    NumeroStagione INT          NOT NULL,
    Sunto          VARCHAR(500) NOT NULL,
    CodiceCast     INT          NOT NULL,
    CONSTRAINT IDSTAGIONE_ID PRIMARY KEY (CodiceSerie, NumeroStagione)
);
CREATE TABLE TEMPLATEPROMO
(
    CodicePromo       INT NOT NULL AUTO_INCREMENT,
    PercentualeSconto INT NOT NULL CHECK (PercentualeSconto > 0 AND PercentualeSconto <= 100),
    CONSTRAINT IDCOUPON PRIMARY KEY (CodicePromo)
);
CREATE TABLE TESSERA
(
    CodiceCinema   INT      NOT NULL,
    UsernameUtente CHAR(20) NOT NULL,
    NumeroTessera  INT      NOT NULL,
    DataRinnovo    DATE     NOT NULL,
    UNIQUE (CodiceCinema, NumeroTessera),
    CONSTRAINT IDTESSERA PRIMARY KEY (CodiceCinema, UsernameUtente)
);
CREATE TABLE UTENTE
(
    Username    CHAR(20) NOT NULL,
    TargaPremio BOOLEAN  NOT NULL DEFAULT FALSE,
    DataNascita DATE     NOT NULL,
    CONSTRAINT FKtipoUsr_ID PRIMARY KEY (Username)
);
CREATE TABLE VALUTAZIONE_FILM
(
    UsernameUtenteValutato CHAR(20) NOT NULL,
    CodiceRecFilm          INT      NOT NULL,
    UsernameUtente         CHAR(20) NOT NULL,
    Positiva               BOOLEAN  NOT NULL,
    CONSTRAINT IDVALUTAZIONE_FILM PRIMARY KEY (UsernameUtenteValutato, CodiceRecFilm, UsernameUtente)
);
CREATE TABLE VALUTAZIONE_SERIE
(
    UsernameUtenteValutato CHAR(20) NOT NULL,
    CodiceRecSerie         INT      NOT NULL,
    UsernameUtente         CHAR(20) NOT NULL,
    Positiva               BOOLEAN  NOT NULL,
    CONSTRAINT IDVALUTAZIONE_SERIE PRIMARY KEY (UsernameUtente, UsernameUtenteValutato, CodiceRecSerie)
);
CREATE TABLE VISUALIZZAZIONI_EPISODIO
(
    UsernameUtente      CHAR(20) NOT NULL,
    CodiceSerie         INT      NOT NULL,
    NumeroEpisodio      INT      NOT NULL,
    NumeroStagione      INT      NOT NULL,
    DataVisualizzazione DATE     NOT NULL,
    CONSTRAINT IDVISUALIZZAZIONI_EPISODIO PRIMARY KEY (UsernameUtente, CodiceSerie, NumeroEpisodio, NumeroStagione)
);
CREATE TABLE VISUALIZZAZIONI_FILM
(
    CodiceFilm     INT      NOT NULL,
    UsernameUtente CHAR(20) NOT NULL,
    CONSTRAINT IDVISUALIZZAZIONI_FILM PRIMARY KEY (UsernameUtente, CodiceFilm)
);


-- Constraints Section
ALTER TABLE AMMINISTRATORE
    ADD CONSTRAINT FKtipoAmm_FK FOREIGN KEY (Username) REFERENCES ACCOUNT (Username) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE CATEGORIZZAZIONE_FILM
    ADD CONSTRAINT FKcat_FILF FOREIGN KEY (CodiceFilm) REFERENCES FILM (Codice) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE CATEGORIZZAZIONE_FILM
    ADD CONSTRAINT FKcat_GENF FOREIGN KEY (NomeGenere) REFERENCES GENERE (Nome) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE CATEGORIZZAZIONE_SERIE
    ADD CONSTRAINT FKcat_SERS FOREIGN KEY (CodiceSerie) REFERENCES SERIE (Codice) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE CATEGORIZZAZIONE_SERIE
    ADD CONSTRAINT FKcat_GENS FOREIGN KEY (NomeGenere) REFERENCES GENERE (Nome) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE EPISODIO
    ADD CONSTRAINT FKcompStagione FOREIGN KEY (CodiceSerie, NumeroStagione) REFERENCES STAGIONE (CodiceSerie, NumeroStagione) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE FILM
    ADD CONSTRAINT FKcastfilm FOREIGN KEY (CodiceCast) REFERENCES CASTING (Codice) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE MULTIPLO
    ADD CONSTRAINT FKpromoMul_FK FOREIGN KEY (CodiceTemplatePromo) REFERENCES TEMPLATEPROMO (CodicePromo) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE PARTECIPAZIONE_CAST
    ADD CONSTRAINT FKmem_CAS FOREIGN KEY (CodiceCast) REFERENCES CASTING (Codice) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE PARTECIPAZIONE_CAST
    ADD CONSTRAINT FKmem_MEM FOREIGN KEY (CodiceMembro) REFERENCES MEMBROCAST (Codice) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE PREFERENZE
    ADD CONSTRAINT FKpre_UTE FOREIGN KEY (UsernameUtente) REFERENCES UTENTE (Username) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE PREFERENZE
    ADD CONSTRAINT FKpre_GEN FOREIGN KEY (NomeGenere) REFERENCES GENERE (Nome) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE PREMI_TESSERA
    ADD CONSTRAINT FKpre_TES FOREIGN KEY (CodiceCinema, UsernameUtente) REFERENCES TESSERA (CodiceCinema, UsernameUtente) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE PREMI_TESSERA
    ADD CONSTRAINT FKpre_PRO FOREIGN KEY (CodicePromoPromo, Scadenza) REFERENCES PROMO (CodiceTemplatePromo, Scadenza) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE PROMO
    ADD CONSTRAINT FKvalidità FOREIGN KEY (CodiceTemplatePromo) REFERENCES TEMPLATEPROMO (CodicePromo) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE PROMO_GENERE
    ADD CONSTRAINT FKpro_MUL FOREIGN KEY (CodiceTemplateMultiplo) REFERENCES MULTIPLO (CodiceTemplatePromo) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE PROMO_GENERE
    ADD CONSTRAINT FKpro_GEN FOREIGN KEY (NomeGenere) REFERENCES GENERE (Nome) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE RECFILM
    ADD CONSTRAINT FKrecensionefilm FOREIGN KEY (CodiceFilm) REFERENCES FILM (Codice) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE RECFILM
    ADD CONSTRAINT FKscrittRecFilm FOREIGN KEY (UsernameUtente) REFERENCES UTENTE (Username) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE RECSERIE
    ADD CONSTRAINT FKrecensioneserie FOREIGN KEY (CodiceSerie) REFERENCES SERIE (Codice) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE RECSERIE
    ADD CONSTRAINT FKscrittRecSerie FOREIGN KEY (UsernameUtente) REFERENCES UTENTE (Username) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE REGISTRATORE
    ADD CONSTRAINT FKtipoReg_FK FOREIGN KEY (Username) REFERENCES ACCOUNT (Username) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE REGISTRATORE
    ADD CONSTRAINT FKafferenza FOREIGN KEY (CodiceCinema) REFERENCES CINEMA (Codice) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE RICHIESTA
    ADD CONSTRAINT FKeffetuazione FOREIGN KEY (UsernameUtente) REFERENCES UTENTE (Username) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE SEZIONAMENTO_FILM
    ADD CONSTRAINT FKrec_SEZF FOREIGN KEY (NomeSezione) REFERENCES SEZIONE (Nome) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE SEZIONAMENTO_FILM
    ADD CONSTRAINT FKrec_RECF FOREIGN KEY (UsernameUtente, CodiceRecFilm) REFERENCES RECFILM (UsernameUtente, CodiceFilm) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE SEZIONAMENTO_SERIE
    ADD CONSTRAINT FKrec_RECS FOREIGN KEY (UsernameUtente, CodiceRecSerie) REFERENCES RECSERIE (UsernameUtente, CodiceSerie) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE SEZIONAMENTO_SERIE
    ADD CONSTRAINT FKrec_SEZS FOREIGN KEY (NomeSezione) REFERENCES SEZIONE (Nome) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE SINGOLO
    ADD CONSTRAINT FKpromoSin_FK FOREIGN KEY (CodiceTemplatePromo) REFERENCES TEMPLATEPROMO (CodicePromo) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE SINGOLO
    ADD CONSTRAINT FKsinSerie FOREIGN KEY (CodiceSerie) REFERENCES SERIE (Codice) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE SINGOLO
    ADD CONSTRAINT FKsinFilm FOREIGN KEY (CodiceFilm) REFERENCES FILM (Codice) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE STAGIONE
    ADD CONSTRAINT FKcompSerie FOREIGN KEY (CodiceSerie) REFERENCES SERIE (Codice) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE STAGIONE
    ADD CONSTRAINT FKcaststagione FOREIGN KEY (CodiceCast) REFERENCES CASTING (Codice) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE TESSERA
    ADD CONSTRAINT FKtesseramento FOREIGN KEY (UsernameUtente) REFERENCES UTENTE (Username) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE TESSERA
    ADD CONSTRAINT FKappartenenza FOREIGN KEY (CodiceCinema) REFERENCES CINEMA (Codice) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE UTENTE
    ADD CONSTRAINT FKtipoUsr_FK FOREIGN KEY (Username) REFERENCES ACCOUNT (Username) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE VALUTAZIONE_FILM
    ADD CONSTRAINT FKval_UTEF FOREIGN KEY (UsernameUtente) REFERENCES UTENTE (Username) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE VALUTAZIONE_FILM
    ADD CONSTRAINT FKval_RECF FOREIGN KEY (UsernameUtenteValutato, CodiceRecFilm) REFERENCES RECFILM (UsernameUtente, CodiceFilm) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE VALUTAZIONE_SERIE
    ADD CONSTRAINT FKval_UTES FOREIGN KEY (UsernameUtente) REFERENCES UTENTE (Username) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE VALUTAZIONE_SERIE
    ADD CONSTRAINT FKval_RECS FOREIGN KEY (UsernameUtenteValutato, CodiceRecSerie) REFERENCES RECSERIE (UsernameUtente, CodiceSerie) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE VISUALIZZAZIONI_EPISODIO
    ADD CONSTRAINT FKvis_EPIS FOREIGN KEY (CodiceSerie, NumeroStagione, NumeroEpisodio) REFERENCES EPISODIO (CodiceSerie, NumeroStagione, NumeroEpisodio) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE VISUALIZZAZIONI_EPISODIO
    ADD CONSTRAINT FKvis_UTES FOREIGN KEY (UsernameUtente) REFERENCES UTENTE (Username) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE VISUALIZZAZIONI_FILM
    ADD CONSTRAINT FKvis_UTEF FOREIGN KEY (UsernameUtente) REFERENCES UTENTE (Username) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;
ALTER TABLE VISUALIZZAZIONI_FILM
    ADD CONSTRAINT FKvis_FILF FOREIGN KEY (CodiceFilm) REFERENCES FILM (Codice) ON
        DELETE CASCADE ON
        UPDATE RESTRICT;

-- TRIGGERS Section
DELIMITER //
CREATE TRIGGER CK_UNIQUE_AMM
    BEFORE INSERT
    ON amministratore
    FOR EACH ROW
BEGIN
    IF NEW.username IN (SELECT username FROM utente) OR NEW.username IN (SELECT username FROM registratore) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Username gia di un altro tipo.';
    END IF;
END;
//
CREATE TRIGGER CK_UNIQUE_REG
    BEFORE INSERT
    ON registratore
    FOR EACH ROW
BEGIN
    IF NEW.username IN (SELECT username FROM amministratore) OR NEW.username IN (SELECT username FROM utente) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Username gia di un altro tipo.';
    END IF;
END;
//
CREATE TRIGGER CK_UNIQUE_USR
    BEFORE INSERT
    ON utente
    FOR EACH ROW
BEGIN
    IF NEW.username IN (SELECT username FROM amministratore) OR NEW.username IN (SELECT username FROM registratore) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Username gia di un altro tipo.';
    END IF;
END;
//
CREATE TRIGGER CK_VISUAL_BEFORE_REC
    BEFORE INSERT
    ON recfilm
    FOR EACH ROW
BEGIN
    IF NOT EXISTS(SELECT UsernameUtente, CodiceFilm
                  FROM visualizzazioni_film
                  WHERE UsernameUtente = NEW.UsernameUtente
                    AND CodiceFilm = NEW.CodiceFilm)
    THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Per poter recensire un film va prima visualizzato.';
    END IF;
END;
//
CREATE TRIGGER CK_VISUAL_BEFORE_REC_SERIES
    BEFORE INSERT
    ON recserie
    FOR EACH ROW
BEGIN
    IF NOT EXISTS (SELECT COUNT(*) AS NumeroVisualizzati
                   FROM visualizzazioni_episodio
                   WHERE visualizzazioni_episodio.CodiceSerie = NEW.CodiceSerie
                     AND visualizzazioni_episodio.UsernameUtente = NEW.UsernameUtente
                   HAVING NumeroVisualizzati = (SELECT COUNT(*)
                                                FROM episodio
                                                WHERE episodio.CodiceSerie = NEW.CodiceSerie))
    THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT =
                'Per poter recensire una serie vanno prima visualizzati tutti i suoi episodi.';
    END IF;
END;
//
DELIMITER ;

-- Index Section
SHOW WARNINGS;
