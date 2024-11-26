-- liquibase formatted sql
-- changeset aevsyukov_1@edu.hse.ru:4 logicalFilePath:01.000.00/route_category.sql
create table route_category
(
    route_id      uuid references route (id),
    category_name varchar references category (category_name),
    primary key (route_id, category_name)
);
-- rollback drop table route_category;
