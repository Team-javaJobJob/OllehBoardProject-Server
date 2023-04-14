package com.example.ollethboardproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BoardException extends RuntimeException {
    private ErrorCode errorCode;
    private String message;


    public String getMessage() {
        if (message == null) {
            return this.message = errorCode.getMessage();
        }
        return this.message;
    }
}
