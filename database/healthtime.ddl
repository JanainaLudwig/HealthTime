CREATE DATABASE healthtime 
    WITH ENCODING 'UTF8';


CREATE DOMAIN DMN_appointment_time
  SMALLINT
  CHECK (VALUE IN (1,  -- 8h00 ~ 8h30
                  2,  -- 8h30 ~ 9h00
                  3,  -- 9h00 ~ 9h30
                  4,  -- 9h30 ~ 10h00
                  5,  -- 10h00 ~ 10h30
                  6,  -- 10h30 ~ 11h00
                  7,  -- 11h00 ~ 11h30
                  8,  -- 13h30 ~ 14h00
                  9,  -- 14h00 ~ 14h30
                  10, -- 14h30 ~ 15h00
                  11, -- 15h00 ~ 15h30
                  12, -- 15h30 ~ 16h00
                  13, -- 16h00 ~ 16h30
                  14  -- 16h30 ~ 17h00
                 ));


CREATE TABLE users (
    id_user SERIAL,
    name VARCHAR(50) NOT NULL
);

ALTER TABLE users ADD CONSTRAINT PK_USERS
    PRIMARY KEY (id_user);


CREATE TABLE consultant (
    id_user INT,
    sus_number VARCHAR(20) NOT NULL UNIQUE,
    id_city INT NOT NULL
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


CREATE TABLE specialty (
    id_specialty SERIAL,
    description VARCHAR(50) UNIQUE
);

ALTER TABLE specialty ADD CONSTRAINT PK_SPECIALTY
    PRIMARY KEY (id_specialty);


CREATE TABLE doctor_specialty (
     id_doctor INT,    
     id_specialty INT
);

ALTER TABLE doctor_specialty ADD CONSTRAINT PK_DOCTOR_SPECIALTY
    PRIMARY KEY (id_doctor, id_specialty);
ALTER TABLE doctor_specialty ADD CONSTRAINT FK_DOCTOR
    FOREIGN KEY (id_doctor) REFERENCES doctor (id_user);
ALTER TABLE doctor_specialty ADD CONSTRAINT FK_SPECIALTY
    FOREIGN KEY (id_specialty) REFERENCES specialty (id_specialty);


CREATE TABLE appointment (
     id_appointment SERIAL,
     id_doctor INT,
     id_consultant INT,    
     id_specialty INT,
     id_city INT,
     appointment_date DATE NOT NULL,
     appointment_time DMN_appointment_time NOT NULL
);

ALTER TABLE appointment ADD CONSTRAINT PK_APPOINTMENT
    PRIMARY KEY (id_appointment);
ALTER TABLE appointment ADD CONSTRAINT FK_DOCTOR
    FOREIGN KEY (id_doctor) REFERENCES doctor (id_user);
ALTER TABLE appointment ADD CONSTRAINT FK_CONSULTANT
    FOREIGN KEY (id_consultant) REFERENCES consultant (id_user);
ALTER TABLE appointment ADD CONSTRAINT FK_SPECIALTY
    FOREIGN KEY (id_specialty) REFERENCES specialty (id_specialty);


CREATE TABLE working_time (
    id_doctor INT,
    appointment_time DMN_appointment_time,
    week_day INT,
    id_city INT NOT NULL

);

ALTER TABLE working_time ADD CONSTRAINT PK_WORKING_TIME
    PRIMARY KEY (id_doctor, appointment_time, week_day);
ALTER TABLE working_time ADD CONSTRAINT FK_WORKING_TIME
    FOREIGN KEY (id_doctor) REFERENCES doctor (id_user);
ALTER TABLE working_time ADD CONSTRAINT CHECK_WEEK_DAY
    CHECK (week_day BETWEEN 1 AND 7);

CREATE TABLE appointment_release (
    id_specialty INT,
    id_patient INT,
    id_doctor INT,
    id_appointment INT DEFAULT NULL,
    release_date DATE NOT NULL
);

ALTER TABLE appointment_release ADD CONSTRAINT PK_APPOINTMENT_RELEASE
    PRIMARY KEY (id_specialty, id_patient, release_date);
ALTER TABLE appointment_release ADD CONSTRAINT FK_SPECIALTY
    FOREIGN KEY (id_specialty) REFERENCES specialty (id_specialty);
ALTER TABLE appointment_release ADD CONSTRAINT FK_PATIENT
    FOREIGN KEY (id_patient) REFERENCES consultant (id_user);
ALTER TABLE appointment_release ADD CONSTRAINT FK_DOCTOR
    FOREIGN KEY (id_doctor) REFERENCES doctor (id_user);
ALTER TABLE appointment_release ADD CONSTRAINT FK_APPOINTMENT
    FOREIGN KEY (id_appointment) REFERENCES appointment (id_appointment)
      ON DELETE SET NULL;


CREATE TABLE password_recovery (
    id_user INT,
    cpf VARCHAR(11) NOT NULL,
    mother_name VARCHAR(100) NOT NULL
);

ALTER TABLE password_recovery ADD CONSTRAINT PK_PASSWORD_RECOVERY
    PRIMARY KEY (id_user);
ALTER TABLE password_recovery ADD CONSTRAINT FK_PASSWORD_USER
    FOREIGN KEY (id_user) REFERENCES users (id_user);


-- Select available appointments
CREATE OR REPLACE FUNCTION available_appointments(consultant int, city int, search_day DATE default CURRENT_TIMESTAMP, specialty int default 1)
  RETURNS TABLE (appointment_time dmn_appointment_time, week_day double precision, id_doctor int)
AS
$body$
SELECT DISTINCT
                wt.appointment_time,
                wt.week_day,
                wt.id_doctor
FROM working_time AS wt
       JOIN doctor AS d ON wt.id_doctor = d.id_user
       JOIN doctor_specialty AS ds ON d.id_user = ds.id_doctor
WHERE ds.id_specialty = specialty
  AND wt.id_city = city
  AND wt.week_day = extract(ISODOW FROM search_day)
  AND wt.appointment_time NOT IN (
    SELECT appointment_time
      FROM appointment
      WHERE id_consultant = consultant
        AND appointment_date = search_day
    )

    EXCEPT

SELECT
       a.appointment_time,
       extract(ISODOW FROM a.appointment_date) AS week_day,
       a.id_doctor
FROM appointment AS a
WHERE a.id_city = city
  AND a.appointment_date = search_day
ORDER BY appointment_time
$body$
language sql;


-- Remove appointment release on schedule
CREATE OR REPLACE FUNCTION set_appointment_on_release()
RETURNS TRIGGER
AS
$body$
BEGIN
UPDATE appointment_release
    SET id_appointment = NEW.id_appointment
    WHERE id_specialty = NEW.id_specialty
      AND id_appointment IS NULL
      AND id_patient = NEW.id_consultant
      AND release_date = (
        SELECT max(release_date) FROM appointment_release
          WHERE id_specialty = NEW.id_specialty
            AND id_appointment IS NULL
            AND id_patient = NEW.id_consultant
      );
RETURN NULL;
END;
$body$
LANGUAGE 'plpgsql';

CREATE TRIGGER update_realease_on_insert AFTER INSERT ON appointment FOR EACH ROW EXECUTE PROCEDURE set_appointment_on_release();