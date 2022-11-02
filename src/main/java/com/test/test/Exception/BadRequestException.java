package com.test.test.Exception;

import lombok.Getter;

@Getter
public class BadRequestException extends Throwable {
    public BadRequestException(String message) {
        super(message);
    }
}
