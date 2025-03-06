package com.lag.todoapp.todoapp.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

@TestConfiguration
@Testcontainers
public class TestContainersConfig {
    @Container
    private static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql")
            .withDatabaseName("db_test")
            .withUsername("root")
            .withPassword("root");

    static {
        mysql.start();
    }

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setJdbcUrl(mysql.getJdbcUrl());
        dataSource.setUsername(mysql.getUsername());
        dataSource.setPassword(mysql.getPassword());
        dataSource.setDriverClassName(mysql.getDriverClassName());

        return dataSource;
    }
}
