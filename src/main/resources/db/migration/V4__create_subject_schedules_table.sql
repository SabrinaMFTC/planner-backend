CREATE TABLE subject_schedules (
    id UUID PRIMARY KEY,
    subject_id UUID NOT NULL,
    week_day VARCHAR(15) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    CONSTRAINT fk_schedule_subject
        FOREIGN KEY (subject_id)
        REFERENCES subjects(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_subject_schedules_subject_id ON subject_schedules(subject_id);
CREATE INDEX idx_subject_schedules_week_day ON subject_schedules(week_day);
