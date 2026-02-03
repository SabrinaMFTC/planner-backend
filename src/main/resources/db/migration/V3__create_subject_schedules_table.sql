CREATE TABLE subject_schedules (
    id UUID PRIMARY KEY,
    subject_id UUID NOT NULL,
    week_day VARCHAR(15) NOT NULL,
    period VARCHAR(50) NOT NULL,

    CONSTRAINT fk_schedule_subject
       FOREIGN KEY (subject_id)
       REFERENCES subjects(id)
       ON DELETE CASCADE,

    CONSTRAINT uk_subject_schedules_weekday_period
       UNIQUE (week_day, period)
);

CREATE INDEX idx_subject_schedules_subject_id ON subject_schedules(subject_id);
CREATE INDEX idx_subject_schedules_week_day ON subject_schedules(week_day);
