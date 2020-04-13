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

insert into books (genre_id, id, name, year)
values (6, 1, 'The Night in Lisbon', 1964),
       (6, 2, 'The Black Obelisk', 1957),
       (6, 3, 'The Old Man and the Sea', 1951),
       (6, 4, 'The Sun Also Rises', 1927),
       (5, 5, 'Animal Farm', 1945),
       (4, 6, 'The Psychopathology of Everyday Life', 1904);

insert into books_authors (author_id, book_id)
values (1, 1),
       (1, 2),
       (2, 3),
       (2, 4),
       (3, 5),
       (4, 6);

insert into comments (id, book_id, value)
values (1, 1, 'Interesting book. Hope everybody will enjoy it!'),
       (2, 1, 'The greatest I ever read!'),
       (3, 3, 'The good one. I have advised it to my father'),
       (4, 3, 'I dont really like it, coudnt even finish it...'),
       (5, 5, 'I love this book. I have re-read it 3 times already!'),
       (6, 6, 'It is difficult one but worth to read it!');