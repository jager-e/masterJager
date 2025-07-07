package org.example;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(RetryTestInvocationContextProvider.class)
@TestTemplate
public @interface RetryTest {
    int value() default 3;
}