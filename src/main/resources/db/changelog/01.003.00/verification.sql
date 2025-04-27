-- liquibase formatted sql
-- changeset aevsyukov_1@edu.hse.ru:14 logicalFilePath:01.003.00/verification.sql
create table verification
(
    id         uuid      default gen_random_uuid()
        primary key,
    user_id    uuid                    not null references "user" (id),
    code       varchar                 not null,
    created_at timestamp default now() not null
)
-- rollback drop table verification;