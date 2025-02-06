package com.outercode.ApiMongoDB.services.exceptions;

public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException(String message) {super(message);}
}
