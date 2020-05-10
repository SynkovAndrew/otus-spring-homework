package com.otus.spring.hw08mongodb.configuration;

import com.otus.spring.hw08mongodb.domain.Author;
import com.otus.spring.hw08mongodb.domain.Book;
import com.otus.spring.hw08mongodb.domain.Comment;
import com.otus.spring.hw08mongodb.domain.Genre;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Set;

import static java.util.List.of;

public abstract class AbstractMongoDataLoader {
    private final MongoTemplate mongoTemplate;

    public AbstractMongoDataLoader(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    protected void cleanData() {
        mongoTemplate.remove(new Query(), Genre.class);
        mongoTemplate.remove(new Query(), Author.class);
        mongoTemplate.remove(new Query(), Book.class);
        mongoTemplate.remove(new Query(), Comment.class);
    }

    protected void loadData() {
        mongoTemplate.insertAll(of(
                Genre.builder().name("Comedy").id("1").build(),
                Genre.builder().name("History").id("2").build(),
                Genre.builder().name("Detective").id("3").build(),
                Genre.builder().name("Psychology").id("4").build(),
                Genre.builder().name("Science Fiction").id("5").build(),
                Genre.builder().name("Fiction").id("6").build()
        ));
        final var erich_maria_remarque = Author.builder().name("Erich Maria Remarque").id("1").build();
        final var ernest_hemingway = Author.builder().name("Ernest Hemingway").id("2").build();
        final var george_orwell = Author.builder().name("George Orwell").id("3").build();
        final var sigmund_freud = Author.builder().name("Sigmund Freud").id("4").build();

        final var the_night_in_lisbon = Book.builder()
                .id("1")
                .genre(Genre.builder()
                        .id("6")
                        .build())
                .name("The Night in Lisbon")
                .year(1964)
                .authors(Set.of(
                        Author.builder()
                                .id("1")
                                .build()
                ))
                .comments(Set.of(
                        Comment.builder()
                                .id("1")
                                .value("Interesting book. Hope everybody will enjoy it!")
                                .build(),
                        Comment.builder()
                                .id("2")
                                .value("The greatest I ever read!")
                                .build()
                ))
                .build();
        final var the_black_obelisk = Book.builder()
                .id("2")
                .genre(Genre.builder()
                        .id("6")
                        .build())
                .name("The Black Obelisk")
                .year(1957)
                .authors(Set.of(
                        Author.builder()
                                .id("1")
                                .build()
                ))
                .comments(Set.of(
                        Comment.builder()
                                .id("3")
                                .value("Good book!")
                                .build()
                ))
                .build();
        final var the_old_man_and_the_sea = Book.builder()
                .id("3")
                .genre(Genre.builder()
                        .id("6")
                        .build())
                .name("The Old Man and the Sea")
                .year(1951)
                .authors(Set.of(
                        Author.builder()
                                .id("2")
                                .build()
                ))
                .comments(Set.of(
                        Comment.builder()
                                .id("4")
                                .value("The good one. I have advised it to my father")
                                .build(),
                        Comment.builder()
                                .id("5")
                                .value("I dont really like it, coudnt even finish it...")
                                .build()
                ))
                .build();
        final var the_sun_also_rises = Book.builder()
                .id("4")
                .genre(Genre.builder()
                        .id("6")
                        .build())
                .name("The Sun Also Rises")
                .year(1927)
                .authors(Set.of(
                        Author.builder()
                                .id("2")
                                .build()
                ))
                .build();
        final var animal_farm = Book.builder()
                .id("5")
                .genre(Genre.builder()
                        .id("5")
                        .build())
                .name("Animal Farm")
                .year(1945)
                .authors(Set.of(
                        Author.builder()
                                .id("3")
                                .build()
                ))
                .comments(Set.of(
                        Comment.builder()
                                .id("6")
                                .value("I love this book. I have re-read it 3 times already!")
                                .build()
                ))
                .build();
        final var the_psychopathology_of_everyday_life = Book.builder()
                .id("6")
                .genre(Genre.builder()
                        .id("4")
                        .build())
                .name("The Psychopathology of Everyday Life")
                .year(1904)
                .authors(Set.of(
                        Author.builder()
                                .id("4")
                                .build()
                ))
                .comments(Set.of(
                        Comment.builder()
                                .id("7")
                                .value("It is difficult one but worth to read it!")
                                .build()
                ))
                .build();
        final var the_authorless_book = Book.builder()
                .id("7")
                .genre(Genre.builder()
                        .id("4")
                        .build())
                .name("The Authorless Book")
                .year(1974)
                .build();

        mongoTemplate.insertAll(of(erich_maria_remarque, ernest_hemingway, george_orwell, sigmund_freud));
        mongoTemplate.insertAll(of(
                Comment.builder()
                        .id("1")
                        .value("Interesting book. Hope everybody will enjoy it!")
                        .build(),
                Comment.builder()
                        .id("2")
                        .value("The greatest I ever read!")
                        .build(),
                Comment.builder()
                        .id("3")
                        .value("Good book!")
                        .build(),
                Comment.builder()
                        .id("4")
                        .value("The good one. I have advised it to my father")
                        .build(),
                Comment.builder()
                        .id("5")
                        .value("I dont really like it, coudnt even finish it...")
                        .build(),
                Comment.builder()
                        .id("6")
                        .value("I love this book. I have re-read it 3 times already!")
                        .build(),
                Comment.builder()
                        .id("7")
                        .value("It is difficult one but worth to read it!")
                        .build()
        ));
        mongoTemplate.insertAll(of(
                the_night_in_lisbon, the_black_obelisk, the_old_man_and_the_sea, the_sun_also_rises,
                animal_farm, the_authorless_book, the_psychopathology_of_everyday_life
        ));
    }
}
