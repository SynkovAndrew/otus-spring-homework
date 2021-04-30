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

insert into books (id, genre_id, author_id, name, year)
values (1, 6, 1, 'The Night in Lisbon', 1964),
       (2, 6, 1, 'The Black Obelisk', 1957),
       (3, 6, 2, 'The Old Man and the Sea', 1951),
       (4, 6, 2, 'The Sun Also Rises', 1927),
       (5, 5, 3, 'Animal Farm', 1945),
       (6, 4, 4, 'The Psychopathology of Everyday Life', 1904);