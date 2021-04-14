package ru.svetlov.reflection;

import ru.svetlov.reflection.annotations.AfterSuite;
import ru.svetlov.reflection.annotations.Test;

public class DemoClass2 {
    @Test(priority = 9)
    public void voidTest() {
        System.out.println("this is void test of priority 9");
    }

    @Test
    public void genericTest() {
        System.out.println(this.getClass().getName() + " genericTest of priority 10");
    }

    @Test(priority = 1)
    public int priorityTest() {
        System.out.println("This is a priority 1 test");
        return 1;
    }
    @Test
    public void throwTest(){
        throw new IllegalArgumentException();
    }
    @AfterSuite
    public String afterTest(){
        return "afterTest cleanup ok.";
    }
}
