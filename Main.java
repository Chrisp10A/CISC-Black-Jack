
// It's in the debug menu
import java.security.PublicKey;
import java.util.*;
public class Main
{
    // Declare Variables
    int rounds;
    // Used in "newRound"
    int inputInt;
    char inputChar;

    public static void main(String[] args){
        System.out.println("----------------------");
        System.out.println("Welcome! This is CISC-Black-Jack");
        try (Scanner input = new Scanner(System.in)) {
            // Ask for the user's name + make Instances
            GameController GameInstance = new GameController();
            Chips Chips = new Chips();
            Main MainInstance = new Main();
            // Bypass static >:D
            GameInstance.introForGame(input);
            // After intro
            System.out.println("Starting Game");
            // GAME LOOP
            while (true) {
                System.out.println("----------------------");
                GameInstance.reset();
                // Maybe something with case + start game
                MainInstance.newRound(input, Chips.getChips());
                GameController.intialDraw();
                if (Chips.checkLose()) {
                    System.out.println("Game Over! You ran out of chips");
                    System.exit(0);
                }
                if (Chips.checkWin()) {
                    System.out.println("No way! You have over 1000 chips! You win!!!");
                    System.exit(0);
                }
                System.out.println("Next round!");
            }
        }
    }


    public void newRound(Scanner input, int totalChips)
    {
        // Prompt user for chips
        System.out.println("wow" + totalChips);
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
        checkDealerHasAce();
        evaluateDraw();
    }

    public void checkDealerHasAce(boolean DealerHasAce, int betChips) {
        // If the Player has enough chips and Dealer has an ace (insurance)
        if ((DealerHasAce) && ((int) (Math.floor(betChips / 2)) + betChips) <= totalChips) {
            betInsuranceChips = (int) (Math.floor(betChips / 2));
            dealerHasAce(input);
        }
    }
    public void checkHasBlackjack(int totalDealer, int totalPlayer) {
        // If either Dealer or Player draws Blackjack (Ace + face card or 10)
        if ((totalDealer == 21) || (totalPlayer == 21)){
            HasBlackjack();
        }
        else {
            startRound(input);
        }
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