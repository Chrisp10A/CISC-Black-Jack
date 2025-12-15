package com.example.javafxblackjack;
import java.io.*;

public class ChipsManager {

    public int load(BlackJack blkj) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/com/example/javafxblackjack/player_chips.txt"))) {
            // Read the file
            return Integer.parseInt((reader.readLine()));
        } catch (IOException e) {
            // Handle exceptions
            blkj.println("ERROR: FAILED TO LOAD FILE");
            return 100;
        }
    }

    public void save(int chips, BlackJack blkj) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/com/example/javafxblackjack/player_chips.txt"))) {
            writer.write((chips + ""));
        } catch (IOException e) {
            blkj.println("ERROR: FAILED TO SAVE FILE");
        }
    }
}