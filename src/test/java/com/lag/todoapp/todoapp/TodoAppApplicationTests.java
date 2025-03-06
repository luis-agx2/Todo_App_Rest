package com.lag.todoapp.todoapp;

import com.lag.todoapp.todoapp.config.TestContainersConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ActiveProfiles(value = "test")
@ContextConfiguration(classes = TestContainersConfig.class)
class TodoAppApplicationTests {
    @Test
    void contextLoads() {
    }
}
