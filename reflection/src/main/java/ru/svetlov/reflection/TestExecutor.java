package ru.svetlov.reflection;

import ru.svetlov.reflection.annotations.AfterSuite;
import ru.svetlov.reflection.annotations.BeforeSuite;
import ru.svetlov.reflection.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TestExecutor {
    protected Class<?> testClass;
    protected SortedMap<Integer, Set<Method>> methods;
    protected Method beforeSuite;
    protected Method afterSuite;

    public static TestExecutor start(Class<?> testClass) throws IllegalArgumentException {
        SortedMap<Integer, Set<Method>> priorityMethods = new TreeMap<>();
        Method[] declaredMethods = testClass.getDeclaredMethods();
        Method beforeSuite = null;
        Method afterSuite = null;
        for (Method m : declaredMethods) {
            if (m.isAnnotationPresent(BeforeSuite.class)) {
                if (beforeSuite != null)
                    throw new IllegalArgumentException("More than one @BeforeSuite annotation" + testClass.getName());
                beforeSuite = m;
                continue;
            }
            if (m.isAnnotationPresent(AfterSuite.class)) {
                if (afterSuite != null)
                    throw new IllegalArgumentException("More than one @AfterSuite annotation in " + testClass.getName());
                afterSuite = m;
                continue;
            }
            if (m.isAnnotationPresent(Test.class)) {
                int key = m.getAnnotation(Test.class).priority();
                if (priorityMethods.containsKey(key))
                    priorityMethods.get(key).add(m);
                else {
                    Set<Method> set = new HashSet<>();
                    set.add(m);
                    priorityMethods.put(key, set);
                }
            }
        }
        if (priorityMethods.size() == 0) throw new IllegalArgumentException("No @Test annotations discovered");
        return new TestExecutor(testClass, priorityMethods, beforeSuite, afterSuite);
    }

    protected TestExecutor(Class<?> testClass,
                           SortedMap<Integer, Set<Method>> methods,
                           Method beforeSuite,
                           Method afterSuite) {
        this.testClass = testClass;
        this.methods = methods;
        this.beforeSuite = beforeSuite;
        this.afterSuite = afterSuite;
    }

    public Map<String, Object> execute() {
        Map<String, Object> result = new HashMap<>(); // возвращаем мапу [имя метода]:[результат вызова]
        try {
            Object obj = testClass.newInstance();
            if (beforeSuite != null)
                result.put(beforeSuite.getName(), runMethod(beforeSuite, obj));
            for (int key : methods.keySet())
                for (Method m : methods.get(key))
                    result.put(m.getName(), runMethod(m, obj));
            if (afterSuite != null)
                result.put(afterSuite.getName(), runMethod(afterSuite, obj));
        } catch (IllegalAccessException | InstantiationException e) {
            result.put(e.getStackTrace()[0].getMethodName(), e); // если вылетело исключение, вернем его
        }
        return result;
    }

    protected Object runMethod(Method method, Object obj) {
        try {
            return method.invoke(obj); // вернем результат вызова
        } catch (InvocationTargetException e) {
            return e.getCause(); // если метод выдал исключение
        } catch (IllegalAccessException e) {
            return e; // если ошиблись с модификатором доступа
        }
    }

    public static void main(String[] args) {
        TestExecutor executor1 = TestExecutor.start(DemoClass1.class);
        TestExecutor executor2 = TestExecutor.start(DemoClass2.class);
        printMap(executor1.execute());
        printMap(executor2.execute());

    }

    private static void printMap(Map<String, Object> results) {
        System.out.println("-->\nExecution results:");
        results.forEach((key, value) -> System.out.printf("[%s] : [%s]%n", key, value));
        System.out.println("-->");

    }
}
