ALTER TABLE subjects
    ALTER COLUMN id DROP DEFAULT;

ALTER TABLE subjects
    ALTER COLUMN week_day TYPE VARCHAR(15);

CREATE INDEX IF NOT EXISTS idx_subjects_week_day
    ON subjects (week_day);
