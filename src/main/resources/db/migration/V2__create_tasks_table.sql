CREATE TABLE tasks (
    id UUID PRIMARY KEY,
    start_date_time TIMESTAMP,
    end_date_time TIMESTAMP,
    planned_date DATE NOT NULL,
    due_date DATE NOT NULL,
    due_time TIME,
    description VARCHAR(250) NOT NULL,
    status VARCHAR(20) NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_tasks_start_date_time ON tasks (start_date_time);
CREATE INDEX IF NOT EXISTS idx_tasks_end_date_time ON tasks (end_date_time);
CREATE INDEX IF NOT EXISTS idx_tasks_planned_date ON tasks (planned_date);
CREATE INDEX IF NOT EXISTS idx_tasks_due_date ON tasks (due_date);
CREATE INDEX IF NOT EXISTS idx_tasks_due_time ON tasks (due_time);
CREATE INDEX IF NOT EXISTS idx_tasks_status ON tasks (status);
