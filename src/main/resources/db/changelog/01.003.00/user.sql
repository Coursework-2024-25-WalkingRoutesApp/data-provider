-- liquibase formatted sql
-- changeset aevsyukov_1@edu.hse.ru:13 logicalFilePath:01.002.00/user.sql
alter table "user" add column is_verified boolean default false not null;
-- rollback alter table "user" drop column is_verified;
