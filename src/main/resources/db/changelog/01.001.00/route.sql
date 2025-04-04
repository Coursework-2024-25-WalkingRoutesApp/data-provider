-- liquibase formatted sql
-- changeset aevsyukov_1@edu.hse.ru:11 logicalFilePath:01.001.00/route.sql
alter table route
    alter column length type double precision using length::double precision;
-- rollback alter table route alter column length type double precision using length::double precision;
