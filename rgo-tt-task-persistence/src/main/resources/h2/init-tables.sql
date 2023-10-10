CREATE TABLE tasks_board (
    PRIMARY KEY(entity_id),
    entity_id  IDENTITY,
    name       VARCHAR(64) NOT NULL,
    short_name VARCHAR(16) NOT NULL
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

CREATE TABLE task_type (
    PRIMARY KEY(entity_id),
    entity_id IDENTITY,
    name      VARCHAR(64) NOT NULL
);

INSERT INTO task_type(name)
VALUES ('Task'),
       ('Bug');

CREATE TABLE task (
    PRIMARY KEY(entity_id),
    entity_id          IDENTITY,
    name               VARCHAR(128)   NOT NULL,
    created_date       TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
    last_modified_date TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
    description        VARCHAR(4096),
    number             BIGINT,
    board_id           BIGINT         NOT NULL REFERENCES tasks_board(entity_id) ON DELETE CASCADE,
    type_id            BIGINT         NOT NULL REFERENCES task_type(entity_id),
    status_id          BIGINT         NOT NULL REFERENCES task_status(entity_id)
);

CREATE UNIQUE INDEX board_id_number_task_uq_idx
    ON task(board_id, number);
