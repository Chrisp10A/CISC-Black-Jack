import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ChipManager {

    private static final String FILE_NAME = "player_chips.txt";

    // Load chip count if file exists, otherwise return a default value
    public static int loadChips() {
        try {
            File file = new File(FILE_NAME);

            if (!file.exists()) {
                return 100; // default starting chips
            }

            Scanner scanner = new Scanner(file);
            int chips = scanner.nextInt();
            scanner.close();
            return chips;

        } catch (Exception e) {
            System.out.println("Error loading chips: " + e.getMessage());
            return 100; // fallback
        }
    }

    // Save chip count to file
    public static void saveChips(int chips) {
        try {
            FileWriter writer = new FileWriter(FILE_NAME);
            writer.write(String.valueOf(chips));
            writer.close();

        } catch (IOException e) {
            System.out.println("Error saving chips: " + e.getMessage());
        }
    }
}