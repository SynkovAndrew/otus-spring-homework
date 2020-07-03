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

create table books
(
    genre_id  integer references genres (id),
    id        serial primary key,
    name      varchar(255) not null,
    year      integer
);

create table authors
(
    id   serial primary key,
    name varchar(255) not null
);

create table books_authors
(
    author_id integer not null references authors (id),
    book_id  integer not null references books (id),
    primary key (author_id, book_id)
);

create table comments
(
    id   serial primary key,
    book_id  integer not null references books (id),
    value varchar(1000) not null
);

create table users
(
    username varchar_ignorecase(50) not null primary key,
    password varchar_ignorecase(500) not null,
    enabled boolean not null
);

create table roles
(
    username varchar_ignorecase(50) not null,
    authority varchar_ignorecase(50) not null,
    constraint fk_roles_users foreign key(username) references users(username)
);

create unique index ix_auth_username on roles (username,role);



