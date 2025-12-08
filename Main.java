
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
    int rounds;
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
    // Used in "intialDraw"
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
            // After intro
            System.out.println("Starting Game");
            // GAME LOOP
            while (true) {
                System.out.println("----------------------");
                MainInstance.reset();
                // Maybe something with case + start game
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
        // Ask for name and asks for game
        System.out.print("Enter your name (leave blank for default name): ");
        name = input.nextLine();
        System.out.println("");
        if (name.equals("")) {
            name = "The player";
            System.out.println("Hello, player. Are you up for a game of Blackjack?");
        }
        else {
            System.out.println("Hello, " + name + ". Are you up for a game of Blackjack?");
        }
        System.out.print("Type 'Y' for yes (type anything else for no): ");
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
        // Reset all variables
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
        // Create two deck, one for the suits (char) and numbers (int)
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
        // Chooses a number and matches it to both decks, put both into their shuffled deck variants
        // 2-10 are number cards while 11-14 are face cards with aces being 14
        shuffledSuitDeck = new ArrayList<>();
        shuffledNumberDeck = new ArrayList<>();
        Random random = new Random();
        for (int a = numberDeck.size() - 1; a >= 0; a--) {
            try {
                randomCard = random.nextInt(a);
            } catch (Exception e) {
                randomCard = 0;
            }
            // Adds the chosen card to both decks, then removes to avoid duplicates
            shuffledNumberDeck.add(numberDeck.get(randomCard));
            shuffledSuitDeck.add(suitDeck.get(randomCard));
            numberDeck.remove(randomCard);
            suitDeck.remove(randomCard);
        }
    }

    public void newRound(Scanner input)
    {
        // Prompt user for chips
        if (rounds == 0) {
            System.out.println("How many chips are you betting? You have: " + totalChips + " chips.");
        } else {
            System.out.println("How many chips are you betting? You have: " + totalChips + " chips. (Type '0' to cash out)");
        }
        do {
            try {
                inputInt = input.nextInt();
                if (inputInt > totalChips) {
                    System.out.println("ERROR: You do not have enough chips");
                } else if (inputInt < 0) {
                    System.out.println("ERROR: Please enter a positive integer");
                } else if (inputInt == 0) {
                    endGame();
                }
            } catch (Exception e) {
                System.out.println("ERROR: Please type a number");
                input.next();
            }
        } while ((0 >= inputInt) || (inputInt > totalChips));
        System.out.println("");
        rounds++;
        System.out.println("Round " + (rounds) + ":");
        intialDraw();
        // If the Player has enough chips and Dealer has an ace (insurance)
        if ((DealerHasAce) && ((int) (Math.floor(betChips / 2)) + betChips) <= totalChips) {
            betInsuranceChips = (int) (Math.floor(betChips / 2));
            dealerHasAce(input);
        }
        // If either Dealer or Player draws Blackjack (Ace + face card or 10)
        if ((totalDealer == 21) || (totalPlayer == 21)){
            HasBlackjack();
        }
        else {
            startRound(input);
        }
    }

    public void intialDraw(){
        // Draws two cards for the Dealer and Player
        betChips = inputInt;
        if (totalChips == betChips) {
            System.out.print("YOU GO ALL IN! ");
        }
        System.out.println("You bet " + inputInt);
        System.out.print("The Dealer draws: ");
		/*
		Used for testcases:
		shuffledNumberDeck.set(0, 5);
		shuffledNumberDeck.set(1, 14);
		*/
        dealerDrawCard();
        dealerDrawCard();
        System.out.println("");
        System.out.print(name + " draws: ");
        playerDrawCard();
        playerDrawCard();
        System.out.println("Total: " + totalPlayer);
    }

    public void dealerDrawCard(){
        // Abstract method with playerDrawCard?
        // "Draws the card and incrments how many cards are in the Dealer's hand"
        activeCardNumber = shuffledNumberDeck.get(0);
        totalDealerCards = totalDealerCards + 1;
        // If it's a number card add value to the total value of the dealer
        if (activeCardNumber < 11){
            totalDealer = (totalDealer + activeCardNumber);
        }
        else {
            // If it's an ace, and it's the dealer's second card
            if (activeCardNumber == 14) {
                if (totalDealerCards == 2){
                    DealerHasAce = true;
                }
                // Adds total value of ace to total value and if it goes over decrease value by 10
                totalDealer = (totalDealer + 11);
                if (21 < totalDealer) {
                    totalDealer = (totalDealer - 10);
                }
                // If ace didn't have to decrease value, log it for next time.
                else {
                    totalDealerAces = (totalDealerAces + 1);
                }
            }
            // All face cards are worth 10
            else {
                totalDealer = (totalDealer + 10);
            }
        }
        // If dealer is over 21 but has an ace that can decrease in value
        if ((21 < totalDealer) && (totalDealerAces > 0)){
            totalDealer = (totalDealer - 10);
            totalDealerAces = (totalDealerAces - 1);
        }
        // Hides the first card value from the player
        if (totalDealerCards == 1){
            System.out.print("XX ");
        }
        convertCardToString();
        printDrawCards(true);
        deleteCard();
    }

    public void playerDrawCard(){
        // Same is dealerDrawCard but no cards are hidden
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
        // Convert int to a String to be printed out
        if (activeCardNumber < 11) {
            activeCard = ("" + activeCardNumber);
        }
        else {
            activeCard = "" + finalFaceCardNames.charAt(activeCardNumber - 11);
        }
    }

    public void printDrawCards(boolean isDealer){
        // Prints the activeCard with the (Number) + (Suit) and a space at the end
        activeCard = (activeCard + shuffledSuitDeck.get(0) + " ");
        // Does not print if this is the Dealer's first card
        if ((!isDealer) || (1 != totalDealerCards)) {
            System.out.print(activeCard + "");
        }
        // Logs cards for later if Dealer
        if (isDealer){
            activeDealerCards = (activeDealerCards + activeCard);
        }
    }

    public void deleteCard(){
        // Remove card from both lists
        shuffledNumberDeck.remove(0);
        shuffledSuitDeck.remove(0);
    }

    public void HasBlackjack(){
        // Check if Player has Blackjack
        if (totalPlayer == 21) {
            System.out.println(name + " has Blackjack!");
        }
        dealerPlay();
        // Check if Dealer had Blackjack
        if (totalDealer == 21) {
            if (totalPlayer == 21) {
                System.out.println("The Dealer also had Blackjack!");
            }
            else {
                System.out.println("The Dealer has Blackjack!");
            }
        }
        // Check if both Dealer and Player have Blackjack
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
        // Checks if Dealer has Blackjack for outcome
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
        // Local Variable
        boolean isValid;
        do {
            // If you can Double Down (Needs to be first time asking and have enough to bet)
            if ((betChips * 2 <= totalChips) && (firstAction)) {
                System.out.println("Do you Hit (H), Stand (S), or Double Down (D)?");
            }
            else {
                System.out.println("Do you Hit (H) or Stand (S)?");
            }
            do {
                try {
                    inputChar = input.next().charAt(0);
                    inputChar = Character.toUpperCase(inputChar);
                } catch (Exception e) {
                    System.out.println("ERROR: Please type a valid letter");
                }
                // If it was a valid action
                if ((inputChar == 'H') || (inputChar == 'S') || ((inputChar == 'D') && (betChips * 2 <= totalChips) && (firstAction))) {
                    firstAction = false;
                    isValid = true;
                }
                else {
                    System.out.println("ERROR: Please type a valid letter");
                    isValid = false;
                }
            } while (!isValid);
            // Adds a space for readablity
            System.out.println("");
            checkHit();
            // If action ends round
        } while (!((inputChar == 'S') || (inputChar == 'D') || (21 < totalPlayer)));
        endRound();
    }

    public void checkHit(){
        // If Double Down
        if (inputChar == 'D') {
            betChips = betChips * 2;
            System.out.println("Double Down! Bet raised to " + (betChips));
            hit();
        }
        // If Hit
        if (inputChar == 'H') {
            hit();
        }
    }

    public void endRound(){
        // Could probably use a case statement here
        // If player busted
        if (totalPlayer > 21){
            loseBusted();
        }
        else {
            // Prints total value of player
            System.out.print("Stand Total: " + (totalPlayer));
            // Adds a '!' if exactly 21
            if (totalPlayer == 21) {
                System.out.println("!");
            }
            else {
                System.out.println("");
            }
            dealerPlay();
            // If Dealer busted
            if (21 < totalDealer) {
                winRound();
            }
            else {
                // If Dealer and Player tied
                if (totalDealer == totalPlayer) {
                    tieRound();
                }
                else {
                    // Check if Player is higher than Dealer, if yes then win
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
        // Prints Hit! and the new total of the Player
        System.out.print("Hit! ");
        playerDrawCard();
        System.out.println("Total " + totalPlayer);
    }

    public void dealerPlay(){
        // Dealer hits until obove 17 in total value
        System.out.println("The Dealer has " + activeDealerCards + " Total: " + totalDealer);
        while (totalDealer < 17) {
            System.out.print("The Dealer draws: ");
            dealerDrawCard();
            System.out.println("Total: " + totalDealer);
        }
    }

    public void loseBusted(){
        // Prints lose text on screen and decreases chips by amount bet
        System.out.println("Busted. " + totalPlayer + " is over 21");
        totalChips = totalChips - betChips;
        System.out.println("You lost " + betChips + " chips");
    }

    public void winRound(){
        // Prints win text and increases chips by amount bet, also accounts for if the Dealer busted instead comparing
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
        // Prints text if tied
        System.out.println("Stand-off. Both dealer and player had " + totalPlayer);
        System.out.println("No change");
    }

    public void loseRound(){
        // Prints lose text on screen, decreases chips by amount bet, and prints both values
        System.out.println("Loss. You had " + totalPlayer + " while the dealer had " + totalDealer);
        totalChips = totalChips - betChips;
        System.out.println("You lost " + betChips + " chips");
    }

    public void dealerHasAce(Scanner input){
        // Prompts user for if they want insurance
        System.out.println("The Dealer has an Ace, do you wish to buy insurance? (Y/N) (" + betInsuranceChips + " chips)");
        do {
            inputChar = input.next().charAt(0);
            inputChar = Character.toUpperCase(inputChar);
        } while (!((inputChar == 'Y') || (inputChar == 'N')));
        if (inputChar == 'Y') {
            // If the dealer has 21 sets the boolean true for bought insurance for later
            if (totalDealer == 21) {
                hasInsurance = (true);
            }
            // If bought insurance but Dealer did not have Blackjack
            else {
                // Lose amount bet for insurance and prints no insurance text
                totalChips = totalChips - betInsuranceChips;
                System.out.println("Dealer does NOT have Blackjack");
                System.out.println("You lost " + betInsuranceChips + " chips");
            }
        }
    }

    public void endGame() {
        // Ends the game, prints summary, and quits the program
        System.out.println("----------------------");
        if (rounds == 1) {
            System.out.println("You played 1 round");
        } else {
            System.out.println("You played " + rounds + " rounds");
        }
        System.out.println("You left with " + totalChips + " chips");
        if (totalChips < 100) {
            System.out.println("You lost " + (100 - totalChips) + " chips");
        } else if (totalChips == 100) {
            System.out.println("You did not gain nor lose any chips");
        } else {
            System.out.println("You won " + (totalChips - 100) + " chips!");
        }
        System.out.println("Thanks for playing! :)");
        System.exit(0);
    }
}