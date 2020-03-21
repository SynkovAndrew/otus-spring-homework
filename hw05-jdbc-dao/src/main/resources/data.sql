insert into genre (id, name)
values (1, 'Comedy'),
       (2, 'History'),
       (3, 'Detective'),
       (4, 'Psychology'),
       (5, 'Science Fiction'),
       (6, 'Fiction');

insert into author (id, name)
values (1, 'Erich Maria Remarque'),
       (2, 'Ernest Hemingway'),
       (3, 'George Orwell'),
       (4, 'Sigmund Freud');

insert into book (author_id, genre_id, id, name, year)
values (1, 6, default, 'The Night in Lisbon', 1964),
       (1, 6, default, 'The Black Obelisk', 1957),
       (2, 6, default, 'The Old Man and the Sea', 1951),
       (2, 6, default, 'The Sun Also Rises', 1927),
       (3, 5, default, 'Animal Farm', 1945),
       (4, 4, default, 'The Psychopathology of Everyday Life', 1904);