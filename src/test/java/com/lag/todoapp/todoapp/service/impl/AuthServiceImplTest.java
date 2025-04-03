package com.lag.todoapp.todoapp.service.impl;

import com.lag.todoapp.todoapp.client.QuoteClient;
import com.lag.todoapp.todoapp.client.dto.QuoteDto;
import com.lag.todoapp.todoapp.config.TestContainersConfig;
import com.lag.todoapp.todoapp.data.Auth;
import com.lag.todoapp.todoapp.data.User;
import com.lag.todoapp.todoapp.entity.RoleEntity;
import com.lag.todoapp.todoapp.entity.UserDetailEntity;
import com.lag.todoapp.todoapp.entity.UserEntity;
import com.lag.todoapp.todoapp.exception.NotFoundException;
import com.lag.todoapp.todoapp.model.CustomUserDetails;
import com.lag.todoapp.todoapp.model.request.LoginRequest;
import com.lag.todoapp.todoapp.model.request.RegisterRequest;
import com.lag.todoapp.todoapp.model.response.LoginDto;
import com.lag.todoapp.todoapp.repository.RoleRepository;
import com.lag.todoapp.todoapp.repository.UserDetailRepository;
import com.lag.todoapp.todoapp.repository.UserRepository;
import com.lag.todoapp.todoapp.service.JwtService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
@ActiveProfiles(value = "test")
@ContextConfiguration(classes = TestContainersConfig.class)
class AuthServiceImplTest {
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserDetailRepository userDetailRepository;

    @Mock
    private QuoteClient quoteClient;

    @InjectMocks
    private AuthServiceImpl authService;

    @Nested
    @DisplayName("Register tests")
    class RegisterTests {
        @Test
        @DisplayName("Must doing register successfully")
        void testRegisterSuccess() {
            RegisterRequest registerRequest = Auth.getRegisterRequest();
            registerRequest.setRoles(Set.of(1L));
            List<RoleEntity> roles = List.of(new RoleEntity(1L, "ROLE_1"));

            Mockito.when(roleRepository.findAllById(Mockito.anyCollection())).thenReturn(roles);

            authService.register(registerRequest);

            Mockito.verify(passwordEncoder, Mockito.atMostOnce()).encode(Mockito.anyString());

            Mockito.verify(userRepository, Mockito.atMostOnce()).save(Mockito.any(UserEntity.class));
            Mockito.verify(userDetailRepository, Mockito.atMostOnce()).save(Mockito.any(UserDetailEntity.class));
        }

        @Test
        @DisplayName("Must failed due to a not found role")
        void testRegisterFailed() {
            RegisterRequest registerRequest = Auth.getRegisterRequest();
            registerRequest.setRoles(Set.of(1L, 2L));
            List<RoleEntity> roles = List.of(new RoleEntity(1L, "ROLE_1"));
            Mockito.when(roleRepository.findAllById(Mockito.anyCollection())).thenReturn(roles);

            Exception exception = Assertions.assertThrows(NotFoundException.class, () -> {
               authService.register(registerRequest);
            });

            Assertions.assertEquals(exception.getClass(), NotFoundException.class);
        }
    }

    @Nested
    @DisplayName("Login tests")
    class loginTests {
        @Test
        @DisplayName("Must doing login successfully")
        void testLoginSuccess() {
            LoginRequest loginRequest = Auth.getLoginRequest();

            Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(User.getUser()));
            Mockito.when(quoteClient.get(Mockito.anyString(), Mockito.eq(QuoteDto.class))).thenReturn(new QuoteDto(1L, "Quote", "Author"));

            LoginDto response = authService.login(loginRequest);

            Mockito.verify(authenticationManager, Mockito.atMostOnce()).authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
            Mockito.verify(jwtService, Mockito.atMostOnce()).generateToken(Mockito.any(CustomUserDetails.class));
            Mockito.verify(quoteClient, Mockito.atMostOnce()).get(Mockito.anyString(), Mockito.eq(QuoteDto.class));

            Assertions.assertNotNull(response);
        }

        @Test
        @DisplayName("Must failed due to unexist email")
        void testLoginFailed() {
            Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

            Exception exception = Assertions.assertThrows(BadCredentialsException.class, () -> {
               authService.login(new LoginRequest());
            });

            Mockito.verify(authenticationManager, Mockito.atMostOnce()).authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
            Mockito.verify(jwtService, Mockito.never()).generateToken(Mockito.any(CustomUserDetails.class));
            Mockito.verify(quoteClient, Mockito.never()).get(Mockito.anyString(), Mockito.eq(QuoteDto.class));

            Assertions.assertEquals(exception.getClass(), BadCredentialsException.class);

        }
    }
}

