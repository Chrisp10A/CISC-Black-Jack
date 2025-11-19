// It's in the debug menu
import java.util.*;
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
	// Used in "createDeck"
	int createSuit;
	int createCardNumber;
	// Lists used in "createDeck"
	ArrayList<String> suitDeck;
	ArrayList numberDeck;
	// Used in "shuffleDeck"
	int randomCard;
	// Lists used in "shuffleDeck"
	ArrayList<String> shuffledSuitDeck;
	ArrayList shuffledNumberDeck;

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
		createDeck();
	}
	
	public void createDeck() {
		suitDeck = new ArrayList<String>();
		numberDeck = new ArrayList();
		createSuit = 1;
		for (int a = 0; a < 4; a++) {
			createCardNumber = 1;
			for (int b = 0; b < 13; b++) {
			createCardNumber = createCardNumber++;
			numberDeck.add(b);
			suitDeck.add("" + finalSuits.charAt(a));
			}
		}
		shuffleDeck();

	}
	public void shuffleDeck(){
		Random random = new Random();
		for (int a = numberDeck.size(); a == 0; a--) {
			randomCard = random.nextInt(a);
			// Add more later :P
		}
	}
}