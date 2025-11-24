// It's in the debug menu
import java.util.*;
public class Main
{
	// Declare Variables
	// Final
	final String finalSuits = "♤♡♧♢";
	final String finalFaceCardNames = "JQKA";
	// Other
	int activeCardNumber;
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
	ArrayList<Integer> numberDeck;
	// Used in "shuffleDeck"
	int randomCard;
	// Lists used in "shuffleDeck"
	ArrayList<String> shuffledSuitDeck;
	ArrayList<Integer> shuffledNumberDeck;
	public static void main(String[] args) {
		int chips = 1000;
		System.out.println("Welcome! This is CISC-Black-Jack");
		Scanner input = new Scanner(System.in);
		/*

        // Ask for the user's name
        System.out.print("Enter your name: ");
        String name = input.nextLine();
        System.out.println("");
		System.out.println("Hello, " + name + ". Are you up for a game of Blackjack?");
		System.out.println("Type 'Y' for yes and 'N' for No");
		String answer = input.nextLine();
		// Should probably use case stuff
		if (answer.equals("Y")) {
			System.out.println("Yay!");
		}
		else {
			System.out.println("Aw man :(");
		}
		*/
		// stops
		System.out.println("Starting Game...");
		// GAME LOOP
		Main MainInstance = new Main();
		MainInstance.reset();
		MainInstance.newRound();
		System.out.println("");
		System.out.println("Finished");
		input.close();
	}

	public void reset()
	{
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
		suitDeck = new ArrayList<>();
		numberDeck = new ArrayList<>();
		createSuit = 1;
		for (int a = 0; a < 1; a++) {
			createCardNumber = 1;
			for (int b = 2; b <= 14; b++) {
			createCardNumber = createCardNumber++;
			numberDeck.add(b);
			suitDeck.add("" + finalSuits.charAt(a));
			}
		}
		shuffleDeck();

	}
	public void shuffleDeck(){
		shuffledSuitDeck = new ArrayList<>();
		shuffledNumberDeck = new ArrayList<>();
		Random random = new Random();
		for (int a = numberDeck.size() - 1; a >= 0; a--) {
			try {
				randomCard = random.nextInt(a);
			} catch (Exception e) {
				randomCard = 0;
			}
			shuffledNumberDeck.add(numberDeck.get(randomCard));
			shuffledSuitDeck.add(suitDeck.get(randomCard));
			numberDeck.remove(randomCard);
			suitDeck.remove(randomCard);
		}
	}
	public void newRound(){
		intialDraw();
		}
	public void intialDraw(){
		System.out.print("The Dealer draws: ");
		dealerDrawsCard();
		dealerDrawsCard();
		System.out.print("The Player draws: ");
		playerDrawsCard();
		playerDrawsCard();
		System.out.print("Total: " + totalPlayer);
	}
	public void dealerDrawsCard(){
		// Yo
	}
	public void playerDrawsCard(){
		// Add stuff pls? 
	}
}