package com.lag.todoapp.todoapp.service.impl;

import com.lag.todoapp.todoapp.client.QuoteClient;
import com.lag.todoapp.todoapp.client.dto.QuoteDto;
import com.lag.todoapp.todoapp.entity.RoleEntity;
import com.lag.todoapp.todoapp.entity.UserDetailEntity;
import com.lag.todoapp.todoapp.entity.UserEntity;
import com.lag.todoapp.todoapp.exception.NotFoundException;
import com.lag.todoapp.todoapp.model.request.LoginRequest;
import com.lag.todoapp.todoapp.model.request.RegisterRequest;
import com.lag.todoapp.todoapp.model.response.LoginDto;
import com.lag.todoapp.todoapp.repository.RoleRepository;
import com.lag.todoapp.todoapp.repository.UserDetailRepository;
import com.lag.todoapp.todoapp.repository.UserRepository;
import com.lag.todoapp.todoapp.service.AuthService;
import com.lag.todoapp.todoapp.service.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserDetailRepository userDetailRepository;

    private final QuoteClient quoteClient;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           UserDetailRepository userDetailRepository,
                           QuoteClient quoteClient) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userDetailRepository = userDetailRepository;
        this.quoteClient = quoteClient;
    }

    @Override
    public LoginDto login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        ));

        UserEntity user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Bad credentials"));

        UserDetails userDetails = buildUserDetails(user);
        String jwt = jwtService.generateToken(userDetails);

        QuoteDto quote = quoteClient.get("https://dummyjson.com/quotes/random", QuoteDto.class);



        return new LoginDto(jwt, quote);
    }

    @Transactional
    @Override
    public void register(RegisterRequest registerRequest) throws NotFoundException {
        UserEntity userToRegister = new UserEntity();

        userToRegister.setEmail(registerRequest.getEmail());
        userToRegister.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userToRegister.setNickname(registerRequest.getNickname());
        userToRegister.setRoles(getRoles(registerRequest.getRoles()));
        userToRegister.setCreatedAt(LocalDateTime.now());
        userToRegister.setAccountNoExpired(true);
        userToRegister.setCredentialNoExpired(true);
        userToRegister.setAccountNoLocked(true);
        userToRegister.setEnabled(true);

        UserEntity registeredUser = userRepository.save(userToRegister);

        UserDetailEntity detailsToRegister = new UserDetailEntity();
        detailsToRegister.setUser(registeredUser);
        detailsToRegister.setFirstName(registerRequest.getFirstName());
        detailsToRegister.setLastName(registerRequest.getLastName());
        detailsToRegister.setAge(registerRequest.getAge());
        detailsToRegister.setCreatedAt(LocalDateTime.now());
        userDetailRepository.save(detailsToRegister);
    }

    private Set<RoleEntity> getRoles(Set<Long> rolesId) throws NotFoundException {
        List<RoleEntity> roles = roleRepository.findAllById(rolesId);

        if (roles.size() < rolesId.size()) {
            throw new NotFoundException("Some role not found");
        }

        return new HashSet<>(roles);
    }

    private UserDetails buildUserDetails(UserEntity user) {
        List<SimpleGrantedAuthority> authorities = buildAuthorities(user.getRoles());

        return new User(
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                user.isAccountNoExpired(),
                user.isCredentialNoExpired(),
                user.isAccountNoLocked(),
                authorities
        );
    }

    private List<SimpleGrantedAuthority> buildAuthorities(Set<RoleEntity> roleEntities) {
        return roleEntities.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();
    }
}
