CREATE TABLE municipality
(
    code TEXT PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE district
(
    code              TEXT PRIMARY KEY,
    name              TEXT NOT NULL,
    municipality_code TEXT NOT NULL,
    FOREIGN KEY (municipality_code) REFERENCES municipality (code)
);