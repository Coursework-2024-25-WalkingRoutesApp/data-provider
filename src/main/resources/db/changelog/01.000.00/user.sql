-- liquibase formatted sql
-- changeset aevsyukov_1@edu.hse.ru:1 logicalFilePath:01.000.00/user.sql
create table "user"
(
    id        uuid default gen_random_uuid()
        primary key,
    user_name varchar not null,
    email     varchar not null,
    password  varchar not null,
    photo_url varchar
)
-- rollback drop table "user";