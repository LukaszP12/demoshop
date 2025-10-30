package com.example.demoshop.domain.model.User;

import example.demoshop.application.user.UserService;
import example.demoshop.domain.model.user.Role;
import example.demoshop.domain.model.user.User;
import example.demoshop.domain.model.user.UserRegistrationDto;
import example.demoshop.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private final UserService userService = new UserService(userRepository, passwordEncoder);

    @Test
    void shouldCreateUserWhenRegistrationDataIsValid() {
        // given
        UserRegistrationDto dto = new UserRegistrationDto("john", "john@example.com", "password123");
        when(passwordEncoder.encode("password123")).thenReturn("encoded123");

        // when
        userService.register(dto);

        // then
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        User saved = captor.getValue();

        assertThat(saved.getUsername()).isEqualTo("john");
        assertThat(saved.email()).isEqualTo("john@example.com");
//        assertThat(saved).isEqualTo("encoded123");
    }

    @Test
    void shouldAssignDefaultRoleToNewUsers() {
        // given
        UserRegistrationDto dto = new UserRegistrationDto("sam", "sam@example.com", "pass");
        when(passwordEncoder.encode("pass")).thenReturn("encoded");

        // when
        userService.register(dto);

        // then
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        User saved = captor.getValue();

        assertThat(saved.role()).isEqualTo("ROLE_USER");
    }

    @Test
    void shouldDefaultToCustomerWhenInvalidRoleProvided() {
        // given
        User user = new User("john", "john@example.com");
        User user2 = new User("john", "john@example.com");
        User user3 = new User("john", "john@example.com");

        // when
        user.setRole("dummyRole");
        user2.setRole(null);
        user3.setRole("ADMIN");

        // then
        assertThat(user.role()).isEqualTo(Role.CUSTOMER);
        assertThat(user2.role()).isEqualTo(Role.CUSTOMER);
        assertThat(user3.role()).isEqualTo(Role.ADMIN);
    }
}
