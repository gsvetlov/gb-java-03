package ru.svetlov.multithreading;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class MainApp {
    public static final int CARS_COUNT = 4;
    private static final CyclicBarrier barrier = new CyclicBarrier(CARS_COUNT + 1);
    private static final CountDownLatch startSignal = new CountDownLatch(1);

    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), barrier, startSignal);
        }
        for (Car car : cars) new Thread(car).start();
        try {
            barrier.await(); // ждем всех участников
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
            startSignal.countDown(); // сигнал к началу гонки
            barrier.await(); // ждем всех на финише
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }


    }
}

