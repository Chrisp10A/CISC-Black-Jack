// It's in the debug menu
import java.util.Scanner;
public class Main
{
	public static void main(String[] args) {
		System.out.println("Welcome! This is CISC-Black-Jack");
		Scanner input = new Scanner(System.in);

        // Ask for the user's name
        System.out.print("Enter your name: ");
        String name = input.nextLine();
        System.out.print("");
		System.out.print("Hello, " + name + " Are you up for a game of Blackjack?");
		String answer = input.nextLine();
		if (answer.equals("Yes")) {
			System.out.println("Yay!");
		}
		else {
			System.out.println("Aw man :(");
		}
		// stops
		input.close();
	}
}