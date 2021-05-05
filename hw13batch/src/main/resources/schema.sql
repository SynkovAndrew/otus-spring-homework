drop table if exists genres cascade;
drop table if exists books cascade;
drop table if exists authors cascade;
drop table if exists comments cascade;
drop table if exists books_authors cascade;

create table genres
(
    id   serial primary key,
    name varchar(255) not null
);


create table authors
(
    id   serial primary key,
    name varchar(255) not null
);

create table books
(
    id        serial primary key,
    name      varchar(255) not null,
    year      integer,
    genre_id  integer references genres (id),
    author_id  integer references authors (id)
);

