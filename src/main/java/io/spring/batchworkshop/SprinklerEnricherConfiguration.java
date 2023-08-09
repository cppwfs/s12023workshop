package io.spring.batchworkshop;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@EnableTask
@Configuration
public class SprinklerEnricherConfiguration {

    @Bean
    public Job sprinklerHistoryJob(JobRepository jobRepository, Step step1) {
        return new JobBuilder("sprinklerHistory", jobRepository)
                .start(step1)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step sprinklerHistoryStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
            ItemReader<SprinklerData> reader, ItemProcessor<SprinklerData, SprinklerData> itemProcessor,
            ItemWriter<SprinklerData> writer) {
        return new StepBuilder("sprinklerHistory", jobRepository).<SprinklerData, SprinklerData>chunk(5, transactionManager).
                reader(reader).
                processor(itemProcessor).
                writer(writer).
                build();
    }

    @Bean
    public JdbcCursorItemReader<SprinklerData> sprinklerDataTableReader(DataSource dataSource) {
        String sql = "select * from sprinkler_history";
        return new JdbcCursorItemReaderBuilder<SprinklerData>()
                .name("SprinklerDataTableReader")
                .dataSource(dataSource)
                .sql(sql)
                .rowMapper(new DataClassRowMapper<>(SprinklerData.class))
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<SprinklerData> billingDataTableWriter(DataSource dataSource) {
        String sql = "insert into sprinkler_event (sprinklerId, reason, startTime) values (:sprinklerId, :reason, :startTime)";
        return new JdbcBatchItemWriterBuilder<SprinklerData>()
                .dataSource(dataSource)
                .sql(sql)
                .beanMapped()
                .build();
    }

    @Bean
    public ItemProcessor<SprinklerData, SprinklerData> itemProcessor() {
        return item -> {
            item.setSprinklerId("AXRVMW25");
            return item;
        };
    }
    
    
    

}
