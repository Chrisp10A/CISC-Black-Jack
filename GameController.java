import java.util.Scanner;

public class GameController {
    // Game state variables
    int totalAces;
    int totalPlayer;
    int totalDealer;
    int totalDealerAces;
    int totalDealerCards;
    boolean hasInsurance;
    boolean DealerHasAce;
    String activeDealerCards;
    int rounds = 0;
    boolean firstAction;
    String name;

    // Deck object replaces all the ArrayList stuff
    Deck deck;

    // Current card being processed
    Card currentCard;

    public void introForGame(Scanner input) {
        System.out.print("Enter your name (leave blank for default name): ");
        name = input.nextLine();
        System.out.println("");
        if (name.equals("")) {
            name = "The player";
            System.out.println("Hello, player. Are you up for a game of Blackjack?");
        } else {
            System.out.println("Hello, " + name + ". Are you up for a game of Blackjack?");
        }
        System.out.print("Type 'Y' for yes (type anything else for no): ");
        String answer = input.nextLine();
        if (answer.equals("Y") || answer.equals("y") || answer.equals("yes") || answer.equals("Yes")) {
            System.out.println("Ok!");
        } else {
            System.out.println("Aw man :(");
            System.out.println("Quitting game...");
            System.exit(0);
        }
    }

    public void reset() {
        totalAces = 0;
        totalPlayer = 0;
        totalDealer = 0;
        totalDealerAces = 0;
        hasInsurance = false;
        DealerHasAce = false;
        totalDealerCards = 0;
        activeDealerCards = "";
        firstAction = true;

        // Create new deck and shuffle it
        deck = new Deck();
        deck.shuffle();
    }

    public void intialDraw() {
        rounds++;
        System.out.println("Round " + rounds + ":");
        System.out.print("The Dealer draws: ");
        dealerDrawCard();
        dealerDrawCard();
        System.out.println("");
        System.out.print(name + " draws: ");
        playerDrawCard();
        playerDrawCard();
        System.out.println("Total: " + totalPlayer);
    }

    public void dealerDrawCard() {
        // Draw card from deck
        currentCard = deck.dealCard();
        int cardValue = currentCard.getValue();
        totalDealerCards++;

        // Card values: ACE=1, 2-10=face value, JACK=11, QUEEN=12, KING=13
        if (cardValue >= 2 && cardValue <= 10) {
            // Number cards 2-10
            totalDealer += cardValue;
        } else if (cardValue == Card.ACE) {
            // Ace
            if (totalDealerCards == 2) {
                DealerHasAce = true;
            }
            totalDealer += 11;
            if (totalDealer > 21) {
                totalDealer -= 10;
            } else {
                totalDealerAces++;
            }
        } else {
            // Jack, Queen, King (all worth 10)
            totalDealer += 10;
        }

        // Adjust for aces if busted
        if (totalDealer > 21 && totalDealerAces > 0) {
            totalDealer -= 10;
            totalDealerAces--;
        }

        // Hide first card
        if (totalDealerCards == 1) {
            System.out.print("XX ");
        }

        // Print the card (but not if it's the first card)
        if (totalDealerCards != 1) {
            System.out.print(currentCard + " ");
        }

        // Log for later reveal
        activeDealerCards += currentCard + " ";
    }

    public void playerDrawCard() {
        currentCard = deck.dealCard();
        int cardValue = currentCard.getValue();

        if (cardValue >= 2 && cardValue <= 10) {
            totalPlayer += cardValue;
        } else if (cardValue == Card.ACE) {
            totalPlayer += 11;
            if (totalPlayer > 21) {
                totalPlayer -= 10;
            } else {
                totalAces++;
            }
        } else {
            // Jack, Queen, King
            totalPlayer += 10;
        }

        // Adjust for aces if busted
        if (totalPlayer > 21 && totalAces > 0) {
            totalPlayer -= 10;
            totalAces--;
        }

        System.out.print(currentCard + " ");
    }

    public boolean checkHasBlackjack() {
        return (totalDealer == 21) || (totalPlayer == 21);
    }

    public void printIfPlayerHasBlackjack() {
        if (totalPlayer == 21) {
            System.out.println(name + " has Blackjack!");
        }
    }

    public void dealerPlay() {
        System.out.println("The Dealer has " + activeDealerCards + "Total: " + totalDealer);
        while (totalDealer < 17) {
            System.out.print("The Dealer draws: ");
            dealerDrawCard();
            System.out.println("Total: " + totalDealer);
        }
    }

    public void hit() {
        System.out.print("Hit! ");
        playerDrawCard();
        System.out.println("Total " + totalPlayer);
    }
}