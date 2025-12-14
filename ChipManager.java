import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ChipManager {

    private static final String FILE_NAME = "player_chips.txt";

    // Load chip count if file exists, otherwise return a default value
    public static int loadChips() {
        Scanner sc = null;
        try {
            sc = new Scanner(new File(FILE_NAME));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int chips = sc.nextInt();
        sc.close();
        return chips;
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