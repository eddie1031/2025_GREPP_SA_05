package io.eddie.batchdemo.C01.job;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class LoggingTaskletJobConfiguration
        extends DefaultBatchConfiguration {

    @Bean
    public Job loggingJob(
            JobRepository jobRepository,
            @Qualifier("printHelloStep") Step printHelloStep,
            @Qualifier("printWorldStep") Step printWorldStep
    ) {
        return new JobBuilder("loggingJob", jobRepository)
                .start(printHelloStep)
                .next(printWorldStep)
                .build();
    }

    @Bean
    @Qualifier("printHelloStep")
    public Step printHelloStep(JobRepository jobRepository,
                               PlatformTransactionManager txManager) {
        return new StepBuilder("printHelloStep", jobRepository)
                .tasklet(printHelloTasklet(), txManager)
                .build();
    }

    @Bean
    @Qualifier("printHelloTasklet")
    public Tasklet printHelloTasklet() {
        return (contribution, chunkContext) -> {
            log.info("Hello, ");
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    @Qualifier("printWorldStep")
    public Step printWorldStep(JobRepository jobRepository,
                               PlatformTransactionManager txManager) {
        return new StepBuilder("printWorldStep", jobRepository)
                .tasklet(printWorldTasklet(), txManager)
                .build();
    }

    @Bean
    public Tasklet printWorldTasklet() {
        return (contribution, chunkContext) -> {
            log.info("World!");
            return RepeatStatus.FINISHED;
        };
    }

}
