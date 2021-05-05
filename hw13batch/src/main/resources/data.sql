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

insert into books (id, name, year, genre_id, author_id)
values (1, 'The Night in Lisbon', 1964, 6, 1),
       (2, 'The Black Obelisk', 1957, 6, 1),
       (3, 'The Old Man and the Sea', 1951, 6, 2),
       (4, 'The Sun Also Rises', 1927, 6, 2),
       (5, 'Animal Farm', 1945, 5, 3),
       (6, 'The Psychopathology of Everyday Life', 1904, 4, 4);