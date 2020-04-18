insert into genres (name)
values ('Comedy'),
       ('History'),
       ('Detective'),
       ('Psychology'),
       ('Science Fiction'),
       ('Fiction');

insert into authors (name)
values ('Erich Maria Remarque'),
       ('Ernest Hemingway'),
       ('George Orwell'),
       ('Sigmund Freud');

insert into books (genre_id, name, year)
values (6,'The Night in Lisbon', 1964),
       (6,'The Black Obelisk', 1957),
       (6,'The Old Man and the Sea', 1951),
       (6,'The Sun Also Rises', 1927),
       (5,'Animal Farm', 1945),
       (4,'The Psychopathology of Everyday Life', 1904);

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
       (3, 'The good one. I have advised it to my father'),
       (3, 'I dont really like it, coudnt even finish it...'),
       (5, 'I love this book. I have re-read it 3 times already!'),
       (6, 'It is difficult one but worth to read it!');