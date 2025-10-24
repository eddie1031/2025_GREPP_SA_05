package io.eddie.batchdemo.C01.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CmdJobConfiguration
        implements CommandLineRunner {

    @Qualifier("loggingJob")
    private final Job loggingJob;

    private final JobLauncher jobLauncher;

    @Override
    public void run(String... args) throws Exception {

        JobParametersBuilder builder = new JobParametersBuilder();
        builder.addLong("run.id", System.currentTimeMillis());

        JobParameters jobParameters = builder.toJobParameters();

        jobLauncher.run(loggingJob, jobParameters);

    }

}
