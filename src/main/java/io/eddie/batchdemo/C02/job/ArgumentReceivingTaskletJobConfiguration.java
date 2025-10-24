package io.eddie.batchdemo.C02.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class ArgumentReceivingTaskletJobConfiguration {

    private final Tasklet sayYourNameTasklet;

    private final PlatformTransactionManager txManager;
    private final JobRepository jobRepository;

    @Bean
    public Job argReceivingJob() {
        return new JobBuilder("argReceivingJob", jobRepository)
                .start(sayYourNameStep())
                .build();
    }

    @Bean
    public Step sayYourNameStep() {
        return new StepBuilder("sayYourNameStep", jobRepository)
                .tasklet(sayYourNameTasklet, txManager)
                .build();
    }


}
