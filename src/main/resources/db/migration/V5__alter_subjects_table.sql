ALTER TABLE subjects
    RENAME COLUMN teacher TO professor;

ALTER TABLE subjects
    DROP COLUMN IF EXISTS week_day,
    DROP COLUMN IF EXISTS start_time,
    DROP COLUMN IF EXISTS end_time;
