CREATE TABLE IF NOT EXISTS handsontwo.auth_user (
 username VARCHAR(45) NOT NULL PRIMARY KEY,
 password TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS handsontwo.auth_otp (
 username VARCHAR(45) NOT NULL PRIMARY KEY,
 code VARCHAR(45) NOT NULL
);