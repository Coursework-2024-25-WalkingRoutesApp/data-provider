-- liquibase formatted sql
-- changeset aevsyukov_1@edu.hse.ru:5 logicalFilePath:01.000.00/route_coordinate.sql
create table route_coordinate
(
    id           uuid default gen_random_uuid()
        primary key,
    route_id     uuid references route (id),
    point        geometry(point, 4326),
    order_number int
);
-- rollback drop table route_coordinate;
