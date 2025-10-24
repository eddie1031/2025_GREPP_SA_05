package io.eddie.batchdemo.C01.config;

import org.springframework.boot.autoconfigure.batch.BatchDataSourceScriptDatabaseInitializer;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.sql.init.DatabaseInitializationMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class SpringBatchEnvironmentConfiguration {

    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3307/batch1");
        dataSource.setUsername("batch1");
        dataSource.setPassword("batch1!");

        return dataSource;

    }

    @Bean
    public PlatformTransactionManager transactionManager(
            DataSource dataSource
    ) {
        JdbcTransactionManager jdbcTransactionManager = new JdbcTransactionManager(dataSource);
        jdbcTransactionManager.setDataSource(dataSource);
        return jdbcTransactionManager;
    }

    @Bean
    public BatchDataSourceScriptDatabaseInitializer batchDataSourceScriptDatabaseInitializer(
            DataSource dataSource,
            BatchProperties batchProperties
    ) {
        return new BatchDataSourceScriptDatabaseInitializer(dataSource, batchProperties.getJdbc());
    }

    @Bean
    public BatchProperties batchProperties() {
        BatchProperties batchProperties = new BatchProperties();

        batchProperties.getJdbc().setInitializeSchema(DatabaseInitializationMode.ALWAYS);
        return batchProperties;

    }

}
