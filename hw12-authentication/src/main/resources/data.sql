insert into genres (id, name)
values (1, 'Comedy'),
       (2, 'History'),
       (3, 'Detective'),
       (4, 'Psychology'),
       (5, 'Science Fiction'),
       (6, 'Fiction');

insert into authors (id, name)
values (1, 'Erich Maria Remarque'),
       (2, 'Ernest Hemingway'),
       (3, 'George Orwell'),
       (4, 'Sigmund Freud');

insert into books (id, genre_id, name, year)
values (1, 6,'The Night in Lisbon', 1964),
       (2, 6,'The Black Obelisk', 1957),
       (3, 6,'The Old Man and the Sea', 1951),
       (4, 6,'The Sun Also Rises', 1927),
       (5, 5,'Animal Farm', 1945),
       (6, 4,'The Psychopathology of Everyday Life', 1904),
       (7, 4,'The Authorless Book', 1974);

insert into books_authors (author_id, book_id)
values (1, 1),
       (1, 2),
       (2, 3),
       (2, 4),
       (3, 5),
       (4, 6);

insert into comments (book_id, value)
values (1, 'Interesting book. Hope everybody will enjoy it!'),
       (1, 'The greatest I ever read!'),
       (2, 'Good book!'),
       (3, 'The good one. I have advised it to my father'),
       (3, 'I dont really like it, coudnt even finish it...'),
       (5, 'I love this book. I have re-read it 3 times already!'),
       (6, 'It is difficult one but worth to read it!');

insert into users (username, password, enabled)
values ('admin', '$2y$12$d.2K6Pjr6NYE0bJxa3clFeAQDtK3L9JDzGwIdTqYnDsDfyxY42MTu', true),
        ('guest', '$2y$12$X3p1fWZ/MCSfnFKBJxj.aeSbgJuZBWoepEMzBiwCUI7jl5rK6SIjK', true);

insert into roles (username, role)
values ('admin', 'ADMIN_ROLE'),
        ('guest', 'GUEST_ROLE');