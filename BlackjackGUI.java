import javax.swing.*;
import java.awt.*;

public class BlackjackGUI {

    private JFrame frame;

    public BlackjackGUI() { //gui interface 
        frame = new JFrame("Blackjack Game");
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //  CLOCK 
        GameClock clock = new GameClock();
        frame.add(clock.getTimeLabel(), BorderLayout.NORTH);

        //  GAME AREA 
        JPanel gamePanel = new JPanel();
        gamePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        gamePanel.add(new JLabel("Game goes here..."));
        frame.add(gamePanel, BorderLayout.CENTER);

        //  BUTTONS 
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(new JButton("Hit"));
        buttonPanel.add(new JButton("Stand"));
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new BlackjackGUI();
    }
}
{int playerChips = ChipManager.loadChips();
System.out.println("Loaded chips: " + playerChips);}
{ChipManager.saveChips(playerChips);
System.out.println("Chips saved: " + playerChips);}