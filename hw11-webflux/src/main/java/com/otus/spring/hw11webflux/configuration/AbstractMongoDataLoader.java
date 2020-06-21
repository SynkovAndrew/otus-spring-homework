package com.otus.spring.hw11webflux.configuration;

import com.otus.spring.hw11webflux.domain.Author;
import com.otus.spring.hw11webflux.domain.Book;
import com.otus.spring.hw11webflux.domain.Comment;
import com.otus.spring.hw11webflux.domain.Genre;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Set;

import static java.util.List.of;

public abstract class AbstractMongoDataLoader {
    private final ReactiveMongoTemplate mongoTemplate;

    public AbstractMongoDataLoader(ReactiveMongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    protected void cleanData() {
        mongoTemplate.remove(new Query(), Genre.class).subscribe(result -> {});
        mongoTemplate.remove(new Query(), Author.class).subscribe(result -> {});
        mongoTemplate.remove(new Query(), Book.class).subscribe(result -> {});
        mongoTemplate.remove(new Query(), Comment.class).subscribe(result -> {});
    }

    protected void loadData() {
        mongoTemplate.insertAll(of(
                Genre.builder().name("Comedy").id("1").build(),
                Genre.builder().name("History").id("2").build(),
                Genre.builder().name("Detective").id("3").build(),
                Genre.builder().name("Psychology").id("4").build(),
                Genre.builder().name("Science Fiction").id("5").build(),
                Genre.builder().name("Fiction").id("6").build()
        )).subscribe(result -> {});
        final var erich_maria_remarque = Author.builder().name("Erich Maria Remarque").id("1").build();
        final var ernest_hemingway = Author.builder().name("Ernest Hemingway").id("2").build();
        final var george_orwell = Author.builder().name("George Orwell").id("3").build();
        final var sigmund_freud = Author.builder().name("Sigmund Freud").id("4").build();

        final var the_night_in_lisbon = Book.builder()
                .id("1")
                .genre("6")
                .name("The Night in Lisbon")
                .year(1964)
                .authors(Set.of("1"))
                .comments(Set.of("1", "2"))
                .build();
        final var the_black_obelisk = Book.builder()
                .id("2")
                .genre("6")
                .name("The Black Obelisk")
                .year(1957)
                .authors(Set.of("1"))
                .comments(Set.of("3"))
                .build();
        final var the_old_man_and_the_sea = Book.builder()
                .id("3")
                .genre("6")
                .name("The Old Man and the Sea")
                .year(1951)
                .authors(Set.of("2"))
                .comments(Set.of("4", "5"))
                .build();
        final var the_sun_also_rises = Book.builder()
                .id("4")
                .genre("6")
                .name("The Sun Also Rises")
                .year(1927)
                .authors(Set.of("2"))
                .build();
        final var animal_farm = Book.builder()
                .id("5")
                .genre("5")
                .name("Animal Farm")
                .year(1945)
                .authors(Set.of("3"))
                .comments(Set.of("6"))
                .build();
        final var the_psychopathology_of_everyday_life = Book.builder()
                .id("6")
                .genre("4")
                .name("The Psychopathology of Everyday Life")
                .year(1904)
                .authors(Set.of("4"))
                .comments(Set.of("7"))
                .build();
        final var the_authorless_book = Book.builder()
                .id("7")
                .genre("4")
                .name("The Authorless Book")
                .year(1974)
                .build();

        mongoTemplate.insertAll(of(erich_maria_remarque, ernest_hemingway, george_orwell, sigmund_freud))
                .subscribe(result -> {});
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
        )).subscribe(result -> {});
        mongoTemplate.insertAll(of(
                the_night_in_lisbon, the_black_obelisk, the_old_man_and_the_sea, the_sun_also_rises,
                animal_farm, the_authorless_book, the_psychopathology_of_everyday_life
        )).subscribe(result -> {});
    }
}
