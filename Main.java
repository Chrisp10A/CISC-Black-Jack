// It's in the debug menu
import java.util.Scanner;
public class Main
{
	// Declare Variables
	// Final
	final String finalSuits = "♤♡♧♢";
	final String finalFaceCardNames = "JQKA";
	// Reset
	int totalAces;
	int totalPlayer;
	int totalDealer;
	int totalDealerAces;
	int totalDealerHasInsurance;
	int totalDealerCards;
	boolean hasInsurance;
	boolean DealerHasAce;
	String activeDealerCards;
	

	public static void main(String[] args) {
		int chips = 100;
		System.out.println("Welcome! This is CISC-Black-Jack");
		Scanner input = new Scanner(System.in);

        // Ask for the user's name
        System.out.print("Enter your name: ");
        String name = input.nextLine();
        System.out.print("");
		System.out.println("Hello, " + name + ". Are you up for a game of Blackjack?");
		System.out.print("Type 'Y' for yes and 'N' for No");
		String answer = input.nextLine();
		// Should probably use case stuff
		if (answer.equals("Y")) {
			System.out.println("Yay!");
		}
		else {
			System.out.println("Aw man :(");
		}
		// stops
		System.out.println("Starting Game...");
		// GAME LOOP
		Main MainInstance = new Main();
		MainInstance.reset();
		
		input.close();
	}

	public void reset()
	{
		System.out.print("reset");
		totalAces = 0;
		totalPlayer = 0;
		totalDealerAces = 0;
		hasInsurance = false;
		DealerHasAce = false;
		totalDealerCards = 0;
		activeDealerCards = null;
	}
}