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
	ArrayList<Integer> numberDeck;
	// Used in "shuffleDeck"
	int randomCard;
	// Lists used in "shuffleDeck"
	ArrayList<String> shuffledSuitDeck;
	ArrayList<Integer> shuffledNumberDeck;
	// Used in "playerDrawCard" and "dealerDrawsCard"
	int activeCardNumber;
	String activeCard;

	public static void main(String[] args){
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
	
	public void createDeck(){
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
		System.out.println("");
		System.out.print("The Player draws: ");
		playerDrawsCard();
		playerDrawsCard();
		System.out.print("Total: " + totalPlayer);
	}

	public void dealerDrawsCard(){
		// Yo, pls make this an abstract method maybe?
		activeCardNumber = shuffledNumberDeck.get(0);
		totalDealerCards = totalDealerCards + 1;
		if (activeCardNumber < 11){
			totalDealer = (totalDealer + activeCardNumber);
		}
		else {
			if (activeCardNumber == 14) {
				if (totalDealerCards == 2){
					DealerHasAce = true;
				}
				totalDealer = (totalDealer + 11);
				if (21 < totalDealer) {
					totalDealer = (totalDealer - 10);
				}
				else {
					totalDealerAces = (totalDealerAces + 1);
				}
			}
			else {
				totalDealer = (totalDealer + 10);
			}
		}
		if ((21 < totalDealer) && (totalAces < 0)){
			totalPlayer = (totalPlayer - 10);
			totalAces = (totalAces - 1);
		}
		if (totalDealerCards == 1){
			System.out.print("XX ");
		}
		convertCardToString();
		printDrawCards(true);
		deleteCard();
	}

	public void playerDrawsCard(){
		activeCardNumber = shuffledNumberDeck.get(0);
		if (activeCardNumber < 11){
			totalPlayer = (totalPlayer + activeCardNumber);
		}
		else {
			if (activeCardNumber == 14) {
				totalPlayer = (totalPlayer + 11);
				if (21 < totalPlayer) {
					totalPlayer = (totalPlayer - 10);
				}
				else {
					totalAces = (totalAces + 1);
				}
			}
			else {
				totalPlayer = (totalPlayer + 10);
			}
		}
		if ((21 < totalPlayer) && (totalAces < 0)) {
			totalPlayer = (totalPlayer - 10);
			totalAces = (totalAces - 1);
		}
		convertCardToString();
		printDrawCards(false);
		deleteCard();
	}

	public void convertCardToString(){
		if (activeCardNumber < 11) {
			activeCard = ("" + activeCardNumber);
		}
		else {
			activeCard = "" + finalFaceCardNames.charAt(activeCardNumber - 10);
		}
	}

	public void printDrawCards(boolean isDealer){
		activeCard = ("" + activeCard + shuffledSuitDeck.get(0) + " ");
		if ((!isDealer) || (1 != totalDealerCards)) {
			System.out.print(activeCard);
		}
		if (isDealer){
			activeDealerCards = (activeDealerCards + activeCard);
		}
	}

	public void deleteCard(){
		shuffledNumberDeck.remove(0);
		shuffledSuitDeck.remove(0);
	}
}