package org.example;

import org.junit.jupiter.api.Assertions;

public class JustTest {

    static int count = 0;

    @RetryTest(3)
    void flakyTest() {
        count++;
        System.out.println("Запуск теста: попытка #" + count);
        Assertions.assertTrue(count >= 2, "Фейлим, пока count < 2");
    }
}
