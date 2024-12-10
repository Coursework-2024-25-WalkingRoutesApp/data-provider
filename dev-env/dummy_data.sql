insert into "user" (id, user_name, email, password)
values ('user1', 'Alice', 'alice@example.com', 'password123'),
       ('user2', 'Bob', 'bob@example.com', 'password456'),
       ('user3', 'Charlie', 'charlie@example.com', 'password789'),
       ('user4', 'David', 'david@example.com', 'password012'),
       ('user5', 'Eve', 'eva@example.com', 'password345'),
       ('user6', 'Frank', 'frank@example.com', 'password678'),
       ('user7', 'Grace', 'grace@example.com', 'password901'),
       ('user8', 'Helen', 'halen@example.com', 'password234'),
       ('user9', 'Ivan', 'ivan@example.com', 'password567'),
       ('user10', 'Jack', 'jack@example.com', 'password890');

insert into category (category_name)
values ('Nature'),
       ('Culture'),
       ('Metropolitan'),
       ('NearCafe');

insert into route (id, user_id, route_name, description, duration, length, start_point, end_point, route_preview,
                   is_draft, last_modified_at, created_at)
values
    (gen_random_uuid(), 'user1', 'route1', 'A challenging mountain hike.', '02:30:00', 10000, 'Point A', 'Point B', 'preview_image_1.jpg', false, now(), now()),
    (gen_random_uuid(), 'user2', 'route2', 'A leisurely cycle around the city.', '01:15:00', 5000, 'Park Entrance','City Center', 'preview_image_2.jpg', true, now(), now()),
    (gen_random_uuid(), 'user3', 'route3', 'Some data', '01:00:00', 2000, 'Metro Station', 'Metro Station', 'preview_image_3.jpg', false, now(), now()),
    (gen_random_uuid(), 'user4', 'route4', 'Some data', '00:45:00', 1000, 'Cafe', 'Cafe', 'preview_image_4.jpg', true, now(), now()),
    (gen_random_uuid(), 'user5', 'route5', 'Some data', '02:00:00', 5000, 'Park', 'Park', 'preview_image_5.jpg', false, now(), now()),
    (gen_random_uuid(), 'user6', 'route6', 'Some data', '01:30:00', 3000, 'Museum', 'Museum', 'preview_image_6.jpg', true, now(), now()),
    (gen_random_uuid(), 'user7', 'route7', 'Some data', '01:00:00', 2000, 'Metro Station', 'Metro Station', 'preview_image_7.jpg', false, now(), now()),
    (gen_random_uuid(), 'user8', 'route8', 'Some data', '00:45:00', 1000, 'Cafe', 'Cafe', 'preview_image_8.jpg', true, now(), now()),
    (gen_random_uuid(), 'user9', 'route9', 'Some data', '02:00:00', 5000, 'Park', 'Park', 'preview_image_9.jpg', false, now(), now()),
    (gen_random_uuid(), 'user10', 'route10', 'Some data', '01:30:00', 3000, 'Museum', 'Museum', 'preview_image_10.jpg', true, now(), now());

insert into route_category (route_id, category_name)
values ((select id from route where route_name = 'route1'), 'Nature'),
       ((select id from route where route_name = 'route2'), 'Culture'),
       ((select id from route where route_name = 'route3'), 'Metropolitan'),
       ((select id from route where route_name = 'route4'), 'NearCafe'),
       ((select id from route where route_name = 'route5'), 'Nature'),
       ((select id from route where route_name = 'route6'), 'Culture'),
       ((select id from route where route_name = 'route7'), 'Metropolitan'),
       ((select id from route where route_name = 'route8'), 'NearCafe'),
       ((select id from route where route_name = 'route9'), 'Nature'),
       ((select id from route where route_name = 'route10'), 'Culture');

