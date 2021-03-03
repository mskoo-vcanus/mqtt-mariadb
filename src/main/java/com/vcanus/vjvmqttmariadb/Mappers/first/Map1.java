package com.vcanus.vjvmqttmariadb.Mappers.first;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Map1 {
    String value() default "";
}
