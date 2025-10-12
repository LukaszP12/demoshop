package main.java.com.example.demoshop.java.com.example.demoshop.domain.model.user;

public class UserNotExistsException extends RuntimeException {

    public UserNotExistsException(String userId) {
        super("User with ID or email '" + userId + "' does not exist.");
    }
}
