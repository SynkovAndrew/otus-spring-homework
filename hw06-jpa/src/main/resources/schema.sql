drop table if exists genre cascade;
drop table if exists book cascade;
drop table if exists author cascade;

create table genre
(
    id   serial primary key,
    name varchar(255) not null
);

create table book
(
    genre_id  integer references genre (id),
    id        serial primary key,
    name      varchar(255) not null,
    year      integer
);

create table author
(
    id   serial primary key,
    name varchar(255) not null
);

create table book_author
(
    author_id integer not null references author (id),
    genre_id  integer not null references genre (id),
    primary key ("author_id", "genre_id")
);



