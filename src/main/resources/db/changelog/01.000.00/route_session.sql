-- liquibase formatted sql
-- changeset aevsyukov_1@edu.hse.ru:6 logicalFilePath:01.000.00/route_session.sql
create table route_session
(
    id          uuid primary key,
    user_id     uuid references "user" (id),
    route_id    uuid references route (id),
    is_finished boolean,
    started_at  timestamp,
    ended_at    timestamp
);
-- rollback drop table route_session;
