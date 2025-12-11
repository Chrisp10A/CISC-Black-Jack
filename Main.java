
// It's in the debug menu
import java.util.*;
public class Main
{
    // Declare Variables
    int rounds;
    // Used in "newRound"
    int inputInt;
    boolean firstAction;
    char inputChar;

    public boolean checkRoundExitConditions(int totalPlayer)
    {
        return !((inputChar == 'S') || (inputChar == 'D') || (21 < totalPlayer));
    }

    public boolean checkHit()
    {
        return ((inputChar == 'D') || (inputChar == 'H'));
    }

    public boolean checkDoubleDown()
    {
        return (inputChar == 'D');
    }

    public static void main(String[] args){
        System.out.println("----------------------");
        System.out.println("Welcome! This is CISC-Black-Jack");
        try (Scanner input = new Scanner(System.in)) {
            // Ask for the user's name + make Instances
            GameController GameController = new GameController();
            Chips Chips = new Chips();
            Main MainInstance = new Main();
            // Bypass static >:D
            GameController.introForGame(input);
            // After intro
            System.out.println("Starting Game");
            // GAME LOOP
            while (true) {
                System.out.println("----------------------");
                GameController.reset();
                // Reset variables in main
                MainInstance.resetText();
                // Maybe something with case + start game
                MainInstance.newRound(input, Chips.getChips());
                Chips.printBet(MainInstance.inputInt);
                GameController.intialDraw();
                if (Chips.checkDealerHasAce(GameController.DealerHasAce)) {
                    Chips.dealerHasAce(input, GameController.totalDealer, GameController.hasInsurance);
                }
                else {
                    if (GameController.checkHasBlackjack()) {
                        GameController.printIfPlayerHasBlackjack();
                        GameController.dealerPlay();
                        Chips.HasBlackjack(GameController.totalDealer, GameController.totalDealer, GameController.name, GameController.hasInsurance);
                    }
                    else {
                        // Will fix later
                        do {
                        MainInstance.startRound(input, Chips.betChips, Chips.totalChips, GameController.totalPlayer);
                        if (MainInstance.checkDoubleDown()) {
                            Chips.doubleDown();
                        }
                        if (MainInstance.checkHit()) {
                            GameController.hit();
                        }
                        } while (MainInstance.checkRoundExitConditions(GameController.totalPlayer));
                        // endRound();
                    }
                }
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

    public void resetText() {
        inputInt = 0;
        firstAction = true;
        inputChar = 0;
    }

    public void newRound(Scanner input, int totalChips)
    {
        // Prompt user for chips
        System.out.println(totalChips);
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
                    // Will add back once all finished
                    //endGame();
                }
            } catch (Exception e) {
                System.out.println("ERROR: Please type a number");
                input.next();
            }
        } while ((0 >= inputInt) || (inputInt > totalChips));
        System.out.println("");
        rounds++;
        System.out.println("Round " + (rounds) + ":");
    }


    public void startRound(Scanner input, int betChips, int totalChips, int totalPlayer)
    {
        // Local Variable
        inputChar = 0;
        boolean isValid;
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
    }
    /* 
    

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
        */
}