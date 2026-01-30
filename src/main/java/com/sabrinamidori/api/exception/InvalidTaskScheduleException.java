package com.sabrinamidori.api.exception;

public class InvalidTaskScheduleException extends RuntimeException {
    public InvalidTaskScheduleException(String message) {
        super(message);
    }
}
