CREATE TABLE tasks (
    id UUID PRIMARY KEY,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    planned_date DATE,
    due_date_time TIMESTAMP NOT NULL,
    description VARCHAR(250) NOT NULL,
    status VARCHAR(20) NOT NULL,
    subject_id UUID NOT NULL,
    CONSTRAINT fk_tasks_subject
        FOREIGN KEY (subject_id)
        REFERENCES subjects(id)
        ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_tasks_subject_id ON tasks (subject_id);
CREATE INDEX IF NOT EXISTS idx_tasks_status ON tasks (status);
CREATE INDEX IF NOT EXISTS idx_tasks_due_date_time ON tasks (due_date_time);
CREATE INDEX IF NOT EXISTS idx_tasks_planned_date ON tasks (planned_date);
CREATE INDEX IF NOT EXISTS idx_tasks_start_time ON tasks (start_time);
