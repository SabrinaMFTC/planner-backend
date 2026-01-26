CREATE TABLE tasks (
    id UUID PRIMARY KEY,
    task_status VARCHAR(20) NOT NULL,
    title VARCHAR(250) NOT NULL,
    description VARCHAR(250) NOT NULL,
    due_date_time TIMESTAMP NOT NULL,
    subject_id UUID NOT NULL,
    CONSTRAINT fk_tasks_subject
        FOREIGN KEY (subject_id)
        REFERENCES subjects(id)
        ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_tasks_subject_id ON tasks (subject_id);
CREATE INDEX IF NOT EXISTS idx_tasks_status ON tasks (task_status);
CREATE INDEX IF NOT EXISTS idx_tasks_due_date_time ON tasks (due_date_time);
