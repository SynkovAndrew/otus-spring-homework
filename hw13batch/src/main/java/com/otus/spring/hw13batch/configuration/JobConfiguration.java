package com.otus.spring.hw13batch.configuration;

import com.otus.spring.hw13batch.entity.MongoDbBook;
import com.otus.spring.hw13batch.entity.SqlDbBook;
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
                .flow(step())
                .end()
                .build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("step")
                .<SqlDbBook, MongoDbBook>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public ItemReader<SqlDbBook> reader() {
        return new JdbcCursorItemReaderBuilder<SqlDbBook>()
                .name("bookReader")
                .dataSource(dataSource)
                .sql("SELECT id, genre_id, name, year FROM books")
                .rowMapper(new BookRowMapper())
                .build();
    }

    @Bean
    public ItemProcessor<SqlDbBook, MongoDbBook> processor() {
        return item -> new MongoDbBook(
                null,
                item.getId(),
                item.getName(),
                item.getYear()
        );
    }

    @Bean
    public ItemWriter<MongoDbBook> writer() {
        return new MongoItemWriterBuilder<MongoDbBook>()
                .collection("books")
                .template(mongoOperations)
                .build();
    }
}
