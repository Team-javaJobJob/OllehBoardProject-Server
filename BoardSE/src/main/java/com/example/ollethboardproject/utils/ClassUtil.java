package com.example.ollethboardproject.utils;

import java.util.Optional;

public class ClassUtil {
    public static <T> Optional<T> castingInstance(Object o, Class<T> clazz) {
        return clazz != null && clazz.isInstance(o) ? Optional.of(clazz.cast(o)) : Optional.empty();
    }
}
