-- liquibase formatted sql
-- changeset aevsyukov_1@edu.hse.ru:7 logicalFilePath:01.000.00/user_checkpoint.sql
create table user_checkpoint
(
    route_session_id uuid references route_session (id),
    coordinate_id    uuid references route_coordinate (id),
    created_at       timestamp,
    primary key (route_session_id, coordinate_id)
);
-- rollback drop table user_checkpoint;
