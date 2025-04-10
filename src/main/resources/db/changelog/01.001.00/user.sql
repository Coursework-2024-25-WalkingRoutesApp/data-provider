-- liquibase formatted sql
-- changeset aevsyukov_1@edu.hse.ru:10 logicalFilePath:01.001.00/user.sql
alter table "user" add column roles varchar[] default array['DEFAULT']::varchar[] not null;

alter table "user" add constraint user_roles_check check (
    array_position(roles, 'DEFAULT') is not null
        or array_position(roles, 'ADMIN') is not null
    );

-- rollback alter table "user" drop constraint user_role_check;
-- rollback alter table "user" drop column role;