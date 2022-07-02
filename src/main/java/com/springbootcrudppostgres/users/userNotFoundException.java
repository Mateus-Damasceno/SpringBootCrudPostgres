package com.springbootcrudppostgres.users;

public class userNotFoundException extends Throwable {
    public userNotFoundException(String message) {
        super(message);
    }
}
