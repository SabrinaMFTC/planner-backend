ALTER TABLE subjects
ADD COLUMN normalized_title VARCHAR(250) NOT NULL;

CREATE UNIQUE INDEX uk_subjects_normalized_title
ON subjects (normalized_title)
