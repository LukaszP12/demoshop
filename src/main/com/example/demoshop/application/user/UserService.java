package example.demoshop.application.user;

import example.demoshop.domain.model.user.User;
import example.demoshop.domain.model.user.UserRegistrationDto;
import example.demoshop.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(@Qualifier("postgresUserRepository") UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User register(UserRegistrationDto dto) {
        if (dto.getUsername() == null || dto.getEmail() == null || dto.getPassword() == null) {
            throw new IllegalArgumentException("Missing registration data");
        }

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalStateException("Email already registered");
        }

        User user = new User(dto.getUsername(), dto.getEmail(), passwordEncoder.encode(dto.getPassword()));
        user.setRole("Customer");

        return userRepository.save(user);
    }
}
