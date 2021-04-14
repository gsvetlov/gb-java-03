package ru.svetlov.reflection;

import ru.svetlov.reflection.annotations.BeforeSuite;
import ru.svetlov.reflection.annotations.Test;

public class DemoClass1 {
    private String testField = "a secret field";

    @BeforeSuite
    public String beforeTest() {
        return testField;
    }

    @Test(priority = 2)
    public int test2() {
        return 2;
    }

    @Test(priority = 6)
    public double test6() {
        return 6.0d;
    }

    @Test(priority = 3)
    public String test3() {
        return "3";
    }
}
