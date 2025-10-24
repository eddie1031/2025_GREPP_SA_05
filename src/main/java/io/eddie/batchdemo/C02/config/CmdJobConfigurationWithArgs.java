package io.eddie.batchdemo.C02.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CmdJobConfigurationWithArgs
        implements CommandLineRunner {

    private final JobLauncher jobLauncher;

    private final Job argReceivingJob;

    @Override
    public void run(String... args) throws Exception {

        JobParametersBuilder builder = new JobParametersBuilder();

        for (String arg : args) {

            String[] parts = arg.split("=", 2);

            if ( parts.length == 2 ) {
                builder.addString(parts[0], parts[1]);
            }

        }

        builder.addLong("run.id", System.currentTimeMillis());
        JobParameters jobParameters = builder.toJobParameters();

        jobLauncher.run(argReceivingJob, jobParameters);

    }

}
