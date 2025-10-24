package io.eddie.batchdemo.C03.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CmdJobConfigurationWithChunk implements CommandLineRunner {

    private final Job orderDataConvertJob;

    private final JobLauncher jobLauncher;

    @Override
    public void run(String... args) throws Exception {
        JobParametersBuilder builder = new JobParametersBuilder();
        builder.addLong("run.id", System.currentTimeMillis());

        JobParameters jobParameters = builder.toJobParameters();

        jobLauncher.run(orderDataConvertJob, jobParameters);
    }


}
