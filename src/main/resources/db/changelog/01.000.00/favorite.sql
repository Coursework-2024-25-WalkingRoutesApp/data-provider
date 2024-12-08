-- liquibase formatted sql
-- changeset aevsyukov_1@edu.hse.ru:9 logicalFilePath:01.000.00/favorite.sql
create table favorite
(
    used_id     varchar references "user" (id),
    route_id    uuid references route (id),
    primary key (used_id, route_id)
);
-- rollback drop table favorite;
