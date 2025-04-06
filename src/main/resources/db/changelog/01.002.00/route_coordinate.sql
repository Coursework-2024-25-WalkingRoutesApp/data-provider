-- liquibase formatted sql
-- changeset aevsyukov_1@edu.hse.ru:12 logicalFilePath:01.002.00/route_coordinate.sql
alter table route_coordinate
    add column title varchar;

alter table route_coordinate
    add column description varchar;
-- rollback alter table route_coordinate drop column title;
-- rollback alter table route_coordinate drop column description;
