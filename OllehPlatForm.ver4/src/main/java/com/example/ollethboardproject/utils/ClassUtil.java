package com.example.ollethboardproject.utils;

import java.util.Optional;

public class ClassUtil {
    public static <T> Optional<T> castingInstance(Object o, Class<T> clazz) {
        return clazz != null && clazz.isInstance(o) ? Optional.of(clazz.cast(o)) : Optional.empty();
    }
}


/*

    clazz가 null이 아니고, clazz가 o의 인스턴스인 경우:
    clazz.cast(o)를 사용하여 o를 clazz로 캐스팅합니다.
    Optional.of(...)를 사용하여 캐스팅된 결과를 Optional 객체로 감싸서 반환합니다.
    그렇지 않은 경우:
    Optional.empty()를 반환합니다.
    이 메서드를 사용하면 객체를 안전하게 지정된 클래스로 캐스팅 할 수 있음. 반환된 'Optional' 객체를 사용하여
    캐스팅된 결과를 확인하고 사용할 수 있다
    'Optional'을 사용하는 이유는 캐스팅이 실패할 수도 있으니까 NULL 대신에 Optional.empty() 를 반환해서
    NPE(NullPointerException) 을 방지하기 위함이다

*/