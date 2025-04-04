-- liquibase formatted sql
-- changeset aevsyukov_1@edu.hse.ru:10 logicalFilePath:01.001.00/user.sql
alter table "user" add column role varchar default 'DEFAULT' not null;

alter table "user" add constraint user_role_check check (role in ('DEFAULT', 'ADMIN'));

-- rollback alter table "user" drop constraint user_role_check;
-- rollback alter table "user" drop column role;