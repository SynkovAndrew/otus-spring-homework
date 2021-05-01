package com.otus.spring.hw13batch.configuration;

import com.otus.spring.hw13batch.entity.*;
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
    public Job migrateBooksJob() {
        return jobBuilderFactory.get("migrateBooksJob")
                .flow(bookStep())
                .next(genreStep())
                .next(authorStep())
                .end()
                .build();
    }

    @Bean
    public Step bookStep() {
        return stepBuilderFactory.get("step")
                .<SqlDbBook, MongoDbBook>chunk(10)
                .reader(bookReader())
                .processor(bookProcessor())
                .writer(bookWriter())
                .build();
    }

    @Bean
    public Step authorStep() {
        return stepBuilderFactory.get("step")
                .<SqlDbAuthor, MongoDbAuthor>chunk(10)
                .reader(authorReader())
                .processor(authorProcessor())
                .writer(authorWriter())
                .build();
    }

    @Bean
    public Step genreStep() {
        return stepBuilderFactory.get("step")
                .<SqlDbGenre, MongoDbGenre>chunk(10)
                .reader(genreReader())
                .processor(genreProcessor())
                .writer(genreWriter())
                .build();
    }

    @Bean
    public ItemProcessor<SqlDbBook, MongoDbBook> bookProcessor() {
        return item -> new MongoDbBook(
                null,
                item.getId(),
                item.getName(),
                item.getYear()
        );
    }

    @Bean
    public ItemProcessor<SqlDbAuthor, MongoDbAuthor> authorProcessor() {
        return item -> new MongoDbAuthor(
                null,
                item.getId(),
                item.getName()
        );
    }

    @Bean
    public ItemProcessor<SqlDbGenre, MongoDbGenre> genreProcessor() {
        return item -> new MongoDbGenre(
                null,
                item.getId(),
                item.getName()
        );
    }

    @Bean
    public ItemWriter<MongoDbBook> bookWriter() {
        return new MongoItemWriterBuilder<MongoDbBook>()
                .collection("books")
                .template(mongoOperations)
                .build();
    }

    @Bean
    public ItemWriter<MongoDbAuthor> authorWriter() {
        return new MongoItemWriterBuilder<MongoDbAuthor>()
                .collection("authors")
                .template(mongoOperations)
                .build();
    }

    @Bean
    public ItemWriter<MongoDbGenre> genreWriter() {
        return new MongoItemWriterBuilder<MongoDbGenre>()
                .collection("genres")
                .template(mongoOperations)
                .build();
    }

    @Bean
    public ItemReader<SqlDbBook> bookReader() {
        return new JdbcCursorItemReaderBuilder<SqlDbBook>()
                .name("bookReader")
                .dataSource(dataSource)
                .sql("SELECT id, genre_id, name, year FROM books")
                .rowMapper(new BookRowMapper())
                .build();
    }

    @Bean
    public ItemReader<SqlDbGenre> genreReader() {
        return new JdbcCursorItemReaderBuilder<SqlDbGenre>()
                .name("genreReader")
                .dataSource(dataSource)
                .sql("SELECT id, name FROM genres")
                .rowMapper(new GenreRowMapper())
                .build();
    }

    @Bean
    public ItemReader<SqlDbAuthor> authorReader() {
        return new JdbcCursorItemReaderBuilder<SqlDbAuthor>()
                .name("authorReader")
                .dataSource(dataSource)
                .sql("SELECT id, name FROM authors")
                .rowMapper(new AuthorRowMapper())
                .build();
    }
}
