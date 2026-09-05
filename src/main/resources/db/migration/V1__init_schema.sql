CREATE SEQUENCE lessons_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE assessments_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE users_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE users
(
    id            BIGINT PRIMARY KEY DEFAULT nextval('users_id_seq'),
    username      VARCHAR(55) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role          VARCHAR(50)  NOT NULL,
    created_at    TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE lessons
(
    id            BIGINT PRIMARY KEY DEFAULT nextval('lessons_id_seq'),
    name          VARCHAR(150) NOT NULL,
    lesson_type   VARCHAR(80) NOT NULL,
    teacher_id    BIGINT NOT NULL,
    room_id       VARCHAR(100) NOT NULL,
    group_id      VARCHAR(150) NOT NULL,
    day_of_week   INTEGER,
    is_upper_week BOOLEAN,
    start_time    BIGINT NOT NULL,
    end_time      BIGINT NOT NULL
);

CREATE TABLE assessments
(
    id              BIGINT PRIMARY KEY DEFAULT nextval('assessments_id_seq'),
    subject_id      BIGINT NOT NULL,
    teacher_id      BIGINT NOT NULL,
    assessment_type VARCHAR(50) NOT NULL,
    group_id        VARCHAR(150) NOT NULL,
    room_id         VARCHAR(100) NOT NULL,
    start_time      BIGINT NOT NULL,
    end_time        BIGINT NOT NULL
);

CREATE TABLE refresh_tokens
(
    id            UUID PRIMARY KEY,
    refresh_token TEXT           NOT NULL,
    expired_at    TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE user_additional_info
(
    id                 BIGINT PRIMARY KEY,
    full_name          VARCHAR(50) NOT NULL,
    cathedra           VARCHAR(255) NOT NULL,
    group_id           VARCHAR(150),
    study_period       INTEGER,
    level_of_education VARCHAR(50)
);

CREATE TABLE lesson_timetags
(
    id                 BIGINT PRIMARY KEY,
    comment            VARCHAR(150) NOT NULL,
    expired_at         TIMESTAMP WITH TIME ZONE NOT NULL
);