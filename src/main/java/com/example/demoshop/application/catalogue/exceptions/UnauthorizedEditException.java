package main.java.com.example.demoshop.java.com.example.demoshop.application.catalogue.exceptions;

public class UnauthorizedEditException extends RuntimeException{

    public UnauthorizedEditException(String message) {
        super(message);
    }
}
