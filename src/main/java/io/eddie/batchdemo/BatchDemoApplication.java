package io.eddie.batchdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.REGEX,
                        pattern = "io\\.eddie\\.batchdemo\\.C01\\..*"
                )
                ,
                @ComponentScan.Filter(
                        type = FilterType.REGEX,
                        pattern = "io\\.eddie\\.batchdemo\\.C02\\..*"
                )
                ,
                @ComponentScan.Filter(
                        type = FilterType.REGEX,
                        pattern = "io\\.eddie\\.batchdemo\\.C03\\..*"
                )
        }
)
public class BatchDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchDemoApplication.class, args);
    }

}
