CREATE DATABASE healthtime 
    WITH ENCODING 'UTF8';


CREATE TABLE users (
    id_user SERIAL,
    name VARCHAR(50) NOT NULL;
);

ALTER TABLE users ADD CONSTRAINT PK_USERS
    PRIMARY KEY (id_user);


CREATE TABLE consultant (
    id_user INT,
    sus_number VARCHAR(20) NOT NULL UNIQUE
);

ALTER TABLE consultant ADD CONSTRAINT PK_CONSULTANT
    PRIMARY KEY (id_user);
ALTER TABLE consultant ADD CONSTRAINT FK_USERS
    FOREIGN KEY (id_user) REFERENCES users (id_user);


CREATE TABLE doctor (
    id_user INT,
    crm_number VARCHAR(20) UNIQUE
);

ALTER TABLE doctor ADD CONSTRAINT PK_DOCTOR
    PRIMARY KEY (id_user);
ALTER TABLE doctor ADD CONSTRAINT FK_USERS
    FOREIGN KEY (id_user) REFERENCES users (id_user);


CREATE TABLE login (
    id_user INT,
    password VARCHAR(100) NOT NULL
);

ALTER TABLE login ADD CONSTRAINT PK_LOGIN
    PRIMARY KEY (id_user);
ALTER TABLE login ADD CONSTRAINT FK_LOGIN
    FOREIGN KEY (id_user) REFERENCES users (id_user);

