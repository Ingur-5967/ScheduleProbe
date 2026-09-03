CREATE SCHEMA IF NOT EXISTS soc;

CREATE TABLE soc.users
(
    id            UUID PRIMARY KEY,
    username      VARCHAR(55) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role          VARCHAR(50)  NOT NULL,
    created_at    TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE soc.lessons
(
    id            UUID PRIMARY KEY,
    name          VARCHAR(150) NOT NULL,
    lesson_type   VARCHAR(80)  NOT NULL,
    teacher_name  VARCHAR(100) NOT NULL,
    room_id       VARCHAR(50) NOT NULL,
    group_id      VARCHAR(65) NOT NULL,
    day_of_week   INTEGER,
    is_upper_week BOOLEAN,
    start_time    BIGINT       NOT NULL,
    end_time      BIGINT       NOT NULL
);

CREATE TABLE soc.assessments
(
    id              UUID PRIMARY KEY,
    subject_id      BIGINT       NOT NULL,
    teacher_id      BIGINT       NOT NULL,
    assessment_type VARCHAR(50)  NOT NULL,
    group_id        VARCHAR(65) NOT NULL,
    room_id         VARCHAR(50) NOT NULL,
    start_time      BIGINT       NOT NULL,
    end_time        BIGINT       NOT NULL
);

CREATE TABLE soc.refresh_tokens
(
    id            UUID PRIMARY KEY,
    refresh_token TEXT           NOT NULL,
    expired_at    TIMESTAMP WITH TIME ZONE NOT NULL
);