package com.example.securitypractice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    USERNAME_DUPLICATED(HttpStatus.CONFLICT, "username is duplicated"),
    USERNAME_NOT_FOUND(HttpStatus.NOT_FOUND, "username not found"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "password is invalid"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "token is invalid"),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "post not founded"),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "permission is invalid"),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "comment not founded");

    private HttpStatus httpStatus;
    private String message;


}
