package main.java.com.example.demoshop.java.com.example.demoshop.application.user;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.user.User;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.user.UserRegistrationDto;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(@Qualifier("postgresUserRepository") UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User register(UserRegistrationDto dto) {
        if (dto.getEmail() == null || dto.getPassword() == null || dto.getUsername() == null) {
            throw new IllegalArgumentException("Missing registration data");
        }

        if (userRepository.existsByEmail(dto.getEmail())){
            throw new IllegalStateException("Email already registered");
        }

        User user = new User(dto.getUsername(), dto.getEmail(), passwordEncoder.encode(dto.getPassword()));

        return userRepository.save(user);
    }
}
