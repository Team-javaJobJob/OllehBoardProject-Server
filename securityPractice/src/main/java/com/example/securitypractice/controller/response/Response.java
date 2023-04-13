package com.example.securitypractice.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response<T> {
    private String message;
    private T result;


    public static Response<Void> error(String message) {
        return new Response<>(message, null);
    }

    public static  Response<Void> success() {
        return new Response<>("SUCCESS", null);
    }

    public static <T> Response<T> success(T result) {
        return new Response<>("SUCCESS", result);
    }

    public String toStream() {
        if (result == null) {
            return "{" +
                    "\"message\":" + "\"" + message + "\"" + ","
                    + "\"result\"" + ":" + null + "}";
        }
        return "{" +
                "\"message\":" + "\"" + message + "\"" + ","
                + "\"result\"" + ":" + "\"" + result + "\"" + "}";
    }
}
