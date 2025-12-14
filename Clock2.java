import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Clock2 {

    public static void printtime() {
        LocalTime currentTime = LocalTime.now();
        String pattern = "hh:mm:ss a";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        String formattedTime = currentTime.format(formatter);
        System.out.println("Current Time: " + formattedTime);
    }
}