insert into route_coordinate (id, route_id, point, order_number)
values
    (gen_random_uuid(), (select id from route where route_name = 'route1'), ST_GeomFromText('POINT(10 20)', 4326), 1),
    (gen_random_uuid(), (select id from route where route_name = 'route1'), ST_GeomFromText('POINT(15 25)', 4326), 2),
    (gen_random_uuid(), (select id from route where route_name = 'route2'), ST_GeomFromText('POINT(5 10)', 4326), 1),
    (gen_random_uuid(), (select id from route where route_name = 'route2'), ST_GeomFromText('POINT(7 12)', 4326), 2),
    (gen_random_uuid(), (select id from route where route_name = 'route3'), ST_GeomFromText('POINT(1 2)', 4326), 1),
    (gen_random_uuid(), (select id from route where route_name = 'route3'), ST_GeomFromText('POINT(3 4)', 4326), 2),
    (gen_random_uuid(), (select id from route where route_name = 'route4'), ST_GeomFromText('POINT(0 0)', 4326), 1),
    (gen_random_uuid(), (select id from route where route_name = 'route4'), ST_GeomFromText('POINT(1 1)', 4326), 2),
    (gen_random_uuid(), (select id from route where route_name = 'route5'), ST_GeomFromText('POINT(10 20)', 4326), 1),
    (gen_random_uuid(), (select id from route where route_name = 'route5'), ST_GeomFromText('POINT(15 25)', 4326), 2),
    (gen_random_uuid(), (select id from route where route_name = 'route6'), ST_GeomFromText('POINT(5 10)', 4326), 1),
    (gen_random_uuid(), (select id from route where route_name = 'route6'), ST_GeomFromText('POINT(7 12)', 4326), 2),
    (gen_random_uuid(), (select id from route where route_name = 'route7'), ST_GeomFromText('POINT(1 2)', 4326), 1),
    (gen_random_uuid(), (select id from route where route_name = 'route7'), ST_GeomFromText('POINT(3 4)', 4326), 2),
    (gen_random_uuid(), (select id from route where route_name = 'route8'), ST_GeomFromText('POINT(0 0)', 4326), 1),
    (gen_random_uuid(), (select id from route where route_name = 'route8'), ST_GeomFromText('POINT(1 1)', 4326), 2),
    (gen_random_uuid(), (select id from route where route_name = 'route9'), ST_GeomFromText('POINT(10 20)', 4326), 1),
    (gen_random_uuid(), (select id from route where route_name = 'route9'), ST_GeomFromText('POINT(15 25)', 4326), 2),
    (gen_random_uuid(), (select id from route where route_name = 'route10'), ST_GeomFromText('POINT(5 10)', 4326), 1),
    (gen_random_uuid(), (select id from route where route_name = 'route10'), ST_GeomFromText('POINT(7 12)', 4326), 2);

insert into route_session (id, user_id, route_id, is_finished, started_at, ended_at)
values
    (gen_random_uuid(), 'user1', (select id from route where route_name = 'route1'), true, now(), now()),
    (gen_random_uuid(), 'user2', (select id from route where route_name = 'route2'), false, now(), now()),
    (gen_random_uuid(), 'user3', (select id from route where route_name = 'route3') , true, now(), now()),
    (gen_random_uuid(), 'user4', (select id from route where route_name = 'route4'), false, now(), now()),
    (gen_random_uuid(), 'user5', (select id from route where route_name = 'route5'), true, now(), now()),
    (gen_random_uuid(), 'user6', (select id from route where route_name = 'route6'), false, now(), now()),
    (gen_random_uuid(), 'user7', (select id from route where route_name = 'route7'), true, now(), now()),
    (gen_random_uuid(), 'user8', (select id from route where route_name = 'route8'), false, now(), now()),
    (gen_random_uuid(), 'user9', (select id from route where route_name = 'route9'), true, now(), now()),
    (gen_random_uuid(), 'user10', (select id from route where route_name = 'route10'), false, now(), now());

