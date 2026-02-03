CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE subjects (
    id UUID PRIMARY KEY,
    title VARCHAR(250) NOT NULL,
    normalized_title VARCHAR(250) NOT NULL,
    professor VARCHAR(100) NOT NULL
);

CREATE UNIQUE INDEX uk_subjects_normalized_title ON subjects (normalized_title);
