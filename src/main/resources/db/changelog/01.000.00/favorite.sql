-- liquibase formatted sql
-- changeset aevsyukov_1@edu.hse.ru:9 logicalFilePath:01.000.00/favorite.sql
create table favorite
(
    user_id     uuid references "user" (id),
    route_id    uuid references route (id),
    primary key (user_id, route_id)
);
-- rollback drop table favorite;
