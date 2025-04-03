package com.lag.todoapp.todoapp.service.impl;

import com.lag.todoapp.todoapp.config.TestContainersConfig;
import com.lag.todoapp.todoapp.model.CustomUserDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Set;

@SpringBootTest
@ActiveProfiles(value = "test")
@ContextConfiguration(classes = TestContainersConfig.class)
class JwtServiceImplTest {
    private final CustomUserDetails userDetails = new CustomUserDetails(
            1L,
            "juandoee",
            "juandoee@gmail.com",
            "123456",
            true,
            true,
            true,
            true,
            Set.of(new SimpleGrantedAuthority("ADMIN"))
    );

    @InjectMocks
    private JwtServiceImpl jwtService;

    @Test
    @DisplayName("Must generate a token")
    void generateTokenTest() {
        String token = jwtService.generateToken(userDetails);

        Assertions.assertNotNull(token);
        Assertions.assertFalse(token.isEmpty());
    }

    @Test
    @DisplayName("Must extract the email from token")
    void extractEmailTest() {
        String token = jwtService.generateToken(userDetails);

        String email = jwtService.extractEmail(token);

        Assertions.assertNotNull(email);
        Assertions.assertEquals(userDetails.getUsername(), email);
    }

    @Test
    @DisplayName("Must token valid")
    void validateTokenSuccessTest() {
        String token = jwtService.generateToken(userDetails);

        boolean isValid = jwtService.validateToken(token, userDetails);

        Assertions.assertTrue(isValid);
    }

    @Test
    @DisplayName("Must token not test")
    void validateTokenFailedTest() {
        CustomUserDetails userDetails2 = new CustomUserDetails(
                2L,
                "juanjose",
                "juanjose@gmail.com",
                "",
                true,
                true,
                true,
                true,
                Set.of()
        );
        String token = jwtService.generateToken(userDetails2);

        boolean isValid = jwtService.validateToken(token, userDetails);

        Assertions.assertFalse(isValid);
    }
}