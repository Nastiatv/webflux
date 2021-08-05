DROP TABLE IF EXISTS to_do;
CREATE TABLE to_do
(
    id          SERIAL      NOT NULL PRIMARY KEY,
    description VARCHAR(50) NOT NULL,
    created     TIMESTAMP   NOT NULL,
    modified    TIMESTAMP   NOT NULL,
    completed   BOOLEAN     NOT NULL
);