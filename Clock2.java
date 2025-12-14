import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Clock2 implements Runnable {

    @Override
    public void run() {
        while (true) {
            printtime();
            try {
                Thread.sleep(1000); // 1 second
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public static void printtime() {
        System.out.println(
            java.time.LocalTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("hh:mm:ss a"))
        );
    }

    public static void ticktock() {
        Thread clockThread = new Thread(new Clock2());
        clockThread.start();
    }
}
