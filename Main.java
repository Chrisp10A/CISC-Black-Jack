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
	int totalDealerCards;
	boolean firstAction;
	boolean hasInsurance;
	boolean DealerHasAce;
	String activeDealerCards;
	// Used in "createDeck"
	int createCardNumber;
	// Lists used in "createDeck"
	ArrayList<String> suitDeck;
	ArrayList<Integer> numberDeck;
	// Used in "shuffleDeck"
	int randomCard;
	// Lists used in "shuffleDeck"
	ArrayList<String> shuffledSuitDeck;
	ArrayList<Integer> shuffledNumberDeck;
	// Used in "intitalDraw"
	String name;
	// Used in "playerDrawCard" and "dealerDrawsCard"
	int activeCardNumber;
	String activeCard;
	// Used in "newRound"
	int totalChips = 100;
	int inputInt;
	int betChips;
	int betInsuranceChips;
	char inputChar;

	public static void main(String[] args){
		System.out.println("----------------------");
		System.out.println("Welcome! This is CISC-Black-Jack");
		try (Scanner input = new Scanner(System.in)) {
			// Ask for the user's name
			Main MainInstance = new Main();
			// Bypass static >:D
			MainInstance.introForGame(input);	
      		// stops
			System.out.println("Starting Game");
			// GAME LOOP
			while (true) {
			System.out.println("----------------------");
			MainInstance.reset();
			// Maybe something with case
			MainInstance.newRound(input);
			if (MainInstance.checkLose()) {
				System.out.println("Game Over! You ran out of chips");
				System.exit(0);
			}
			if (MainInstance.checkWin()) {
				System.out.println("No way! You have over 1000 chips! You win!!!");
				System.exit(0);
			}
			System.out.println("Next round!");
			}
		}
	}

	public void introForGame(Scanner input) {
		System.out.print("Enter your name: ");
		name = input.nextLine();
		System.out.println("");
		if (name.equals("")) {
			name = "The player";
			System.out.println("Hello, player. Are you up for a game of Blackjack?");
		}
		else {
			System.out.println("Hello, " + name + ". Are you up for a game of Blackjack?");
		}
		System.out.print("Type 'Y' for yes: ");
		String answer = input.nextLine();
		if (answer.equals("Y") || answer.equals("y") || answer.equals("yes") || answer.equals("Yes")) {
			System.out.println("Ok!");
		}
		else {
			System.out.println("Aw man :(");
			System.out.println("Quitting game...");
			System.exit(0);
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

	public boolean checkWin(){
		if (totalChips >= 1000) {
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
		inputInt = 0;
		inputChar = 0;
		createDeck();
	}
	
	public void createDeck(){
		suitDeck = new ArrayList<>();
		numberDeck = new ArrayList<>();
		for (int createSuit = 0; createSuit < 4; createSuit++) {
			createCardNumber = 1;
			for (int b = 2; b <= 14; b++) {
			createCardNumber = createCardNumber++;
			numberDeck.add(b);
			suitDeck.add("" + finalSuits.charAt(createSuit));
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

	public void newRound(Scanner input)
	{
			System.out.println("How many chips are you betting? You have: " + totalChips + " chips");
			do { 
				try {
					inputInt = input.nextInt();
				} catch (Exception e) {
					System.out.println("ERROR: Please type a number");
					input.next();
				}
			} while ((0 >= inputInt) || (inputInt > totalChips));
		System.out.println("");
		intialDraw();
		// Insurance be like
		if ((DealerHasAce) && ((int) (Math.floor(betChips / 2)) + betChips) <= totalChips) {
			betInsuranceChips = (int) (Math.floor(betChips / 2));
			dealerHasAce(input);
		}
		if ((totalDealer == 21) || (totalPlayer == 21)){
			HasBlackjack();
		}
		else {
			startRound(input);
		}
	}
	
	public void intialDraw(){
		betChips = inputInt;
		if (totalChips == betChips) {
			System.out.print("YOU GO ALL IN! ");
		}
		System.out.println("You bet " + inputInt);
		System.out.print("The Dealer draws: ");
		/*
		shuffledNumberDeck.set(0, 14);
		shuffledNumberDeck.set(1, 10);
		*/
		dealerDrawCard();
		dealerDrawCard();
		System.out.println("");
		System.out.print(name + " draws: ");
		/*
		shuffledNumberDeck.set(0, 14);
		shuffledNumberDeck.set(1, 10);
		*/
		playerDrawCard();
		playerDrawCard();
		System.out.println("Total: " + totalPlayer);
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
			System.out.print(activeCard + "");
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
			System.out.println(name + " has Blackjack!");
		}
		dealerPlay();
		if (totalDealer == 21) {
			if (totalPlayer == 21) {
				System.out.println("The Dealer also had Blackjack!");
			}
			else {
				System.out.println("The Dealer has Blackjack!");
			}	
		}		
		if ((totalDealer == 21) && (totalPlayer == 21)) {
			System.out.println("Stand-off. Both Player and Dealer hava a total of 21.");
			if (hasInsurance) {
				System.out.println("Insurance! You won " + betInsuranceChips + " chips");
				totalChips = totalChips + betInsuranceChips;
			}
			else {
			System.out.println("No change");
			}
		}
		else {
			if (totalDealer == 21) {
				totalChips = totalChips - betChips;
				System.out.println("You lost " + betChips + " chips");
				if (hasInsurance) {
					totalChips = totalChips + betInsuranceChips * 2;
					System.out.println("Insurance! You won back your " + betInsuranceChips + " chips");
					}
				}
				else {
					totalChips = totalChips + betChips + (int) ((Math.floor(betChips / 2)));
					System.out.println("Win! You had " + totalPlayer + " while the dealer had " + totalDealer);
					System.out.println("You won " + betChips + " chips and " + (Math.round(Math.floor((betChips / 2)))) + " bonus chips for getting Blackjack");
				}
			}
	}

	public void startRound(Scanner input)
	{
		boolean isValid;
		do { 
				if ((betChips * 2 <= totalChips) && (firstAction)) {
					System.out.println("Do you Hit (H), Stand (S), or Double Down (D)?");
				}  
				else {
					System.out.println("Do you Hit (H) or Stand (S)?");
				}
				do { 
					try {
						inputChar = input.next().charAt(0);
					} catch (Exception e) {
						System.out.println("ERROR: Please type a valid letter");
					}
					if ((inputChar == 'H') || (inputChar == 'S') || ((inputChar == 'D') && (betChips * 2 <= totalChips) && (firstAction))) {
						firstAction = false;
						isValid = true;
					}
					else {
						System.out.println("ERROR: Please type a valid letter");
						isValid = false;
					}
				} while (!isValid);
				System.out.println("");
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
		System.out.print("Hit! ");
		playerDrawCard();
		System.out.println("Total " + totalPlayer);
	}

	public void dealerPlay(){
		System.out.println("The Dealer has " + activeDealerCards + " Total: " + totalDealer);
		while (totalDealer < 17) {
			System.out.print("The Dealer draws: ");
			dealerDrawCard();
			System.out.println("Total: " + totalDealer);
		}
	}

	public void loseBusted(){
		System.out.println("Busted. " + totalPlayer + " is over 21");
		totalChips = totalChips - betChips;
		System.out.println("You lost " + betChips + " chips");
	}

	public void winRound(){
		totalChips = totalChips + betChips;
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
		System.out.println("Loss. You had " + totalPlayer + " while the dealer had " + totalDealer);
		totalChips = totalChips - betChips;
		System.out.println("You lost " + betChips + " chips");
	}

	public void dealerHasAce(Scanner input){
		System.out.println("The Dealer has an Ace, do you wish to buy insurance? (Y/N) (" + betInsuranceChips + " chips)");
		do { 
				inputChar = input.next().charAt(0);
			} while (!((inputChar == 'Y') || (inputChar == 'N')));
		if (inputChar == 'Y') {
			if (totalDealer == 21) {
				hasInsurance = (true);
			}
			else {
				totalChips = totalChips - betInsuranceChips;
				System.out.println("Dealer does NOT have Blackjack");
				System.out.println("You lost " + betInsuranceChips + " chips");
			}
		}
	}
}