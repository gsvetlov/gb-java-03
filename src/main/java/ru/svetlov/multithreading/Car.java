package ru.svetlov.multithreading;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantLock;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private static final ReentrantLock winnersCup = new ReentrantLock();

    static {
        CARS_COUNT = 0;
    }

    private final Race race;
    private final int speed;
    private final String name;
    private final CyclicBarrier barrier;
    private final CountDownLatch signal;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed, CyclicBarrier barrier, CountDownLatch startSignal) {
        this.race = race;
        this.speed = speed;
        this.barrier = barrier;
        this.signal = startSignal;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            barrier.await(); // ждем когда все участники подготовятся
            signal.await(); // ожидаем сигнала на старт
            for (Stage stage : race.getStages())
                stage.go(this);
            if (winnersCup.tryLock()) // кто первый захватил lock, тот и выиграл
                System.out.println(name + " выиграл гонку!");
            barrier.await(); // ожидаем всех участников на финише
            if (winnersCup.isHeldByCurrentThread()) winnersCup.unlock(); // освободим lock, для порядка
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
