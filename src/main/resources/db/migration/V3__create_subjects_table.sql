CREATE TABLE subjects (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    title VARCHAR(250) NOT NULL,
    normalized_title VARCHAR(250) NOT NULL,
    professor VARCHAR(100) NOT NULL,

    CONSTRAINT fk_subject_user
    FOREIGN KEY (user_id)
    REFERENCES users (id)
    ON DELETE CASCADE
);

CREATE UNIQUE INDEX uk_subjects_normalized_title ON subjects (normalized_title);
