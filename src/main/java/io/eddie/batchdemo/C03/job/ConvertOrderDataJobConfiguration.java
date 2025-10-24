package io.eddie.batchdemo.C03.job;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.eddie.batchdemo.C03.mapper.TextOrderDataMapper;
import io.eddie.batchdemo.C03.model.OrderData;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class ConvertOrderDataJobConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager txManager;

    @Bean
    public Job orderDataConvertJob() {
        return new JobBuilder("orderDataConvertJob", jobRepository)
                .start(aggregateOrderDataStep(jobRepository, txManager))
                .build();
    }

    @StepScope
    public Step aggregateOrderDataStep(
            JobRepository jobRepository,
            PlatformTransactionManager txManager
    ) {
        return new StepBuilder("aggregateOrderDataStep", jobRepository)

                .<OrderData, OrderData>chunk(1, txManager)

                // reader
                .reader(
                        new FlatFileItemReaderBuilder<OrderData>()
                                .name("textOrderDataReader")
                                .resource(new ClassPathResource("/in/order_data.txt"))
                                .lineMapper(new TextOrderDataMapper())
                                .build()
                )

                .writer(
                        new JsonFileItemWriterBuilder<OrderData>()
                                .name("orderJsonDataWriter")
                                .resource(new FileSystemResource("src/main/resources/out/order_data.json"))
                                .jsonObjectMarshaller(
                                        new JacksonJsonObjectMarshaller<>() {
                                            {
                                                setObjectMapper(
                                                        new ObjectMapper()
                                                                .registerModule(new JavaTimeModule())
                                                );
                                            }
                                        }
                                )
                                .build()
                )

                .build();
    }


}
