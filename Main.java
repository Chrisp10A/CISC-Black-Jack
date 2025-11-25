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
	boolean firstAction;
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
	// Used in "newRound"
	int totalChips = 100;
	int inputInt;
	int betChips;
	char inputChar;

	public static void main(String[] args){
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
		while (true) {
		MainInstance.reset();
		MainInstance.newRound();
		System.out.println("");
		if (MainInstance.checkLose()) {
			System.out.println("Game Over! You ran out of chips");
			System.exit(0);
		}
		System.out.println("Next round!");
		}
	}

	public boolean checkLose(){
		if (totalChips == 0) {
			return (true);
		}
		else {
			return (false);
		}
	}

	public void reset()
	{
		totalAces = 0;
		totalPlayer = 0;
		totalDealer = 0;
		totalDealerAces = 0;
		firstAction = true;
		hasInsurance = false;
		DealerHasAce = false;
		totalDealerCards = 0;
		activeDealerCards = "";
		createDeck();
	}
	
	public void createDeck(){
		suitDeck = new ArrayList<>();
		numberDeck = new ArrayList<>();
		createSuit = 1;
		for (int a = 0; a < 4; a++) {
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
		Scanner input = new Scanner(System.in);
		System.out.println("");
		System.out.println("How many chips are you betting? You have: " + totalChips + " chips");
		do { 
			inputInt = input.nextInt();
		} while ((0 > inputInt) || (inputInt > totalChips));
		
		intialDraw();
		if ((totalDealer == 21) || (totalPlayer == 21)){
			HasBlackjack();
		}
		else {
			startRound();
		}
		}
	
	public void intialDraw(){
		System.out.println("You bet " + inputInt);
		betChips = inputInt;
		System.out.print("The Dealer draws: ");
		dealerDrawCard();
		dealerDrawCard();
		System.out.println("");
		System.out.print("The Player draws: ");
		playerDrawCard();
		playerDrawCard();
		System.out.print("Total: " + totalPlayer);
	}

	public void dealerDrawCard(){
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
		if ((21 < totalDealer) && (totalDealerAces > 0)){
			totalDealer = (totalDealer - 10);
			totalDealerAces = (totalDealerAces - 1);
		}
		if (totalDealerCards == 1){
			System.out.print("XX ");
		}
		convertCardToString();
		printDrawCards(true);
		deleteCard();
	}

	public void playerDrawCard(){
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
		if ((21 < totalPlayer) && (totalAces > 0)) {
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
			activeCard = "" + finalFaceCardNames.charAt(activeCardNumber - 11);
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

	public void HasBlackjack(){
		if (totalPlayer == 21) {
			System.out.println("The Player has Blackjack!");
		}
		dealerPlay();
		if (totalDealer == 21) {
			if (totalPlayer == 21) {
				System.out.println("The Dealer also had Blackjack!");
			}
			else {
				System.out.println("The Dealer has Blackjack!");
			}			
			if ((totalDealer == 21) && (totalPlayer == 21)) {
				System.out.println("Stand-off. Both Player and Dealer hava a total of 21.");
				System.out.println("No change");
			}
			else {
				if (totalDealer == 21) {
					totalChips = totalChips - betChips;
					System.out.print("You lost " + betChips + " chips");
				}
				else {
					System.out.print("Win! You had " + totalPlayer + " while the dealer had " + totalDealer);
					System.out.print("You won " + betChips + " chips and " + (Math.floor((betChips / 2))) + " bonus chips for getting Blackjack");
				}
			}

		}
	}

	public void startRound(){
		Scanner input = new Scanner(System.in);
		do { 
			System.out.println("");
			if ((betChips * 2 <= totalChips) && (firstAction)) {
				System.out.println("Do you Hit (H), Stand (S), or Double Down (D)?");
				firstAction = false;
			}
			else {
				System.out.println("Do you Hit (H) or Stand (S)?");
			}
			do { 
				inputChar = input.next().charAt(0);
			} while (!((inputChar == 'H') || (inputChar == 'S') || ((inputChar == 'D') && (betChips * 2 <= totalChips))));
			checkHit();
		} while (!((inputChar == 'S') || (inputChar == 'D') || (21 < totalPlayer)));
		endRound();
	}

	public void checkHit(){
		if (inputChar == 'D') {
			betChips = betChips * 2;
			System.out.println("Double Down! Bet raised to " + (betChips));
			hit();
		}
		if (inputChar == 'H') {
			hit();
		}
	}

	public void endRound(){
		// Could probably use a case statement here, maybe ._.
		if (totalPlayer > 21){
			loseBusted();
		}
		else {
			System.out.println("");
			System.out.print("Stand Total: " + (totalPlayer));
			if (totalPlayer == 21) {
				System.out.println("!");
			}
			else {
				System.out.println("");
			}
			dealerPlay();
			if (21 < totalDealer) {
				winRound();
			}
			else {
				if (totalDealer == totalPlayer) {
					tieRound();
				}
				else {
					if (totalDealer < totalPlayer) {
						winRound();
					}
					else {
						loseRound();
					}
				}
			}
		}
	}

	public void hit(){
		System.out.println("Hit! ");
		playerDrawCard();
		System.out.println("Total " + totalPlayer);
	}

	public void dealerPlay(){
		System.out.println("");
		System.out.println("The Dealer has " + activeDealerCards + " Total: " + totalDealer);
		while (totalDealer < 17) {
			System.out.println("");
			System.out.println("The Dealer draws: ");
			dealerDrawCard();
			System.out.print("Total: " + totalDealer);
		}
	}

	public void loseBusted(){
		System.out.println("");
		System.out.println("Busted. " + totalPlayer + " is over 21");
		totalChips = totalChips - betChips;
		System.out.println("You lost " + betChips + " chips");
	}

	public void winRound(){
		totalChips = totalChips + betChips;
		System.out.println("");
		if (21 < totalDealer) {
			System.out.println("Busted " + totalDealer + " is over 21.");
			System.out.println("Win! You had " + totalPlayer + " while the Dealer busted!");
		}
		else {
			System.out.println("Win! You had " + totalPlayer + " while the dealer had " + totalDealer);
		}
		System.out.println("You won " + betChips + " chips");
	}

	public void tieRound(){
		System.out.println("Tie! Both dealer and player had " + totalPlayer);
		System.out.println("No change");
	}

	public void loseRound(){
		System.out.println("");
		System.out.println("Loss. You had " + totalPlayer + " while the dealer had " + totalDealer);
		totalChips = totalChips - betChips;
		System.out.println("You lost " + betChips + " chips");
	}
}