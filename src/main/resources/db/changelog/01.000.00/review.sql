-- liquibase formatted sql
-- changeset aevsyukov_1@edu.hse.ru:8 logicalFilePath:01.000.00/review.sql
create table review
(
    user_id     uuid references "user" (id),
    route_id    uuid references route (id),
    mark        int,
    review_text varchar,
    created_at  timestamp,
    primary key (user_id, route_id)
);
-- rollback drop table review;
