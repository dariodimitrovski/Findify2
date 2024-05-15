create table users
(
    id           bigserial primary key,
    first_name   text not null,
    last_name    text not null,
    phone_number text not null,
    email        text not null,
    password     text not null,
    user_role    text not null,
    image           BYTEA
);

create table categories
(
    id   bigserial primary key,
    name text not null
);

create table locations
(
    id  bigserial primary key,
    lat double precision,
    lng double precision
);

create table posts
(
    id              bigserial primary key,
    title           text      not null,
    state           text,
    image           BYTEA,
    user_id         bigserial not null references users (id) on delete cascade,
    municipality_id bigserial not null references municipalities (id) on delete cascade,
    category_id     bigserial not null references categories (id) on delete cascade,
    description     text,
    location_id     bigserial references locations (id) on delete cascade,
    time            text      not null
);

create table comments
(
    id      bigserial primary key,
    comment text,
    post_id bigserial not null references posts (id) on delete cascade,
    user_id bigserial not null references users (id) on delete cascade
);
