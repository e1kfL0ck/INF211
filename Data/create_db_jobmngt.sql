-- Title :             SQL script to create the DB of the jobmngt project
-- Version :           0.1
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

create table appuser
(
    id       serial primary key,
    mail     varchar(50) not null unique,
    city     VARCHAR(30),
    password varchar(50) not null check (length(password) >= 4),
    usertype varchar(50) check (usertype in ('company', 'candidate'))
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

create table sector
(
    id    serial primary key,
    label varchar(50) not null unique
);

create table QualificationLevel
(
    id    serial primary key,
    label varchar(50) not null unique
);

CREATE TABLE JobOffer
(
    id                     SERIAL PRIMARY KEY,
    title                  VARCHAR(50) NOT NULL,
    publicationDate        DATE         NOT NULL DEFAULT CURRENT_DATE,
    description            TEXT,
    company_id             INT          NOT NULL,
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
    application_id INT  NOT NULL,
    job_offer_id   INT  NOT NULL,
    date           DATE NOT NULL DEFAULT CURRENT_DATE,
    message        TEXT NOT NULL,
    FOREIGN KEY (application_id) REFERENCES Application (id) ON DELETE CASCADE,
    FOREIGN KEY (job_offer_id) REFERENCES JobOffer (id) ON DELETE CASCADE
);

CREATE TABLE ApplicationMessage
(
    id             SERIAL PRIMARY KEY,
    application_id INT  NOT NULL,
    job_offer_id   INT  NOT NULL,
    date           DATE NOT NULL DEFAULT CURRENT_DATE,
    message        TEXT NOT NULL,
    FOREIGN KEY (application_id) REFERENCES Application (id) ON DELETE CASCADE,
    FOREIGN KEY (job_offer_id) REFERENCES JobOffer (id) ON DELETE CASCADE
);

alter table appuser
    add constraint mailformat check (mail ~* '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$');

-- +----------------------------------------------------------------------------------------------+
-- | Insert some data                                                                             |
-- +----------------------------------------------------------------------------------------------+

-- Some users to test the login form
/*insert into appuser(mail, password, usertype, city)
values ('g.a@imt.fr', '1234', 'candidate', 'Brest');
insert into appuser(mail, password, usertype, city)
values ('ceo@google.fr', 'abcd', 'company', 'Rennes');*/

-- Some sectors
insert into sector(label)
values ('Purchase/Logistic'); --  1
insert into sector(label)
values ('Administration'); --  2
insert into sector(label)
values ('Agriculture'); --  3
insert into sector(label)
values ('Agrofood'); --  4
insert into sector(label)
values ('Insurance'); --  5
insert into sector(label)
values ('Audit/Advise/Expertise'); --  6
insert into sector(label)
values ('Public works/Real estate'); --  7
insert into sector(label)
values ('Trade'); --  8
insert into sector(label)
values ('Communication/Art/Media/Fashion'); --  9
insert into sector(label)
values ('Accounting'); -- 10
insert into sector(label)
values ('Direction/Execution'); -- 11
insert into sector(label)
values ('Distribution/Sale'); -- 12
insert into sector(label)
values ('Electronic/Microelectronic'); -- 13
insert into sector(label)
values ('Environment'); -- 14
insert into sector(label)
values ('Finance/Bank'); -- 15
insert into sector(label)
values ('Training/Teaching'); -- 16
insert into sector(label)
values ('Hotel/Restaurant/Tourism'); -- 17
insert into sector(label)
values ('Industry/Engineering/Production'); -- 18
insert into sector(label)
values ('Computer science'); -- 19
insert into sector(label)
values ('Juridique/Fiscal/Droit'); -- 20
insert into sector(label)
values ('Marketing'); -- 21
insert into sector(label)
values ('Public/Parapublic'); -- 22
insert into sector(label)
values ('Human resources'); -- 23
insert into sector(label)
values ('Health/Social/Biology/HHumanitarian'); -- 24
insert into sector(label)
values ('Telecom/Networking');
-- 25

-- Some qualification levels
insert into QualificationLevel(label)
values ('Professional level'); --  1
insert into QualificationLevel(label)
values ('A-diploma'); --  2
insert into QualificationLevel(label)
values ('Licence'); --  3
insert into QualificationLevel(label)
values ('Master'); --  4
insert into QualificationLevel(label)
values ('PhD'); --  5


