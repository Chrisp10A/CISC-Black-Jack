import javax.swing.*;
import java.time.LocalTime;

public class GameClock {

    private JLabel timeLabel;
    private Timer timer;

    public GameClock() {
        timeLabel = new JLabel("Time: --:--:--");

        // Update the time every second
        timer = new Timer(1000, e -> {
            LocalTime currentTime = LocalTime.now().withNano(0);
            timeLabel.setText("Time: " + currentTime.toString());
        });

        timer.start();
    }
}