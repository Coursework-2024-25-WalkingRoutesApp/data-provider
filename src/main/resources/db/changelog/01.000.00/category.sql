-- liquibase formatted sql
-- changeset aevsyukov_1@edu.hse.ru:3 logicalFilePath:01.000.00/category.sql
create table category
(
    category_name varchar primary key
);
-- rollback drop table category;
