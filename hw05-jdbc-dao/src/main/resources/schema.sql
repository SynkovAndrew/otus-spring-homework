drop table if exists genre cascade;
drop table if exists book cascade;
drop table if exists author cascade;

create table author
(
    id   serial primary key,
    name varchar(255) not null
);

create table genre
(
    id   serial primary key,
    name varchar(255) not null
);

create table book
(
    author_id integer references author (id),
    genre_id  integer references genre (id),
    id        serial primary key,
    name      varchar(255) not null,
    year      integer
);