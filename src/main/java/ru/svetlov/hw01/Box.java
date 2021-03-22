package ru.svetlov.hw01;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Box<T extends Fruit> implements Comparable<Box<? extends Fruit>> {
    private final List<T> contents = new ArrayList<>();

    public float getWeight(){
        return contents.size() > 0 ? contents.get(0).getUnitWeight() * contents.size() : 0f;
    }

    public boolean add(T item){
        return contents.add(item);
    }

    public boolean addAll(Collection<T> items){
        return contents.addAll(items);
    }

    public boolean isEmpty(){
        return contents.size() == 0;
    }

    public void empty(){
        contents.clear();
    }

    public boolean compare(Box<? extends Fruit> other){
        return compareTo(other) == 0;
    }

    @Override
    public int compareTo(Box<? extends Fruit> b) {
        float result  = getWeight() - b.getWeight();
        if (Math.abs(result) < 0.0001f) return 0;
        return result > 0 ? 1 : -1;
    }

    public void moveContents(Box<T> otherBox){
        otherBox.addAll(contents);
        contents.clear();
    }
}
