ALTER TABLE subject_schedules
ADD COLUMN IF NOT EXISTS period VARCHAR(50);

UPDATE subject_schedules
SET period = 'MORNING_1'
WHERE period IS NULL
    AND start_time = '07:30:00'
    AND end_time = '09:10:00';

UPDATE subject_schedules
SET period = 'MORNING_2'
WHERE period IS NULL
  AND start_time = '09:20:00'
  AND end_time = '11:00:00';

UPDATE subject_schedules
SET period = 'EVENING_1'
WHERE period IS NULL
  AND start_time = '19:20:00'
  AND end_time = '21:00:00';

UPDATE subject_schedules
SET period = 'EVENING_2'
WHERE period IS NULL
  AND start_time = '21:10:00'
  AND end_time = '22:50:00';

DO $$
    BEGIN
        IF EXISTS (SELECT 1 FROM subject_schedules WHERE period IS NULL) THEN
            RAISE EXCEPTION 'subject_schedules.period could not be backfilled for some rows. Check start_time/end_time values.';
        END IF;
    END $$;

ALTER TABLE subject_schedules
ALTER COLUMN period SET NOT NULL;

ALTER TABLE subject_schedules
DROP COLUMN IF EXISTS start_time,
DROP COLUMN IF EXISTS end_time;

ALTER TABLE subject_schedules
ADD CONSTRAINT uk_subject_schedules_weekday_period
UNIQUE (week_day, period);

ALTER TABLE tasks
DROP COLUMN IF EXISTS title;
