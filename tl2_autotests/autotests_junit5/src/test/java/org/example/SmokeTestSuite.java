package org.example;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite // Помечаем как сьют
@SelectClasses({
        LoginTest.class,
        SearchTest.class,
        CreateAutosearchTest.class,
        TagsTest.class,
        ViewedTest.class
})
public class SmokeTestSuite {
}
