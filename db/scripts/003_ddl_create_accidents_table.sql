CREATE TABLE IF NOT EXISTS accidents
(
    id      SERIAL PRIMARY KEY,
    name    TEXT,
    text    TEXT,
    address TEXT,
    type_id INTEGER REFERENCES types (id)
);