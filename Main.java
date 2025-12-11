
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

    public boolean checkBusted(int totalPlayer)
    {
        return (totalPlayer > 21);
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
                        do {
                        MainInstance.startRound(input, Chips.betChips, Chips.totalChips, GameController.totalPlayer);
                        if (MainInstance.checkDoubleDown()) {
                            Chips.doubleDown();
                        }
                        if (MainInstance.checkHit()) {
                            GameController.hit();
                        }
                        } while (MainInstance.checkRoundExitConditions(GameController.totalPlayer));
                        if (MainInstance.checkBusted(GameController.totalPlayer)) {
                        Chips.loseBusted(GameController.totalPlayer);
                        }
                        GameController.dealerPlay();
                        Chips.endResult(GameController.totalPlayer, GameController.totalDealer);
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

    public void endGame(int totalChips) {
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