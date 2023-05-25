package com.example.ollethboardproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "user not found"),
    DUPLICATED_USERNAME(HttpStatus.CONFLICT, "username is duplicated"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "token is invalid"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "password is invalid"),
    POST_DOES_NOT_EXIST(HttpStatus.NOT_FOUND, "post does not exist"),
    COMMENT_DOES_NOT_EXIST(HttpStatus.NOT_FOUND, "comment does not exist"),
    HAS_NOT_PERMISSION_TO_ACCESS(HttpStatus.UNAUTHORIZED, "has not permission to access"),
    REPLY_DOES_NOT_EXIST(HttpStatus.NOT_FOUND, "reply does not exist"),
    REPLY_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "reply already exists"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "unauthorized"),
    COMMUNITY_DOES_NOT_EXIST(HttpStatus.NOT_FOUND, "community does not exist"),
    COMMUNITY_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "community already exists"),
    ALREADY_REGISTER(HttpStatus.CONFLICT, "has already registered"),
    PERMISSION_DENIED(HttpStatus.FORBIDDEN, "permission denied"),
    KEYWORD_DOES_NOT_EXIST(HttpStatus.NOT_FOUND,"keyword does not exist");

    private final HttpStatus httpStatus;
    private final String message;
}