CREATE TABLE IF NOT EXISTS csrf.token (
  id SERIAL PRIMARY KEY,
  identifier VARCHAR(45) NOT NULL,
  token TEXT NOT NULL
);
