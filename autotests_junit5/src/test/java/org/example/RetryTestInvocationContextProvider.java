package org.example;
import org.junit.jupiter.api.extension.*;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.*;

public class RetryTestInvocationContextProvider implements TestTemplateInvocationContextProvider {

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        return context.getTestMethod().map(m -> m.isAnnotationPresent(RetryTest.class)).orElse(false);
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        Method testMethod = context.getRequiredTestMethod();
        RetryTest retry = testMethod.getAnnotation(RetryTest.class);
        int retries = retry.value();

        return IntStream.rangeClosed(1, retries).mapToObj(attempt -> new TestTemplateInvocationContext() {
            @Override
            public String getDisplayName(int invocationIndex) {
                return "Попытка #" + attempt;
            }

            @Override
            public List<Extension> getAdditionalExtensions() {
                return Arrays.asList(new RetryExecution(attempt, retries));
            }
        });
    }
}