package ru.svetlov.multithreading;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
    private final Semaphore sema;

    public Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
        sema = new Semaphore(MainApp.CARS_COUNT / 2); // не более половины от всех участников
    }

    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                sema.acquire();
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep((long) length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
                sema.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
