package com.lag.todoapp.todoapp.service.impl;

import com.lag.todoapp.todoapp.config.TestContainersConfig;
import com.lag.todoapp.todoapp.data.User;
import com.lag.todoapp.todoapp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles(value = "test")
@ContextConfiguration(classes = TestContainersConfig.class)
class CustomUserDetailsServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsServiceImpl customUserDetailsService;

    @Test
    @DisplayName("Must build user details successfully")
    void loadUserByUsernameTest() {
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(User.getUser()));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("email@test.com");

        Assertions.assertNotNull(userDetails);
        Assertions.assertNotNull(userDetails.getUsername());
    }

    @Test
    @DisplayName("Must not build user details due to error")
    void loadUserByUsernameErrorTest() {
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(UsernameNotFoundException.class, () -> {
           customUserDetailsService.loadUserByUsername("email@test.com");
        });

        Assertions.assertNotNull(exception);
        Assertions.assertEquals(UsernameNotFoundException.class, exception.getClass());

    }
}