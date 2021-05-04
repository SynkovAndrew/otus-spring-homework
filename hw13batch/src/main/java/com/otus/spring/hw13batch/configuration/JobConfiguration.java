package com.otus.spring.hw13batch.configuration;

import com.otus.spring.hw13batch.entity.mongo.AuthorMongoEntity;
import com.otus.spring.hw13batch.entity.mongo.BookMongoEntity;
import com.otus.spring.hw13batch.entity.mongo.GenreMongoEntity;
import com.otus.spring.hw13batch.entity.sql.AuthorSqlEntity;
import com.otus.spring.hw13batch.entity.sql.BookSqlView;
import com.otus.spring.hw13batch.entity.sql.GenreSqlEntity;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class JobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;
    private final MongoOperations mongoOperations;

    @Bean
    public Job migrateBooksJob(@Qualifier("bookStep") Step bookStep,
                               @Qualifier("authorStep") Step authorStep,
                               @Qualifier("genreStep") Step genreStep) {
        return jobBuilderFactory.get("migrateBooksJob")
                .flow(bookStep)
                .next(genreStep)
                .next(authorStep)
                .end()
                .build();
    }

    @Bean
    @Qualifier("bookStep")
    public Step bookStep(ItemReader<BookSqlView> bookReader,
                         ItemProcessor<BookSqlView, BookMongoEntity> bookProcessor,
                         ItemWriter<BookMongoEntity> bookWriter) {
        return stepBuilderFactory.get("step")
                .<BookSqlView, BookMongoEntity>chunk(10)
                .reader(bookReader)
                .processor(bookProcessor)
                .writer(bookWriter)
                .build();
    }

    @Bean
    @Qualifier("authorStep")
    public Step authorStep(ItemReader<AuthorSqlEntity> authorReader,
                           ItemProcessor<AuthorSqlEntity, AuthorMongoEntity> authorProcessor,
                           ItemWriter<AuthorMongoEntity> authorWriter) {
        return stepBuilderFactory.get("step")
                .<AuthorSqlEntity, AuthorMongoEntity>chunk(10)
                .reader(authorReader)
                .processor(authorProcessor)
                .writer(authorWriter)
                .build();
    }

    @Bean
    @Qualifier("genreStep")
    public Step genreStep(ItemReader<GenreSqlEntity> genreReader,
                          ItemProcessor<GenreSqlEntity, GenreMongoEntity> genreProcessor,
                          ItemWriter<GenreMongoEntity> genreWriter) {
        return stepBuilderFactory.get("step")
                .<GenreSqlEntity, GenreMongoEntity>chunk(10)
                .reader(genreReader)
                .processor(genreProcessor)
                .writer(genreWriter)
                .build();
    }

    @Bean
    public ItemProcessor<BookSqlView, BookMongoEntity> bookProcessor() {
        return item -> new BookMongoEntity(
                null,
                item.getId(),
                item.getName(),
                item.getYear()
        );
    }

    @Bean
    public ItemProcessor<AuthorSqlEntity, AuthorMongoEntity> authorProcessor() {
        return item -> new AuthorMongoEntity(
                null,
                item.getId(),
                item.getName()
        );
    }

    @Bean
    public ItemProcessor<GenreSqlEntity, GenreMongoEntity> genreProcessor() {
        return item -> new GenreMongoEntity(
                null,
                item.getId(),
                item.getName()
        );
    }

    @Bean
    public ItemWriter<BookMongoEntity> bookWriter() {
        return new MongoItemWriterBuilder<BookMongoEntity>()
                .collection("books")
                .template(mongoOperations)
                .build();
    }

    @Bean
    public ItemWriter<AuthorMongoEntity> authorWriter() {
        return new MongoItemWriterBuilder<AuthorMongoEntity>()
                .collection("authors")
                .template(mongoOperations)
                .build();
    }

    @Bean
    public ItemWriter<GenreMongoEntity> genreWriter() {
        return new MongoItemWriterBuilder<GenreMongoEntity>()
                .collection("genres")
                .template(mongoOperations)
                .build();
    }

    @Bean
    public ItemReader<BookSqlView> bookReader(BookRowMapper bookRowMapper) {
        return new JdbcCursorItemReaderBuilder<BookSqlView>()
                .name("bookReader")
                .dataSource(dataSource)
                .sql("SELECT id, genre_id, name, year FROM books")
                .rowMapper(bookRowMapper)
                .build();
    }

    @Bean
    public ItemReader<GenreSqlEntity> genreReader(GenreRowMapper genreRowMapper) {
        return new JdbcCursorItemReaderBuilder<GenreSqlEntity>()
                .name("genreReader")
                .dataSource(dataSource)
                .sql("SELECT id, name FROM genres")
                .rowMapper(genreRowMapper)
                .build();
    }

    @Bean
    public ItemReader<AuthorSqlEntity> authorReader(AuthorRowMapper authorRowMapper) {
        return new JdbcCursorItemReaderBuilder<AuthorSqlEntity>()
                .name("authorReader")
                .dataSource(dataSource)
                .sql("SELECT id, name FROM authors")
                .rowMapper(authorRowMapper)
                .build();
    }
}
