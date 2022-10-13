CREATE TABLE IF NOT EXISTS handsonone.springuser (
    id SERIAL PRIMARY KEY,
    username VARCHAR(45) NOT NULL,
    password TEXT NOT NULL,
    algorithm VARCHAR(45) NOT NULL
 );

CREATE TABLE IF NOT EXISTS handsonone.authority (
    id SERIAL PRIMARY KEY,
    name VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS handsonone.springuser_authority (
    id SERIAL PRIMARY KEY,
    springuser INT REFERENCES handsonone.user(id) ON DELETE CASCADE,
    authority INT REFERENCES handsonone.authority(id) ON DELETE CASCADE
);

 CREATE TABLE IF NOT EXISTS handsonone.product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(45) NOT NULL,
    price VARCHAR(45) NOT NULL,
    currency VARCHAR(45) NOT NULL
);