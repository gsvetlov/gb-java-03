package ru.svetlov.hw01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        // #1
        System.out.println("#1");
        Integer[] arr = new Integer[]{10, 20, 30, 40};
        System.out.println(Arrays.toString(arr));
        swap(arr, 0, 1);
        System.out.println(Arrays.toString(arr));

        // #2
        System.out.println("#2");
        List<String> list = toArrayList(new String[]{"apple, lemon, melon, orange"});
        System.out.println(list.getClass().getName() + " " + list);

        // #3
        System.out.println("#3");
        Box<Orange> box1 = new Box<>();
        for (int i = 0; i < 10; i++)
            box1.add(new Orange());
        Box<Orange> orangeBox = new Box<>();
        box1.moveContents(orangeBox);
        System.out.printf("Box1 weights: %.2f \n", box1.getWeight());
        System.out.printf("Box of oranges weights: %.2f \n", orangeBox.getWeight());
        Box<Apple> appleBox = new Box<>();
        for (int i = 0; i < 15; i++)
            appleBox.add(new Apple());
        System.out.printf("Box of apples weights: %.2f\n", appleBox.getWeight());
        boolean weightsEqual = orangeBox.compare(appleBox);
        System.out.println("Are they equal? " + weightsEqual);
    }

    public static <T> void swap(T[] array, int indexA, int indexB) {
        T temp = array[indexA];
        array[indexA] = array[indexB];
        array[indexB] = temp;
    }

    public static <E> ArrayList<E> toArrayList(E[] array) {
        ArrayList<E> list = new ArrayList<>(array.length);
        Collections.addAll(list, array);
        return list;

    }
}

/*
 * 1. Написать метод, который меняет два элемента массива местами.(массив может быть любого ссылочного типа);
 * 2. Написать метод, который преобразует массив в ArrayList;
 * 3. Большая задача:
 * a. Есть классы Fruit -> Apple, Orange;(больше фруктов не надо)
 * b. Класс Box в который можно складывать фрукты, коробки условно сортируются по типу фрукта, поэтому в одну коробку нельзя сложить и яблоки, и апельсины;
 * c. Для хранения фруктов внутри коробки можете использовать ArrayList;
 * d. Сделать метод getWeight() который высчитывает вес коробки, зная количество фруктов и вес одного фрукта(вес яблока - 1.0f, апельсина - 1.5f, не важно в каких это единицах);
 * e. Внутри класса коробка сделать метод compare, который позволяет сравнить текущую коробку с той, которую подадут в compare в качестве параметра, true - если их веса равны, false в противном случае(коробки с яблоками мы можем сравнивать с коробками с апельсинами);
 * f. Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую коробку(помним про сортировку фруктов, нельзя яблоки высыпать в коробку с апельсинами), соответственно в текущей коробке фруктов не остается, а в другую перекидываются объекты, которые были в этой коробке;
 * g. Не забываем про метод добавления фрукта в коробку.
 */
