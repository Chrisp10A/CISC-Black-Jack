package com.example.javafxblackjack;
import javafx.scene.control.*;

public class Clock2 extends Thread {

    public BlackJack blackJack;
    public Clock2(BlackJack blackJack)
    {
        this.blackJack = blackJack;
    }

    public static String time() {
        return (java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("hh:mm:ss a")));
    }

    @Override
    public void run() {
        while (true) {
            printtime();
            try {
                Thread.sleep(1000); // 1 second
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public void printtime() {
        String time = (java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("hh:mm:ss a")));
        blackJack.tick(time);
    }
}
