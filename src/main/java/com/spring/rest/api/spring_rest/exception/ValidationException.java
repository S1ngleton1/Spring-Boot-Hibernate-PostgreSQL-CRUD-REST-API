package com.spring.rest.api.spring_rest.exception;

import java.util.List;

public class ValidationException extends RuntimeException{
    List<String> messages;
    public ValidationException(List<String> messages) {
        super(messages.toString());
        this.messages = messages;
    }

    public List<String> getMessages() {
        return messages;
    }
}
