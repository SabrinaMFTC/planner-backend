CREATE TABLE tasks (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    start_date_time TIMESTAMP,
    end_date_time TIMESTAMP,
    planned_date DATE NOT NULL,
    due_date_time TIMESTAMP NOT NULL,
    description VARCHAR(250) NOT NULL,
    status VARCHAR(20) NOT NULL,
    type VARCHAR(20) NOT NULL,

    CONSTRAINT fk_task_user
       FOREIGN KEY (user_id)
       REFERENCES users (id)
       ON DELETE CASCADE
);

CREATE INDEX idx_tasks_planned_date ON tasks (planned_date);
CREATE INDEX idx_tasks_due_date_time ON tasks (due_date_time);
CREATE INDEX idx_tasks_status ON tasks (status);
CREATE INDEX idx_tasks_user_id ON tasks (user_id);
