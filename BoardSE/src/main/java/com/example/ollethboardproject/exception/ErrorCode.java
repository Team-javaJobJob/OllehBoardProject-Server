package com.example.ollethboardproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"user not found"),
    DUPLICATED_USERNAME(HttpStatus.CONFLICT,"username is duplicated"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "token is invalid"),
    POST_DOES_NOT_EXIST(HttpStatus.NOT_FOUND,"post does not exist"),
    HAS_NOT_PERMISSION_TO_ACCESS(HttpStatus.UNAUTHORIZED,"has not permission to access");

    private final HttpStatus httpStatus;
    private final String message;

}
