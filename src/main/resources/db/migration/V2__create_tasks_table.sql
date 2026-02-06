CREATE TABLE tasks (
    id UUID PRIMARY KEY,
    start_date_time TIMESTAMP,
    end_date_time TIMESTAMP,
    planned_date DATE NOT NULL,
    due_date_time TIMESTAMP NOT NULL,
    description VARCHAR(250) NOT NULL,
    status VARCHAR(20) NOT NULL,
    type VARCHAR(20) NOT NULL
);

CREATE INDEX idx_tasks_planned_date ON tasks (planned_date);
CREATE INDEX idx_tasks_due_date_time ON tasks (due_date_time);
CREATE INDEX idx_tasks_status ON tasks (status);