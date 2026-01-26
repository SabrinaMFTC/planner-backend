CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE subjects (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR(250) NOT NULL,
    week_day VARCHAR(10) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    teacher VARCHAR(100) NOT NULL
);
