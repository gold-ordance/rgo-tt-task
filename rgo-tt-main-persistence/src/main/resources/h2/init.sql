CREATE TABLE tasks_board (
    PRIMARY KEY(entity_id),
    entity_id IDENTITY,
    name      VARCHAR(64) NOT NULL
);

CREATE TABLE task_status (
    PRIMARY KEY(entity_id),
    entity_id IDENTITY,
    name      VARCHAR(64) NOT NULL
);

INSERT INTO task_status(name)
VALUES ('TO DO'),
       ('IN PROGRESS'),
       ('DONE');

CREATE TABLE task (
    PRIMARY KEY(entity_id),
    entity_id          IDENTITY,
    name               VARCHAR(128)  NOT NULL,
    created_date       TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    board_id           BIGINT        NOT NULL REFERENCES tasks_board(entity_id) ON DELETE CASCADE,
    status_id          BIGINT        NOT NULL DEFAULT 1 REFERENCES task_status(entity_id),
    description        VARCHAR(4096)
);
