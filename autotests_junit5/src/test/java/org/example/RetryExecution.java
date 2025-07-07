package org.example;

import org.junit.jupiter.api.extension.*;
import org.opentest4j.TestAbortedException;

import java.util.Optional;

public class RetryExecution implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private final int attempt;
    private final int maxAttempts;
    private boolean success = false;

    public RetryExecution(int attempt, int maxAttempts) {
        this.attempt = attempt;
        this.maxAttempts = maxAttempts;
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        System.out.println("🔁 Запуск попытки #" + attempt);
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        Optional<Throwable> failure = context.getExecutionException();
        if (!failure.isPresent()) {
            success = true;
        }

        if (failure.isPresent() && attempt < maxAttempts) {
            System.out.println("❌ Попытка #" + attempt + " неудачна: " + failure.get().getMessage());
            throw new TestAbortedException("Тест будет повторён...");
        }

        if (failure.isPresent() && attempt == maxAttempts) {
            System.out.println("⛔ Последняя попытка провалена.");
        }
    }
}