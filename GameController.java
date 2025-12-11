// Handles the game rules!
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameController {

    // Final
    final String finalSuits = "♤♡♧♢";
    final String finalFaceCardNames = "JQKA";
    // Reset
    int totalAces;
    int totalPlayer;
    int totalDealer;
    int totalDealerAces;
    int totalDealerCards;
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

    public void GameController() {
        System.out.println("I'm alive!");
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
        // Todo: Use try-catch also 
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

    public void reset()
    {
        // Reset all variables
        totalAces = 0;
        totalPlayer = 0;
        totalDealer = 0;
        totalDealerAces = 0;
        hasInsurance = false;
        DealerHasAce = false;
        totalDealerCards = 0;
        activeDealerCards = "";
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

    public void intialDraw(){
        // Draws two cards for the Dealer and Player
        System.out.print("The Dealer draws: ");
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

    public boolean checkHasBlackjack() {
        // If either Dealer or Player draws Blackjack (Ace + face card or 10)
        if ((totalDealer == 21) || (totalPlayer == 21)){
            return (true);
        }
        else {
            return (false);
        }
    }

    public void printIfPlayerHasBlackjack() {
        // Check if Player has Blackjack
        if (totalPlayer == 21) {
            System.out.println(name + " has Blackjack!");
        }
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

}