insert into user_checkpoint (route_session_id, coordinate_id, created_at)
values
    ((select id from route_session where user_id = 'user1'), (select id from route_coordinate where route_id = (select id from route where user_id = 'user1') limit 1), now()),
    ((select id from route_session where user_id = 'user1'), (select id from route_coordinate where route_id = (select id from route where user_id = 'user1') limit 1 offset 1), now()),
    ((select id from route_session where user_id = 'user2'), (select id from route_coordinate where route_id = (select id from route where user_id = 'user2') limit 1), now()),
    ((select id from route_session where user_id = 'user2'), (select id from route_coordinate where route_id = (select id from route where user_id = 'user2') limit 1 offset 1), now()),
    ((select id from route_session where user_id = 'user3'), (select id from route_coordinate where route_id = (select id from route where user_id = 'user3') limit 1), now()),
    ((select id from route_session where user_id = 'user3'), (select id from route_coordinate where route_id = (select id from route where user_id = 'user3') limit 1 offset 1), now()),
    ((select id from route_session where user_id = 'user4'), (select id from route_coordinate where route_id = (select id from route where user_id = 'user4') limit 1), now()),
    ((select id from route_session where user_id = 'user4'), (select id from route_coordinate where route_id = (select id from route where user_id = 'user4') limit 1 offset 1), now()),
    ((select id from route_session where user_id = 'user5'), (select id from route_coordinate where route_id = (select id from route where user_id = 'user5') limit 1), now()),
    ((select id from route_session where user_id = 'user5'), (select id from route_coordinate where route_id = (select id from route where user_id = 'user5') limit 1 offset 1), now()),
    ((select id from route_session where user_id = 'user6'), (select id from route_coordinate where route_id = (select id from route where user_id = 'user6') limit 1), now()),
    ((select id from route_session where user_id = 'user6'), (select id from route_coordinate where route_id = (select id from route where user_id = 'user6') limit 1 offset 1), now()),
    ((select id from route_session where user_id = 'user7'), (select id from route_coordinate where route_id = (select id from route where user_id = 'user7') limit 1), now()),
    ((select id from route_session where user_id = 'user7'), (select id from route_coordinate where route_id = (select id from route where user_id = 'user7') limit 1 offset 1), now()),
    ((select id from route_session where user_id = 'user8'), (select id from route_coordinate where route_id = (select id from route where user_id = 'user8') limit 1), now()),
    ((select id from route_session where user_id = 'user8'), (select id from route_coordinate where route_id = (select id from route where user_id = 'user8') limit 1 offset 1), now()),
    ((select id from route_session where user_id = 'user9'), (select id from route_coordinate where route_id = (select id from route where user_id = 'user9') limit 1), now()),
    ((select id from route_session where user_id = 'user9'), (select id from route_coordinate where route_id = (select id from route where user_id = 'user9') limit 1 offset 1), now()),
    ((select id from route_session where user_id = 'user10'), (select id from route_coordinate where route_id = (select id from route where user_id = 'user10') limit 1), now()),
    ((select id from route_session where user_id = 'user10'), (select id from route_coordinate where route_id = (select id from route where user_id = 'user10') limit 1 offset 1), now());

insert into review (used_id, route_id, mark, review_text)
values
    ('user1', (select id from route where route_name = 'route1'), 5, 'Great route!'),
    ('user2', (select id from route where route_name = 'route2'), 4, 'Nice route!'),
    ('user3', (select id from route where route_name = 'route3'), 3, 'Good route!'),
    ('user4', (select id from route where route_name = 'route4'), 2, 'Okay route!'),
    ('user5', (select id from route where route_name = 'route5'), 1, 'Bad route!'),
    ('user6', (select id from route where route_name = 'route6'), 5, 'Great route!'),
    ('user7', (select id from route where route_name = 'route7'), 4, 'Nice route!'),
    ('user8', (select id from route where route_name = 'route8'), 3, 'Good route!'),
    ('user9', (select id from route where route_name = 'route9'), 2, 'Okay route!'),
    ('user10', (select id from route where route_name = 'route10'), 1, 'Bad route!');

insert into favorite (user_id, route_id)
values
    ('user1', (select id from route where route_name = 'route1')),
    ('user2', (select id from route where route_name = 'route2')),
    ('user3', (select id from route where route_name = 'route3')),
    ('user4', (select id from route where route_name = 'route4')),
    ('user5', (select id from route where route_name = 'route5')),
    ('user6', (select id from route where route_name = 'route6')),
    ('user7', (select id from route where route_name = 'route7')),
    ('user8', (select id from route where route_name = 'route8')),
    ('user9', (select id from route where route_name = 'route9')),
    ('user10', (select id from route where route_name = 'route10'));
