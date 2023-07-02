package com.example.ollethboardproject.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Response<T> {
    private T response;
    private String message;


    public static <T> Response<T> success(T response) {
        return new Response<>(response, "success");
    }

    public static <T> Response<Void> success() {
        return new Response<>(null, "success");
    }

    public static Response<Void> error(String message) {
        return new Response<>(null, message);
    }

    public String toStream() {
        if (response == null) {
            return "{" +
                    "\"response\":" + "\"" + null + "\"" + ","
                    + "\"message\"" + ":" + message + "}";
        }
        return "{" +
                "\"response\":" + "\"" + response + "\"" + ","
                + "\"message\"" + ":" + "\"" + message + "\"" + "}";
    }
}
