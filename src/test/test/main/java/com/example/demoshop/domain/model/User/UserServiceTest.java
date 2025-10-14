package main.java.com.example.demoshop.domain.model.User;

import main.java.com.example.demoshop.java.com.example.demoshop.application.user.UserService;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.user.User;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.user.UserRegistrationDto;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;

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
        assertThat(saved.getEmail()).isEqualTo("john@example.com");
        assertThat(saved.getPassword()).isEqualTo("encoded123");
    }

}
