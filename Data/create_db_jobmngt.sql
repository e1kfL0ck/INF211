-- Title :             SQL script to create the DB of the jobmngt project
-- Version :           0.2
-- Creation date :     24/08/2023
-- Last modification date : 26/02/2025
-- Author :            Grégory SMITS
-- Description :       Script used to create the DB of the job management app.
--                     Tested on PostgreSQL 10

-- +----------------------------------------------------------------------------------------------+
-- | Suppress tables if they exist                                                                |
-- +----------------------------------------------------------------------------------------------+

DROP TABLE IF EXISTS appuser;
DROP TABLE IF EXISTS ApplicationSector;
DROP TABLE IF EXISTS JobOfferSector;
DROP TABLE IF EXISTS ApplicationMessage;
DROP TABLE IF EXISTS JobOfferMessage;
DROP TABLE IF EXISTS Application;
DROP TABLE IF EXISTS JobOffer;
DROP TABLE IF EXISTS Candidate;
DROP TABLE IF EXISTS Company;
DROP TABLE IF EXISTS ActivitySector;
DROP TABLE IF EXISTS QualificationLevel;

-- +----------------------------------------------------------------------------------------------+
-- | Tables creation                                                                              |
-- +----------------------------------------------------------------------------------------------+

CREATE TABLE appuser
(
    id       SERIAL PRIMARY KEY,
    mail     VARCHAR(50) NOT NULL UNIQUE,
    city     VARCHAR(30),
    password VARCHAR(50) NOT NULL CHECK (LENGTH(password) >= 4),
    usertype VARCHAR(50) CHECK (usertype IN ('company', 'candidate'))
);

CREATE TABLE Company
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(50) NOT NULL,
    description TEXT,
    user_id     INT UNIQUE,
    FOREIGN KEY (user_id) REFERENCES appuser (id) ON DELETE CASCADE
);

CREATE TABLE Candidate
(
    id        SERIAL PRIMARY KEY,
    lastName  VARCHAR(50) NOT NULL,
    firstName VARCHAR(50) NOT NULL,
    user_id   INT UNIQUE,
    FOREIGN KEY (user_id) REFERENCES appuser (id) ON DELETE CASCADE
);

CREATE TABLE sector
(
    id    SERIAL PRIMARY KEY,
    label VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE QualificationLevel
(
    id    SERIAL PRIMARY KEY,
    label VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE JobOffer
(
    id                     SERIAL PRIMARY KEY,
    title                  VARCHAR(50) NOT NULL,
    publicationDate        DATE        NOT NULL DEFAULT CURRENT_DATE,
    description            VARCHAR(1000),
    company_id             INT         NOT NULL,
    qualification_level_id INT,
    FOREIGN KEY (company_id) REFERENCES Company (id) ON DELETE CASCADE,
    FOREIGN KEY (qualification_level_id) REFERENCES QualificationLevel (id) ON DELETE SET NULL
);

CREATE TABLE Application
(
    id                     SERIAL PRIMARY KEY,
    cv                     TEXT NOT NULL,
    applicationDate        DATE NOT NULL DEFAULT CURRENT_DATE,
    candidate_id           INT  NOT NULL,
    qualification_level_id INT,
    FOREIGN KEY (candidate_id) REFERENCES Candidate (id) ON DELETE CASCADE,
    FOREIGN KEY (qualification_level_id) REFERENCES QualificationLevel (id) ON DELETE SET NULL
);

CREATE TABLE JobOfferSector
(
    job_offer_id INT NOT NULL,
    sector_id    INT NOT NULL,
    PRIMARY KEY (job_offer_id, sector_id),
    FOREIGN KEY (job_offer_id) REFERENCES JobOffer (id) ON DELETE CASCADE,
    FOREIGN KEY (sector_id) REFERENCES sector (id) ON DELETE CASCADE
);

CREATE TABLE ApplicationSector
(
    application_id INT NOT NULL,
    sector_id      INT NOT NULL,
    PRIMARY KEY (application_id, sector_id),
    FOREIGN KEY (application_id) REFERENCES Application (id) ON DELETE CASCADE,
    FOREIGN KEY (sector_id) REFERENCES sector (id) ON DELETE CASCADE
);

CREATE TABLE JobOfferMessage
(
    id             SERIAL PRIMARY KEY,
    application_id INT           NOT NULL,
    job_offer_id   INT           NOT NULL,
    date           DATE          NOT NULL DEFAULT CURRENT_DATE,
    message        VARCHAR(1000) NOT NULL,
    FOREIGN KEY (application_id) REFERENCES Application (id) ON DELETE CASCADE,
    FOREIGN KEY (job_offer_id) REFERENCES JobOffer (id) ON DELETE CASCADE
);

CREATE TABLE ApplicationMessage
(
    id             SERIAL PRIMARY KEY,
    application_id INT           NOT NULL,
    job_offer_id   INT           NOT NULL,
    date           DATE          NOT NULL DEFAULT CURRENT_DATE,
    message        VARCHAR(1000) NOT NULL,
    FOREIGN KEY (application_id) REFERENCES Application (id) ON DELETE CASCADE,
    FOREIGN KEY (job_offer_id) REFERENCES JobOffer (id) ON DELETE CASCADE
);

ALTER TABLE appuser
    ADD CONSTRAINT mailformat CHECK (mail ~* '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$');

-- +----------------------------------------------------------------------------------------------+
-- | Insert some data                                                                             |
-- +----------------------------------------------------------------------------------------------+

-- Some users to test the login form
/*INSERT INTO appuser(mail, password, usertype, city)
VALUES ('g.a@imt.fr', '1234', 'candidate', 'Brest');
INSERT INTO appuser(mail, password, usertype, city)
VALUES ('ceo@google.fr', 'abcd', 'company', 'Rennes');*/

-- Some sectors
INSERT INTO sector(label)
VALUES ('Purchase/Logistic'),
       ('Administration'),
       ('Agriculture'),
       ('Agrofood'),
       ('Insurance'),
       ('Audit/Advise/Expertise'),
       ('Public works/Real estate'),
       ('Trade'),
       ('Communication/Art/Media/Fashion'),
       ('Accounting'),
       ('Direction/Execution'),
       ('Distribution/Sale'),
       ('Electronic/Microelectronic'),
       ('Environment'),
       ('Finance/Bank'),
       ('Training/Teaching'),
       ('Hotel/Restaurant/Tourism'),
       ('Industry/Engineering/Production'),
       ('Computer science'),
       ('Juridique/Fiscal/Droit'),
       ('Marketing'),
       ('Public/Parapublic'),
       ('Human resources'),
       ('Health/Social/Biology/HHumanitarian'),
       ('Telecom/Networking');

-- Some qualification levels
INSERT INTO QualificationLevel(label)
VALUES ('Professional level'),
       ('A-diploma'),
       ('Licence'),
       ('Master'),
       ('PhD');